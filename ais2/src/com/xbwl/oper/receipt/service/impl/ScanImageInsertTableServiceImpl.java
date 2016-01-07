package com.xbwl.oper.receipt.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xbwl.entity.OprReceipt;
import com.xbwl.entity.OprSign;
import com.xbwl.entity.TbImages;
import com.xbwl.oper.receipt.dao.IOprReceiptDao;
import com.xbwl.oper.receipt.service.IScanImageInsertTableService;
import com.xbwl.oper.receipt.service.ITbImagesService;
import com.xbwl.oper.stock.service.IOprHistoryService;
import com.xbwl.oper.stock.service.IOprSignService;
import com.xbwl.rbac.Service.IUserService;
import com.xbwl.rbac.entity.SysUser;

/**
 * 回单图片扫描定时调用方法
 * author shuw
 * time May 7, 2012 11:23:44 AM
 */
@Service("scanImageInsertTableServiceImpl")
@Transactional(rollbackFor = { Exception.class })
public class ScanImageInsertTableServiceImpl implements IScanImageInsertTableService{

	@Resource(name="tbImagesServiceImpl")
	private ITbImagesService tbImagesService;
	
	@Resource(name = "oprReceiptHibernateDaoImpl")
	private IOprReceiptDao oprReceiptDao;
	
	@Resource(name="oprSignServiceImpl")
	private IOprSignService oprSignService;
	
	@Resource(name="oprHistoryServiceImpl")
	private IOprHistoryService oprHistoryService;
	
	@Resource
	private IUserService userService;
	
	@Value("${oprReceiptServiceImpl.log_scan}")
	private Long log_scan;
	
	/**
	 * 定时任务：把上传的图片地址回写回单表
	 * @throws Exception
	 */
	public void saveAllImagesInfo(String type,Long dno,String imageName,String loginName) throws Exception{
			Date date = new Date();
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
			String datas=df.format(date);

			TbImages tbImages=new TbImages();
			List<SysUser> userList = userService.findBy("loginName",loginName);
			String userName=null;
			tbImages.setBillno(dno+"");
			if(userList.size()!=0){
				userName=userList.get(0).getUserName();
				tbImages.setUsername(userName);
			}
			tbImages.setImgpath(imageName);
			tbImages.setSenddate(date);
			tbImagesService.save(tbImages);
			
			if("3".equals(type)){
				List<OprSign> oprSignList = oprSignService.findBy("dno",dno);
				if(oprSignList.size()!=0){
					OprSign sign=oprSignList.get(0);
					sign.setScanAdd(imageName);
					oprSignService.save(sign);
				}
			}else{
				List<OprReceipt> ceiptList = oprReceiptDao.findBy("dno",dno);

				if(ceiptList.size()!=0){
					OprReceipt oprReceipt=ceiptList.get(0);
					
					if(oprReceipt.getScanNum()!=null&&oprReceipt.getScanNum()>10l){
						return;
					}
					
					if(oprReceipt.getScanStauts()!=null&&oprReceipt.getScanStauts()==1l){
						oprReceipt.setScanAddr(oprReceipt.getScanAddr()+"&%&"+tbImages.getImgpath());
						oprReceipt.setScanNum(oprReceipt.getScanNum()+1);
						oprReceipt.setScanMan(userName);
						oprReceiptDao.save(oprReceipt);
					}else{
						oprReceipt.setScanAddr(tbImages.getImgpath());
						oprReceipt.setScanNum(1);
						oprReceipt.setCurStatus("已扫描");
						oprReceipt.setScanTime(date);
						oprReceipt.setScanStauts(1l);
						oprReceipt.setScanMan(userName);
						oprReceiptDao.save(oprReceipt);
					}
					
					this.oprHistoryService.saveLogByUser(oprReceipt.getDno(),userName+"第"+oprReceipt.getScanNum()+"次回单扫描",this.log_scan,userList.get(0));
				}
			}
	}

}
