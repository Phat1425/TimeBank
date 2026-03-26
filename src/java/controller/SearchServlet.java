package controller;

import dao.ServiceDAO;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Service;

@WebServlet(name = "SearchServlet", urlPatterns = {"/search"})
public class SearchServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String keyword = request.getParameter("q");
        if (keyword == null) keyword = "";

        ServiceDAO dao = new ServiceDAO();
        List<Service> services = dao.searchServices(keyword);
        
        request.setAttribute("services", services);
        request.setAttribute("keyword", keyword);

        request.getRequestDispatcher("search.jsp").forward(request, response);
    }
}
