package com.yakimov.filters;



import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AuthFilter implements Filter {
    private ServletContext context;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.context = filterConfig.getServletContext();
        this.context.log("AuthenticationFilter initialized"); //DEBUG
    }

    @Override
    public void doFilter(ServletRequest servletRequest, 
    		ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
    	if(!servletResponse.isCommitted()) {
		    HttpServletRequest request = (HttpServletRequest) servletRequest;
		    HttpServletResponse response = (HttpServletResponse) servletResponse;
		    HttpSession session = request.getSession(false);
		    if (session == null && !request.getRequestURI().equals(request.getContextPath() + "/login")){
		        this.context.log("Unauthtorized access denied"); //DEBUG
		        response.sendRedirect(request.getContextPath() + "/login");
		    }
		    else{
		    	filterChain.doFilter(servletRequest, servletResponse);
		    }
    	}
    }

    @Override
    public void destroy() {

    }
}
