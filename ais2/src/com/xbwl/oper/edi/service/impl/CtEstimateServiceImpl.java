package com.xbwl.oper.edi.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xbwl.common.exception.ServiceException;
import com.xbwl.common.log.anno.ModuleName;
import com.xbwl.common.orm.IBaseDAO;
import com.xbwl.common.orm.Page;
import com.xbwl.common.service.impl.BaseServiceImpl;
import com.xbwl.common.utils.LogType;
import com.xbwl.entity.CtEstimate;
import com.xbwl.entity.SysQianshou;
import com.xbwl.oper.edi.dao.ICtEstimateDao;
import com.xbwl.oper.edi.service.ICtEstimateService;
import com.xbwl.ws.client.IWSEdiToAisRemote;
import com.xbwl.ws.client.result.base.WSResult;

import dto.CtEstimateDto;

/**
 * EDIԤ�ƻ�����ʱ������ʵ����
 * 
 * @project ais2
 * @author czl
 * @Time Feb 9, 2012 5:07:30 PM
 */
@Service("ctEstimateServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class CtEstimateServiceImpl extends BaseServiceImpl<CtEstimate, String>
		implements ICtEstimateService {

	@Resource(name = "ctEstimateHibernateDaoImpl")
	private ICtEstimateDao ctEstimateDao;

	@Resource(name = "wsEdiToAisRemote")
	private IWSEdiToAisRemote wsEdiToAisRemote;

	@Override
	public IBaseDAO<CtEstimate, String> getBaseDao() {
		return this.ctEstimateDao;
	}

	@Override
	public void save(CtEstimate entity) {
		List<CtEstimate> list = this.ctEstimateDao.findBy("DNo", entity
				.getDNo());
		if (null != list && list.size() > 0) {// ������ڴ����͵��ţ���ɾ��
			for (int i = 0; i < list.size(); i++) {
				this.ctEstimateDao.delete(list.get(i));
			}
		}
		super.save(entity);
	}

	@ModuleName(value="AISɨ��CT_ESTIMATE��д��EDI",logType=LogType.buss)
	public void scanningCtEstimateService() throws Exception {
		Page pg = new Page();
		pg.setStart(0);// ��ʼҳ��
		pg.setLimit(100);// ��ҳ��С
		String hql = "from CtEstimate";
		Page<CtEstimate> page = this.ctEstimateDao.findPage(pg, hql);

		for (int i = 1; i <= page.getTotalPages(); i++) {
			List<CtEstimate> list = page.getResult();
			if (null != list && list.size() > 0) {
				try {
					for (int j = 0; j < list.size(); j++) {
						CtEstimateDto dto = new CtEstimateDto();
						CtEstimate entity = list.get(j);
						BeanUtils.copyProperties(entity, dto,
								new String[] { "EId" });
						dto.setEId(null);
						dto.setCreateTime(new Date());
						dto.setCreateName("��ʱ����");
						WSResult rs = this.wsEdiToAisRemote
								.saveToCtEstimate(dto);
						if(null==rs){
							throw new ServiceException("EDI��Ŀû��������");
						}else if(WSResult.SUCCESS.equals(rs.getCode())) {
							this.delete(entity);
						} else {
							throw new ServiceException(rs.getMessage());
						}
					}
					if (page.getPageNo() > i) {
						// pg.setStart(i-1);ɾ��֮����һҳ�Ϳ�����
						page = this.ctEstimateDao.findPage(pg, hql);
					}else{
						break;
					}
				} catch (Exception e) {
					e.printStackTrace();
					throw new ServiceException("��ʱ����ɨ��CT_ESTIMATE��ʧ��!");
				}
			}
		}
	}
}
