package com.xbwl.common.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;

import jcifs.smb.NtlmPasswordAuthentication;
import jcifs.smb.SmbFile;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.util.Assert;

import com.xbwl.common.web.struts2.Struts2Utils;

public class SysFileUtils {
	
	static String imgFormat=Configure.getSingleton().values("picture.format");
	
	static String filePath=Configure.getSingleton().values("file.virtualpath");
	
	/**bathpath:上一级目录，filename:文件名字
	 *@author:zhaowenqiang
	 *@date:2010-7-27
	 * @param basePath
	 * @param filename
	 * @return String ：处理过的文件名称（不带路劲）
	 */
	public static String makeFile(String basePath) {
		
		String path=basePath+ File.separator +new SimpleDateFormat("yyyyMMdd").format(new Date());
		
		File dir = new File(path);
		
		if (!dir.exists()) {
			if (!dir.mkdirs()) {
				throw new IllegalStateException("创建目录失败:" + dir.getPath());
			}
		}
		
		
		return  path;
	}
	
	/**
	 * 在项目根目录下创建指定层级的目录
	 * @author yab
	 */
	public static String makeDir(String basePath,ServletContext context) throws Exception {
		String path = context.getRealPath("/");
		path += basePath;
		
		File dir = new File(path);
		if (null != dir && !dir.exists()) {
			if (!dir.mkdirs()) {
				throw new IllegalStateException("创建目录失败:" + dir.getPath());
			}
		}
		
		return path;
	}
	
	public static void smbMakeDir(String smbBasePath) throws Exception {
		Assert.notNull(smbBasePath,"smbBasePath不能为空！");
		
		String domain = Configure.getSingleton().values(Constants.SMB_DOMAIN);
		String username = Configure.getSingleton().values(Constants.SMB_USERNAME);
		String password = Configure.getSingleton().values(Constants.SMB_PASSWORD);
		String smbPath = Configure.getSingleton().values(Constants.SMB_PATH);
		
		smbPath += smbBasePath;
		
		NtlmPasswordAuthentication nt = new NtlmPasswordAuthentication(domain,username,password);
		
		SmbFileUtils.mkdirs(smbPath, nt);
	}
	
	/**
	 * 上传文件
	 * @author yab
	 */
	public static File uploadFileByName(String basePath,ServletContext context,String fileName,File uploadFile) throws Exception{
		
		String path = makeDir(basePath,context);
		
		File file=new File(path,makeFileName(fileName,uploadFile));
		
		FileUtils.copyFile(uploadFile,file);
		
		return file;
	}
	
	public static String uploadFileByName(String contentPath,String fileName,File uploadFile) throws Exception{
		
		String fname = makeFileName(fileName,uploadFile);
		fname = contentPath + fname;
		
		
		String smbPath = Configure.getSingleton().values(Constants.SMB_PATH);
		String domain = Configure.getSingleton().values(Constants.SMB_DOMAIN);
		String username = Configure.getSingleton().values(Constants.SMB_USERNAME);
		String password = Configure.getSingleton().values(Constants.SMB_PASSWORD);
		
		smbPath += fname;
		SmbFileUtils.smbFileWrite(new FileInputStream(uploadFile), smbPath,domain,username,password);

		return fname;
	}
	
	public static String uploadFileByName(String contentPath,String fileName,InputStream uploadInputStream) throws Exception{
		
		String smbPath = Configure.getSingleton().values(Constants.SMB_PATH);
		String domain = Configure.getSingleton().values(Constants.SMB_DOMAIN);
		String username = Configure.getSingleton().values(Constants.SMB_USERNAME);
		String password = Configure.getSingleton().values(Constants.SMB_PASSWORD);
		
		contentPath += fileName;
		smbPath += contentPath;
		SmbFileUtils.smbFileWrite(uploadInputStream, smbPath,domain,username,password);

		return contentPath;
	}
	
	public static void deleteFile(String contentPath) throws Exception {
		
		Assert.notNull(contentPath,"contentPath不能为空！");
		
		String domain = Configure.getSingleton().values(Constants.SMB_DOMAIN);
		String username = Configure.getSingleton().values(Constants.SMB_USERNAME);
		String password = Configure.getSingleton().values(Constants.SMB_PASSWORD);
		String smbPath = Configure.getSingleton().values(Constants.SMB_PATH);
		
		smbPath += contentPath;
		SmbFileUtils.smbFileDelete(smbPath, domain, username, password);
	}
	
	public static InputStream smbFileRead(String contentPath) throws Exception{
		Assert.notNull(contentPath,"contentPath不能为空！");
		
		String domain = Configure.getSingleton().values(Constants.SMB_DOMAIN);
		String username = Configure.getSingleton().values(Constants.SMB_USERNAME);
		String password = Configure.getSingleton().values(Constants.SMB_PASSWORD);
		String smbPath = Configure.getSingleton().values(Constants.SMB_PATH);
		smbPath += contentPath;
		
		return SmbFileUtils.smbFileRead(smbPath, domain, username, password);
	}
	
