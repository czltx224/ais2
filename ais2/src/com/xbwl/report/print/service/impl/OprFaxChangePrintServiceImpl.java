package com.xbwl.report.print.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.ralasafe.WebRalasafe;
import org.ralasafe.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xbwl.entity.OprChangeDetail;
import com.xbwl.entity.OprChangeMain;
import com.xbwl.entity.OprFaxIn;
import com.xbwl.oper.fax.dao.IOprFaxChangeDao;
import com.xbwl.oper.fax.dao.IOprFaxInDao;
import com.xbwl.report.print.bean.BillLadingList;
import com.xbwl.report.print.bean.OprFaxChangeBean;
import com.xbwl.report.print.bean.PrintBean;
import com.xbwl.report.print.printServiceInterface.IPrintServiceInterface;

/**
 * @author czl
 * @createTime 2012-03-16 03:21 AM
 *
 * ����֪ͨ����ӡ����ʵ����
 */
@Service("oprFaxChangePrintServiceImpl")
@Transactional(rollbackFor=Exception.class)
public class OprFaxChangePrintServiceImpl implements IPrintServiceInterface {

	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	@Resource(name="oprFaxChangeHibernateDaoImpl")
	private IOprFaxChangeDao oprFaxChangeDao;
	
	@Resource(name="oprFaxInHibernateDaoImpl")
	private IOprFaxInDao oprFaxInDao;
	
	@Value("${print_fax_change_img}")
	private String print_fax_change_img;
	
	public void afterPrint(PrintBean bean) {
		logger.debug("����֪ͨ����ӡ��ϣ�");
	}

	public List<? extends PrintBean> setPrintBeanList(BillLadingList mainbill,
			Map<String, String> map) {
		List<PrintBean> list= new ArrayList<PrintBean>();
		
		String dno = map.get("dno");
		String changeNo = map.get("changeNo");
		
		if(null==map.get("changeNos") || "".equals(map.get("changeNos").trim())){
			mainbill.setMsg("�봫������ϸ���Ź�����");
			return list;
		}
		if(null==changeNo || "".equals(changeNo)){
			mainbill.setMsg("�봫���ĵ��Ź�����");
			return list;
		}
		if(null==dno || "".equals(dno)){
			mainbill.setMsg("�봫���͵��Ź�����");
			return list;
		}
		String changeNos[] = map.get("changeNos").split(",");
		OprChangeMain changeMain = this.oprFaxChangeDao.get(Long.valueOf(changeNo));
		if(null==changeMain){
			mainbill.setMsg("���ĵ�������û���ҵ��ø��ĵ��ţ�");
			return list;
		}
		OprFaxIn fax = this.oprFaxInDao.get(changeMain.getDno());
		if(null==fax){
			mainbill.setMsg("û���ҵ���Ӧ�Ĵ����¼��");
			return list;
		}
		OprChangeDetail detail = null;
		OprFaxChangeBean bean = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm ss");
		SimpleDateFormat sdfPrint = new SimpleDateFormat("yyyy-MM-dd HH:mm");//��ӡ��ʽ
		User user = WebRalasafe.getCurrentUser(ServletActionContext.getRequest());
		Iterator<OprChangeDetail> itr = changeMain.getOprChangeDetail().iterator();
		while(itr.hasNext()){
			detail = itr.next();
			boolean bool = false;
			for (int i = 0; i < changeNos.length; i++) {
				
				if(detail.getId().equals(Long.valueOf(changeNos[i]))){
					bool =true;
					break;
				}
			}
			if(!bool){
				continue;
			}
			
			bean = new OprFaxChangeBean();
			bean.setChangeContent("ԭ "+detail.getChangeFieldZh()+" Ϊ��"+detail.getChangePre()+"   �ָ�Ϊ��"+detail.getChangePost());
			try{
				bean.setChangeDate(sdf.format(changeMain.getCreateTime()));
			}catch (Exception e) {
				mainbill.setMsg("��������������ʱ������");
				logger.debug("��������������ʱ������");
			}
			bean.setChangeDepart(changeMain.getDepartName()==null?"":changeMain.getDepartName());
			bean.setChangeName(changeMain.getCreateName()==null?"":changeMain.getCreateName());
			bean.setChangeNo(changeNo);
			bean.setChangeTitle("�������ĸ���֪ͨ��");//���ñ���
			bean.setConsignee(fax.getConsignee()==null?"":fax.getConsignee());
			bean.setCusName(fax.getCpName()==null?"":fax.getCpName());
			bean.setDistributionMode(fax.getDistributionMode()==null?"":fax.getDistributionMode());
			bean.setDno(fax.getDno()+"");
			bean.setFlightMainNo(fax.getFlightMainNo()==null?"":fax.getFlightMainNo());
			bean.setSubNo(fax.getSubNo()==null?"":fax.getSubNo());
			bean.setGoWhere(fax.getRealGoWhere()==null?"":fax.getRealGoWhere());
			bean.setPayWay(fax.getWhoCash()==null?"":fax.getWhoCash());
			bean.setPiece(fax.getPiece()==null?"0":fax.getPiece()+"");
			bean.setTakeMode(fax.getTakeMode()==null?"":fax.getTakeMode());
			bean.setWeight(fax.getCqWeight()==null?"0":fax.getCqWeight()+"");//����
			bean.setWeightFee(fax.getCusWeight()==null?"":fax.getCusWeight()+"");//�Ʒ�����
			bean.setXbwlImagePath(this.print_fax_change_img);//����ͼƬ·��
			
			bean.setPrintId("");
			bean.setPrintName(user.get("name")+"");
			bean.setPrintNum(1l);
			bean.setPrintTime(sdfPrint.format(new Date()));//��ӡʱ�� 
			bean.setSourceNo(changeNo);
			
			list.add(bean);
		}
		return list;
	}

}
