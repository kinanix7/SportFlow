// MemberManagementServlet.java
package com.sportflow.controller.admin;

import com.sportflow.dao.MemberDAO;
import com.sportflow.dao.UserDAO;
import com.sportflow.model.Member;
import com.sportflow.model.User;
import com.sportflow.util.HashUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

@WebServlet("/admin/members")
public class MemberManagementServlet extends HttpServlet {

    private MemberDAO memberDAO;
    private UserDAO userDAO;

    @Override
    public void init() throws ServletException {
        memberDAO = new MemberDAO();
        userDAO = new UserDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "list";
        }

        switch (action) {
            case "list":
                listMembers(request, response);
                break;
            case "add":
                showAddForm(request, response);
                break;
            case "edit":
                showEditForm(request, response);
                break;
            case "delete":
                deleteMember(request, response);
                break;
            default:
                listMembers(request, response);
        }
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "list"; // Or some default action
        }

        switch (action) {
            case "add":
                addMember(request, response);
                break;
            case "update":
                updateMember(request, response);
                break;
            default:
                // Handle invalid actions (e.g., redirect to list)
                response.sendRedirect(request.getContextPath() + "/admin/members");
                break;
        }
    }


    private void listMembers(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Member> members = memberDAO.getAllMembers();
        request.setAttribute("members", members);
        request.getRequestDispatcher("/WEB-INF/jsp/admin/members.jsp").forward(request, response);
    }
    private void showAddForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/jsp/admin/add-member.jsp").forward(request, response);
    }

    private void addMember(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String username = request.getParameter("username");
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            String firstName = request.getParameter("firstName");
            String lastName = request.getParameter("lastName");
            LocalDate birthDate = LocalDate.parse(request.getParameter("birthDate"));
            String sport = request.getParameter("sport");

            // 1. Create User
            User user = new User();
            user.setUsername(username);
            user.setEmail(email);
            user.setPassword(HashUtil.hashPassword(password)); // Hash the password!
            user.setRole("MEMBER");
            User createdUser = userDAO.createUser(user);

            if (createdUser == null) {
                request.setAttribute("error", "Failed to create user.");
                showAddForm(request, response); // Show the form again with an error
                return;
            }

            // 2. Create Member
            Member member = new Member();
            member.setUserId(createdUser.getId());
            member.setFirstName(firstName);
            member.setLastName(lastName);
            member.setBirthDate(birthDate);
            member.setSport(sport);

            memberDAO.createMember(member);
            response.sendRedirect(request.getContextPath() + "/admin/members");


        } catch (DateTimeParseException e) {
            request.setAttribute("error", "Invalid date format.");
            showAddForm(request, response); // Show the form again with an error

        }  catch (Exception e) {
            request.setAttribute("error", "An error occurred: " + e.getMessage());
            showAddForm(request, response);

        }
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int memberId = Integer.parseInt(request.getParameter("id"));
        Member member = memberDAO.getMemberById(memberId);
        request.setAttribute("member", member);
        request.getRequestDispatcher("/WEB-INF/jsp/admin/edit-member.jsp").forward(request, response);
    }

    private void updateMember(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int memberId = Integer.parseInt(request.getParameter("id"));
            String firstName = request.getParameter("firstName");
            String lastName = request.getParameter("lastName");
            LocalDate birthDate = LocalDate.parse(request.getParameter("birthDate"));
            String sport = request.getParameter("sport");

            Member member = memberDAO.getMemberById(memberId);

            member.setFirstName(firstName);
            member.setLastName(lastName);
            member.setBirthDate(birthDate);
            member.setSport(sport);

            memberDAO.updateMember(member);

            response.sendRedirect(request.getContextPath() + "/admin/members");

        } catch (DateTimeParseException e) {
            request.setAttribute("error", "Invalid date format.");
            showEditForm(request, response);  // Forward back to the edit form
        }
        catch (Exception e) {
            request.setAttribute("error", "An unexpected error occurred: " + e.getMessage());
            showEditForm(request, response); // Forward back to the edit form
        }

    }


    private void deleteMember(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int memberId = Integer.parseInt(request.getParameter("id"));
        memberDAO.deleteMember(memberId);
        response.sendRedirect(request.getContextPath() + "/admin/members");
    }
}