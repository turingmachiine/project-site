package servlet;

import help.FreeMarkerConfigurator;
import help.Render;
import model.User;
import orm.HouseRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/houses")
public class HousesServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        FreeMarkerConfigurator.getInstance(this);
        Map<String, Object> root = new HashMap<>();
        resp.setCharacterEncoding("utf-8");
        User user = (User) req.getSession().getAttribute("current_user");
        if (user != null) {
            root.put("user", user);
        }
        try {
            root.put("houses", new HouseRepository().findAll());
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("500");
        }
        Render.render(req, resp, "houses.ftl", root);
    }
}
