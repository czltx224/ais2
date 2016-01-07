package com.xbwl.finance.Service;

import java.io.File;

import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.FiCashiercollectionExcel;

/**
 * author shuw
 * time Nov 15, 2011 9:55:45 AM
 */

public interface IFiCashiercollectionExcelService extends IBaseService<FiCashiercollectionExcel,Long> {

	/**
	 * 导入Excel
	 * @param excelFile
	 * @param fileName
	 * @throws Exception
	 */
	public String saveFiExcel(File excelFile,String fileName) throws Exception;
	
	/**
	 * 获取批次号
	 * @param bussDepartId
	 * @return
	 * @throws Exception
	 */
	public Long getBatchNO() throws Exception;
}
