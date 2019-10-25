package servlet;

import help.FreeMarkerConfigurator;
import help.Render;
import model.User;
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
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/registration")
@MultipartConfig
public class RegistrationServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        FreeMarkerConfigurator.getInstance(this);
        Map<String, Object> root = new HashMap<>();
        root.put("context",req.getContextPath());
        HttpSession session = req.getSession();
        resp.setCharacterEncoding("utf-8");
        User user = (User) session.getAttribute("current_user");
        Cookie[] cookies = req.getCookies();
        if (user == null && cookies != null) {
            for (Cookie cookie: cookies) {
                try {
                    user = new UserRepository().validateUser(cookie.getName(), cookie.getValue());
                    if (user != null) {
                        break;
                    }
                } catch (SQLException | ClassNotFoundException e) {
                    System.out.println("500");
                }
            }
        }
        if (user != null) {
            session.setAttribute("current_user", user);
            resp.sendRedirect("/profile");
        } else {
            Render.render(req, resp, "registration.ftl", root);

        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("current_user");
        DateFormat format = new SimpleDateFormat("dd/mm/yyyy");
        String date = format.format(Calendar.getInstance().getTime());

        if (user != null) {
            resp.sendRedirect("/profile");
        } else {
            Part filePart = req.getPart("file"); // Retrieves <input type="file" name="file">
            String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString(); // MSIE fix.
            if (fileName.equals("")) {
                try {
                    new UserRepository().create(new User(
                            0,
                            req.getParameter("first_name"),
                            req.getParameter("last_name"),
                            req.getParameter("email"),
                            req.getParameter("password"),
                            null,
                            date
                    ));
                } catch (ClassNotFoundException | SQLException e) {
                    System.out.println("e.printStackTrace();");
                }
            } else {
                String[] filenames = fileName.split("\\.");
                InputStream fileContent = filePart.getInputStream();
                File uploads = new File("/home/baddie/IdeaProjects/project-site/out/artifacts/project_site_war_exploded/resources/images");
                File file = File.createTempFile("img", "." + filenames[1], uploads);
                Files.copy(fileContent, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
                System.out.println(file.toPath());
                System.out.println(file.getPath());
                String need_path = "/resources/images/" + file.getPath().split("/")[10];
                System.out.println(need_path);
                try {
                    new UserRepository().create(new User(
                            0,
                            req.getParameter("first_name"),
                            req.getParameter("last_name"),
                            req.getParameter("email"),
                            req.getParameter("password"),
                            need_path,
                            date
                    ));
                } catch (ClassNotFoundException | SQLException e) {
                    System.out.println("e.printStackTrace();");
                }
            }
            try {
                session.setAttribute("current_user", new UserRepository().findUserByName(req.getParameter("first_name")));
            } catch (SQLException | ClassNotFoundException e) {
                System.out.print("500");
            }
            resp.sendRedirect("/profile");
        }
    }
}
