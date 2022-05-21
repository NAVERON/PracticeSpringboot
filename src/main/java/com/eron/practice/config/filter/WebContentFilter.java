package com.eron.practice.config.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Component 
@WebFilter(filterName = "basic", urlPatterns = "/*") 
public class WebContentFilter implements Filter {
    
    private static final Logger log = LoggerFactory.getLogger(WebContentFilter.class);

    @Override 
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        log.info("do filter sth for WebContentFilter - ");
        
        chain.doFilter(request, response);  // 执行才能通过 否则不能通过请求
    }
    

}










