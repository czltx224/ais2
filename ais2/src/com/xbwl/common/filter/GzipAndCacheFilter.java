package com.xbwl.common.filter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.zip.GZIPOutputStream;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.ehcache.constructs.web.GenericResponseWrapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
  
/**  
 *   
 *   
 */  
public class GzipAndCacheFilter implements Filter {   
    private static final Logger LOG = LoggerFactory   
            .getLogger(GzipAndCacheFilter.class);   
  
    public void destroy() {   
    }   
  
    public void doFilter(ServletRequest request, ServletResponse response,   
            FilterChain chain) throws IOException, ServletException {   
        final HttpServletRequest httpRequest = (HttpServletRequest) request;   
        final HttpServletResponse httpResponse = (HttpServletResponse) response;   
        
        //ralasafe
        
        String url = httpRequest.getRequestURI();
        if(url.indexOf("ralasafe")>0){
        	chain.doFilter(request, response);
        	return;
        }
        
        if (acceptsGzipEncoding(httpRequest)) {   
            if (LOG.isDebugEnabled()) {   
                LOG.debug("gzip:" + httpRequest.getRequestURL());   
            }   
  
            // 创建gzip stream   
            final ByteArrayOutputStream compressed = new ByteArrayOutputStream();   
            final GZIPOutputStream gzout = new GZIPOutputStream(compressed);   
  
            // 处理request   
            final GenericResponseWrapper wrapper = new GenericResponseWrapper(   
                    httpResponse, gzout);   
            chain.doFilter(request, wrapper);   
            wrapper.flush();   
  
            gzout.close();   
  
            int statusCode = wrapper.getStatus();   
            if (statusCode != HttpServletResponse.SC_OK) {   
                return;   
            }   
  
            byte[] compressedBytes = compressed.toByteArray();   
  
            httpResponse.setHeader("Content-Encoding", "gzip");   
  
            addCacheControl(httpResponse, httpRequest);   
  
            response.setContentLength(compressedBytes.length);   
            response.getOutputStream().write(compressedBytes);   
  
        } else {   
            chain.doFilter(request, response);   
        }   
    }   
  
    public void init(FilterConfig filterConfig) throws ServletException {   
    }   
  
    private boolean acceptsGzipEncoding(HttpServletRequest request) {   
        return acceptsEncoding(request, "gzip");   
    }   
  
    private boolean acceptsEncoding(final HttpServletRequest request,   
            final String name) {   
        final boolean accepts = headerContains(request, "Accept-Encoding", name);   
        return accepts;   
    }   
  
    private boolean headerContains(final HttpServletRequest request,   
            final String header, final String value) {   
        final Enumeration<?> accepted = request.getHeaders(header);   
        while (accepted.hasMoreElements()) {   
            final String headerValue = (String) accepted.nextElement();   
            if (headerValue.indexOf(value) != -1) {   
                return true;   
            }   
        }   
        return false;   
    }   
  
    /**  
     * 根据不同的资源采用不同的缓存策略  
     *   
     * @param httpResponse  
     * @param httpRequest  
     */  
    private void addCacheControl(HttpServletResponse httpResponse,   
            HttpServletRequest httpRequest) {   
        String URL = httpRequest.getRequestURL().toString();   
  
        if (URL.charAt(URL.length() - 1) == '/') {   
            httpResponse.setHeader("Cache-Control", "private, max-age=0");   
            httpResponse.setHeader("Expires", "-1");   
            return;   
        }  
        if (httpRequest.getRequestURI().indexOf(".action")!=-1 || httpRequest.getRequestURI().indexOf("js/view")!=-1 || httpRequest.getRequestURI().indexOf("WEB-INF/xbwl/")!=-1) {   
            httpResponse.setHeader("Cache-Control", "private, max-age=0");   
            httpResponse.setHeader("Expires", "-1");   
            return;   
        } 
        httpResponse.setHeader("Cache-Control", "public, max-age=31536000");   
        httpResponse.setHeader("Vary", "Accept-Encoding");   
    }   
}  
