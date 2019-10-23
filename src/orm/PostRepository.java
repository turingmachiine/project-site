package orm;

import help.DbConnection;
import model.Post;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class PostRepository implements CrudRepository<Post> {
    @Override
    public void save(Post model) {

    }

    @Override
    public void create(Post model) throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        DbConnection conn = new DbConnection("jdbc:postgresql://localhost:5432/db_project", "baddie",
                "g0yi8o1s");
        PreparedStatement statement = conn.getConn().prepareStatement("INSERT INTO post_table VALUES " +
                "(DEFAULT, ?, ?, NULL, ?)");
        statement.setString(1, model.getTitle());
        statement.setString(2, model.getPostText());
        statement.setInt(3, model.getHouseReference().getId());
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
                    null,
                    new HouseRepository().findByID(rs.getInt("house_ref")));
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
                    null,
                    new HouseRepository().findByID(rs.getInt("house_ref"))));
        }
        return result;
    }

    @Override
    public void update(Post model) {

    }
}
