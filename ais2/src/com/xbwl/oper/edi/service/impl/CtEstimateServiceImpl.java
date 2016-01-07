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
 * EDI预计货量临时表服务层实现类
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
		if (null != list && list.size() > 0) {// 如果存在此配送单号，则删除
			for (int i = 0; i < list.size(); i++) {
				this.ctEstimateDao.delete(list.get(i));
			}
		}
		super.save(entity);
	}

	@ModuleName(value="AIS扫描CT_ESTIMATE表写入EDI",logType=LogType.buss)
	public void scanningCtEstimateService() throws Exception {
		Page pg = new Page();
		pg.setStart(0);// 起始页码
		pg.setLimit(100);// 分页大小
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
						dto.setCreateName("定时任务");
						WSResult rs = this.wsEdiToAisRemote
								.saveToCtEstimate(dto);
						if(null==rs){
							throw new ServiceException("EDI项目没有启动！");
						}else if(WSResult.SUCCESS.equals(rs.getCode())) {
							this.delete(entity);
						} else {
							throw new ServiceException(rs.getMessage());
						}
					}
					if (page.getPageNo() > i) {
						// pg.setStart(i-1);删除之后查第一页就可以了
						page = this.ctEstimateDao.findPage(pg, hql);
					}else{
						break;
					}
				} catch (Exception e) {
					e.printStackTrace();
					throw new ServiceException("定时任务扫描CT_ESTIMATE表失败!");
				}
			}
		}
	}
}
