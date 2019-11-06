package servlet;

import help.FreeMarkerConfigurator;
import help.Render;
import model.House;
import model.User;
import orm.HouseRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/house")
public class HouseServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        FreeMarkerConfigurator.getInstance(this);
        resp.setCharacterEncoding("utf-8");
        Map<String, Object> root = new HashMap<>();
        User user = (User) req.getSession().getAttribute("current_user");
        if (user != null) {
            root.put("user", user);
        }
        root.put("context", req.getContextPath());
        if (req.getParameter("id") != null){
            try {
                House temp = new HouseRepository().findByID(Integer.parseInt(req.getParameter("id")));
                root.put("house", temp);
                root.put("otherHouses", new HouseRepository().findOtherHousesByUser(temp.getCreator().getId(),
                        Integer.parseInt(req.getParameter("id"))));
            } catch (SQLException | ClassNotFoundException e) {
                System.out.println("e.printStackTrace();");
            }
            Render.render(req, resp, "house.ftl", root);
        } else {
            resp.sendRedirect("/");
        }
    }
}
