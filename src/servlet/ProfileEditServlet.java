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
        FreeMarkerConfigurator.getInstance(this);
        Map<String, Object> root = new HashMap<>();
        root.put("user", usr);
        Render.render(req, resp, "edit.ftl", root);


    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        HttpSession session = req.getSession();
        User usr = (User) session.getAttribute("current_user");
        DateFormat format = new SimpleDateFormat("dd MMMM yyyy hh:mm");
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
                String rel_path = "/resources/images/";
                File uploads = new File(getServletContext().getRealPath("") + rel_path);
                File file = File.createTempFile("img", "." + filenames[1], uploads);
                Files.copy(fileContent, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
                String[] parts = file.getPath().split("/");
                String need_path = rel_path + parts[parts.length -1];
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
