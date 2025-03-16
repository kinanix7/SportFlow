package com.sportflow.controller.admin;

import com.sportflow.dao.MemberDAO;
import com.sportflow.dao.UserDAO;
import com.sportflow.model.Member;
import com.sportflow.model.User;
import com.sportflow.util.DateUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@WebServlet(name = "MemberManagementServlet", urlPatterns = {
        "/admin/members",         // GET: Display list of members
        "/admin/members/add",     // POST: Add a new member
        "/admin/members/edit",    // POST: Edit an existing member
        "/admin/members/delete"   // POST: Delete a member
})

public class MemberManagementServlet extends HttpServlet {

    private MemberDAO memberDAO;
    private UserDAO userDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        memberDAO = new MemberDAO();
        userDAO = new UserDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // For simplicity, combine all user and member data fetching here.  In a real app,
            // you might separate this into different methods/servlets.

            List<Member> members = memberDAO.getAllMembersWithUserDetails();
            request.setAttribute("members", members);
            request.getRequestDispatcher("/WEB-INF/jsp/admin/members.jsp").forward(request, response);


        } catch (SQLException e) {
            throw new ServletException("Database error", e);
        }
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getServletPath();

        try {
            switch (action) {
                case "/admin/members/add":
                    addMember(request, response);
                    break;
                case "/admin/members/edit":
                    editMember(request, response);
                    break;
                case "/admin/members/delete":
                    deleteMember(request, response);
                    break;
                default:
                    response.sendRedirect(request.getContextPath() + "/admin/members");
            }
        } catch (SQLException e) {
            throw new ServletException("Database error", e);
        }
    }


    private void addMember(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        // Create User first
        User user = new User();
        user.setUsername(request.getParameter("username"));
        user.setPassword(request.getParameter("password")); // Will be hashed in DAO
        user.setEmail(request.getParameter("email"));
        user.setRole("MEMBER");
        userDAO.createUser(user);  // This also sets the user's ID

        // Create Member, linking to the newly created User
        Member member = new Member();
        member.setUserId(user.getId()); // Use the ID from the created User
        member.setFirstName(request.getParameter("firstName"));
        member.setLastName(request.getParameter("lastName"));
        String birthDateStr = request.getParameter("birthDate");
        member.setBirthDate(DateUtil.parseDate(birthDateStr)); // Use DateUtil
        member.setSport(request.getParameter("sport"));
        memberDAO.createMember(member);

        response.sendRedirect(request.getContextPath() + "/admin/members");
    }



    private void editMember(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        // Get member and user IDs
        int memberId = Integer.parseInt(request.getParameter("memberId"));
        int userId = Integer.parseInt(request.getParameter("userId")); // Get userId from the form

        // Fetch existing member and user
        Member member = memberDAO.getMemberById(memberId);
        User user = userDAO.getUserById(userId);  // Fetch the associated User

        // Update Member details
        member.setFirstName(request.getParameter("firstName"));
        member.setLastName(request.getParameter("lastName"));
        String birthDateStr = request.getParameter("birthDate");
        member.setBirthDate(DateUtil.parseDate(birthDateStr));  // Parse date
        member.setSport(request.getParameter("sport"));

        // Update User details
        user.setUsername(request.getParameter("username"));
        user.setEmail(request.getParameter("email"));


        // Update in database
        memberDAO.updateMember(member);
        userDAO.updateUser(user);  // Update the User object

        response.sendRedirect(request.getContextPath() + "/admin/members");
    }


    private void deleteMember(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int memberId = Integer.parseInt(request.getParameter("memberId"));
        int userId = Integer.parseInt(request.getParameter("userId"));

        // Delete member (cascading delete will handle user deletion)
        memberDAO.deleteMember(memberId);
        userDAO.deleteUser(userId);  // Explicitly delete the User

        response.sendRedirect(request.getContextPath() + "/admin/members");
    }
}