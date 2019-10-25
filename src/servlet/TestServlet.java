package servlet;

import help.FreeMarkerConfigurator;
import help.Render;
import model.User;
import orm.UserRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/test")
@MultipartConfig
public class TestServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        FreeMarkerConfigurator.getInstance(this);
        Map<String, Object> root = new HashMap<>();
        Render.render(req, resp, "test.ftl", root);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Part filePart = req.getPart("file"); // Retrieves <input type="file" name="file">
        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString(); // MSIE fix.
        String[] filenames = fileName.split("\\.");
        InputStream fileContent = filePart.getInputStream();
        File uploads = new File("/home/baddie/IdeaProjects/project-site/web/WEB-INF/assets/images");
        File file = File.createTempFile(filenames[0], "." + filenames[1], uploads);
        Files.copy(fileContent, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
        try {
            new UserRepository().create(new User(
                    0,
                    req.getParameter("first_name"),
                    req.getParameter("last_name"),
                    req.getParameter("email"),
                    req.getParameter("password"),
                    file.toPath().toString(),
                    null
            ));
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("e.printStackTrace();");
        }
        resp.sendRedirect("/test");
    }
}
