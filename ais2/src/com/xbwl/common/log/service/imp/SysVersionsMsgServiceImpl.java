package com.xbwl.common.log.service.imp;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tmatesoft.svn.core.ISVNLogEntryHandler;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNLogEntry;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.internal.io.dav.DAVRepositoryFactory;
import org.tmatesoft.svn.core.internal.io.fs.FSRepositoryFactory;
import org.tmatesoft.svn.core.internal.io.svn.SVNRepositoryFactoryImpl;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;
import org.tmatesoft.svn.core.wc.SVNWCUtil;

import com.xbwl.common.log.dao.ISysVersionsMsgDao;
import com.xbwl.common.log.service.ISysVersionsMsgService;
import com.xbwl.common.orm.IBaseDAO;
import com.xbwl.common.service.impl.BaseServiceImpl;
import com.xbwl.common.utils.DateUtil;
import com.xbwl.entity.SysVersionsMsg;

/**
 * @author Administrator
 * @createTime 11:36:11 AM
 * @updateName Administrator
 * @updateTime 11:36:11 AM
 * 
 */
@Service("sysVersionsMsgService")
@Transactional(rollbackFor=Exception.class)
public class SysVersionsMsgServiceImpl extends BaseServiceImpl<SysVersionsMsg,Long> implements
		ISysVersionsMsgService {
	
	@Value("${svn.url}")
    private  String url ;
	@Value("${svn.userName}")
    private  String username ;
	@Value("${svn.password}")
    private  String password = "zwq";
    private  String[] tagpaths = new String[]{"trunk/tr1/ais2"};
    private  long startRevision = 0;
    private  long endRevision = -1;
   
    static{
    	DAVRepositoryFactory.setup();
    
    	SVNRepositoryFactoryImpl.setup();
    
    	FSRepositoryFactory.setup();
    }
	
	@Resource
	private ISysVersionsMsgDao sysVersionsMsgDao;

	@Override
	public IBaseDAO getBaseDao() {
		return sysVersionsMsgDao;
	}
	
	public void inserMsg(){
		registerCollecter(url,tagpaths,username,password,getMaxRevision(),endRevision);
	}
	
	private Long getMaxRevision(){
		return sysVersionsMsgDao.findUnique("select nvl(max(revision),-1) from SysVersionsMsg");
	}
	
	// REVIEW-ACCEPT 增加方法注释
	 /**用户获取SVN的代码更新信息，并插入数据库
	 * @param url
	 * @param targetPaths
	 * @param userName
	 * @param password
	 * @param startRevision
	 * @param endRevision
	 */
	//FIXED
	public void  registerCollecter(String url, String[] targetPaths, String userName, String password,
	            long startRevision, long endRevision){
	        SVNRepository repository = null;
	        try {
	            repository = SVNRepositoryFactory.create(SVNURL.parseURIDecoded(url));
	            ISVNAuthenticationManager authManager = SVNWCUtil.
	            createDefaultAuthenticationManager(userName, password);
	            repository.setAuthenticationManager(authManager);
	            ISVNLogEntryHandler handler = new ISVNLogEntryHandler() {
	                public void handleLogEntry(SVNLogEntry logEntry)
	                        throws SVNException {
	                	SysVersionsMsg sysVersionsMsg=new SysVersionsMsg();
	                	
	                	sysVersionsMsg.setAuthor(logEntry.getAuthor());
	                	sysVersionsMsg.setRevision(logEntry.getRevision());
	                	sysVersionsMsg.setRevisionDate(logEntry.getDate());
	                	sysVersionsMsg.setLogentry(logEntry.toString());
	                	sysVersionsMsg.setMessage(logEntry.getMessage());
	                	
	                	sysVersionsMsgDao.save(sysVersionsMsg);
	                }
	
	            };
	            repository.log(targetPaths, startRevision+1, endRevision, true, true, handler);
	        } catch (SVNException e){
	        	e.printStackTrace();
	           logger.error(e.getLocalizedMessage());
	           // REVIEW-ACCEPT 应当记录异常到日志中
	           //FIXED
	            return;
	        }
	    }

}
