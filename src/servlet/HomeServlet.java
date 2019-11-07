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
        User user = (User) session.getAttribute("current_user");
        Map<String, Object> root = new HashMap<>();
        root.put("user", user);
        try {
            root.put("topPosts", new PostRepository().findAll());
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        Render.render(req, resp, "home.ftl", root);
    }
}
