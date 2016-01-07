package com.xbwl.oper.fax.web;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.ralasafe.WebRalasafe;
import org.ralasafe.user.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.util.Element;
import com.xbwl.common.service.IBaseService;
import com.xbwl.common.web.action.SimpleActionSupport;
import com.xbwl.common.web.struts2.Struts2Utils;
import com.xbwl.entity.OprFaxIn;
import com.xbwl.entity.OprRequestDo;
import com.xbwl.entity.OprValueAddFee;
import com.xbwl.oper.fax.service.IOprFaxInService;
import com.xbwl.oper.fax.vo.EdiFaxInVo;
import com.xbwl.oper.fax.vo.FaxReturnMsg;
import com.xbwl.oper.fax.vo.OprFaxInQueryVo;
import com.xbwl.oper.fax.vo.OprFaxInVo;

/**
 * author CaoZhili time Jul 6, 2011 2:40:02 PM
 */
@Controller
@Action("oprFaxInAction")
@Scope("prototype")
@Namespace("/fax")
@Results( {
		@Result(name = "inputQuery", location = "/WEB-INF/xbwl/stock/comprehensive_inquiry.jsp", type = "dispatcher"),
		@Result(name = "input", location = "/WEB-INF/xbwl/fax/faxIn.jsp", type = "dispatcher"),
		@Result(name = "getInfo", location = "/WEB-INF/xbwl/fax/opr_fax_info.jsp", type = "dispatcher"),
		@Result(name = "info", location = "/WEB-INF/xbwl/fax/opr_fax_info.jsp", type = "dispatcher"),
		@Result(name = "reload", type = "json", params = { "root","validateInfo", "excludeNullProperties", "true" }),
		@Result(name = "list", type = "json", params = { "root", "pages","excludeNullProperties", "true" }
		) }
)
public class OprFaxInAction extends SimpleActionSupport {

	@Resource(name = "oprFaxInServiceImpl")
	private IOprFaxInService oprFaxInService;

	private OprFaxIn oprFaxIn;
	private String flightMainNo;//������
	private String flightNo;//�����
	private Long dno;
	private String dnos;
	private OprFaxInVo oprFaxInVo;
	private Long rightDepart;//ҵ����
	
	private String cusName;//��������
	private String cusTel;//�ջ��˵绰
	private Long cusId;
	private String type;//����
	private String consignee;//�ջ���
	private Date startTime;//��ʼʱ��
	private Date endTime;//����ʱ��
	
	private Date createTime;
	private String sort; // �����ֶ� 
	private String dir;    //˳�� DESC/ASC
	
	private String pub_flightMainNo;//δ��������ѯ����������
	private Long isFlyLate;//�����Ƿ�����0Ϊδ���󣨼���������1Ϊ����
	
	private String dateType;
	
	@Element(value = OprValueAddFee.class)
	private List<OprValueAddFee> addFeeList;
	
	@Element(value = OprRequestDo.class)
	private List<OprRequestDo> requestList;
	
	@Element(value = OprFaxInQueryVo.class)
	private List<OprFaxInQueryVo> olist;

	@Value("${opr.edi_distributionMode}")
	private String edi_distributionMode;
	
	public String info(){
		return "info";
	}
	
	@Override
	protected Object createNewInstance() {

		return new OprFaxIn();
	}

	@Override
	public Map getContextMap() {
		return null;
	}

	@Override
	protected IBaseService getManager() {

		return this.oprFaxInService;
	}

	@Override
	public Object getModel() {

		return this.oprFaxIn;
	}

	@Override
	public void setModel(Object obj) {

		this.oprFaxIn = (OprFaxIn) obj;
	}
	
	

	public String getFlightMainNo() {
		return flightMainNo;
	}

	public void setFlightMainNo(String flightMainNo) {
		this.flightMainNo = flightMainNo;
	}

	public String getFlightNo() {
		return flightNo;
	}

	public void setFlightNo(String flightNo) {
		this.flightNo = flightNo;
	}
	
	/**
	 * @return the addFeeList
	 */
	public List<OprValueAddFee> getAddFeeList() {
		return addFeeList;
	}

	/**
	 * @param addFeeList the addFeeList to set
	 */
	public void setAddFeeList(List<OprValueAddFee> addFeeList) {
		this.addFeeList = addFeeList;
	}

