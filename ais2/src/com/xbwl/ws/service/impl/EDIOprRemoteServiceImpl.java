package com.xbwl.ws.service.impl;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.xbwl.entity.OprHistory;
import com.xbwl.entity.OprRemark;
import com.xbwl.entity.OprSign;
import com.xbwl.oper.stock.service.IOprHistoryService;
import com.xbwl.oper.stock.service.IOprRemarkService;
import com.xbwl.oper.stock.service.IOprSignService;
import com.xbwl.ws.client.result.base.WSResult;
import com.xbwl.ws.service.IEDIOprRemoteService;

import dto.OprHistoryDto;
import dto.OprRemarkDto;
import dto.OprSignDto;

/**
 * EDI远程调用实现类
 * @author czl
 * @date 2012-05-22
 */
@Component("ediOprRemoteServiceImpl")
public class EDIOprRemoteServiceImpl implements IEDIOprRemoteService {

	@Resource(name="oprSignServiceImpl")
	private IOprSignService oprSignService;
	
	@Resource(name="oprRemarkServiceImpl")
	private IOprRemarkService oprRemarkService;
	
	@Resource(name="oprHistoryServiceImpl")
	private IOprHistoryService oprHistoryService;
	
	public WSResult saveToOprSign(OprSignDto dto) {
		WSResult result = new WSResult();
		try{
			if(dto==null){
				result.setCode("2");
				result.setMessage("签收对象为空！");
				return result;
			}
			OprSign sign = new OprSign();
			BeanUtils.copyProperties(dto, sign,new String[]{"id"});
			this.oprSignService.saveTask(sign);
			// throw new ServiceException("test");
		}catch (Exception e) {
			e.printStackTrace();
			result.setCode("1");
			result.setMessage("写入AIS签收表失败！");
		}
		return result;
	}

	public WSResult saveNewRemark(OprRemarkDto dto) {
		WSResult result = new WSResult();
		try{
			if(dto==null){
				result.setCode("2");
				result.setMessage("备注对象为空！");
				return result;
			}
			OprRemark remark = new OprRemark();
			BeanUtils.copyProperties(dto, remark,new String[]{"id"});
			this.oprRemarkService.save(remark);
		}catch (Exception e) {
			e.printStackTrace();
			result.setCode("1");
			result.setMessage("备注更新失败！");
		}
		return result;
	}

	public WSResult saveToOprHistroy(OprHistoryDto dto) {
		WSResult result = new WSResult();
		try{
			if(dto==null){
				result.setCode("2");
				result.setMessage("历史记录对象为空！");
				return result;
			}
			OprHistory his = new OprHistory();
			BeanUtils.copyProperties(dto, his,new String[]{"id"});
			this.oprHistoryService.saveTransitLog(his);
		}catch (Exception e) {
			e.printStackTrace();
			result.setCode("1");
			result.setMessage("EDI日记记录写入失败！");
		}
		return result;
	}

}
