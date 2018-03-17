package com.webapp.common.xss;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * XSS过滤
 * @author Robert.XU
 * @email <xutao@bjnja.com>
 * @date 2017-04-01 10:20
 */
public class XssFilter implements Filter {

	@Override
	public void init(FilterConfig config) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
		XssHttpServletRequestWrapper xssRequest = new XssHttpServletRequestWrapper(
				(HttpServletRequest) request);
		((HttpServletResponse)response).addHeader("Access-Control-Allow-Origin","*");
		((HttpServletResponse)response).addHeader("Access-Control-Allow-Methods","POST");
		((HttpServletResponse)response).addHeader("Access-Control-Allow-Headers","x-requested-with,content-type");
		chain.doFilter(xssRequest, response);
	}

	@Override
	public void destroy() {
	}

}