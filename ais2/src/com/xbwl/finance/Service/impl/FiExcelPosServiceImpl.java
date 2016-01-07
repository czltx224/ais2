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
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.hibernate.Query;
import org.hibernate.criterion.Criterion;
import org.ralasafe.WebRalasafe;
import org.ralasafe.user.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xbwl.common.exception.ServiceException;
import com.xbwl.common.log.anno.ModuleName;
import com.xbwl.common.orm.IBaseDAO;
import com.xbwl.common.orm.Page;
import com.xbwl.common.orm.PropertyFilter;
import com.xbwl.common.service.impl.BaseServiceImpl;
import com.xbwl.common.utils.LogType;
import com.xbwl.common.utils.ReadExcel;
import com.xbwl.common.utils.ReadExcel2007;
import com.xbwl.entity.FiCapitaaccountset;
import com.xbwl.entity.FiCashiercollectionExcel;
import com.xbwl.entity.FiExcelPos;
import com.xbwl.finance.Service.IFiExcelPosService;
import com.xbwl.finance.dao.IFiCapitaaccountsetDao;
import com.xbwl.finance.dao.IFiExcelPosDao;

@Service("fiExcelPosServiceImpl")
@Transactional(rollbackFor={Exception.class})
public class FiExcelPosServiceImpl extends BaseServiceImpl<FiExcelPos,Long> implements
		IFiExcelPosService {

	@Resource(name="fiExcelPosHibernateDaoImpl")
	private IFiExcelPosDao fiExcelPosDao;
	
	@Resource(name = "fiCapitaaccountsetHibernateDaoImpl")
	private IFiCapitaaccountsetDao fiCapitaaccountsetDao;
	
	
	@Override
	public IBaseDAO getBaseDao() {
		return fiExcelPosDao;
	}


	public Long getBatchNO() throws Exception {
		Map  times = (Map)this.fiExcelPosDao.createSQLQuery("  select SEQ_FI_EXCEL_BATCH_NO.nextval cartimes from  dual  ").list().get(0);
		Long s =Long.valueOf(times.get("CARTIMES")+"");
		return s;
	}

	@ModuleName(value="导入POS",logType=LogType.fi)
	public String saveFiExcel(File excelFile, String fileName) throws Exception {
		User user= WebRalasafe.getCurrentUser(ServletActionContext.getRequest());
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		//List<FiCapitaaccountset>listc =fiCapitaaccountsetDao.find("from FiCapitaaccountset fi  where fi.isDelete=? ",1l);
		Long batchNo =getBatchNO();
		List list=null;
		List<FiExcelPos> fiList = new ArrayList<FiExcelPos>();
		
		if(fileName.toLowerCase().endsWith(".xlsx")){
			ReadExcel2007 readExcel2007 = new ReadExcel2007(11);
			InputStream fintFile = new FileInputStream(excelFile);
			try{
				list = readExcel2007.readExcel2007(fintFile);
			} catch (Exception e) {
				e.printStackTrace();
				throw new ServiceException("读取数据错误，存在非法空值");
			}finally{
				if (fintFile!=null) {
					fintFile.close();
					fintFile=null;
				}
			}
		}else if(fileName.toLowerCase().endsWith(".xls")){
			ReadExcel readExcel = new ReadExcel(11);
			InputStream fintFile = new FileInputStream(excelFile);
			try {
				list = readExcel.readExcel(fintFile);
			} catch (Exception e) {
				e.printStackTrace();
				throw new ServiceException("读取数据错误，存在非法空值");
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
			FiExcelPos fExcel =  new FiExcelPos();
				fExcel.setBatchNo(batchNo);
				List row=(List)list.get(i);
				if(i!=0){
					Iterator jt=row.iterator();
					String posNo=(String) jt.next();
					if(posNo.indexOf(".")>0){
						fExcel.setPosNo(posNo.substring(0,posNo.indexOf(".")));
					}else{
						fExcel.setPosNo(posNo);
					}
					jt.hasNext();
					
					String transactionNumber=(String) jt.next();
					if(transactionNumber.indexOf(".")>0){
						fExcel.setTransactionNumber(Long.parseLong(transactionNumber.substring(0,transactionNumber.indexOf('.'))));
					}else{
						fExcel.setTransactionNumber(Long.parseLong(transactionNumber));
					}
						
					jt.hasNext();

					String amount=(String)jt.next();
					if(amount==null||"".equals(amount)){
						fExcel.setAmount(0.0);
					}else{
						fExcel.setAmount(Double.parseDouble(amount.trim()));
					}
					jt.hasNext();

					String  feeAmount =(String) jt.next();
					if(feeAmount==null||"".equals(feeAmount)){
						fExcel.setFeeAmount(0.0);
					}else{
						fExcel.setFeeAmount(Double.parseDouble(feeAmount.trim()));
					}
					jt.hasNext();
					
					String  settlemenAmount =(String) jt.next();
					if(settlemenAmount==null||"".equals(settlemenAmount)){
						fExcel.setSettlemenAmount(0.0);
					}else{
						fExcel.setSettlemenAmount(Double.parseDouble(settlemenAmount.trim()));
					}
					jt.hasNext();
					
					String collectionDept=(String) jt.next();
					if(collectionDept==null||"".equals(collectionDept)){
						fExcel.setCollectionDept("");
					}else{
						fExcel.setCollectionDept(collectionDept);
					}
					jt.hasNext();
					
					String collectionTime=(String) jt.next();
					fExcel.setCollectionTime(format.parse(collectionTime));
					jt.hasNext();
					
					String accountNum=(String) jt.next();
					System.out.println(accountNum);
					if(accountNum==null||"".equals(accountNum)){
						fExcel.setAccountNum("");
					}else{
						fExcel.setAccountNum(accountNum);
					}
					jt.hasNext();
					
					String accountName=(String) jt.next();
					if(accountName==null||"".equals(accountName)){
						fExcel.setAccountName("");
					}else{
						fExcel.setAccountName(accountName);
					}
					jt.hasNext();
					
					String merchanCode=(String) jt.next();
					if(merchanCode==null||"".equals(merchanCode)){
						fExcel.setMerchanCode("");
					}else{
						fExcel.setMerchanCode(merchanCode);
					}
					
					String remark=(String)jt.next();
					if(remark==null||"".equals(remark)){
						fExcel.setRemark("");
					}else{
						fExcel.setRemark(remark);
					}
					fiList.add(fExcel);
				}
		}

		
		for(FiExcelPos fiExcelPos : fiList){
			List<FiCapitaaccountset> fiCapitaaccountsetList=this.fiCapitaaccountsetDao.find("from FiCapitaaccountset fi  where fi.accountNum=? and fi.accountType=14254 and fi.isDelete=1", fiExcelPos.getAccountNum());
			if(fiCapitaaccountsetList.size()==0){
				throw new ServiceException("资金账号<"+fiExcelPos.getAccountNum()+">在系统中不存在!");
			}else if(fiCapitaaccountsetList.size()==1){
				fiExcelPos.setFiCapitaaccountsetId(fiCapitaaccountsetList.get(0).getId());
				
				if(fiExcelPos.getAmount()!=0.0){
					this.fiExcelPosDao.save(fiExcelPos);
				}
				
			}else{
				throw new ServiceException("POS机在系统中设置重复!");
			}
		}
		return batchNo+"";
	}
	
}
