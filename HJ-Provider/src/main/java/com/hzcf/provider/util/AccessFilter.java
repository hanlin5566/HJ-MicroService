package com.hzcf.provider.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ReadListener;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

/**
 * Create by hanlin on 2018年8月8日
 **/
@Component
public class AccessFilter implements Filter{
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		try {
			if (request instanceof HttpServletRequest) {
				request = new RepeatedlyReadRequestWrapper((HttpServletRequest) request);
			}
			HttpServletRequest httpRequest = (HttpServletRequest)request;
			String uri = httpRequest.getRequestURI();
			String param = StreamUtils.copyToString(request.getInputStream(), Charset.forName("UTF-8"));
			logger.info("访问记录,请求地址[{}],客户端IP[{}],请求参数[{}]。",uri,IPUtils.getIpAddress(httpRequest),param);
			chain.doFilter(request, response);
		} catch (Exception e) {
			return;
		}
		return;
	}

	@Override
	public void destroy() {
		
	}

}


class BufferedServletInputStream extends ServletInputStream {
    private ByteArrayInputStream inputStream;
    public BufferedServletInputStream(byte[] buffer) {
        this.inputStream = new ByteArrayInputStream( buffer );
    }
    @Override
    public int available() throws IOException {
        return inputStream.available();
    }
    @Override
    public int read() throws IOException {
        return inputStream.read();
    }
    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        return inputStream.read( b, off, len );
    }
	@Override
	public boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean isReady() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public void setReadListener(ReadListener listener) {
		// TODO Auto-generated method stub
		
	}
}
 
class RepeatedlyReadRequestWrapper extends HttpServletRequestWrapper {
    private byte[] buffer;
    public RepeatedlyReadRequestWrapper(HttpServletRequest request) throws IOException {
        super( request );
        InputStream is = request.getInputStream();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte buff[] = new byte[ 1024 ];
        int read;
        while( ( read = is.read( buff ) ) > 0 ) {
            baos.write( buff, 0, read );
        }
        this.buffer = baos.toByteArray();
    }
    @Override
    public ServletInputStream getInputStream() throws IOException {
        return new BufferedServletInputStream( this.buffer );
    }
}
