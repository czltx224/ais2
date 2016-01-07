package com.xbwl.oper.stock.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.ralasafe.WebRalasafe;
import org.ralasafe.user.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xbwl.common.exception.ServiceException;
import com.xbwl.common.log.anno.ModuleName;
import com.xbwl.common.orm.IBaseDAO;
import com.xbwl.common.service.impl.BaseServiceImpl;
import com.xbwl.common.utils.LogType;
import com.xbwl.entity.OprFaxIn;
import com.xbwl.entity.OprOvermemoDetail;
import com.xbwl.entity.OprReceipt;
import com.xbwl.entity.OprReturnGoods;
import com.xbwl.entity.OprStatus;
import com.xbwl.entity.SysDepart;
import com.xbwl.finance.dto.IFiInterface;
import com.xbwl.finance.dto.impl.FiInterfaceProDto;
import com.xbwl.oper.fax.dao.IOprFaxInDao;
import com.xbwl.oper.receipt.service.IOprReceiptService;
import com.xbwl.oper.reports.util.AppendConditions;
import com.xbwl.oper.stock.dao.IOprReturnGoodsDao;
import com.xbwl.oper.stock.dao.IOprStatusDao;
import com.xbwl.oper.stock.service.IOprExceptionStockService;
import com.xbwl.oper.stock.service.IOprHistoryService;
import com.xbwl.oper.stock.service.IOprOvermemoDetailService;
import com.xbwl.oper.stock.service.IOprReturnGoodsService;
import com.xbwl.oper.stock.service.IOprStockService;
import com.xbwl.rbac.Service.IDepartService;

/**
 * @author CaoZhili time Jul 30, 2011 10:43:52 AM
 * 
 * ������������ʵ����
 */
