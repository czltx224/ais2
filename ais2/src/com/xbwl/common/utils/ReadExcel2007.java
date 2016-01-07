package com.xbwl.common.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.xbwl.rbac.entity.SysUser;

/**
 * author shuw
 * time Nov 15, 2011 5:14:31 PM
 */

public class ReadExcel2007 {
	/**
	 * Logger for this class
	 */   
	private static final Logger logger = Logger.getLogger(ReadExcel2007.class);
	
	private int rowCount;
	private Integer columnCount;
	
	public ReadExcel2007(){}
	
	public ReadExcel2007(int columnCount){
		this.columnCount=columnCount;
	}

	private String getCellVelue(Row row,int index){
		String k = "";
		Cell cell = row.getCell(index);
		if(null==cell){  //��Ϊ��ʱ����ʾû��ֵ���˳��˷���
			return k;
		}
		Integer type = cell.getCellType(); // �õ���Ԫ����������
		switch (type) { // �ж���������
			case Cell.CELL_TYPE_BLANK:
				break;
			case Cell.CELL_TYPE_BOOLEAN:
				k = cell.getBooleanCellValue() + "";
				break;
			case Cell.CELL_TYPE_ERROR:
				k = cell.getErrorCellValue() + "";
				break;
			case Cell.CELL_TYPE_FORMULA:
				k = cell.getCellFormula();
				break;
			case Cell.CELL_TYPE_NUMERIC:
				if(DateUtil.isCellDateFormatted(cell)){
					k = new DataFormatter().formatRawCellContents(cell.getNumericCellValue(), 0, "yyyy-MM-dd");// ��ʽ������
				}else{
					k=cell.getNumericCellValue()+"";
				}
				break;
			case Cell.CELL_TYPE_STRING:
					k = cell.getStringCellValue().toString();
				break;
			default:
				break;
		}
		return k;
	}
	
	public List readExcelByFileName(String filePath) throws Exception{
		FileInputStream excelFileStream = new FileInputStream(filePath);
		List data=null;
		try {
			data= readExcel2007(excelFileStream);
			// REVIEW-ACCEPT ע�⣬���close�����޷��ﵽ��Ӧ��ʹ��try catch finally������
			// FIXED
			excelFileStream.close(); 
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}finally{
			excelFileStream.close();
			excelFileStream=null;
		}

		return data;
	}
	
	/**�����ж���EXCEL���ݣ�������LIST�з���
	 * @author hkq
	 * @param filepath
	 * @return
	 * @throws IOException
	 */
	public  List readExcel2007(InputStream fileName) throws Exception{
		XSSFWorkbook workbook = new XSSFWorkbook( fileName);
		Sheet sheet  = workbook.getSheetAt(0);
	    List tableData=new ArrayList();
		int lastRowNum=sheet.getLastRowNum();
		for(int i=0;i<=lastRowNum;i++){
				Row row=sheet.getRow(i);
				List rowData=new ArrayList();
				for(int j=0;j<columnCount;j++){
					String cellValue=getCellVelue(row, j);
					rowData.add(cellValue);
				}
				tableData.add(rowData);
		}
		return tableData;
	}
	
	public int getRowCount() {
		return rowCount;
	}
	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}
	public Integer getColumnCount() {
		return columnCount;
	}
	public void setColumnCount(Integer columnCount) {
		this.columnCount = columnCount;
	}
}
