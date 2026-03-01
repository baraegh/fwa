package com.cinema.filters;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebFilter("/*")
public class AuthenticationFilter implements Filter {

    public static final List<String> PUBLIC_URLS = List.of("/signIn", "/signUp");

    public void init(ServletConfig config) {

    }

    @Override
    public void doFilter(ServletRequest rq, ServletResponse rs, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpReq = (HttpServletRequest) rq;
        HttpServletResponse httpRes = (HttpServletResponse) rs;

        String      path = httpReq.getServletPath();
        HttpSession session = httpReq.getSession(false);
        Boolean     isLoggedIn = (session != null && session.getAttribute("user") != null);

        if (PUBLIC_URLS.contains(path)) {
            if (isLoggedIn) {
                httpRes.sendRedirect("Profile");
            } else {
                chain.doFilter(httpReq, httpRes);
            }
        }else if (isLoggedIn) {
            chain.doFilter(httpReq, httpRes);
        } else {
            httpRes.sendError(403);
        }

    }

    public void destroy() {

    }
    
}