@Service("oprReturnGoodsServiceImpl")
@Transactional(rollbackFor = { Exception.class })
public class OprReturnGoodsServiceImpl extends
		BaseServiceImpl<OprReturnGoods, Long> implements IOprReturnGoodsService {

	@Resource(name = "oprReturnGoodsHibernateDaoImpl")
	private IOprReturnGoodsDao oprReturnGoodsDao;

	@Resource(name = "oprFaxInHibernateDaoImpl")
	private IOprFaxInDao oprFaxInDao;

	@Resource(name = "oprStatusHibernateDaoImpl")
	private IOprStatusDao oprStatusDao;

	@Resource(name="oprExceptionStockServiceImpl")
	private IOprExceptionStockService oprExceptionStockService;
	
	@Resource(name = "oprHistoryServiceImpl")
	private IOprHistoryService oprHistoryService;
	
	@Resource(name="oprStockServiceImpl")
	private IOprStockService oprStockService;
	
	@Resource(name="oprReceiptServiceImpl")
	private IOprReceiptService oprReceiptService;
	
	@Resource(name="fiInterfaceImpl")
	private IFiInterface fiInterface;
	
	@Resource(name="oprOvermemoDetailServiceImpl")
	private IOprOvermemoDetailService oprOvermemoDetailService;
	
	@Resource(name="departServiceImpl")
	private IDepartService departService;
	
	@Resource(name="appendConditions")
	private AppendConditions appendConditions;
	
	@Value("${oprReturnGoodsServiceImpl.log_returnEnterStock}")
	private Long   log_returnEnterStock;//�������
	
	@Value("${oprReturnGoodsServiceImpl.log_returnRegistration}")
	private Long   log_returnRegistration;//�����Ǽ�
	
	@Value("${oprOutStockServiceImpl.log_returAudit}")
	private Long   log_returAudit;//�������
	
	public static final  String bufen="���ַ���";
	public static final String zhengpiao ="��Ʊ����";
	public static final String chailing ="���㷵��";
	
	@Override
	public IBaseDAO<OprReturnGoods, Long> getBaseDao() {
		return this.oprReturnGoodsDao;
	}

	@ModuleName(value="�����Ǽ�",logType=LogType.buss)
	public void saveRegistrationService(OprReturnGoods oprReturnGoods)
			throws Exception {
		List<OprStatus> statuslist = this.oprStatusDao.findBy("dno", oprReturnGoods.getDno());
		if(statuslist.size()>0 && statuslist.get(0).getOutStatus()==0){
			throw new ServiceException("����������͵���û�г��⣡");
		}
		OprStatus entity = statuslist.get(0);
		
		if(oprReturnGoods.getReturnType().equals(zhengpiao )){
			entity.setReturnStatus(1l);//��Ʊ����
		}else if(oprReturnGoods.getReturnType().equals(bufen )){
			entity.setReturnStatus(2l);//���ַ���
		}else if(oprReturnGoods.getReturnType().equals(chailing )){
			entity.setIsException(1l);
			entity.setReturnStatus(3l);//���㷵��
		}
		entity.setReturnTime(new Date());
		SimpleDateFormat sdf =  new SimpleDateFormat("yyyy��MM��dd��");
		this.oprReturnGoodsDao.save(oprReturnGoods);
		this.oprHistoryService.saveLog(oprReturnGoods.getDno(), oprReturnGoods.getCreateName()+"��"+sdf.format(oprReturnGoods.getCreateTime())+"�����Ǽ�"+oprReturnGoods.getReturnNum()+"��", log_returnRegistration);
		
		this.oprStatusDao.save(entity);
		
	}

	@ModuleName(value="�������",logType=LogType.buss)
	public void saveEnterStockService(String ids, Long returnStatus)
			throws Exception {

		String[] strids = ids.split(",");
		OprReturnGoods oprReturnGoods = null;
		
		for (int i = 0; i < strids.length; i++) {

			oprReturnGoods = this.oprReturnGoodsDao.get(new Long(strids[i]));
			
			if(oprReturnGoods.getStatus()==2l){
				//�Ѿ����Ļ���������ظ����
				throw new ServiceException("���͵���Ϊ"+oprReturnGoods.getDno()+"�Ļ����Ѿ���⣡");
			}
			
			// �޸�״̬�������е�״̬
			List<OprStatus> list = this.oprStatusDao.findBy("dno",
					oprReturnGoods.getDno());
			if (null!=list && list.size() > 0) {
				OprStatus entity = list.get(0);
				Date dt = new Date();
				if(oprReturnGoods.getReturnType().equals(zhengpiao )){
					entity.setReturnStatus(1l);//��Ʊ����
				}else if(oprReturnGoods.getReturnType().equals(bufen )){
					entity.setReturnStatus(2l);//���ַ���
				}else if(oprReturnGoods.getReturnType().equals(chailing )){
					entity.setIsException(1l);
					entity.setReturnStatus(3l);//���㷵��
				}
				entity.setOutStatus(returnStatus);
				if(oprReturnGoods.getReturnType().equals(zhengpiao )){
					entity.setOutStatus(0l);//��Ʊ�������Ϊδ����״̬
				}
				entity.setReturnEnterTime(dt);
				this.oprStatusDao.save(entity);
			}else{
				throw new ServiceException("���͵�������");
			}
			// �޸ķ������е�״̬
			OprReturnGoods goodsEntity = this.oprReturnGoodsDao
					.get(oprReturnGoods.getId());
			goodsEntity.setStatus(returnStatus);
			SimpleDateFormat sdf =  new SimpleDateFormat("yyyy��MM��dd��");
			this.oprHistoryService.saveLog(oprReturnGoods.getDno(), oprReturnGoods.getCreateName()+"��"+sdf.format(oprReturnGoods.getCreateTime())+"�������"+oprReturnGoods.getReturnNum()+"��", log_returnEnterStock);
			
			if(oprReturnGoods.getReturnType().equals(zhengpiao) || oprReturnGoods.getReturnType().equals(bufen)){
				//throw new ServiceException("ֻ����Ʊ�����Ͳ��ַ�������������");
				try{
					this.oprStockService.saveReturnGoodsStock(oprReturnGoods);//��Ʊ�Ͳ������������
				}catch (Exception e) {
					throw new ServiceException("�������ʧ�ܣ�");
				}
			}else if(oprReturnGoods.getReturnType().equals(chailing)){
			// �޸Ļ򱣴浽�쳣���
				try{
					this.oprExceptionStockService.saveReturnGoodsStock(oprReturnGoods);//�������쳣���
				}catch(Exception e){
					throw new ServiceException("�쳣�������ʧ�ܣ�");
				}
			}else{
				throw new ServiceException("�û��ﷵ�����Ͳ���ȷ��");
			}
			this.oprReturnGoodsDao.save(goodsEntity);

		}
	}
	@ModuleName(value="�ж��Ƿ��������Ǽ�",logType=LogType.buss)
	public OprFaxIn allowRegistration(Long dno) throws Exception{
		User user = WebRalasafe.getCurrentUser(ServletActionContext.getRequest());
		Long bussDepartId = Long.valueOf(user.get("bussDepart")+"");
		List<OprStatus> list = this.oprStatusDao.findBy("dno", dno);
		List<OprReceipt> reList = this.oprReceiptService.findBy("dno",dno);
		if(null!=reList && reList.size()>0){
			if(reList.get(0).getConfirmStatus()==1l){
				//�Ѿ��ص�ȷ�յĻ����������
				throw new ServiceException("��Ʊ���Ѿ��ص�ȷ�գ�����������");
			}
		}
		if(null!=list && list.size()>0){
			
			if(list.get(0).getOutStatus()==0L){
				throw new ServiceException("�û��ﻹû�г����أ�");
			}else if(list.get(0).getOutStatus()==3L){
				throw new ServiceException("�û��ﻹ�Ǵ���Ԥ��״̬��");
			}else if(list.get(0).getSignStatus()==1l){
				//�Ѿ�ǩ�յĻ��ﲻ������
				throw new ServiceException("��Ʊ���Ѿ�ǩ�գ�����������");
			}else if(list.get(0).getFeeAuditStatus()==1l){//status.setFeeAuditStatus(0l)
				throw new ServiceException("��Ʊ����ת���Ѿ���ˣ�����������");
			}else{
 				List<OprReturnGoods> returnlist = this.oprReturnGoodsDao.findBy("dno", dno);
 				for(int i=0;i<returnlist.size();i++){
 					if(returnlist.get(i).getStatus()!=3l){
 						throw new ServiceException("�û����Ѿ�����,��û�г����أ�");
 					}
 				}
 
				OprFaxIn faxIn = this.oprFaxInDao.get(dno);
				if(!faxIn.getEndDepartId().equals(bussDepartId)){
					SysDepart depart = this.departService.get(faxIn.getEndDepartId());
					throw new ServiceException("�뵽"+depart.getDepartName()+"����!");
				}
				
				return faxIn;
			}
		}else{
			throw new ServiceException("�û��ﲻ���ڣ�");
		}
	}

	public Long findMaxOvermemoNoByDno(Long dno) throws Exception {
		
		String sql="select max(overmemo) as overmemoNo from opr_overmemo_detail t where t.d_no=?";
		List<Map> list  = this.oprReturnGoodsDao.createSQLQuery(sql, dno).list();
		
		if(null!=list && list.size()>0){
			if(null!=list.get(0).get("OVERMEMONO") && !"".equals(list.get(0).get("OVERMEMONO"))){
				return Long.valueOf(list.get(0).get("OVERMEMONO")+"");
			}
		}
		return null;
	}

	@ModuleName(value="�������",logType=LogType.buss)
	public void auditReturnGoods(Long returnGoodsId) throws Exception {
		if(null==returnGoodsId || "".equals(returnGoodsId)){
			throw new ServiceException("û�з������ţ�");
		}
		OprReturnGoods returnGoods = this.oprReturnGoodsDao.get(returnGoodsId);
		if(null!=returnGoods.getAuditStatus() && returnGoods.getAuditStatus().equals(1l)){
			throw new ServiceException("�÷����Ѿ���ˣ�");
		}
		
		if(null==returnGoods.getOutNo()){
			throw new ServiceException("û�н��ӵ��ţ�");
		}
		User user = WebRalasafe.getCurrentUser(ServletActionContext.getRequest());
		List<FiInterfaceProDto> listfiInterfaceDto = new ArrayList<FiInterfaceProDto>();
		List<OprOvermemoDetail> list= this.oprOvermemoDetailService.find("from OprOvermemoDetail where oprOvermemo.id=? and dno=?", returnGoods.getOutNo(),returnGoods.getDno());
		FiInterfaceProDto dto = null;
		OprFaxIn fax = null;
		String distributionMode ="";
		for (OprOvermemoDetail detail : list) {
			dto = new FiInterfaceProDto();
			fax = this.oprFaxInDao.get(detail.getDno());
			if(detail.getDistributionMode().equals("��ת") || detail.getDistributionMode().equals("�ⷢ")){
				distributionMode = detail.getDistributionMode();
				dto.setCustomerId(fax.getGoWhereId()==null?0l:fax.getGoWhereId());
				dto.setCustomerName(fax.getGowhere()==null?"":fax.getGowhere());
			}
			dto.setDno(detail.getDno()==null?0l:detail.getDno());
			dto.setDistributionMode(fax.getDistributionMode()==null?"":fax.getDistributionMode());
			dto.setAmount(returnGoods.getReturnCost()==null?0l:returnGoods.getReturnCost());
			
			dto.setSourceData("���͵�");//ҵ����ò��������ܽӿ�
			dto.setSourceNo(returnGoods.getDno());//ҵ����ò��������ܽӿ�
			listfiInterfaceDto.add(dto);
		}
		
		// ��������ɱ�Ϊ�գ�δ���ɱ�����մ�����ϵ���ת�ѡ�
//		if (returnGoods.getReturnCost() == 0) {//�����ת��
			// ��մ�����ϵ���ת�ѡ�
		OprFaxIn faxIn = this.oprFaxInDao.get(returnGoods.getDno());

		faxIn.setTraFee(new Double(0));
		faxIn.setTraFeeRate(new Double(0));
		this.oprFaxInDao.save(faxIn);
		OprStatus status = this.oprStatusDao.findBy("dno", returnGoods.getDno()).get(0);
		status.setPayTra(0l);
		//status.setFeeAuditStatus(0l);//��ת�������:0δ��ˣ�1�����
//		}
		
		//1�����ò���ӿ�
		if(returnGoods.getReturnType().equals(zhengpiao)){
			this.fiInterface.oprReturnToFi(listfiInterfaceDto);//ҵ����ò��������ܽӿ�
			status.setIsCreateFi(0l);//�Ƿ��ѹ��� 0��δ���ˣ�1���ѹ��ˡ���Ҫ����쳣���⡣
		}
		this.oprStatusDao.save(status);
		
		//2�����óɱ��ӿ�
		if(distributionMode.equals("��ת") || distributionMode.equals("�ⷢ")){
			//if(null!=returnGoods.getReturnCost() && returnGoods.getReturnCost()>=0){
				//throw new ServiceException("�÷���û�з����ɱ���");
				List<FiInterfaceProDto> dtoList = new ArrayList<FiInterfaceProDto>();
				for (FiInterfaceProDto fdto : listfiInterfaceDto) {
					fdto.setSourceData("�����Ǽ�");//�����ɱ�д����ת�ɱ�
					fdto.setSourceNo(returnGoods.getId());//�����ɱ�д����ת�ɱ�
					
					dtoList.add(fdto);
				}
				this.fiInterface.oprReturnToFiTransitcost(dtoList);
			//}
		}
		
		//�������д����־
		this.oprHistoryService.saveLog(returnGoods.getDno(), user.get("name")+"��������ˡ�",this.log_returAudit);
		
		//3���޸ķ���������״̬
		returnGoods.setAuditStatus(1l);
		this.oprReturnGoodsDao.save(returnGoods);
	}

	public String findTotalCountService(Map<String, String> map) throws Exception {
		//����ͳ��
		
		StringBuffer sb = new StringBuffer();
		sb.append("select count(*) totalNum,sum(r.return_num) totalPiece,")
             .append(" sum(r.return_cost) totalCost,sum(r.consignee_fee) totalConsigneeFee,")
             .append("sum(r.payment_collection) totalPayment,")
             .append("sum(nvl(piece,0)) faxPiece,sum(nvl(cq_weight,0)) cqWeight,sum(nvl(CUS_VALUE_ADD_FEE,0)) CUSVALUEADDFEE")
             .append(" from opr_return_goods r ,")
             .append("(select t.flight_no,t.cp_name,t.d_no,t.distribution_mode,t.take_mode,t.piece,t.cq_weight,")
             .append("t.consignee,t.consignee_tel,t.addr,t.real_go_where,t.cus_value_add_fee from opr_fax_in t where t.status=1 ) f")
             .append(" where r.d_no=f.d_no(+)");
		
		//sb.append(" and f.status(+)=1 ");//δ��������
		String dno = map.get("EQL_dno");
		String sts[] = {"dno","GED_createTime","LED_createTime","GED_updateTime","LED_updateTime"};
		if(null!=dno && !"".equals(dno)){
			sb.append( " and r.d_no=:EQL_dno");
		}else{
			sb.append(this.appendConditions.appendCountDate(map));
			sb.append(this.appendConditions.appendConditions(map, sts));
		}
		return sb.toString();
		
	}
}
