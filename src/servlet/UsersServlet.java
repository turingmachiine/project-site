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
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/users")
public class UsersServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        FreeMarkerConfigurator.getInstance(this);
        if (req.getParameter("id") != null) {
            try {
                User user = new UserRepository().findByID(Integer.parseInt(req.getParameter("id")));
                System.out.println();
            } catch (SQLException | ClassNotFoundException e) {
                System.out.println();
            }
        } else if (req.getParameter("name") != null) {
            try {
                User user = new UserRepository().findUserByName(req.getParameter("name"));
                System.out.println();
            } catch (SQLException | ClassNotFoundException e) {
                System.out.println();
            }
        } else {
            try {
                ArrayList<User> users = new UserRepository().findAll();
                Map<String, Object> root = new HashMap<>();
                root.put("users", users);
                Render.render(req,resp,"profiles.ftl", root);
            } catch (SQLException | ClassNotFoundException e) {
                System.out.println();
            }
        }

    }
}
