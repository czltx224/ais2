package com.xbwl.oper.szsm.dao.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.xbwl.common.orm.hibernate.BaseDAOHibernateImpl;
import com.xbwl.entity.DigitalChinaExchange;
import com.xbwl.oper.szsm.dao.IDataExchangeDao;

/**
 * 神州数码对接数据访问层实现类
 * @author czl
 * @date 2012-06-20
 */
@Repository("dataExchangeDaoImpl")
public class DataExchangeDaoImpl extends BaseDAOHibernateImpl<DigitalChinaExchange, Long>
		implements IDataExchangeDao {

	@Value("${szsm_cus_id}")
	private Long szsm_cus_id;
	
	public String searchTmTransferD() throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append("select f.d_no,f.sub_no,to_char(s.edi_reach_time,'yyyy-MM-dd hh24:mi:ss') TIME,e.id,e.car_code From opr_fax_in f,opr_status s,")
          .append("(select d.id,d.d_no,o.car_code car_code from opr_overmemo o,opr_overmemo_detail d where o.id=d.overmemo")   
          .append(" and overmemo_type='中转' and d.status=0")
          .append(" and o.id=(select max(a.id) from opr_overmemo a,opr_overmemo_detail b where a.id=b.overmemo") 
          .append(" and a.overmemo_type='中转' and b.status=0 and b.d_no=d.d_no)")
          .append(" and d.create_time>to_date(to_char(sysdate-1,'yyyy-MM-dd')||' 00:00:00','yyyy-MM-dd hh24:mi:ss')")
          .append(") e")
          .append(" where f.d_no=s.d_no and f.d_no=e.d_no");
	    sb.append(" and not exists(select * from digital_china_exchange dce where dce.other_id=to_char(e.id) and dce.d_no= f.d_no and dce.type='1')");
	    sb.append(" and f.cus_id=").append(szsm_cus_id);
	    sb.append(" and s.edi_reach_time is not null");
	    //测试一条数据
	   // sb.append(" and f.d_no=21778");
	   // System.out.println(sb);
		return sb.toString();
	}

	public String searchTmDnSign() throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append("select f.d_no,f.sub_no,n.id,nvl(n.replace_sign,n.sign_man) sign_man,to_char(n.create_time,'yyyy-MM-dd hh24:mi:ss') TIME")
		  .append(" From opr_fax_in f,opr_sign n")
          .append(" where f.d_no=n.d_no");
		sb.append(" and not exists(select * from digital_china_exchange dce where dce.other_id=to_char(n.id) and dce.d_no= f.d_no and dce.type='2')");
		sb.append(" and f.cus_id=").append(szsm_cus_id);
		sb.append("and n.CREATE_TIME>to_date(to_char(sysdate-1,'yyyy-MM-dd')||' 00:00:00','yyyy-MM-dd hh24:mi:ss')");
		//测试一条数据
		//sb.append(" and f.d_no=21778");
		return sb.toString();
	}

}
