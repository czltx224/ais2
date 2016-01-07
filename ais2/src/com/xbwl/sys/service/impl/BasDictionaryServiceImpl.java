package com.xbwl.sys.service.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdcn.bpaf.common.exception.ServiceException;
import com.xbwl.common.orm.IBaseDAO;
import com.xbwl.common.service.impl.BaseServiceImpl;
import com.xbwl.entity.BasDictionary;
import com.xbwl.sys.dao.IBasDictionaryDao;
import com.xbwl.sys.service.IBasDictionaryService;

@Service
@Transactional(rollbackFor={Exception.class})
public class BasDictionaryServiceImpl extends BaseServiceImpl<BasDictionary,Long> 
implements IBasDictionaryService{

	@Resource
	private IBasDictionaryDao  dictionaryDao;
	
	@Override
	public IBaseDAO<BasDictionary, Long> getBaseDao() {
		return dictionaryDao;
	}

	public String findOverRalaList(Map<String, String> map)
			throws ServiceException {
		String EQL_basDictionaryId = map.get("EQL_basDictionaryId");
		
		StringBuffer sb = new StringBuffer();
			sb.append("select id,type_name typeName,type_code typeCode,t.dictionary_id from bas_dictionary_detail t");
			
		if(null!=EQL_basDictionaryId && !"".equals(EQL_basDictionaryId)){
			sb.append(" where t.dictionary_id=:EQL_basDictionaryId");
		}
		
		return sb.toString();
	}

}
