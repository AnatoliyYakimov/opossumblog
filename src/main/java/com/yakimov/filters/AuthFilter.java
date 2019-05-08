package com.yakimov.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AuthFilter implements Filter {
    private ServletContext context;
    HttpServletResponse response;
    HttpServletRequest request;
    HttpSession session;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.context = filterConfig.getServletContext();
    }

    @Override
    public void doFilter(ServletRequest servletRequest, 
    		ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        request = (HttpServletRequest) servletRequest;
        response = (HttpServletResponse) servletResponse;
    	if(responseIsNotCommited()) {   
		    session = request.getSession(false);
		    if (sessionIsEmpty() && isNotLoginPath()){
		        sendToLoginPath();
		    }
		    else{
		    	filterChain.doFilter(servletRequest, servletResponse);
		    }
    	}
    }

    private boolean responseIsNotCommited() {
        return !response.isCommitted();
    }
    
    private boolean sessionIsEmpty() {
        return session == null;
    }
    
    private boolean isNotLoginPath() {
        return !request.getRequestURI().equals(request.getContextPath() + "/login");
    }
    
    private void sendToLoginPath() throws IOException {
        response.sendRedirect(request.getContextPath() + "/login");
    }

    @Override
    public void destroy() {

    }
}
