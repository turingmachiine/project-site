package orm;

import help.DbConnection;
import model.Post;
import model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class PostRepository implements CrudRepository<Post> {
    @Override
    public void save(Post model) {

    }

    public Post findByName(String name) throws SQLException, ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        DbConnection conn = new DbConnection("jdbc:postgresql://localhost:5432/db_project", "baddie",
                "g0yi8o1s");
        PreparedStatement statement = conn.getConn().prepareStatement("SELECT * FROM post_table where title = ?");
        statement.setString(1,name);
        ResultSet rs = statement.executeQuery();
        if (rs.next()) {
            return new Post(
                    rs.getInt("id"),
                    rs.getString("title"),
                    rs.getString("post_text"),
                    rs.getString("post_date"),
                    new HouseRepository().findByID(rs.getInt("house_ref")),
                    new UserRepository().findByID(rs.getInt("author")),
                    rs.getString("image"));
        }
        else return null;
    }


    @Override
    public void create(Post model) throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        DbConnection conn = new DbConnection("jdbc:postgresql://localhost:5432/db_project", "baddie",
                "g0yi8o1s");
        PreparedStatement statement = conn.getConn().prepareStatement("INSERT INTO post_table VALUES " +
                "(DEFAULT, ?, ?, ?, ?, ?, ?)");
        statement.setString(1, model.getTitle());
        statement.setString(2, model.getPostText());
        statement.setString(3,model.getPostDate());
        if (model.getHouseReference() == null) {
            statement.setObject(4, null);
        } else {
            statement.setInt(4,model.getHouseReference().getId());
        }
        statement.setInt(5, model.getAuthor().getId());
        statement.setString(6, model.getImage());
        statement.executeUpdate();
    }

    @Override
    public Post findByID(int id) throws SQLException, ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        DbConnection conn = new DbConnection("jdbc:postgresql://localhost:5432/db_project", "baddie",
                "g0yi8o1s");
        PreparedStatement statement = conn.getConn().prepareStatement("SELECT * FROM post_table where id = ?");
        statement.setInt(1,id);
        ResultSet rs = statement.executeQuery();
        if (rs.next()) {
            return new Post(
                    rs.getInt("id"),
                    rs.getString("title"),
                    rs.getString("post_text"),
                    rs.getString("post_date"),
                    new HouseRepository().findByID(rs.getInt("house_ref")),
                    new UserRepository().findByID(rs.getInt("author")),
                    rs.getString("image"));
        }
        else return null;

    }

    @Override
    public void delete(Post model) {

    }

    @Override
    public ArrayList<Post> findAll() throws SQLException, ClassNotFoundException {
        ArrayList<Post> result = new ArrayList<>();
        Class.forName("org.postgresql.Driver");
        DbConnection conn = new DbConnection("jdbc:postgresql://localhost:5432/db_project", "baddie",
                "g0yi8o1s");
        Statement statement = conn.getConn().createStatement();
        ResultSet rs = statement.executeQuery("SELECT * FROM post_table");
        while(rs.next()) {
            result.add(new Post(
                    rs.getInt("id"),
                    rs.getString("title"),
                    rs.getString("post_text"),
                    rs.getString("post_date"),
                    new HouseRepository().findByID(rs.getInt("house_ref")),
                    new UserRepository().findByID(rs.getInt("author")),
                    rs.getString("image")));
        }
        return result;
    }

    @Override
    public void update(Post model) {

    }
}
