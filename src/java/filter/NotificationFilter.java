package filter;

import dao.MessageDAO;
import java.io.IOException;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import model.User;

// @WebFilter(filterName = "NotificationFilter", urlPatterns = {"/*"})
public class NotificationFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String path = httpRequest.getServletPath();
        
        // Only run for main pages, skip static assets
        if (!path.contains(".") || path.endsWith(".jsp") || path.endsWith(".html")) {
            try {
                HttpSession session = httpRequest.getSession(false);
                if (session != null && session.getAttribute("user") != null) {
                    User user = (User) session.getAttribute("user");
                    MessageDAO mDao = new MessageDAO();
                    int unreadCount = mDao.getUnreadCount(user.getUserId());
                    request.setAttribute("unreadCount", unreadCount);
                }
            } catch (Exception e) {
                System.err.println("NotificationFilter error: " + e.getMessage());
            }
        }
        
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {}
}
