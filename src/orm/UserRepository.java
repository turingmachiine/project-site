package orm;
import help.DbConnection;
import model.User;

import java.io.FileNotFoundException;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class UserRepository implements CrudRepository<User>{
    @Override
    public void save(User model) {

    }

    @Override
    public void create(User model) throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        DbConnection conn = new DbConnection("jdbc:postgresql://localhost:5432/db_project", "baddie",
                "g0yi8o1s");
        PreparedStatement statement = conn.getConn().prepareStatement("INSERT INTO user_table VALUES " +
                "(DEFAULT, ?, ?, ?, ?, ?, ?)");
        statement.setString(1,model.getFirstName());
        statement.setString(2, model.getLastName());
        statement.setString(3, model.getEmail());
        statement.setString(4, model.getPassword());
        statement.setString(5, model.getProfilePic());
        statement.setString(6,model.getRegDate());
        statement.executeUpdate();
    }

    @Override
    public User findByID(int id) throws SQLException, ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        DbConnection conn = new DbConnection("jdbc:postgresql://localhost:5432/db_project", "baddie",
                "g0yi8o1s");
        PreparedStatement statement = conn.getConn().prepareStatement("SELECT * FROM user_table where id = ?");
        statement.setInt(1,id);
        ResultSet rs = statement.executeQuery();
        if (rs.next()) {
            return new User(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("lastname"),
                    rs.getString("email"),
                    rs.getString("password"),
                    rs.getString("profile_pic"),
                    rs.getString("reg_date"));
        }
        else return null;
    }

    public User findUserByName(String name) throws SQLException, ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        DbConnection conn = new DbConnection("jdbc:postgresql://localhost:5432/db_project", "baddie",
                "g0yi8o1s");
        PreparedStatement statement = conn.getConn().prepareStatement("SELECT * FROM user_table WHERE name = ?");
        statement.setString(1, name);
        ResultSet rs = statement.executeQuery();
        if (rs.next()) {
            return new User(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("lastname"),
                    rs.getString("email"),
                    rs.getString("password"),
                    rs.getString("profile_pic"),
                    rs.getString("reg_date"));
        }
        else return null;
    }

    public User validateUser(String email, String pass) throws SQLException, ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        DbConnection conn = new DbConnection("jdbc:postgresql://localhost:5432/db_project", "baddie",
                "g0yi8o1s");
        PreparedStatement statement = conn.getConn().prepareStatement("SELECT * FROM user_table " +
                "WHERE email = ? AND password = ?");
        statement.setString(1, email);
        statement.setString(2, pass);
        ResultSet rs = statement.executeQuery();
        if (rs.next()) {
            return new User(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("lastname"),
                    rs.getString("email"),
                    rs.getString("password"),
                    rs.getString("profile_pic"),
                    rs.getString("reg_date"));
        }
        else return null;
    }

    @Override
    public void delete(User model) {

    }

    @Override
    public ArrayList<User> findAll() throws SQLException, ClassNotFoundException {
        ArrayList<User> result = new ArrayList<>();
        Class.forName("org.postgresql.Driver");
        DbConnection conn = new DbConnection("jdbc:postgresql://localhost:5432/db_project", "baddie",
                "g0yi8o1s");
        Statement statement = conn.getConn().createStatement();
        ResultSet rs = statement.executeQuery("SELECT * FROM user_table");
        while(rs.next()) {
            result.add(new User(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("lastname"),
                    rs.getString("email"),
                    rs.getString("password"),
                    rs.getString("profile_pic"),
                    null));
        }
        return result;
    }

    @Override
    public void update(User model) throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        DbConnection conn = new DbConnection("jdbc:postgresql://localhost:5432/db_project", "baddie",
                "g0yi8o1s");
        if (!model.getFirstName().equals("")){
            PreparedStatement statement = conn.getConn().prepareStatement("UPDATE user_table SET name = ? WHERE id = ?");
            statement.setString(1, model.getFirstName());
            statement.setInt(2, model.getId());
            statement.executeUpdate();
        }
        if (!model.getLastName().equals("")){
            PreparedStatement statement = conn.getConn().prepareStatement("UPDATE user_table SET lastname = ? WHERE id = ?");
            statement.setString(1, model.getLastName());
            statement.setInt(2, model.getId());
            statement.executeUpdate();
        }
        if (!model.getEmail().equals("")) {
            PreparedStatement statement = conn.getConn().prepareStatement("UPDATE user_table SET email = ? WHERE id = ?");
            statement.setString(1, model.getEmail());
            statement.setInt(2, model.getId());
            statement.executeUpdate();
        }
        if (!model.getPassword().equals("")){
            PreparedStatement statement = conn.getConn().prepareStatement("UPDATE user_table SET password = ? WHERE id = ?");
            statement.setString(1, model.getPassword());
            statement.setInt(2, model.getId());
            statement.executeUpdate();
        }

        if (model.getProfilePic() != null){
            PreparedStatement statement = conn.getConn().prepareStatement("UPDATE user_table SET profile_pic = ? WHERE id = ?");
            statement.setString(1, model.getProfilePic());
            statement.setInt(2, model.getId());
            statement.executeUpdate();
        }

    }
}
