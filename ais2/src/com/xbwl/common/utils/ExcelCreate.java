package com.xbwl.common.utils;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

public class ExcelCreate{
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(ExcelCreate.class);
	
	String sSheetName="new sheet";
	
	/**
	 * 把data的数据保存在HSSFWorkbook中
	 * @描述:
	 * @公司:广东数据通信网络有限公司
	 * @作者:hkq
	 * @日期:Mar 15, 2011 4:16:34 PM	
	 * @param data
	 * @param wb
	 * @return
	 */
	public boolean inputData(List data,HSSFWorkbook wb,HSSFSheet sheet,int rowIndex) throws Throwable{
	
		 if(data==null){
			 return false;
		 }
		 if(data.size()>65535){
			 logger.error("目前数据行数:"+data.size()+",数据超出EXCEL允许的最大行数65535");
			 return false;
		 }
		 for(int i=0;i<data.size();i++){
		    // Create a row and put some cells in it. Rows are 0 based.
	        HSSFRow row = sheet.createRow(rowIndex++);//建立新行
	        List rowdata=(List)data.get(i);
            if(rowdata.size()>255){
            	 logger.error("第"+i+"行数据:"+data.size()+",超出EXCEL允许的最大列数255");
            	 continue;
            }
	        for(int j=0;j<rowdata.size();j++){
	        	Object objectData=rowdata.get(j);
	         // Create a cell and put a value in it.
	           HSSFCell cell = row.createCell((short)j);//建立新cell
	           cell.setCellType(HSSFCell.CELL_TYPE_STRING);   
	           cell.setCellValue(String.valueOf(objectData));//设置cell的整数类型的值


	            // Or do it on one line.
	         //  row.createCell((short)1).setCellValue(objectData);//设置cell浮点类型的值
	         //  row.createCell((short)2).setCellValue("test");//设置cell字符类型的值
	         //  row.createCell((short)3).setCellValue(true);//设置cell布尔类型的值 
	           
	         /*  HSSFCellStyle cellStyle = wb.createCellStyle();//建立新的cell样式
	           Workbook workBook = new Workbook();
	           HSSFDataFormat hSSFDataFormat = new HSSFDataFormat(workBook);
	           cellStyle.setDataFormat(hSSFDataFormat.getFormat("yyyy-mm-dd h:mm:ss"));//设置cell样式为定制的日期格式
	           HSSFCell dCell =row.createCell((short)4);
	     
	           dCell.setCellValue(new Date());//设置cell为日期类型的值
	           dCell.setCellStyle(cellStyle); //设置该cell日期的显示格式
	           
	           HSSFCell csCell =row.createCell((short)5);
	           csCell.setEncoding(HSSFCell.ENCODING_UTF_16);//设置cell编码解决中文高位字节截断
	           csCell.setCellValue("中文测试_Chinese Words Test");//设置中西文结合字符串
	           
	           row.createCell((short)6).setCellType(HSSFCell.CELL_TYPE_ERROR);//建立错误cell
	           */
	        }
	        
		 }
	     return true;
	}
	public String createByCopy(List data,InputStream excelFileStream,String saveName,int rowIndex) throws Exception,Throwable{
			
		  POIFSFileSystem excel = new POIFSFileSystem(excelFileStream);
		  HSSFWorkbook wb = new HSSFWorkbook(excel);
		// REVIEW 非空判断应当在new对象前进行。
	      if(data==null){
	    	  return null;
	      }
	      if(saveName==null){
	    	  return null;
	      }
	 	  HSSFSheet sheet = wb.getSheetAt(0);//建立新的sheet对象
	      inputData(data,wb,sheet,rowIndex);
	     // Write the output to a file
	   
	      FileOutputStream fileOut =null;
	      try{
	    	  fileOut = new FileOutputStream(saveName);
	    	  wb.write(fileOut);
	      }catch(Exception e){
	    	  throw e;
	      }finally{
	    	  if(fileOut!=null ){
	             fileOut.flush();
	             fileOut.close();
	    	  }
	      }
	     return saveName;
	 }	
	 public String createByCopy(List data,String sourceName,String saveName,int rowIndex) throws Exception,Throwable{
		  logger.info("原文件路径:"+sourceName);
		  logger.info("目标文件路径:"+saveName);
		  FileInputStream fis=new FileInputStream(sourceName);
		  return createByCopy(data,fis,saveName,rowIndex);
		 
	  }	
	/**
	 * 
	 * @描述:
	 * @公司:广东数据通信网络有限公司
	 * @作者:hkq
	 * @日期:Mar 15, 2011 4:14:57 PM	
	 * @param data  导出EXCEL的数据，是一个表结构，行和列都是List类型
	 * @param saveName导出保存的文件名，应是全路径
	 * @return  返回文件保存路径
	 * @throws Exception
	 */
  public String create(List data,String saveName) throws Exception,Throwable{

      HSSFWorkbook wb = new HSSFWorkbook();//建立新HSSFWorkbook对象
		// REVIEW 非空判断应当在new对象前进行。
      if(data==null){
    	  return null;
      }
      if(saveName==null){
    	  return null;
      }
 	  HSSFSheet sheet = wb.createSheet(sSheetName);//建立新的sheet对象
      inputData(data,wb,sheet,0);
     // Write the output to a file
   
      FileOutputStream fileOut =null;
      try{
    	  fileOut = new FileOutputStream(saveName);
    	  wb.write(fileOut);
      }catch(Exception e){
    	  throw e;
      }finally{
    	  if(fileOut!=null ){
             fileOut.flush();
             fileOut.close();
    	  }
      }
     return saveName;
  }	
  
  public static void main(String[] args)throws IOException{
	  ExcelCreate test =new ExcelCreate();
	  String saveName="c:\\test.xls";
	  List data=new java.util.ArrayList();
	 
	  List row=new java.util.ArrayList();
	  row.add("ssssssss");
	  row.add("中文");
	  row.add("2011-03-15 12:01:01");
	  row.add(323);
	  for(int i=0;i<8;i++){
	    row.add(4343.32);
	  }
	  for(int i=0;i<65535;i++){
	    data.add(row);
	 }
	  try{
	     test.create(data, saveName);
	  }catch(Exception e){
		  e.printStackTrace();
	  }catch(Throwable te){
		  logger.error("数据太大，内存溢出！");
		  te.printStackTrace();
	  }
	  
  }
  
}

