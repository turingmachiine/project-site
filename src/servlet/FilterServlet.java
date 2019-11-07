package servlet;

import model.User;
import orm.UserRepository;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class FilterServlet implements Filter {


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        User user = (User) req.getSession().getAttribute("current_user");
        String path = req.getServletPath();
        if (path.equals("/login") || path.equals("/registration")) {
            if (user == null) {
                filterChain.doFilter(req, resp);
            } else {
                resp.sendRedirect("/profile");
            }
        } else {
            if (user != null) {
                filterChain.doFilter(req, servletResponse);
            } else {
                Cookie[] cookies = req.getCookies();
                if (cookies != null) {
                    for (Cookie cookie : cookies) {
                        try {
                            user = new UserRepository().findUserByEmail(cookie.getValue());
                            if (user != null) {
                                req.getSession().setAttribute("current_user", user);
                                break;
                            }
                        } catch (SQLException | ClassNotFoundException e) {
                            System.out.println("500");
                        }
                    }
                }
                if (user == null && path.equals("/profile") || path.equals("/create_post") || path.equals("/create_house")
                        || path.equals("/edit")) {
                    resp.sendRedirect("/login");
                } else  {
                    filterChain.doFilter(req, servletResponse);
                }
            }
        }

    }
}