	public String getInfo(){
		return "getInfo";
	}
	
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	public String getDnos() {
		return dnos;
	}

	public void setDnos(String dnos) {
		this.dnos = dnos;
	}

	/**
	 * �ۺϺ���š�����ʱ���Ʊ����
	 * @return
	 */
	public String updateFlight(){
		try {
	 		oprFaxInService.updateFlight(getPksByIds(),oprFaxIn,this.isFlyLate);
    		getValidateInfo().setMsg("���ݱ���ɹ���");
    		addMessage("���ݱ���ɹ���");
        } catch (Exception e) {
        	getValidateInfo().setSuccess(false);
        	getValidateInfo().setMsg("���ݱ���ʧ�ܣ�");
            addError("���ݱ���ʧ�ܣ�", e);
        }
		return RELOAD;
	}
	
	/**
	 * ���������źͺ���Ų�ѯ������Ϣ
	 * @return
	 */
	public String findMainMsg(){
		try {
			setPageConfig();
			oprFaxInService.findMainMsgByMainNo(flightMainNo, flightNo,this.getPages());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "list";
	}
	
	/**
	 * ������ϸ
	 * @return
	 */
	public String save(){
		try {
			User user=WebRalasafe.getCurrentUser(Struts2Utils.getRequest());
			Date dt = new Date();
			if(null==oprFaxIn.getDno()){
				oprFaxIn.setCreateTime(dt);
				oprFaxIn.setCreateName(user.get("name")+"");
			}
			oprFaxIn.setUpdateTime(dt);
			oprFaxIn.setUpdateName(user.get("name")+"");
			
			FaxReturnMsg msg=oprFaxInService.saveFaxDetail(oprFaxIn,addFeeList,requestList);
			if(msg.getReturnMsg().equals("true")){
				super.getValidateInfo().setSuccess(true);
				super.getValidateInfo().setMsg("����ɹ���");
			}else{
				super.getValidateInfo().setSuccess(false);
				super.getValidateInfo().setMsg("����ʧ��,ʧ��ԭ��:"+msg.getReturnMsg());
				return "reload";
			}
			EdiFaxInVo ediFaxInVo = new EdiFaxInVo();
			BeanUtils.copyProperties(oprFaxIn, ediFaxInVo);
			ediFaxInVo.setDno(msg.getDno());
			ediFaxInVo.setConsigneeId(msg.getConsigneeId());
			
			//�������ת������EDIд������
			if(edi_distributionMode.equals(oprFaxIn.getDistributionMode())){
				try{
					this.oprFaxInService.insertEdiDataService(ediFaxInVo,requestList);
			    }catch (Exception e) {}
			}
		} catch (Exception e) {
			super.addError("����ʧ��,ʧ��ԭ��:"+e.getLocalizedMessage(), e);
			return "reload";
		}
		return "reload";
	}
	
	/**
	 * �ۺϲ�ѯ
	 * @return
	 */
	public String inquery(){
		try {
			setPageConfig();
			OprFaxInQueryVo inQueryVo = olist.get(0);
			oprFaxInService.queryCondition(1l,getPages(), inQueryVo,sort,dir);
			super.getValidateInfo().setSuccess(true);
			super.getValidateInfo().setMsg("��ѯ�����ɹ�");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("��������");
			super.getValidateInfo().setSuccess(false);
			super.getValidateInfo().setMsg("����ʧ��,ʧ��ԭ��:"+e.getLocalizedMessage());
			return "reload";
		}
		return LIST;
	}
	
	/**
	 * �ۺϲ�ѯ����
	 * @return
	 */
	public String inqueryTotal(){
		try {
			setPageConfig();
			OprFaxInQueryVo inQueryVo = olist.get(0);
			oprFaxInService.queryCondition(2l,getPages(), inQueryVo,null,null);
			List list =getPages().getResult();
			OprFaxInQueryVo oprFaxInQueryVo=(OprFaxInQueryVo) list.get(0);
			Struts2Utils.renderJson(oprFaxInQueryVo);
			super.getValidateInfo().setSuccess(true);
			super.getValidateInfo().setMsg("��ѯ�����ɹ�");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("��������");
			super.getValidateInfo().setSuccess(false);
			super.getValidateInfo().setMsg("����ʧ��,ʧ��ԭ��:"+e.getLocalizedMessage());
		}
		return null;
	}
	
	
	/**
	 * �ۺϲ�ѯ,��ѯһ��������Ϣ
	 * @return
	 */
	public String inqueryOne(){
		try {
			setPageConfig();
			OprFaxInQueryVo inQueryVo = new OprFaxInQueryVo();
			inQueryVo.setDno(dno);
			inQueryVo.setRightDepartId(rightDepart);
			oprFaxInService.queryCondition(1l,getPages(), inQueryVo,null,null);
			List list =getPages().getResult();
			OprFaxInQueryVo oprFaxInQueryVo=(OprFaxInQueryVo) list.get(0);
			Struts2Utils.renderJson(oprFaxInQueryVo);
			
			super.getValidateInfo().setSuccess(true);
			super.getValidateInfo().setMsg("��ѯ�����ɹ�");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("��������");
			super.getValidateInfo().setSuccess(false);
			super.getValidateInfo().setMsg("����ʧ��,ʧ��ԭ��:"+e.getLocalizedMessage());
			return "reload";
		}
		return null;
	}
/*
	public String  inquerySum(){
			try {
				setPageConfig();
				OprFaxInQueryVo oprVo=new OprFaxInQueryVo();
				
				oprVo.setGoodsstatus("���ܣ�");
				if(!"".equals(getIds())&&getIds()!=null){
						String sql = oprFaxInService.getQuerySumSql(getIds());
						Query query=oprFaxInService.createSQLQuery( sql);
						List <Map>list =query.list();
						
						for(Map totalMap : list ){
							oprVo.setDno(totalMap.get("DNO")==null?0l:Long.parseLong(String.valueOf(totalMap.get("DNO"))));
							oprVo.setPiece(totalMap.get("PIECE")==null?0l:Long.parseLong(String.valueOf(totalMap.get("PIECE"))));
							oprVo.setStockpiece(totalMap.get("REALPIECE")==null?0l:Long.parseLong(String.valueOf(totalMap.get("REALPIECE"))));
							oprVo.setCusweight(totalMap.get("CUSWEIGHT")==null?0:Double.parseDouble(String.valueOf(totalMap.get("CUSWEIGHT"))));
							oprVo.setBulk((totalMap.get("BULK")==null?0:Double.parseDouble(String.valueOf(totalMap.get("BULK")))));
						}
						Struts2Utils.renderJson(oprVo);
				}else{
					oprVo.setDno(0l);
					oprVo.setPiece(0l);
					oprVo.setStockpiece(0l);
					oprVo.setCusweight(0.00);
					oprVo.setBulk(0.0);
					Struts2Utils.renderJson(oprVo);
					}
			} catch (Exception e) {
				e.printStackTrace();
			}
		
		return null;
	}
	*/
	public String inputQuery(){
		return "inputQuery";
	}

	public OprFaxIn getOprFaxIn() {
		return oprFaxIn;
	}

	public void setOprFaxIn(OprFaxIn oprFaxIn) {
		this.oprFaxIn = oprFaxIn;
	}

	public Long getDno() {
		return dno;
	}

	public void setDno(Long dno) {
		this.dno = dno;
	}

	public OprFaxInVo getOprFaxInVo() {
		return oprFaxInVo;
	}

	public void setOprFaxInVo(OprFaxInVo oprFaxInVo) {
		this.oprFaxInVo = oprFaxInVo;
	}
	/**
	 * @return the requestList
	 */
	public List<OprRequestDo> getRequestList() {
		return requestList;
	}

	/**
	 * @param requestList the requestList to set
	 */
	public void setRequestList(List<OprRequestDo> requestList) {
		this.requestList = requestList;
	}
	
	/**
	 * @return the cusId
	 */
	public Long getCusId() {
		return cusId;
	}

	/**
	 * @param cusId the cusId to set
	 */
	public void setCusId(Long cusId) {
		this.cusId = cusId;
	}

	public List<OprFaxInQueryVo> getOlist() {
		return olist;
	}

	public void setOlist(List<OprFaxInQueryVo> olist) {
		this.olist = olist;
	}

	public Long getRightDepart() {
		return rightDepart;
	}

	public void setRightDepart(Long rightDepart) {
		this.rightDepart = rightDepart;
	}
	
	/**
	 * @return the cusName
	 */
	public String getCusName() {
		return cusName;
	}

	/**
	 * @param cusName the cusName to set
	 */
	public void setCusName(String cusName) {
		this.cusName = cusName;
	}

	/**
	 * @return the cusTel
	 */
	public String getCusTel() {
		return cusTel;
	}

	/**
	 * @param cusTel the cusTel to set
	 */
	public void setCusTel(String cusTel) {
		this.cusTel = cusTel;
	}
	
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	
	/**
	 * @return the consignee
	 */
	public String getConsignee() {
		return consignee;
	}

	/**
	 * @param consignee the consignee to set
	 */
	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}
    
