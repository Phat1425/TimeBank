package controller;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(name = "LandingServlet", urlPatterns = {"", "/welcome"})
public class LandingServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        
        // If user is already logged in, take them to the home feed
        if (session.getAttribute("user") != null) {
            response.sendRedirect("home");
            return;
        }

        // Otherwise, show the beautiful landing page
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }
}
