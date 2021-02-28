package com.company.app.util.filter;

import com.company.app.util.constant.Path;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Blocks access to all jsp-files in "src/main/webapp/jsp".
 */
public class JspFilter implements Filter {
    public static final Logger LOG= Logger.getLogger(JspFilter.class);

    @Override
    public void init(FilterConfig filterConfig) {
        LOG.info("The JSP Filter started working");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String errorUserUrl = request.getContextPath() + Path.ERROR;
        try {
            response.sendRedirect(errorUserUrl);
        } catch (IOException e) {
            LOG.error(e.getMessage());
        }
    }

    @Override
    public void destroy() {
        LOG.info("The JSP Filter has finished its work");
    }
}
