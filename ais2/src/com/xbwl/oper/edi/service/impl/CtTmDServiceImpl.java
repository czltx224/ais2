package com.xbwl.oper.edi.service.impl;

import java.util.Date;
import java.util.List;

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
import com.xbwl.entity.CtTmD;
import com.xbwl.entity.OprFaxIn;
import com.xbwl.oper.edi.dao.ICtTmDDao;
import com.xbwl.oper.edi.service.ICtTmDService;
import com.xbwl.oper.fax.service.IOprFaxInService;
import com.xbwl.sys.service.ICustomerService;
import com.xbwl.ws.client.IWSEdiToAisRemote;
import com.xbwl.ws.client.result.base.WSResult;

import dto.CtTmdDto;

/**
 * EDI主表信息服务层实现类
 * 
 * @project ais2
 * @author czl
 * @Time Feb 13, 2012 3:29:06 PM
 */
@Service("ctTmDServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class CtTmDServiceImpl extends BaseServiceImpl<CtTmD, String> implements
		ICtTmDService {

	@Resource(name = "ctTmDHibernateDaoImpl")
	private ICtTmDDao ctTmDDao;

	@Resource(name = "wsEdiToAisRemote")
	private IWSEdiToAisRemote wsEdiToAisRemote;
	
	@Value("${opr.edi_distributionMode}")
	private String edi_distributionMode;
	
	@Resource(name="oprFaxInServiceImpl")
	private IOprFaxInService oprFaxInService;
	
	@Resource(name="customerServiceImpl")
	private ICustomerService customerService;

	@Override
	public IBaseDAO<CtTmD, String> getBaseDao() {
		return this.ctTmDDao;
	}

	@Override
	public void save(CtTmD entity) {
		List<CtTmD> list = this.ctTmDDao.findBy("DNo", entity.getDNo());
		if (null != list && list.size() > 0) {// 如果存在此配送单号，则删除
			for (int i = 0; i < list.size(); i++) {
				this.ctTmDDao.delete(list.get(i));
			}
		}
		super.save(entity);
	}

	@ModuleName(value="AIS扫描CT_TM_D表写入EDI",logType=LogType.buss)
	public void scanningAISCtTmDService() throws Exception {
		Page pg = new Page();
		pg.setStart(0);// 起始页码
		pg.setLimit(100);// 分页大小
		String hql = "from CtTmD";
		Page<CtTmD> page = this.ctTmDDao.findPage(pg, hql);
		for (int i = 1; i <= page.getTotalPages(); i++) {
			List<CtTmD> list = page.getResult();
			if (null != list && list.size() > 0) {
				try {
					for (int j = 0; j < list.size(); j++) {
						CtTmdDto dto = new CtTmdDto();
						CtTmD entity = list.get(j);
						BeanUtils.copyProperties(entity, dto,
								new String[] { "ctDNo" });
						dto.setCtDNo(null);
						dto.setCreateTime(new Date());
						dto.setCreateName("定时任务");
						WSResult rs = this.wsEdiToAisRemote.saveToCtTmd(dto);
						if(null==rs){
							throw new ServiceException("EDI项目没有启动！");
						}else if (WSResult.SUCCESS.equals(rs.getCode())) {
							this.delete(entity);
						} else {
							throw new ServiceException(rs.getMessage());
						}
					}
					if (page.getPageNo() > i) {
						// pg.setStart(i-1);删除之后查第一页就可以了
						page = this.ctTmDDao.findPage(pg, hql);
					}else{
						break;
					}
				} catch (Exception e) {
					e.printStackTrace();
					throw new ServiceException("定时任务扫描CT_TM_D表失败!");
				}
			}
		}
	}

	//作废EDI临时信息主表
	public void deleteStatusCtTmD(Long dno) throws Exception {
		List<CtTmD> tmdlist = this.ctTmDDao.findBy("DNo", dno+"");
		
		if(null==dno){
			throw new ServiceException("配送单号为空！");
		}
		
		if(null!=tmdlist && tmdlist.size()>0){
			CtTmD tmd = tmdlist.get(0);
			tmd.setStatus(0l);//0表示需要删除
			this.ctTmDDao.save(tmd);
		}else{
			CtTmD tmd = new CtTmD();
			OprFaxIn faxIn = this.oprFaxInService.get(dno);
			if(!this.edi_distributionMode.equals(faxIn.getDistributionMode())){
				throw new ServiceException("配送单号为 "+dno+"的货物不是中转的货物！");
			}
			try{
				tmd.setCtId(Long.valueOf(this.customerService.get(faxIn.getGoWhereId()).getEdiUserId()));
			}catch (Exception e) {
				throw new ServiceException("获取EDI编码获取失败！"); 
			}
			
			tmd.setDNo(faxIn.getDno()+"");
			tmd.setStopflag("0");
			tmd.setGoodStatus("1");
			tmd.setOkFlag("0");
			tmd.setSignFlag("0");
			tmd.setExceptionFlag("1");//1为否，0为是
			tmd.setIsvaluables("0");
			tmd.setCtName(faxIn.getGowhere());
			tmd.setCubage(faxIn.getBulk());
			tmd.setCustomerServiceName(faxIn.getCustomerService());
			tmd.setDnAmt(faxIn.getConsigneeFee());
			tmd.setDnAmtChange(0d);
			tmd.setEndpayAmt(faxIn.getPaymentCollection());
			tmd.setFlyTime(faxIn.getFlightTime());
			tmd.setGoods(faxIn.getGoods());
			tmd.setPiece(faxIn.getPiece());
			tmd.setReceAdd(faxIn.getAddr());
			tmd.setReceCorp("");
			tmd.setReceMan(faxIn.getConsignee());
			tmd.setReceTel(faxIn.getConsigneeTel());
			tmd.setShfNo(faxIn.getFlightNo());
			tmd.setSustbillNo(faxIn.getSubNo());
			tmd.setTakeMode(faxIn.getTakeMode().trim());
			tmd.setTraAmt(faxIn.getTraFee());
			tmd.setTraCost(faxIn.getTraFeeRate());
			tmd.setWeight(faxIn.getCusWeight());
			tmd.setYdNo(faxIn.getFlightMainNo());
			tmd.setSignType(faxIn.getReceiptType());//签单类型
			tmd.setDeptName(faxIn.getInDepartId()==457l?"广州":"深圳");
			tmd.setIsSp(faxIn.getSonderzug()==null?"0":faxIn.getSonderzug()+"");//是否专车
			tmd.setIsurgent(faxIn.getUrgentService()==null?"0":faxIn.getUrgentService()+"");//是否加急
			tmd.setStatus(0l);//STATUS 1：正常，0：保存时，需要删除的判断依据
			
			this.save(tmd);
		}
	}
}