	public static boolean checkFilePath(String contentPath) throws Exception{
		Assert.notNull(contentPath,"contentPath不能为空！");
		
		String domain = Configure.getSingleton().values(Constants.SMB_DOMAIN);
		String username = Configure.getSingleton().values(Constants.SMB_USERNAME);
		String password = Configure.getSingleton().values(Constants.SMB_PASSWORD);
		String smbPath = Configure.getSingleton().values(Constants.SMB_PATH);
		smbPath += contentPath;
		
		NtlmPasswordAuthentication nt = new NtlmPasswordAuthentication(domain,username,password);
		
		SmbFile smbFile = new SmbFile(smbPath,nt);
		
		if(null == smbFile || !smbFile.exists()){
			return false;
		}
		
		return true;
	}
	
	/**生成文件名称
	 *@author:zhaowenqiang
	 *@date:2010-8-9
	 * @param fileName
	 * @param file
	 * @return
	 * @throws IOException 
	 */
	public static String makeFileName(String fileName,File file) throws Exception{
		
		StringBuffer imgNameBuffer=new StringBuffer(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())+"_");
		
		imgNameBuffer.append(RandomStringUtils.randomAlphanumeric(3)+"_");
		
		String extension = FilenameUtils.getExtension(fileName);
		
		FileInputStream fis = new FileInputStream(file);
		
		BufferedImage bufferedImg = ImageIO.read(fis); 
		
		if(null != bufferedImg) {
			imgNameBuffer.append(bufferedImg.getWidth()+"x"+bufferedImg.getHeight());
		}
		
		return imgNameBuffer.toString()+"."+extension;
	
	}
	
	/**生成文件名称
	 *@author:zhaowenqiang
	 *@date:2010-8-9
	 * @param fileName
	 * @param file
	 * @return
	 * @throws IOException 
	 */
	public static String makeFileNameAddDno(String dno,String fileName,File file) throws Exception{
		
		StringBuffer imgNameBuffer=new StringBuffer(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())+"_");
		
		imgNameBuffer.append(RandomStringUtils.randomAlphanumeric(3)+"_");
		
		String extension = FilenameUtils.getExtension(fileName);
		
		FileInputStream fis = new FileInputStream(file);
		
		BufferedImage bufferedImg = ImageIO.read(fis); 
		
		if(null != bufferedImg) {
			imgNameBuffer.append(bufferedImg.getWidth()+"x"+bufferedImg.getHeight());
		}
		
		return dno+"_"+imgNameBuffer.toString()+"."+extension;
	
	}
	
	/**生成文件名称
	 *@date:2010-8-9
	 * @param fileName
	 * @param file
	 * @return
	 * @throws IOException 
	 */
	public static String makeFileNameAddDno(String fileName, File file)
			throws Exception {

		StringBuffer imgNameBuffer = new StringBuffer(new SimpleDateFormat(
				"yyyyMMddHHmmss").format(new Date())
				+ "_");

		imgNameBuffer.append(RandomStringUtils.randomAlphanumeric(3) + "_");
		String extension = FilenameUtils.getExtension(fileName);
		FileInputStream fis = new FileInputStream(file);
		BufferedImage bufferedImg = ImageIO.read(fis);

		if (null != bufferedImg) {
			imgNameBuffer.append(bufferedImg.getWidth() + "x"
					+ bufferedImg.getHeight());
		}

		return imgNameBuffer.toString() + "." + extension;
	}
	
	
	/**保存图片文件，返回要文件名（路劲去掉前缀）
	 *@author:zhaowenqiang
	 *@date:2010-8-9
	 * @return
	 * @throws IOException 
	 */
	public static File makeImgFileByName(String basePath,String fileName,File file) throws Exception{
		
		File imgFile=new File(SysFileUtils.makeFile(basePath),SysFileUtils.makeFileName(fileName,file));
		
		FileUtils.copyFile(file, imgFile);
		
		return imgFile;
		
	}
	
	/**
	 *获取要求保存到数据库中的路径
	 *@author:zhaowenqiang
	 *@date:2010-8-9
	 *@param file
	 */
	public static String getRelativePath(File file){
		
		return StringUtils.substringAfter(StringUtils.substringAfter(file.toURI().getPath(), "/"),"/");
	}

	public static String getFilePath() {
		return filePath;
	}
	
	public static String getSavePath(String fieldName,Object ob){
		if(ob!=null && fieldName!=null){
			return Configure.getSingleton().values(ob.getClass().getName()+"."+fieldName);
		}else{
			return "";
		}
	}
	
	public static String uploadFile(String fileName,File file) throws Exception {
		Assert.notNull(fileName,"上传文件名称不能为空！");
		Assert.notNull(file,"上传文件不能为空！");
		String appPath = Struts2Utils.getSession().getServletContext().getRealPath("/");
		
		String basePath = "/userupload";
		
		try{
			basePath = Configure.getSingleton().values(Constants.UPLOAD_PATH);
		}catch(Exception e){
		} 
		
		makeDir(basePath,Struts2Utils.getSession().getServletContext());
		
		String savePath = appPath + basePath;
		
		String saveFileName = makeFileName(fileName,file);
		
		File saveFile = new File(savePath,saveFileName);
		
		FileUtils.copyFile(file, saveFile);
		
		return basePath + "/" + saveFile.getName();
	}
	
}
