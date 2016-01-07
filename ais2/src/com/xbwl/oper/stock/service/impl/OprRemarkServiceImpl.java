package com.xbwl.oper.stock.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.xbwl.common.exception.ServiceException;
import com.xbwl.common.log.anno.ModuleName;
import com.xbwl.common.orm.IBaseDAO;
import com.xbwl.common.service.impl.BaseServiceImpl;
import com.xbwl.common.utils.LogType;
import com.xbwl.common.web.struts2.Struts2Utils;
import com.xbwl.entity.OprFaxIn;
import com.xbwl.entity.OprRemark;
import com.xbwl.oper.fax.dao.IOprFaxInDao;
import com.xbwl.oper.fax.service.IOprFaxInService;
import com.xbwl.oper.stock.dao.IOprRemarkDao;
import com.xbwl.oper.stock.service.IOprRemarkService;

/**
 * author CaoZhili time Jul 19, 2011 4:13:11 PM
 * 
 * ��ע��¼�����ʵ����
 */
@Service("oprRemarkServiceImpl")
@Transactional(rollbackFor = { Exception.class })
public class OprRemarkServiceImpl extends BaseServiceImpl<OprRemark, Long>
		implements IOprRemarkService {

	@Resource(name = "oprRemarkHibernateDaoImpl")
	private IOprRemarkDao oprRemarkDao;
	
	@Resource(name = "oprFaxInHibernateDaoImpl")
	private IOprFaxInDao oprFaxInDao;
	
	
	@Override
	public IBaseDAO<OprRemark, Long> getBaseDao() {

		return this.oprRemarkDao;
	}

	@Override
	@ModuleName(value="�޸ı�ע��ͬ���������",logType=LogType.buss)
	public void save(OprRemark entity) {

		OprFaxIn fax = oprFaxInDao.get(entity.getDno());
		//�޸Ĵ����ı�ע
		if(null!=fax){
			if(null!=fax.getRemark() && !"".equals(fax.getRemark())){
				fax.setRemark(fax.getRemark()+"/"+entity.getRemark());
			}else{
				fax.setRemark(entity.getRemark());
			}
			oprFaxInDao.save(fax);
			oprRemarkDao.save(entity);
		}
	}

	//�ۺϲ�ѯ��Ʊ�������һ����ע
	public void saveRemarks(List<Long> dnos, String remark) throws Exception {
		//Assert.notNull(dnos);
		if(dnos==null||dnos.size()==0||remark==null||"".equals(remark)){
			throw new ServiceException("�ύ���������ݲ���Ϊ��");
		}
		for(Long dno:dnos){
			OprRemark oRemark = new OprRemark();
			oRemark.setDno(dno);
			oRemark.setRemark(remark);
			save(oRemark);
		}
	}

	public void saveRemark(Long dno, String remark) throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	

}
