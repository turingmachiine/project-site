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

@WebServlet("/edit")
@MultipartConfig
public class ProfileEditServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User usr = (User) session.getAttribute("current_user");
        resp.setCharacterEncoding("utf-8");
        if (usr != null) {
            FreeMarkerConfigurator.getInstance(this);
            Map<String, Object> root = new HashMap<>();
            root.put("context", req.getContextPath());
            root.put("firstname", usr.getFirstName());
            root.put("lastname", usr.getLastName());
            root.put("email", usr.getEmail());
            root.put("user_pic", usr.getProfilePic());
            Render.render(req, resp, "edit.ftl", root);
        } else {
            resp.sendRedirect("/login");
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User usr = (User) session.getAttribute("current_user");
        DateFormat format = new SimpleDateFormat("dd/mm/yyyy");
        String date = format.format(Calendar.getInstance().getTime());
        if (usr != null) {
            Part filePart = req.getPart("file"); // Retrieves <input type="file" name="file">
            String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();// MSIE fix.
            if (fileName.equals("")) {
                try {
                    new UserRepository().update(new User(
                            usr.getId(),
                            req.getParameter("first_name"),
                            req.getParameter("last_name"),
                            req.getParameter("email"),
                            req.getParameter("password"),
                            null,
                            date));
                } catch (ClassNotFoundException | SQLException e) {
                    System.out.println("e.printStackTrace();");
                }
            } else {
                String[] filenames = fileName.split("\\.");
                InputStream fileContent = filePart.getInputStream();
                File uploads = new File("/home/baddie/IdeaProjects/project-site/web/assets/images");
                File file = File.createTempFile("img", "." + filenames[1], uploads);
                Files.copy(fileContent, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
                String need_path = "/" + file.getPath().split("/")[6] + "/" + file.getPath().split("/")[7] +
                        "/" + file.getPath().split("/")[8];
                System.out.println(need_path);
                try {
                    new UserRepository().update(new User(
                            usr.getId(),
                            req.getParameter("first_name"),
                            req.getParameter("last_name"),
                            req.getParameter("email"),
                            req.getParameter("password"),
                            need_path,
                            date));
                } catch (ClassNotFoundException | SQLException e) {
                    System.out.println("e.printStackTrace();");
                }
            }
            try {
                session.setAttribute("current_user", new UserRepository().findByID(usr.getId()));
            } catch (SQLException | ClassNotFoundException e) {
                System.out.println("500");
            }
            resp.sendRedirect("/profile");
        } else {
            resp.sendRedirect("/login");
        }
    }
}