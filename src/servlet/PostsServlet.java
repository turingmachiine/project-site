package servlet;

import help.FreeMarkerConfigurator;
import help.Render;
import model.User;
import orm.HouseRepository;
import orm.PostRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/posts")
public class PostsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        FreeMarkerConfigurator.getInstance(this);
        resp.setCharacterEncoding("utf-8");
        Map<String, Object> root = new HashMap<>();
        User user = (User) req.getSession().getAttribute("current_user");
        if (user != null) {
            root.put("user", user);
        }
        int id = req.getParameter("house_id") != null? Integer.parseInt(req.getParameter("house_id")) : -1;
        try {
            if (id == -1) {
                root.put("posts", new PostRepository().findAll());
            } else {
                root.put("posts", new PostRepository().findPostsAboutHouse(id));
            }
            root.put("houses", new HouseRepository().findAll());
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        Render.render(req, resp, "posts.ftl", root);
    }
}
