package com.company.app.util.filter;

import org.apache.log4j.Logger;

import javax.servlet.*;
import java.io.IOException;

/**
 * Sets the UTF-8 encoding for the page.
 */
public class CharsetFilter implements Filter {
    public static final Logger LOG= Logger.getLogger(CharsetFilter.class);
    private String encoding;

    @Override
    public void init(FilterConfig filterConfig) {
        LOG.info("The Charset Filter started working");
        encoding = filterConfig.getInitParameter("requestEncoding");
        if (encoding == null) encoding = "UTF-8";
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        request.setCharacterEncoding(encoding);
        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding(encoding);
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        LOG.info("The Charset Filter has finished its work");
    }
}
