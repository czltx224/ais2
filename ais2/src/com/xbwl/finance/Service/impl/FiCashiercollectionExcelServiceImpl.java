package com.xbwl.finance.Service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.ralasafe.WebRalasafe;
import org.ralasafe.user.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xbwl.common.exception.ServiceException;
import com.xbwl.common.orm.IBaseDAO;
import com.xbwl.common.service.impl.BaseServiceImpl;
import com.xbwl.common.utils.ReadExcel;
import com.xbwl.common.utils.ReadExcel2007;
import com.xbwl.entity.FiCapitaaccountset;
import com.xbwl.entity.FiCashiercollectionExcel;
import com.xbwl.finance.Service.IFiCashiercollectionExcelService;
import com.xbwl.finance.dao.IFiCapitaaccountsetDao;
import com.xbwl.finance.dao.IFiCashiercollectionExcelDao;

/**
 * author shuw
 * time Nov 15, 2011 9:57:13 AM
 */
@Service("fiCashiercollectionExcelServiceImpl")
@Transactional(rollbackFor={Exception.class})
public class FiCashiercollectionExcelServiceImpl extends
							BaseServiceImpl<FiCashiercollectionExcel, Long> implements
							IFiCashiercollectionExcelService{

	@Resource(name = "fiCashiercollectionExcelHibernateDaoImpl")
	private IFiCashiercollectionExcelDao fiCashiercollectionExcelDao;

	@Resource(name = "fiCapitaaccountsetHibernateDaoImpl")
	private IFiCapitaaccountsetDao fiCapitaaccountsetDao;
	
	public IBaseDAO<FiCashiercollectionExcel, Long> getBaseDao() {
		return fiCashiercollectionExcelDao;
	}

	public Long getBatchNO() throws Exception {
		Map  times = (Map)fiCashiercollectionExcelDao.createSQLQuery("  select SEQ_FI_EXCEL_BATCH_NO.nextval cartimes from  dual  ").list().get(0);
		Long s =Long.valueOf(times.get("CARTIMES")+"");
		return s;
	}

	public String saveFiExcel(File excelFile, String fileName) throws Exception {
		User user= WebRalasafe.getCurrentUser(ServletActionContext.getRequest());
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		//List<FiCapitaaccountset>listc =fiCapitaaccountsetDao.find("from FiCapitaaccountset fi  where fi.isDelete=? ",1l);
		List list =null;
		Long batchNo =getBatchNO();
		List<FiCashiercollectionExcel> fiList = new ArrayList<FiCashiercollectionExcel>();
		if(fileName.toLowerCase().endsWith(".xlsx")){
				ReadExcel2007 readExcel2007 = new ReadExcel2007(5);
				InputStream fintFile = new FileInputStream(excelFile);
				try {
					list = readExcel2007.readExcel2007(fintFile);
				} catch (Exception e) {
						throw new ServiceException("读取数据错误，存在非法空值");
				}finally{
					if (fintFile!=null) {
						fintFile.close();
						fintFile=null;
					}
				}
		}else if(fileName.toLowerCase().endsWith(".xls")){
			ReadExcel readExcel = new ReadExcel(5);
			InputStream fintFile = new FileInputStream(excelFile);
			try {
				list = readExcel.readExcel(fintFile);
			} catch (Exception e) {
					throw new ServiceException(e.getMessage());
			}finally{
				if (fintFile!=null) {
					fintFile.close();
					fintFile=null;
				}
			}	
		}else{
			throw new ServiceException("请导入Excel文件，后缀为.xlsx或者.xls，不支持其他后缀文件名的导入");
		}
		
		for(int i=0;i<list.size();i++){
			FiCashiercollectionExcel fExcel =  new FiCashiercollectionExcel();
				
				fExcel.setBatchNo(batchNo);
				List row=(List)list.get(i);
				if(i!=0){
					Iterator jt=row.iterator();
					String dateString=(String) jt.next();
					fExcel.setDoMoneyData(	format.parse(dateString));
					jt.hasNext();
					
					String excelNo=(String) jt.next();
					if("".equals(excelNo)||excelNo==null){
						throw new ServiceException("导入的Excel数据，账号不能为空");
					}else{
						fExcel.setAccountNum(excelNo);
					}
					jt.hasNext();

					String company=(String)jt.next();
					fExcel.setCompanyName(company);
					jt.hasNext();
					

					String  excelAmount =(String) jt.next();
					if(excelAmount==null||"".equals(excelAmount)){
						fExcel.setAmount(0.0);
					}else{
						fExcel.setAmount(Double.parseDouble(excelAmount.trim()));
					}
					
					jt.hasNext();
					
					String  remark =(String ) jt.next();
					fExcel.setRemark(remark);
					fiList.add(fExcel);
				}
		}
		
		for(FiCashiercollectionExcel fiCashiercollectionExcel : fiList){  //and fi.accountType=14254 
			List<FiCapitaaccountset> fiCapitaaccountsetList=this.fiCapitaaccountsetDao.find("from FiCapitaaccountset fi  where fi.accountNum=? and fi.isDelete=1", fiCashiercollectionExcel.getAccountNum().trim());;
			if(fiCapitaaccountsetList.size()==0){
				throw new ServiceException("资金账号<"+fiCashiercollectionExcel.getAccountNum()+">在系统中不存在!");
			}else if(fiCapitaaccountsetList.size()==1){
				fiCashiercollectionExcel.setFiCapitaaccountsetId(fiCapitaaccountsetList.get(0).getId());
				
				if(fiCashiercollectionExcel.getAmount()!=0.0){
					fiCashiercollectionExcelDao.save(fiCashiercollectionExcel);
				}
				
			}else{
				throw new ServiceException("银行账号在基础设置中重复!");
			}
		}
		return batchNo+"";
	}
	
	
}
