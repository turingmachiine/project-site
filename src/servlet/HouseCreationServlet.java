package servlet;

import help.FreeMarkerConfigurator;
import help.Render;
import model.House;
import model.HouseClass;
import model.Location;
import model.User;
import orm.HouseClassRepository;
import orm.HouseRepository;
import orm.LocationRepository;
import orm.UserRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@WebServlet("/create_house")
@MultipartConfig
public class HouseCreationServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User usr = (User) session.getAttribute("current_user");
        resp.setCharacterEncoding("utf-8");
        if (usr != null) {
            FreeMarkerConfigurator.getInstance(this);
            Map<String, Object> root = new HashMap<>();
            root.put("context", req.getContextPath());
            root.put("user", usr);
            ArrayList<HouseClass> houseClasses = new ArrayList<>();
            try {
                houseClasses = new HouseClassRepository().findAll();
            } catch (SQLException | ClassNotFoundException e) {
                System.out.println("500");
            }
            root.put("classes", houseClasses);
            Render.render(req, resp, "create_house.ftl", root);
        } else {
            resp.sendRedirect("/login");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User usr = (User) session.getAttribute("current_user");
        if (usr != null) {
            float latitude = Float.parseFloat(req.getParameter("latitude"));
            float longitude = Float.parseFloat(req.getParameter("longitude"));
            String city = req.getParameter("city");
            DateFormat format = new SimpleDateFormat("dd/mm/yyyy");
            String date = format.format(Calendar.getInstance().getTime());
            try {
                new LocationRepository().create(new Location(0, latitude, longitude, city));
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
            try {
                new HouseRepository().create(new House(
                        0,
                        req.getParameter("house_name"),
                        usr,
                        date,
                        new HouseClassRepository().findByName(req.getParameter("class")),
                        req.getParameter("description"),
                        new LocationRepository().findByCoordinats(latitude, longitude)));
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
            try {
                session.setAttribute("id", new HouseRepository().findByName(
                        req.getParameter("house_name")).getId());
            } catch (SQLException | ClassNotFoundException e) {
                System.out.println("500");
            }
            resp.sendRedirect("/house");
        } else {
            resp.sendRedirect("/login");
        }
    }
}

/*
    Object o = req.getParameter("house_name");
    float latitude = Float.parseFloat(req.getParameter("latitude"));
    float longitude = Float.parseFloat(req.getParameter("longitude"));
    String city = req.getParameter("city");
            try {
        new LocationRepository().create(new Location(0, latitude, longitude, city));
    } catch (ClassNotFoundException | SQLException e) {
        e.printStackTrace();
    }
            try {
        new HouseRepository().create(new House(
                0,
                req.getParameter("house_name"),
                user,
                null,
                new HouseClassRepository().findByName(req.getParameter("class")),
                req.getParameter("description"),
                new LocationRepository().findByCoordinats(latitude, longitude)));
    } catch (SQLException | ClassNotFoundException e) {
        e.printStackTrace();
    }
            try {
        resp.sendRedirect("/house?id_house=" + new HouseRepository().findByName(req.getParameter("house_name")).getId());
    } catch (SQLException | ClassNotFoundException e) {
        System.out.println("500");
    }
}*/
