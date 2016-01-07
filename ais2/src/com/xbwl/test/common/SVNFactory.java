package com.xbwl.test.common;
/**
 * @author Administrator
 * @createTime 9:04:08 AM
 * @updateName Administrator
 * @updateTime 9:04:08 AM
 * 
 */

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

import com.xbwl.common.utils.DateUtil;


/**
 * 
 * @author xxx
 *
 */
public class SVNFactory
{
    
    public SVNFactory(){
        
        DAVRepositoryFactory.setup();
        
        SVNRepositoryFactoryImpl.setup();
        
        FSRepositoryFactory.setup();
    } 
    
    public void registerCollecter(String url, String[] targetPaths, String userName, String password,
            long startRevision, long endRevision){
        SVNRepository repository = null;
        try
        {
            repository = SVNRepositoryFactory.create(SVNURL.parseURIDecoded(url));
            ISVNAuthenticationManager authManager = SVNWCUtil.
            createDefaultAuthenticationManager(userName, password);
            repository.setAuthenticationManager(authManager);
            ISVNLogEntryHandler handler = new ISVNLogEntryHandler() {
                public void handleLogEntry(SVNLogEntry logEntry)
                        throws SVNException {
                	
                   System.out.println(logEntry.getAuthor()+" "+logEntry.getRevision()+" "+DateUtil.format(logEntry.getDate(),"yyyy-MM-dd hh:mm:ss")+" "+logEntry.getMessage()+" "+logEntry.toString());
                }

            };
            repository.log(targetPaths, startRevision, endRevision, true, true, handler);
        }
        catch (SVNException e)
        {
            System.err.println("Error while creating an SVNRepository for the location '"
                    + url + "': " + e.getMessage());
            return;
        }
    }
}



