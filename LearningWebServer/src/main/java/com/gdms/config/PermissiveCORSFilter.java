/**
 *
 */
package com.gdms.config;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

/**
 * This is a very simple CORS filter that allows posts and gets.
 */
@Component
public class PermissiveCORSFilter implements Filter {

    /**
     * Filters requests from GUI
     *
     * @param req
     *            - Servlet request
     * @param res
     *            - Servlet response
     * @param chain
     *            - The filter chain
     *
     */
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) res;
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers",
                "Origin, X-Requested-With, Content-Type, Accept, "
                + "Cache-Control, Authorization");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Expose-Headers", "*");
        chain.doFilter(req, res);
    }

    /**
     * Initializes filter
     *
     * @param filterConfig
     *            - The filter configuration
     *
     */
    @Override
    public void init(FilterConfig filterConfig) {
    }

    /**
     * Closes filter
     *
     */
    @Override
    public void destroy() {
    }
}