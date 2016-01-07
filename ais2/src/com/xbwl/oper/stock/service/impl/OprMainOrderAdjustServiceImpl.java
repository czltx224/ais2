package com.xbwl.oper.stock.service.impl;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdcn.bpaf.common.exception.ServiceException;
import com.xbwl.common.log.anno.ModuleName;
import com.xbwl.common.orm.IBaseDAO;
import com.xbwl.common.orm.Page;
import com.xbwl.common.service.impl.BaseServiceImpl;
import com.xbwl.common.utils.LogType;
import com.xbwl.entity.BasFlight;
import com.xbwl.entity.OprFaxIn;
import com.xbwl.entity.OprFaxMain;
import com.xbwl.entity.OprMainOrderAdjust;
import com.xbwl.entity.OprStatus;
import com.xbwl.finance.Service.ICqCorporateRateService;
import com.xbwl.finance.vo.FaxrateSerarchVo;
import com.xbwl.oper.fax.service.IOprFaxInService;
import com.xbwl.oper.fax.service.IOprFaxMainService;
import com.xbwl.oper.stock.dao.IOprMainOrderAdjustDao;
import com.xbwl.oper.stock.dao.IOprStatusDao;
import com.xbwl.oper.stock.service.IOprHistoryService;
import com.xbwl.oper.stock.service.IOprMainOrderAdjustService;
import com.xbwl.oper.stock.vo.OprMainOrderAdjustVo;
import com.xbwl.sys.service.IBasFlightService;

/**
 * @author CaoZhili time Aug 8, 2011 9:57:46 AM
 * 
 * 主单调整记录表服务层实现类
 */
