package servlet;

import help.FreeMarkerConfigurator;
import help.Render;
import model.User;
import orm.PostRepository;
import orm.UserRepository;

import javax.security.auth.login.Configuration;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("")
public class HomeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        FreeMarkerConfigurator.getInstance(this);
        HttpSession session = req.getSession();
        resp.setCharacterEncoding("utf-8");
        User user = (User) session.getAttribute("current_user");
        Cookie[] cookies = req.getCookies();
        Map<String, Object> root = new HashMap<>();
        if (user == null && cookies != null) {
            for (Cookie cookie: cookies) {
                try {
                    user = new UserRepository().validateUser(cookie.getName(), cookie.getValue());
                    if (user != null) {
                        break;
                    }
                } catch (SQLException | ClassNotFoundException e) {
                    System.out.println("500");
                }
            }
        }
        if (user != null) {
            root.put("user", user);
        }
        root.put("context", req.getContextPath());
        try {
            root.put("topPosts", new PostRepository().findAll());
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        resp.setCharacterEncoding("utf-8");
        Render.render(req, resp, "home.ftl", root);
    }
}
