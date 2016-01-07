package com.xbwl.common.utils;

import java.io.InputStream;
import java.io.OutputStream;

import jcifs.smb.NtlmPasswordAuthentication;
import jcifs.smb.SmbFile;
import jcifs.smb.SmbFileOutputStream;

import org.springframework.util.Assert;

import com.xbwl.common.exception.ServiceException;


/**
 * 访问局域网共享工具类
 * @author yab
 *
 */
public class SmbFileUtils {
	
	//递归创建目录
	public static void mkdirs(String smbBasePath,NtlmPasswordAuthentication ntAuth) throws Exception{
		Assert.notNull(smbBasePath,"smbBasePath不能为空！");
		Assert.notNull(ntAuth,"ntAuth不能为空！");
		
		SmbFile smbFile = new SmbFile(smbBasePath,ntAuth);
		
		if(!smbFile.exists()) {
			smbFile.mkdirs();
		}
	}
	
	public static void mkdirs(SmbFile smbFile) throws Exception {
		Assert.notNull(smbFile,"smbFile不能为空！");
		
		if(!smbFile.exists()) {
			smbFile.mkdirs();
		}
	}
	
	public static void write(InputStream input,OutputStream output) throws Exception {
		// REVIEW-ACCEPT 传入参数需要做非空判断
		// REVIEW-ACCEPT 定义变量不需要放入try块 
		//FIXED
		 byte[] buffer = new byte[1024];
		 int len = -1;
		 
		 Assert.notNull(input);
		 Assert.notNull(output);
		 
		 try {
			// REVIEW-ACCEPT 读取长度为0的时候也是不需要写入的 
			 //FIXED
			 while((len=input.read(buffer)) != -1) {
				 output.write(buffer,0,len);
			 }
		 } catch(Exception e) {
			 throw e;
		 } finally {
			// REVIEW-ACCEPT 关闭前需要做空判断
			 //FIXED
			 
			 if(input!=null){
				 input.close();
			 }
			 if(output!=null){
				 output.close();
			 }
			// REVIEW-ACCEPT 设置为空有无比较？疑似无效代码
			 //FIXED 保证内存回收
			 
			 input=null;
			 output=null;
		 }
	}
	
	@SuppressWarnings("finally")
	public static void smbFileWrite(InputStream input,String smbPath,NtlmPasswordAuthentication nt) throws Exception {
			Assert.notNull(input, "input不能为空！");
			Assert.notNull(smbPath, "smbPath不能为空！");
			Assert.notNull(nt, "nt不能为空！");
			
			SmbFile smbFile = new SmbFile(smbPath,nt);
			
			if(!smbFile.exists()) {
				mkdirs(smbFile.getParent(), nt);
			}
			
			SmbFileOutputStream output= new SmbFileOutputStream(smbFile);
			write(input,output);
	}
	
	/**
	 * 通过smb方式上传文件
	 * @param input 输入流
	 * @param smbPath smbPath路径 
	 * @param domain  域
	 * @param username 共享用户名
	 * @param password 共享密码
	 */
	public static void smbFileWrite(InputStream input,String smbPath,String domain,String username,String password) throws Exception {
		Assert.notNull(input, "input不能为空！");
		Assert.notNull(smbPath, "smbPath不能为空！");
		Assert.notNull(domain, "domain不能为空！");
		Assert.notNull(username, "username不能为空！");
		Assert.notNull(password, "password不能为空！");
		
		NtlmPasswordAuthentication nt = new NtlmPasswordAuthentication(domain,username,password);   
		smbFileWrite(input,smbPath,nt);
	}
	
	//得到smbFile输出流
	public static InputStream smbFileRead(SmbFile smbFile) throws Exception {
		Assert.notNull(smbFile, "smbFile不能为空！");
		
		return smbFile.getInputStream();
	}
	
	public static InputStream smbFileRead(String smbPath,NtlmPasswordAuthentication nt) throws Exception {
		Assert.notNull(smbPath, "smbPath不能为空！");
		Assert.notNull(nt, "nt不能为空！");
		
		SmbFile smbFile = new SmbFile(smbPath,nt);
		return smbFileRead(smbFile);
	}
	
	public static InputStream smbFileRead(String smbPath,String domain,String username,String password) throws Exception {
		Assert.notNull(smbPath, "smbPath不能为空！");
		Assert.notNull(domain, "domain不能为空！");
		Assert.notNull(username, "username不能为空！");
		Assert.notNull(password, "password不能为空！");
		
		NtlmPasswordAuthentication nt = new NtlmPasswordAuthentication(domain,username,password);  
		SmbFile smbFile = new SmbFile(smbPath,nt);
		return smbFileRead(smbFile);
	}
	
	/**
	 * 删除文件
	 * @param smbPath smbPath路径 
	 * @param domain  域
	 * @param username 共享用户名
	 * @param password 共享密码
	 */
	public static void smbFileDelete(String smbPath,String domain,String username,String password) throws Exception {
		Assert.notNull(smbPath, "smbPath不能为空！");
		Assert.notNull(domain, "domain不能为空！");
		Assert.notNull(username, "username不能为空！");
		Assert.notNull(password, "password不能为空！");
		
		NtlmPasswordAuthentication nt = new NtlmPasswordAuthentication(domain,username,password);  
		smbFileDelete(smbPath,nt);
	}
	
	/**
	 * 删除文件
	 * @param smbPath smbPath路径 
	 * @param nt nt认证
	 */
	public static void smbFileDelete(String smbPath,NtlmPasswordAuthentication nt) throws Exception {
		Assert.notNull(smbPath, "smbPath不能为空！");
		Assert.notNull(nt, "nt不能为空！");
		
		SmbFile smbFile = new SmbFile(smbPath,nt);
		smbFile.delete();
	}
	
	
}
