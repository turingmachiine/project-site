package servlet;

import help.FreeMarkerConfigurator;
import help.Render;
import model.User;
import orm.HouseRepository;
import orm.PostRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/profile")
public class ProfileServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User usr = (User) session.getAttribute("current_user");
        FreeMarkerConfigurator.getInstance(this);
        Map<String, Object> root = new HashMap<>();
        resp.setCharacterEncoding("utf-8");
        root.put("context", req.getContextPath());
        root.put("user", usr);
        try {
            root.put("posts",new PostRepository().getPostWithUserComments(usr.getId()));
            root.put("houses", new HouseRepository().findHousesByUser(usr.getId()));
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("500");
        }
        Render.render(req, resp, "profile.ftl", root);


    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
