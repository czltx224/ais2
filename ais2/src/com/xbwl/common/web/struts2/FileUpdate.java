package com.xbwl.common.web.struts2;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class FileUpdate {
	private static final int BUFFER_SIZE=16 * 1024;
	
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	public static void copy(File src, File dst) throws IOException  {
		 InputStream in = null ;
         OutputStream out = null ;
        try  {
	       // REVIEW-ACCEPT 嵌套try是否有必要
	    	//FIXED
	       in = new BufferedInputStream( new FileInputStream(src), BUFFER_SIZE);
	       out = new BufferedOutputStream( new FileOutputStream(dst), BUFFER_SIZE);
	       byte [] buffer = new byte [BUFFER_SIZE];
	       while (in.read(buffer) > 0 )  {
	           out.write(buffer);
	       }
        }catch (Exception e) {
        	e.printStackTrace();
        	// REVIEW-ACCEPT 没有catch部分
        	//FIXED
        } finally  {
            if ( null != in)  {
               in.close();
           } 
             if ( null != out)  {
               out.close();
           } 
        } 
   } 
	public  static String getExtention(String fileName)  {
         int pos = fileName.lastIndexOf( "." );
         return fileName.substring(pos);
    } 
	


}
