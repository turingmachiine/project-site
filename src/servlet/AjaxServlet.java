package servlet;

import model.House;
import orm.HouseRepository;
import org.json.JSONObject;


import org.json.JSONArray;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet("/dosearch")
public class AjaxServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        String name = req.getParameter("term");
        try {
            ArrayList<House> houses = new HouseRepository().findByNameLike(name);
            JSONArray array = new JSONArray();
            for (House house : houses) {
                array.put(new JSONObject(house));
            }
            JSONObject object = new JSONObject();
            object.put("objects", array);
            resp.getWriter().write(object.toString());
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

}
