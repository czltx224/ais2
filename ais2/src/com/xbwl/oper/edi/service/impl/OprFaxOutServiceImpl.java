package com.xbwl.oper.edi.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xbwl.common.exception.ServiceException;
import com.xbwl.common.log.anno.ModuleName;
import com.xbwl.common.orm.IBaseDAO;
import com.xbwl.common.orm.Page;
import com.xbwl.common.service.impl.BaseServiceImpl;
import com.xbwl.common.utils.LogType;
import com.xbwl.entity.CtTmD;
import com.xbwl.entity.OprFaxOut;
import com.xbwl.entity.OprRequestDo;
import com.xbwl.oper.edi.dao.IOprFaxOutDao;
import com.xbwl.oper.edi.service.ICtTmDService;
import com.xbwl.oper.edi.service.IOprFaxOutService;
import com.xbwl.oper.stock.service.IOprRequestDoService;
import com.xbwl.ws.client.IWSEdiToAisRemote;
import com.xbwl.ws.client.result.base.WSResult;

import dto.CtTmdDto;

/**
 * @author LiuHao
 * @time Apr 16, 2012 4:36:32 PM
 */
@Service("oprFaxOutServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class OprFaxOutServiceImpl extends BaseServiceImpl<OprFaxOut, Long>
		implements IOprFaxOutService {

	@Resource(name = "wsEdiToAisRemote")
	private IWSEdiToAisRemote wsEdiToAisRemote;

	@Resource(name = "oprFaxOutHibernateDaoImpl")
	private IOprFaxOutDao oprFaxOutDao;

	@Resource(name = "ctTmDServiceImpl")
	private ICtTmDService ctTmDService;
	
	@Resource(name="oprRequestDoServiceImpl")
	private IOprRequestDoService oprRequestDoService;

	@Override
	public IBaseDAO getBaseDao() {
		return oprFaxOutDao;
	}

	@ModuleName(value="AISɨ��OPR_FAX_OUT��д��EDI",logType=LogType.buss)
	public void faxChangeScanning() throws Exception {
		Page pg = new Page();
		pg.setStart(0);// ��ʼҳ��
		pg.setLimit(100);// ��ҳ��С
		String hql = "from OprFaxOut";
		Page<OprFaxOut> page = this.oprFaxOutDao.findPage(pg, hql);

		for (int i = 1; i <= page.getTotalPages(); i++) {
			List<OprFaxOut> list = page.getResult();
			if (null != list && list.size() > 0) {
				for (int j = 0; j < list.size(); j++) {
					OprFaxOut faxOut = list.get(j);
					List<CtTmD> tmdList = this.ctTmDService.findBy("DNo",
							faxOut.getDno()+"");
					if (null != tmdList && tmdList.size() > 0) {
						return;
					}
					List<OprRequestDo> requestList = this.oprRequestDoService.findBy("dno", faxOut.getDno());
					String request ="";
					String signRequest ="";
					if(null!=requestList && requestList.size()>0){
						for (int k = 0; k < requestList.size(); k++) {
							OprRequestDo req = requestList.get(k);
							if("�ͻ�".equals(req.getRequestStage())){
								request+=req.getRequest()+"/";
							}else if("ǩ��".equals(req.getRequestStage())){
								signRequest+=req.getRequest()+"/";
							}
						}
					}
					CtTmdDto dto = new CtTmdDto();
					dto.setConsignId(faxOut.getConsignId());
						
					try {
						dto.setCtId(Long.valueOf(faxOut.getEdiUserId()));
					} catch (Exception e) {
						this.delete(faxOut);
						continue;
					}
					dto.setCtName(faxOut.getGowhere());
					dto.setCubage(faxOut.getBulk());
					dto.setCustomerServiceName(faxOut.getCustomerService());
					dto.setDirverClockOutTime(faxOut.getOutTime());// ��������ʱ��
					dto.setDnAmt(faxOut.getConsigneeFee());
					dto.setDnAmtChange(0d);
					dto.setDNo(faxOut.getDno() + "");
					dto.setDnside(faxOut.getWhoCash());// Ԥ������
					dto.setEndpayAmt(faxOut.getPaymentCollection());
					dto.setExceptionFlag("0");
					dto.setFlyTime(faxOut.getFlightTime());
					dto.setGoods(faxOut.getGoods());
					dto.setGoodStatus("1");
					dto.setIsvaluables("0");
					dto.setOkFlag("0");
					dto.setPiece(faxOut.getPiece());
					dto.setReceAdd(faxOut.getAddr());
					dto.setReceCorp("");
					dto.setReceMan(faxOut.getConsignee());
					dto.setReceTel(faxOut.getConsigneeTel());
					dto.setRequest(request);//�ͻ�Ҫ��
					dto.setRemark(faxOut.getRemark());
					dto.setShfNo(faxOut.getFlightNo());
					dto.setSignFlag("0");
					dto.setSignType(faxOut.getReceiptType());
					dto.setStopflag("0");
					dto.setSustbillNo(faxOut.getSubNo());
					dto.setTakeMode(faxOut.getTakeMode());
					dto.setTraAmt(faxOut.getTraFee());
					dto.setTraCost(faxOut.getTraFeeRate());
					dto.setWeight(faxOut.getCusWeight());
					dto.setYdNo(faxOut.getFlightMainNo());
					dto.setCreateName("��ʱ����");
					dto.setSignRequest(signRequest);//ǩ��Ҫ��
					dto.setSignType(faxOut.getReceiptType());//ǩ������
					dto.setDeptName(faxOut.getInDepartId()==457l?"����":"����");
					dto.setIsSp(faxOut.getSonderzug()==null?"0":faxOut.getSonderzug()+"");//�Ƿ�ר��
					dto.setIsurgent(faxOut.getUrgentService()==null?"0":faxOut.getUrgentService()+"");//�Ƿ�Ӽ�
					dto.setStatus(1l);//STATUS 1��������0������ʱ����Ҫɾ�����ж�����
					
					//WSResult rs = null;
					WSResult rs = this.wsEdiToAisRemote.saveToCtTmd(dto);
					if(null==rs){
						throw new ServiceException("EDI��Ŀû��������");
					}else if(WSResult.SUCCESS.equals(rs.getCode())) {
						this.delete(faxOut);
					}else{
						throw new ServiceException(rs.getMessage());
					}
				}
				if (page.getPageNo() > i) {
					// pg.setStart(i-1);ɾ��֮����һҳ�Ϳ�����
					page = this.oprFaxOutDao.findPage(pg, hql);
				}else{
					break;
				}
			}
		}
	}
}
