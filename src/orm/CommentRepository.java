package orm;

import help.DbConnection;
import model.Comment;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class CommentRepository implements CrudRepository<Comment> {
    @Override
    public void save(Comment model) {

    }

    @Override
    public void create(Comment model) throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        DbConnection conn = new DbConnection("jdbc:postgresql://localhost:5432/db_project", "baddie",
                "g0yi8o1s");
        PreparedStatement statement = conn.getConn().prepareStatement("INSERT INTO comment_table VALUES " +
                "(DEFAULT, ?, ?, ?, ?, ?, ?)");
        statement.setString(1,model.getCommentText());
        statement.setString(2, model.getCommentDate());
        statement.setInt(3, model.getUpvotes());
        statement.setInt(4, model.getDownvotes());
        statement.setInt(5, model.getUser().getId());
        statement.setInt(6, model.getPost().getId());
        statement.executeUpdate();
        conn.close();
    }

    @Override
    public Comment findByID(int id) throws SQLException, ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        DbConnection conn = new DbConnection("jdbc:postgresql://localhost:5432/db_project", "baddie",
                "g0yi8o1s");
        PreparedStatement statement = conn.getConn().prepareStatement("SELECT * FROM comment_table where id = ?");
        statement.setInt(1,id);
        ResultSet rs = statement.executeQuery();
        if (rs.next()) {
            conn.close();
            return new Comment(
                    rs.getInt("id"),
                    rs.getString("comment_text"),
                    rs.getString("comment_date"),
                    rs.getInt("upvotes"),
                    rs.getInt("downvotes"),
                    new UserRepository().findByID(rs.getInt("user_ref")),
                    new PostRepository().findByID(rs.getInt("post_ref")));
        } else {
            conn.close();
            return null;
        }
    }

    @Override
    public void delete(Comment model) {

    }

    @Override
    public ArrayList<Comment> findAll() throws SQLException, ClassNotFoundException {
        ArrayList<Comment> result = new ArrayList<>();
        Class.forName("org.postgresql.Driver");
        DbConnection conn = new DbConnection("jdbc:postgresql://localhost:5432/db_project", "baddie",
                "g0yi8o1s");
        Statement statement = conn.getConn().createStatement();
        ResultSet rs = statement.executeQuery("SELECT * FROM comment_table");
        while(rs.next()) {
            result.add(new Comment(
                    rs.getInt("id"),
                    rs.getString("comment_text"),
                    rs.getString("comment_date"),
                    rs.getInt("upvotes"),
                    rs.getInt("downvotes"),
                    new UserRepository().findByID(rs.getInt("user_ref")),
                    new PostRepository().findByID(rs.getInt("post_ref"))));
        }
        conn.close();
        return result;
    }

    @Override
    public void update(Comment model) {

    }

    public ArrayList<Comment> getFromPost(int id) throws ClassNotFoundException, SQLException {
        ArrayList<Comment> result = new ArrayList<>();
        Class.forName("org.postgresql.Driver");
        DbConnection conn = new DbConnection("jdbc:postgresql://localhost:5432/db_project", "baddie",
                "g0yi8o1s");
        PreparedStatement statement = conn.getConn().prepareStatement("SELECT * FROM comment_table WHERE post_ref = ?");
        statement.setInt(1, id);
        ResultSet rs = statement.executeQuery();
        while(rs.next()) {
            result.add(new Comment(
                    rs.getInt("id"),
                    rs.getString("comment_text"),
                    rs.getString("comment_date"),
                    rs.getInt("upvotes"),
                    rs.getInt("downvotes"),
                    new UserRepository().findByID(rs.getInt("user_ref")),
                    new PostRepository().findByID(rs.getInt("post_ref"))));
        }
        conn.close();
        return result;
    }
}