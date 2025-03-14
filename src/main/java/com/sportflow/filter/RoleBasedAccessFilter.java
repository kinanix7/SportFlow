// RoleBasedAccessFilter.java
package com.sportflow.filter;

import com.sportflow.model.User;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RoleBasedAccessFilter implements Filter {

    private Map<String, String> roleBasedAccess;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Define role-based access rules
        roleBasedAccess = new HashMap<>();
        roleBasedAccess.put("/admin/*", "ADMIN");
        roleBasedAccess.put("/member/*", "MEMBER");
        roleBasedAccess.put("/entrainer/*", "ENTRAINER");
        // Add more rules as needed
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        HttpSession session = request.getSession(false);

        String requestPath = request.getRequestURI().substring(request.getContextPath().length()); // Get path without context

        // Check if the requested path requires role-based access
        for (Map.Entry<String, String> entry : roleBasedAccess.entrySet()) {
            if (requestPath.startsWith(entry.getKey())) {
                // Check if user has required role
                if (session != null && session.getAttribute("user") != null) {
                    User user = (User) session.getAttribute("user");
                    if (entry.getValue().equals(user.getRole())) {
                        chain.doFilter(req, res);  // User has the correct role, proceed
                        return;
                    }
                    else{
                        // User does NOT have the required role
                        response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access Denied"); // 403 Forbidden
                        return; // Important: Stop processing here!
                    }

                }
                else{
                    response.sendRedirect(request.getContextPath() + "/login");
                    return;
                }


            }
        }
        chain.doFilter(req, res); // No specific role required, so continue
    }


    @Override
    public void destroy() {
        // Cleanup resources
    }
}