	/**
	 * @return the startTime
	 */
	public Date getStartTime() {
		return startTime;
	}

	/**
	 * @param startTime the startTime to set
	 */
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	/**
	 * @return the endTime
	 */
	public Date getEndTime() {
		return endTime;
	}

	/**
	 * @param endTime the endTime to set
	 */
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	/**
	 * @return the pub_flightMainNo
	 */
	public String getPub_flightMainNo() {
		return pub_flightMainNo;
	}

	/**
	 * @param pub_flightMainNo the pub_flightMainNo to set
	 */
	public void setPub_flightMainNo(String pub_flightMainNo) {
		this.pub_flightMainNo = pub_flightMainNo;
	}

	public Long getIsFlyLate() {
		return isFlyLate;
	}

	public void setIsFlyLate(Long isFlyLate) {
		this.isFlyLate = isFlyLate;
	}
	
	

	public String getDateType() {
		return dateType;
	}

	public void setDateType(String dateType) {
		this.dateType = dateType;
	}

	/**
	 * �ջ�����Ϣͳ��
	 * @return
	 */
	public String findCusInfo(){
		this.setPageConfig();
		try {
			oprFaxInService.findCusInfo(this.getPages(), cusName, cusTel, cusId,type,consignee, startTime, endTime);
		} catch (Exception e) {
			addError("��������", e);
			logger.error("��������");
			super.getValidateInfo().setSuccess(false);
			super.getValidateInfo().setMsg("����ʧ��,ʧ��ԭ��:"+e.getLocalizedMessage());
			return "reload";
		}
		return LIST;
	}
	public String inquerySum(){
		try {
			setPageConfig();
			oprFaxInService.getSumInfoByMainno(this.getPages(),flightMainNo,createTime);
			Map mapOld=this.getPages().getResultMap().get(0);
			Map map=new HashMap();
			map.put("flightMainNo", mapOld.get("SUM_TICKET")==null?"":"Ʊ��"+mapOld.get("SUM_TICKET"));
			
			map.put("cpFee", mapOld.get("SUM_CPFEE")==null?"":"Ԥ(Ԫ)��"+mapOld.get("SUM_CPFEE"));
			map.put("consigneeFee", mapOld.get("SUM_CONSIGNEEFEE")==null?"":"��(Ԫ)��"+mapOld.get("SUM_CONSIGNEEFEE"));
			map.put("paymentCollection", mapOld.get("SUM_PAY")==null?"":"��(Ԫ)��"+mapOld.get("SUM_PAY"));
			map.put("piece", mapOld.get("SUM_PIECE")==null?"":"����"+mapOld.get("SUM_PIECE"));
			map.put("cqWeight", mapOld.get("SUM_WEIGHT")==null?"": "��(KG)��"+mapOld.get("SUM_WEIGHT"));
			map.put("dno", "<span style='color:red'>�ϼ�:</span>");
			Struts2Utils.renderJson(map);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("��ѯ����");
			super.getValidateInfo().setSuccess(false);
			super.getValidateInfo().setMsg(
					"��ѯʧ��,ʧ��ԭ��:" + e.getLocalizedMessage());
			return "reload";
		}
		return null;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getDir() {
		return dir;
	}

	public void setDir(String dir) {
		this.dir = dir;
	}
	
	public String refreshCus(){
		try {
			oprFaxInService.customerServiceAdjust(dnos);
			this.getValidateInfo().setSuccess(true);
			this.getValidateInfo().setMsg("ͬ���ɹ�");
		} catch (Exception e) {
			addError("ͬ���ͷ�Ա����", e);
		}
		return RELOAD;
	}
}
