package com.xbwl.message.service;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xbwl.common.exception.ServiceException;
import com.xbwl.common.orm.IBaseDAO;
import com.xbwl.common.service.impl.BaseServiceImpl;
import com.xbwl.entity.SmsSendsmsRecord;
import com.xbwl.message.dao.ISmsSendsmsRecordDao;
import com.xbwl.ws.client.IWSSmsSendsmsService;
import com.xbwl.ws.client.result.base.WSResult;

import dto.SmsSendsmsDto;

/**
 * 短信发送记录表服务层实现类
 * @author czl
 *
 */
@Service("smsSendsmsRecordServiceImpl")
@Transactional(rollbackFor=Exception.class)
public class SmsSendsmsRecordServiceImpl extends
		BaseServiceImpl<SmsSendsmsRecord, Long> implements
		ISmsSendsmsRecordService {

	@Resource(name="smsSendsmsRecordHibernateDaoImpl")
	private ISmsSendsmsRecordDao sendsmsRecordDao;
	
	@Value("${message_send_password}")
	private String messageSendPassword;
	
	@Resource(name="wSSmsSendsmsRemot")
	private IWSSmsSendsmsService wSendsmsService;
	
	@Override
	public IBaseDAO<SmsSendsmsRecord, Long> getBaseDao() {
		return this.sendsmsRecordDao;
	}

	public void saveBySmsSendsmsDto(SmsSendsmsDto dto) throws Exception {
		String[] tels =  dto.getTel().split(",");//电话
		
		for(int i=0;i<tels.length;i++){
			SmsSendsmsRecord entity = new SmsSendsmsRecord();
			entity.setTel(tels[i]);
			entity.setContext(dto.getContext());
			entity.setSendName(dto.getSendName());
			entity.setSendDepart(dto.getSendDepart());
			entity.setSmstype(dto.getSmstype());
			entity.setReceiver(dto.getReceiver());
			entity.setBillno(dto.getBillno());
			entity.setIpAddr(dto.getClientIp());
			entity.setSystemName(dto.getSystemName());
			entity.setCreateName(dto.getSendName());
			entity.setCreateTime(new Date());
			entity.setSysNo(dto.getSysNo());
			
			this.sendsmsRecordDao.save(entity);
		}
	}

	public String getSmsUid(String ipAddr) throws Exception {
		String uid = "";
		String password = this.messageSendPassword;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Md5PasswordEncoder md5=new Md5PasswordEncoder();
		uid+=ipAddr;
		uid+=password;
		uid+=sdf.format(new Date());
		try {
			uid=md5.encodePassword(uid,"");
		} catch (Exception e1) {
			throw new ServiceException("MD5转换失败！");
		}
		
		return  uid;
	}

	public void saveSendMsg(SmsSendsmsDto dto,String consigneeTels) throws Exception {
		
		WSResult result = this.wSendsmsService.saveMsg(dto);
		//WSResult result = null;
		if(null!=result && !result.getCode().equals(WSResult.SUCCESS)){
			throw new ServiceException(result.getMessage());
		}else{
			//写入短信发送记录表
			dto.setTel(consigneeTels);
			this.saveBySmsSendsmsDto(dto);
		}
	}
}
