package com.xbwl.sys.service;

import java.io.File;
import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.SysProblem;

/**
 * author shuw
 * time Feb 11, 2012 2:41:21 PM
 */

public interface ISysProblemService extends IBaseService<SysProblem, Long>{

	/**
	 * 新增问题保存方法
	 * @param sysProblem 问题实体类
	 * @param fileName  图片名字
	 * @param file 图片
	 * @throws Exception
	 */
	public void savePhoto(SysProblem sysProblem,String fileName,File file) throws Exception;
}
