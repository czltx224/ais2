package com.xbwl.finance.Service;

import java.io.File;

import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.FiExcelPos;

public interface IFiExcelPosService extends IBaseService<FiExcelPos,Long> {

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