@Service("oprMainOrderAdjustServiceImpl")
@Transactional(rollbackFor = { Exception.class })
public class OprMainOrderAdjustServiceImpl extends
		BaseServiceImpl<OprMainOrderAdjust, Long> implements
		IOprMainOrderAdjustService {

	@Resource(name="oprMainOrderAdjustHibernateDaoImpl")
	private IOprMainOrderAdjustDao oprMainOrderAdjustDao;
	
	@Resource(name = "oprFaxInServiceImpl")
	private IOprFaxInService oprFaxInService;
	
	@Resource(name="oprFaxMainServiceImpl")
	private IOprFaxMainService oprFaxMainService;
	
	@Resource(name="oprHistoryServiceImpl")
	private IOprHistoryService oprHistoryService;
	
	@Value("${oprMainOrderAdjustAction.weightAdjust}")
	private Long weightAdjust;
	
	@Value("${oprMainOrderAdjustAction.mainNoAdjust}")
	private Long mainNoAdjust;
	
	@Value("${oprMainOrderAdjustAction.consigneeFeeAdjust}")
	private Long consigneeFeeAdjust;
	
	@Resource(name="oprStatusHibernateDaoImpl")
	private IOprStatusDao oprStatusDao;
	
	@Resource(name="cqCorporateRateServiceImpl")
	private ICqCorporateRateService cqCorporateRateService;
	
	@Resource(name="basFilghtServiceImpl")
	private IBasFlightService basFlightService;
	
	@Override
	public IBaseDAO<OprMainOrderAdjust, Long> getBaseDao() {

		return this.oprMainOrderAdjustDao;
	}

	@ModuleName(value="主单及货量调整",logType=LogType.buss)
	public void updateMainNoAndWeightService(List<OprMainOrderAdjustVo> mainIds)
			throws Exception {
		OprFaxIn oprFax=null;
		String oldFlightMainNo=null;
		OprMainOrderAdjust entity=null;
		OprStatus oprStatus = null;
		for(int i=0;i<mainIds.size();i++){
			Long dno = mainIds.get(i).getDno();
			oprFax=this.oprFaxInService.get(dno);
			oprStatus = this.oprStatusDao.findStatusByDno(dno);
			Double consigneeFee = oprFax.getConsigneeFee();
			//已经签收了就不允许再更改
			if(null!=oprStatus.getSignStatus() && oprStatus.getSignStatus()>0l){
				throw new ServiceException("配送单号为"+dno+"的货物已经签收，不允许再更改。");
			}
			
			oldFlightMainNo=oprFax.getFlightMainNo();
			entity=new OprMainOrderAdjust();
			
			entity.setOldConsigneeFee(oprFax.getConsigneeFee());
			entity.setOldFlightMainNo(oprFax.getFlightMainNo());
			entity.setOldWeight(oprFax.getCusWeight());
			entity.setConsigneeRate(oprFax.getConsigneeRate());
			entity.setDno(oprFax.getDno());
			entity.setSubNo(oprFax.getSubNo());
			
			if(null!=mainIds.get(i).getNewWeight()&& mainIds.get(i).getNewWeight()>=oprFax.getCusWeight()){
				this.oprHistoryService.saveLog(oprFax.getDno(), "重量从"+oprFax.getCusWeight()+"KG调整到"+mainIds.get(i).getNewWeight()+"KG", this.weightAdjust);
				List<BasFlight> basFlightList = this.basFlightService.findBy("flightNumber",oprFax.getFlightNo());
				String startCity = null;
				if(null!=basFlightList && basFlightList.size()>0){
					startCity = basFlightList.get(0).getEndCity();
				}else{
					throw new ServiceException("没有找到对应的航班号，请在基础资料中进行维护！");
				}
				Page page = new Page();
				page.setStart(0);
				page.setLimit(20);
				FaxrateSerarchVo vo = new FaxrateSerarchVo();
				vo.setAddrType(oprFax.getAreaType());
				vo.setBulk(oprFax.getBulk());
				vo.setCity(oprFax.getCity());
				vo.setTown(oprFax.getTown());
				vo.setConsigneeTel(new String[]{oprFax.getConsigneeTel()});
				vo.setCpId(oprFax.getGoWhereId());
				vo.setCusId(oprFax.getCusId());
				vo.setCusName(oprFax.getCpName());
				vo.setCusWeight(oprFax.getCusWeight());
				vo.setDistributionMode(oprFax.getDistributionMode());
				vo.setPiece(oprFax.getPiece());
				vo.setStartCity(startCity);
				vo.setStreet(oprFax.getStreet());
				vo.setTakeMode(oprFax.getTakeMode());
				vo.setTown(oprFax.getTown());
				vo.setTrafficMode(oprFax.getTrafficMode());
				vo.setValuationType(oprFax.getValuationType());
				vo.setWhoCash(oprFax.getWhoCash());
				
				Page returnPage = this.cqCorporateRateService.findRate(page, vo);
				HashMap<String,String> map = (HashMap)returnPage.getResultMap().get(0);
				
			//	Double newFee =  Double.parseDouble(map.get(""));
					
				// AppendConditions.formatDouble((mainIds.get(i).getNewWeight()/oprFax.getCusWeight())*oprFax.getConsigneeFee(),2);
				
				Double newFee = 0.0;
				Object conFee = map.get("conFee");
				Object stFee = map.get("stFee");
				if(conFee != null){
					newFee = Double.parseDouble(conFee.toString());
				}else if(stFee!=null){
					newFee = Double.parseDouble(stFee.toString());
				}
				
				if(newFee == 0){
					//price = oprFax.getConsigneeFee();
					throw new ServiceException("未找到对应协议价！");
				}
				
				oprFax.setCusWeight(mainIds.get(i).getNewWeight());
				mainIds.get(i).setNewConsigneeFee(newFee);
				
				if(null!=newFee && newFee>oprFax.getConsigneeFee() && oprFax.getConsigneeFee()>0){
					this.oprHistoryService.saveLog(oprFax.getDno(), "提送费从"+oprFax.getConsigneeFee()+"元调整到"+newFee+"元,调整金额为"+(newFee-oprFax.getConsigneeFee())+"元。", this.consigneeFeeAdjust);
					oprFax.setConsigneeFee(newFee);
				}
			}else if(null==mainIds.get(i).getNewWeight() || 0d==mainIds.get(i).getNewWeight()){
				//如果没有填写重量，则不做修改记录
			}else{
				throw new ServiceException("重量不允许调小！");
			}
			if(null!=mainIds.get(i).getNewFlightMainNo() && mainIds.get(i).getNewFlightMainNo().length()>0){
				this.oprHistoryService.saveLog(oprFax.getDno(), "主单号从"+oprFax.getFlightMainNo()+"调整到"+mainIds.get(i).getNewFlightMainNo(), this.mainNoAdjust);
				oprFax.setFlightMainNo(mainIds.get(i).getNewFlightMainNo());
			}
			
			entity.setAdjustMoney(oprFax.getConsigneeFee()-consigneeFee);
			entity.setNewConsigneeFee(oprFax.getConsigneeFee());
			entity.setNewFlightMainNo(oprFax.getFlightMainNo());
			entity.setNewWeight(oprFax.getCusWeight());
			
			
			//修改传真表的主单号主单号、重量和提送费
			//this.oprFaxInService.save(oprFax);
			
			this.oprMainOrderAdjustDao.save(entity);
			
			//保存到OPR_FAX_MAIN表
			this.saveFaxMain(oldFlightMainNo,oprFax, entity);
		}
	}
	
	private void saveFaxMain(String oldFlightMainNo,OprFaxIn fax,OprMainOrderAdjust oprMainOrderAdjust)throws Exception{
		
		List<OprFaxMain> list=this.oprFaxMainService.findBy("flightMainNo", fax.getFlightMainNo());
		OprFaxMain entity=null;
		if(null==list || list.size()==0){
			//新增  
			OprFaxMain obj=this.oprFaxMainService.getTotalByFlightMainNo(fax.getFlightMainNo());
			entity=new OprFaxMain();
			entity.setFlightMainNo(fax.getFlightMainNo());
			entity.setRealWeight(obj.getTotalWeight());
			entity.setTotalPiece(obj.getTotalPiece());
			entity.setTotalWeight(obj.getTotalWeight());
			
			this.oprFaxMainService.save(entity);
			
			//--- 修改传真表中的 faxMainId
			fax.setFaxMainId(entity.getId());
			this.oprFaxInService.save(fax);
			
		}else{
			//修改
			entity=list.get(0);
			entity.setFlightMainNo(fax.getFlightMainNo());
			entity.setTotalWeight(entity.getTotalWeight()+fax.getCusWeight());
			this.oprFaxMainService.save(entity);
			//修改................被改动的数据
			
			List<OprFaxMain> mainlist = this.oprFaxMainService.findBy("flightMainNo", oldFlightMainNo);
			if(null!=mainlist && mainlist.size()>0){
				OprFaxMain main=mainlist.get(0);
				main.setTotalWeight(main.getTotalWeight()-fax.getCusWeight());
				if(!oldFlightMainNo.equals(main.getFlightMainNo())){
					this.oprFaxMainService.save(main);
				}
			}
		}
	}

}
