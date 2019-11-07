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
import javax.servlet.http.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@WebServlet("/create_house")
@MultipartConfig
public class HouseCreationServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("utf-8");
        FreeMarkerConfigurator.getInstance(this);
        Map<String, Object> root = new HashMap<>();
        HttpSession session = req.getSession();
        User usr = (User) session.getAttribute("current_user");
        root.put("user", usr);
        ArrayList<HouseClass> houseClasses = new ArrayList<>();
        try {
            houseClasses = new HouseClassRepository().findAll();
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("500");
        }
        root.put("classes", houseClasses);
        Render.render(req, resp, "create_house.ftl", root);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        HttpSession session = req.getSession();
        User usr = (User) session.getAttribute("current_user");
        float latitude = Float.parseFloat(req.getParameter("latitude"));
        float longitude = Float.parseFloat(req.getParameter("longitude"));
        String city = req.getParameter("city");
        DateFormat format = new SimpleDateFormat("dd MMMM yyyy HH:mm");
        String date = format.format(Calendar.getInstance().getTime());
        try {
            new LocationRepository().create(new Location(0, latitude, longitude, city));
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        Part filePart = req.getPart("file"); // Retrieves <input type="file" name="file">
        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
        try {
            if (fileName.equals("")) {
                new HouseRepository().create(new House(
                        0,
                        req.getParameter("house_name"),
                        usr,
                        date,
                        new HouseClassRepository().findByName(req.getParameter("class")),
                        req.getParameter("description"),
                        new LocationRepository().findByCoordinats(latitude, longitude),
                        null));
            } else {
                String[] filenames = fileName.split("\\.");
                InputStream fileContent = filePart.getInputStream();
                File uploads = new File("/home/baddie/IdeaProjects/project-site/out/artifacts/project_site_war_exploded/resources/images");
                File file = File.createTempFile("img", "." + filenames[1], uploads);
                Files.copy(fileContent, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
                String need_path = "/resources/images/" + file.getPath().split("/")[10];
                new HouseRepository().create(new House(
                        0,
                        req.getParameter("house_name"),
                        usr,
                        date,
                        new HouseClassRepository().findByName(req.getParameter("class")),
                        req.getParameter("description"),
                        new LocationRepository().findByCoordinats(latitude, longitude),
                        need_path));
            }
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
    }
}