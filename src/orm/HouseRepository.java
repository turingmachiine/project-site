package orm;

import help.DbConnection;
import model.House;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class HouseRepository implements CrudRepository<House> {

    @Override
    public void save(House model) {

    }

    public ArrayList<House> findHousesByUser(int id) throws SQLException, ClassNotFoundException {
        ArrayList<House> result = new ArrayList<>();
        Class.forName("org.postgresql.Driver");
        DbConnection conn = new DbConnection("jdbc:postgresql://localhost:5432/db_project", "baddie",
                "g0yi8o1s");
        PreparedStatement statement = conn.getConn().prepareStatement("SELECT * FROM old_house_table WHERE creator = ?");
        statement.setInt(1, id);
        ResultSet rs = statement.executeQuery();
        while(rs.next()) {
            result.add(new House(
                    rs.getInt("id"),
                    rs.getString("name"),
                    new UserRepository().findByID(id),
                    rs.getString("cre_date"),
                    new HouseClassRepository().findByID(rs.getInt("class_ref")),
                    rs.getString("description"),
                    new LocationRepository().findByID(rs.getInt("location")),
                    rs.getString("image")));
        }
        conn.close();
        return result;
    }

    public ArrayList<House> findOtherHousesByUser(int userId, int houseId) throws SQLException, ClassNotFoundException {
        ArrayList<House> result = new ArrayList<>();
        Class.forName("org.postgresql.Driver");
        DbConnection conn = new DbConnection("jdbc:postgresql://localhost:5432/db_project", "baddie",
                "g0yi8o1s");
        PreparedStatement statement = conn.getConn().prepareStatement("SELECT * FROM old_house_table WHERE creator = ? AND id != ?");
        statement.setInt(1, userId);
        statement.setInt(2, houseId);
        ResultSet rs = statement.executeQuery();
        while(rs.next()) {
            result.add(new House(
                    rs.getInt("id"),
                    rs.getString("name"),
                    new UserRepository().findByID(userId),
                    rs.getString("cre_date"),
                    new HouseClassRepository().findByID(rs.getInt("class_ref")),
                    rs.getString("description"),
                    new LocationRepository().findByID(rs.getInt("location")),
                    rs.getString("image")));
        }
        conn.close();
        return result;
    }

    public House findByName(String name) throws SQLException, ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        DbConnection conn = new DbConnection("jdbc:postgresql://localhost:5432/db_project", "baddie",
                "g0yi8o1s");
        PreparedStatement statement = conn.getConn().prepareStatement("SELECT * FROM old_house_table where name = ?");
        statement.setString(1,name);
        ResultSet rs = statement.executeQuery();
        if (rs.next()) {
            conn.close();
            return new House(
                    rs.getInt("id"),
                    rs.getString("name"),
                    new UserRepository().findByID(rs.getInt("creator")),
                    rs.getString("cre_date"),
                    new HouseClassRepository().findByID(rs.getInt("class_ref")),
                    rs.getString("description"),
                    new LocationRepository().findByID(rs.getInt("location")),
                    rs.getString("image"));
        }
        else {
            conn.close();
            return null;
        }
    }
    @Override
    public void create(House model) throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        DbConnection conn = new DbConnection("jdbc:postgresql://localhost:5432/db_project", "baddie",
                "g0yi8o1s");
        PreparedStatement statement = conn.getConn().prepareStatement("INSERT INTO old_house_table VALUES " +
                "(DEFAULT, ?, ?, ?, ?, ?, ?, ?)");
        statement.setString(1,model.getName());
        statement.setInt(2, model.getCreator().getId());
        statement.setString(3,model.getCreationDate());
        statement.setInt(4, model.getHouseClass().getId());
        statement.setString(5, model.getDescription());
        statement.setInt(6, model.getLocation().getId());
        statement.setString(7, model.getImage());
        statement.executeUpdate();
        conn.close();
    }

    @Override
    public House findByID(int id) throws SQLException, ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        DbConnection conn = new DbConnection("jdbc:postgresql://localhost:5432/db_project", "baddie",
                "g0yi8o1s");
        PreparedStatement statement = conn.getConn().prepareStatement("SELECT * FROM old_house_table where id = ?");
        statement.setInt(1,id);
        ResultSet rs = statement.executeQuery();
        if (rs.next()) {
            conn.close();
            return new House(
                    rs.getInt("id"),
                    rs.getString("name"),
                    new UserRepository().findByID(rs.getInt("creator")),
                    rs.getString("cre_date"),
                    new HouseClassRepository().findByID(rs.getInt("class_ref")),
                    rs.getString("description"),
                    new LocationRepository().findByID(rs.getInt("location")),
                    rs.getString("image"));
        }
        else {
            conn.close();
            return null;
        }

    }

    @Override
    public void delete(House model) {

    }

    @Override
    public ArrayList<House> findAll() throws SQLException, ClassNotFoundException {
        ArrayList<House> result = new ArrayList<>();
        Class.forName("org.postgresql.Driver");
        DbConnection conn = new DbConnection("jdbc:postgresql://localhost:5432/db_project", "baddie",
                "g0yi8o1s");
        Statement statement = conn.getConn().createStatement();
        ResultSet rs = statement.executeQuery("SELECT * FROM old_house_table");
        while(rs.next()) {
            result.add(new House(
                    rs.getInt("id"),
                    rs.getString("name"),
                    new UserRepository().findByID(rs.getInt("creator")),
                    rs.getString("cre_date"),
                    new HouseClassRepository().findByID(rs.getInt("class_ref")),
                    rs.getString("description"),
                    new LocationRepository().findByID(rs.getInt("location")),
                    rs.getString("image")));
        }
        conn.close();
        return result;
    }

    @Override
    public void update(House model) throws ClassNotFoundException, SQLException {

    }

    public ArrayList<House> findByNameLike(String name) throws ClassNotFoundException, SQLException {
        ArrayList<House> result = new ArrayList<>();
        Class.forName("org.postgresql.Driver");
        DbConnection conn = new DbConnection("jdbc:postgresql://localhost:5432/db_project", "baddie",
                "g0yi8o1s");
        PreparedStatement statement = conn.getConn().prepareStatement("SELECT * FROM old_house_table WHERE name like ?");
        statement.setString(1, "%" + name + "%");
        ResultSet rs = statement.executeQuery();
        while(rs.next()) {
            result.add(new House(
                    rs.getInt("id"),
                    rs.getString("name"),
                    new UserRepository().findByID(rs.getInt("creator")),
                    rs.getString("cre_date"),
                    new HouseClassRepository().findByID(rs.getInt("class_ref")),
                    rs.getString("description"),
                    new LocationRepository().findByID(rs.getInt("location")),
                    rs.getString("image")));
        }
        conn.close();
        return result;
    }
}
