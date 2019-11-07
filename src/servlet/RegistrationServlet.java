package servlet;

import help.FreeMarkerConfigurator;
import help.Render;
import model.User;
import orm.UserRepository;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
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
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
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
        Render.render(req, resp, "registration.ftl", root);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("current_user");
        DateFormat format = new SimpleDateFormat("dd MMMM yyyy HH:mm");
        String date = format.format(Calendar.getInstance().getTime());
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        KeySpec spec = new PBEKeySpec(req.getParameter("password").toCharArray(), salt, 65536, 128);
        try {
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
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
            String need_path = "/resources/images/" + file.getPath().split("/")[10];
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
