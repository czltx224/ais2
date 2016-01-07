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
	 * �������Ᵽ�淽��
	 * @param sysProblem ����ʵ����
	 * @param fileName  ͼƬ����
	 * @param file ͼƬ
	 * @throws Exception
	 */
	public void savePhoto(SysProblem sysProblem,String fileName,File file) throws Exception;
}
