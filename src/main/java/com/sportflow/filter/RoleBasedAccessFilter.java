package com.sportflow.filter;

import com.sportflow.model.User;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter(urlPatterns = {"/admin/*", "/member/*", "/entrainer/*"})
public class RoleBasedAccessFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(false);

        User user = (session != null) ? (User) session.getAttribute("user") : null;

        if (user != null) {
            String requestURI = httpRequest.getRequestURI();
            String userRole = user.getRole();

            if (requestURI.startsWith(httpRequest.getContextPath() + "/admin/") && !userRole.equals("ADMIN")) {
                httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "Access Denied");
                return;
            } else if (requestURI.startsWith(httpRequest.getContextPath() + "/member/") && !userRole.equals("MEMBER")) {
                httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "Access Denied");
                return;
            } else if (requestURI.startsWith(httpRequest.getContextPath() + "/entrainer/") && !userRole.equals("ENTRAINER")) {
                httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "Access Denied");
                return;
            }
        } else {
        }

        chain.doFilter(request, response);
    }
}