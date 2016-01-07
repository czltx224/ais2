package com.xbwl.oper.edi.service.impl;

import java.net.InetAddress;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xbwl.common.exception.ServiceException;
import com.xbwl.common.log.anno.ModuleName;
import com.xbwl.common.orm.IBaseDAO;
import com.xbwl.common.orm.Page;
import com.xbwl.common.service.impl.BaseServiceImpl;
import com.xbwl.common.utils.LogType;
import com.xbwl.entity.OprFaxIn;
import com.xbwl.entity.OprSign;
import com.xbwl.entity.SysQianshou;
import com.xbwl.message.service.ISmsSendsmsRecordService;
import com.xbwl.oper.edi.dao.ISysQianshouDao;
import com.xbwl.oper.edi.service.ISysQianshouService;
import com.xbwl.oper.fax.service.IOprFaxInService;
import com.xbwl.oper.reports.util.AppendConditions;
import com.xbwl.oper.stock.service.IOprSignService;
import com.xbwl.ws.client.IWSEdiToAisRemote;
import com.xbwl.ws.client.result.base.WSResult;

import dto.OprSignDto;

/**
 * 短信签收记录表服务层实现类
 * @author czl
 * @date 2012-05-28
 *
 */
@Service("sysQianshouServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class SysQianshouServiceImpl extends BaseServiceImpl<SysQianshou, String>
		implements ISysQianshouService {

	@Resource(name = "sysQianshouHibernateDaoImpl")
	private ISysQianshouDao sysQianshouDao;

	@Resource(name="oprSignServiceImpl")
	private IOprSignService oprSignService;
	
	@Resource(name="wsEdiToAisRemote")
	private IWSEdiToAisRemote wsEdiToAisRemote;
	
	@Resource(name="smsSendsmsRecordServiceImpl")
	private ISmsSendsmsRecordService smsSendsmsRecordService;
	
	@Resource(name="appendConditions")
	private AppendConditions appendConditions;
	
	@Resource(name="oprFaxInServiceImpl")
	private IOprFaxInService oprFaxInService;
	
	@Value("${opr.edi_distributionMode}")
	private String edi_distributionMode;
	
	@Override
	public IBaseDAO<SysQianshou, String> getBaseDao() {
		return this.sysQianshouDao;
	}

	@ModuleName(value="AIS扫描SYS_QIANSHOU表写入EDI",logType=LogType.buss)
	public void scanningSysQianshouService() throws Exception {
		String hql = "from SysQianshou";
		Page pg = new Page();
		pg.setStart(0);//起始页码
		pg.setLimit(100);//分页大小
		Page<SysQianshou> page = this.sysQianshouDao.findPage(pg, hql);
		
		InetAddress addr = InetAddress.getLocalHost();
		String localIp=addr.getHostAddress();//获得本机IP
		
		for (int i = 1; i <= page.getTotalPages(); i++) {
			List<SysQianshou> list = page.getResult();
			for (int j = 0; j < list.size(); j++) {
				//业务处理
				SysQianshou sysQianshou = list.get(j);
				//去除字符，获取配送单号
				Long dno =null;
				try{
					dno = Long.valueOf(sysQianshou.getContext().replaceAll("[^\\d]*(\\d)[^\\d]*", "$1"));
				}catch (Exception e) {
					this.sysQianshouDao.delete(sysQianshou);
					continue;
				}
				//去吃数字，获取发送人
				String sendMan = sysQianshou.getContext().replaceAll("[^\\D]*(\\D)[^\\D]*", "$1");
				List<OprSign> signList = this.oprSignService.findBy("dno",dno);
				if(null!=signList && signList.size()>0){
					OprSign sign = signList.get(0);
					OprFaxIn fax = this.oprFaxInService.get(sign.getDno());
					if(null==fax){
						this.sysQianshouDao.delete(sysQianshou);
						continue;
					}
					if(!fax.getDistributionMode().equals(edi_distributionMode)){
						this.sysQianshouDao.delete(sysQianshou);
						continue;
					}
					OprSignDto dto = new OprSignDto();
					BeanUtils.copyProperties(sign, dto);
					WSResult rs = this.wsEdiToAisRemote.messageRemoteSign(dto);
					if(null==rs){
						throw new ServiceException("EDI项目没有启动！");
					}else if(!WSResult.SUCCESS.equals(rs.getCode())){
						throw new ServiceException(rs.getMessage());
					}
					this.sysQianshouDao.delete(sysQianshou);
					
					/*
					
					//发送短信给收货人，询问对送货服务是否满意
					SmsSendsmsDto smsDto =  new SmsSendsmsDto();
					String context = "您好，您对新邦物流单号为 "+sign.getDno()+""+" 送货服务满意度是：(回复数字)1、满意，2、一般，3、不满意，8、不再提示此信息";
					smsDto.setTel(sysQianshou.getTel());
					smsDto.setContext(context);//短信内容
					smsDto.setSendName(sendMan);
					smsDto.setSendDepart("");
					smsDto.setSmstype("AIS短信签收");
					smsDto.setReceiver(sign.getSignMan());//接收对象
					smsDto.setBillno(sign.getDno()+"");
					smsDto.setClientIp(localIp);
					smsDto.setSystemName("ais");
					smsDto.setUid(this.smsSendsmsRecordService.getSmsUid(localIp));
					smsDto.setSysNo(2l);//手动设置
					
					// this.smsSendsmsRecordService.saveSendMsg(smsDto,sysQianshou.getTel());
					 */
				}else{
					this.sysQianshouDao.delete(sysQianshou);
				}
			}
			if(page.getPageNo()>i){
				// pg.setStart(i-1);删除之后查第一页就可以了
				page = this.sysQianshouDao.findPage(pg, hql);
			}else{
				break;
			}
		}
	}

	public String findRecordList(Map<String, String> map) throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append("select t.sysqsid,t.tel,t.context,to_char(t.createtime,'yyyy-MM-dd hh24:mi:ss') createtime,")
		  .append("SUCCESS_FLAG,ERRER_INFO from sys_qianshou_record t where 1=1");
		
		//添加日期条件
		sb.append(appendConditions.appendCountDate(map));
		
		//添加条件
		sb.append(appendConditions.appendConditions(map,null));
		
		sb.append(" order by t.sysqsid desc");
		return sb.toString();
	}
}
