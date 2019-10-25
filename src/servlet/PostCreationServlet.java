package servlet;

import help.FreeMarkerConfigurator;
import help.Render;
import model.Post;
import model.User;
import orm.HouseRepository;
import orm.PostRepository;
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


@WebServlet("/create_post")
@MultipartConfig
public class PostCreationServlet extends HttpServlet {
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
            ArrayList<String> names = new ArrayList<>();
            Render.render(req, resp, "create_post.ftl", root);
        } else {
            resp.sendRedirect("/login");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User usr = (User) session.getAttribute("current_user");
        if (usr != null) {
            try {
                Part filePart = req.getPart("file"); // Retrieves <input type="file" name="file">
                String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
                if (fileName.equals("")) {
                    DateFormat format = new SimpleDateFormat("dd/mm/yyyy");
                    String date = format.format(Calendar.getInstance().getTime());
                    new PostRepository().create(new Post(
                            0,
                            req.getParameter("title"),
                            req.getParameter("text"),
                            date,
                            new HouseRepository().findByName(req.getParameter("house")),
                            usr,
                            null));
                } else {
                    String[] filenames = fileName.split("\\.");
                    InputStream fileContent = filePart.getInputStream();
                    File uploads = new File("/home/baddie/IdeaProjects/project-site/web/assets/images/posts");
                    File file = File.createTempFile("img", "." + filenames[1], uploads);
                    Files.copy(fileContent, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
                    System.out.println(file.toPath());
                    System.out.println(file.getPath());
                    String need_path = "/" + file.getPath().split("/")[6] + "/" + file.getPath().split("/")[7] +
                            "/" + file.getPath().split("/")[8];

                    DateFormat format = new SimpleDateFormat("dd/mm/yyyy");
                    String date = format.format(new Date(System.currentTimeMillis()));
                    new PostRepository().create(new Post(
                            0,
                            req.getParameter("title"),
                            req.getParameter("text"),
                            date,
                            new HouseRepository().findByName(req.getParameter("house")),
                            usr,
                            need_path));
                }
            } catch(SQLException | ClassNotFoundException e){
                    System.out.println("500");
                }
                try {
                    session.setAttribute("id", new PostRepository().findByName(
                            req.getParameter("title")).getId());
                } catch (SQLException | ClassNotFoundException e) {
                    System.out.println("500");
                }
                resp.sendRedirect("/post");
            } else {
            resp.sendRedirect("/login");
        }
    }
}