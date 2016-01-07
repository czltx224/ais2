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
	 * ��data�����ݱ�����HSSFWorkbook��
	 * @����:
	 * @��˾:�㶫����ͨ���������޹�˾
	 * @����:hkq
	 * @����:Mar 15, 2011 4:16:34 PM	
	 * @param data
	 * @param wb
	 * @return
	 */
	public boolean inputData(List data,HSSFWorkbook wb,HSSFSheet sheet,int rowIndex) throws Throwable{
	
		 if(data==null){
			 return false;
		 }
		 if(data.size()>65535){
			 logger.error("Ŀǰ��������:"+data.size()+",���ݳ���EXCEL������������65535");
			 return false;
		 }
		 for(int i=0;i<data.size();i++){
		    // Create a row and put some cells in it. Rows are 0 based.
	        HSSFRow row = sheet.createRow(rowIndex++);//��������
	        List rowdata=(List)data.get(i);
            if(rowdata.size()>255){
            	 logger.error("��"+i+"������:"+data.size()+",����EXCEL������������255");
            	 continue;
            }
	        for(int j=0;j<rowdata.size();j++){
	        	Object objectData=rowdata.get(j);
	         // Create a cell and put a value in it.
	           HSSFCell cell = row.createCell((short)j);//������cell
	           cell.setCellType(HSSFCell.CELL_TYPE_STRING);   
	           cell.setCellValue(String.valueOf(objectData));//����cell���������͵�ֵ


	            // Or do it on one line.
	         //  row.createCell((short)1).setCellValue(objectData);//����cell�������͵�ֵ
	         //  row.createCell((short)2).setCellValue("test");//����cell�ַ����͵�ֵ
	         //  row.createCell((short)3).setCellValue(true);//����cell�������͵�ֵ 
	           
	         /*  HSSFCellStyle cellStyle = wb.createCellStyle();//�����µ�cell��ʽ
	           Workbook workBook = new Workbook();
	           HSSFDataFormat hSSFDataFormat = new HSSFDataFormat(workBook);
	           cellStyle.setDataFormat(hSSFDataFormat.getFormat("yyyy-mm-dd h:mm:ss"));//����cell��ʽΪ���Ƶ����ڸ�ʽ
	           HSSFCell dCell =row.createCell((short)4);
	     
	           dCell.setCellValue(new Date());//����cellΪ�������͵�ֵ
	           dCell.setCellStyle(cellStyle); //���ø�cell���ڵ���ʾ��ʽ
	           
	           HSSFCell csCell =row.createCell((short)5);
	           csCell.setEncoding(HSSFCell.ENCODING_UTF_16);//����cell���������ĸ�λ�ֽڽض�
	           csCell.setCellValue("���Ĳ���_Chinese Words Test");//���������Ľ���ַ���
	           
	           row.createCell((short)6).setCellType(HSSFCell.CELL_TYPE_ERROR);//��������cell
	           */
	        }
	        
		 }
	     return true;
	}
	public String createByCopy(List data,InputStream excelFileStream,String saveName,int rowIndex) throws Exception,Throwable{
			
		  POIFSFileSystem excel = new POIFSFileSystem(excelFileStream);
		  HSSFWorkbook wb = new HSSFWorkbook(excel);
		// REVIEW �ǿ��ж�Ӧ����new����ǰ���С�
	      if(data==null){
	    	  return null;
	      }
	      if(saveName==null){
	    	  return null;
	      }
	 	  HSSFSheet sheet = wb.getSheetAt(0);//�����µ�sheet����
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
		  logger.info("ԭ�ļ�·��:"+sourceName);
		  logger.info("Ŀ���ļ�·��:"+saveName);
		  FileInputStream fis=new FileInputStream(sourceName);
		  return createByCopy(data,fis,saveName,rowIndex);
		 
	  }	
	/**
	 * 
	 * @����:
	 * @��˾:�㶫����ͨ���������޹�˾
	 * @����:hkq
	 * @����:Mar 15, 2011 4:14:57 PM	
	 * @param data  ����EXCEL�����ݣ���һ����ṹ���к��ж���List����
	 * @param saveName����������ļ�����Ӧ��ȫ·��
	 * @return  �����ļ�����·��
	 * @throws Exception
	 */
  public String create(List data,String saveName) throws Exception,Throwable{

      HSSFWorkbook wb = new HSSFWorkbook();//������HSSFWorkbook����
		// REVIEW �ǿ��ж�Ӧ����new����ǰ���С�
      if(data==null){
    	  return null;
      }
      if(saveName==null){
    	  return null;
      }
 	  HSSFSheet sheet = wb.createSheet(sSheetName);//�����µ�sheet����
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
	  row.add("����");
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
		  logger.error("����̫���ڴ������");
		  te.printStackTrace();
	  }
	  
  }
  
}

