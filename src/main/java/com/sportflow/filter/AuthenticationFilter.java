// AuthenticationFilter.java
package com.sportflow.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

public class AuthenticationFilter implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        HttpSession session = request.getSession(false);
        String loginURI = request.getContextPath() + "/login";

        boolean loggedIn = session != null && session.getAttribute("user") != null;
        boolean loginRequest = request.getRequestURI().equals(loginURI);
        boolean resourceRequest = request.getRequestURI().startsWith(request.getContextPath() + "/css/") ||
                request.getRequestURI().startsWith(request.getContextPath() + "/js/");
        if (loggedIn || loginRequest || resourceRequest) {
            chain.doFilter(request, response); // Proceed to the next filter or servlet
        } else {
            response.sendRedirect(loginURI); // Redirect to login page
        }
    }

    // init() and destroy() methods can be left empty if not needed
}