package controller;

import dao.MessageDAO;
import dao.UserDAO;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Message;
import model.User;

@WebServlet(name = "ChatServlet", urlPatterns = {"/chat"})
public class ChatServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("user");
        if (currentUser == null) {
            response.sendRedirect("login");
            return;
        }

        MessageDAO mDao = new MessageDAO();
        UserDAO uDao = new UserDAO();
        
        // Load recent chat users and their unread counts
        List<Integer> chattedUserIds = mDao.getChattedUserIds(currentUser.getUserId());
        List<User> chattedUsers = new ArrayList<>();
        java.util.Map<Integer, Integer> unreadCounts = new java.util.HashMap<>();
        
        for (int id : chattedUserIds) {
            if (id != currentUser.getUserId()) {
                User u = uDao.getUserById(id);
                if (u != null) {
                    chattedUsers.add(u);
                    unreadCounts.put(id, mDao.getUnreadFromUserCount(currentUser.getUserId(), id));
                }
            }
        }
        
        String toParam = request.getParameter("to");
        if (toParam != null && !toParam.isEmpty()) {
            int toUserId = Integer.parseInt(toParam);
            User toUser = uDao.getUserById(toUserId);
            
            // Add if not in list yet
            boolean exists = false;
            for (User u : chattedUsers) {
                if (u.getUserId() == toUserId) { exists = true; break; }
            }
            if (!exists && toUser != null) {
                chattedUsers.add(0, toUser);
            }
            
            List<Message> conversation = mDao.getConversation(currentUser.getUserId(), toUserId);
            mDao.markAsRead(toUserId, currentUser.getUserId()); // Mark unread messages from this user to me as read
            request.setAttribute("conversation", conversation);
            request.setAttribute("toUser", toUser);
        }

        request.setAttribute("chattedUsers", chattedUsers);
        request.setAttribute("unreadCounts", unreadCounts);
        request.getRequestDispatcher("chat.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("user");
        if (currentUser == null) {
            response.sendRedirect("login");
            return;
        }

        int receiverId = Integer.parseInt(request.getParameter("receiverId"));
        String content = request.getParameter("content");

        Message msg = new Message();
        msg.setSenderId(currentUser.getUserId());
        msg.setReceiverId(receiverId);
        msg.setContent(content);

        MessageDAO mDao = new MessageDAO();
        mDao.sendMessage(msg);

        response.sendRedirect("chat?to=" + receiverId);
    }
}
