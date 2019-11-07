package servlet;

import help.FreeMarkerConfigurator;
import help.Render;
import model.User;
import orm.UserRepository;


import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

import javax.print.attribute.standard.RequestingUserName;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        FreeMarkerConfigurator.getInstance(this);
        Map<String, Object> root = new HashMap<>();
        Render.render(req, resp, "login.ftl", root);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        resp.setCharacterEncoding("utf-8");
        String email = req.getParameter("login");
        String password = req.getParameter("password");
        User user = (User) session.getAttribute("current_user");
        try {
            User usr = new UserRepository().validateUser(email, password);
            if (usr != null) {
                if (req.getParameter("rememberme") != null) {
                    Cookie cookie = new Cookie("email", email);
                    cookie.setMaxAge(60 * 60);
                    cookie.setPath("/");
                    resp.addCookie(cookie);
                }
                session.setAttribute("current_user", usr);
                resp.sendRedirect("/profile");

            } else resp.sendRedirect("/login");
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
