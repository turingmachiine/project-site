package servlet;

import help.FreeMarkerConfigurator;
import help.Render;
import model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/profile")
public class ProfileServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User usr = (User) session.getAttribute("current_user");
        if (usr != null) {
            FreeMarkerConfigurator.getInstance(this);
            Map<String, Object> root = new HashMap<>();
            resp.setCharacterEncoding("utf-8");
            root.put("context", req.getContextPath());
            root.put("user", usr);
            Render.render(req, resp, "profile.ftl", root);
        } else {
            resp.sendRedirect("/login");
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
