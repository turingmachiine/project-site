package servlet;

import model.Comment;
import model.User;
import orm.CommentRepository;
import orm.PostRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

@WebServlet("/comment")
public class CommentServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        System.out.println(req.getParameter("comment"));
        DateFormat format = new SimpleDateFormat("dd MMMM yyyy HH:mm");
        String date = format.format(Calendar.getInstance().getTime());
        User usr = (User) req.getSession().getAttribute("current_user");
        try {
            new CommentRepository().create(new Comment(
                    0,
                    req.getParameter("comment"),
                    date,
                    0,
                    0,
                    (User) req.getSession().getAttribute("current_user"),
                    new PostRepository().findByID(Integer.parseInt(req.getParameter("id")))));
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(500);
        }
        resp.sendRedirect("/post?id=" + req.getParameter("id"));
    }
}
