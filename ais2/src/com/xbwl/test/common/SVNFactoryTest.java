package com.xbwl.test.common;
/**
 * @author Administrator
 * @createTime 9:05:10 AM
 * @updateName Administrator
 * @updateTime 9:05:10 AM
 * 
 */

public class SVNFactoryTest
{
    private static String url = "http://192.168.8.201:8088/svn/xbwl-ais/";
    private static String username = "zwq";
    private static String password = "zwq";
    private static String[] tagpaths = new String[]{"trunk/tr1/ais2"};
    private static long startRevision = 0;
    private static long endRevision = -1;
    
    public static void main(String[] args){
        SVNFactory factory = new SVNFactory();
        factory.registerCollecter(url, tagpaths, username, password, startRevision, endRevision);
    }

}

