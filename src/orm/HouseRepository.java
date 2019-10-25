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

    public ArrayList<String> getAllImages(House model) throws ClassNotFoundException, SQLException {
        ArrayList<String> res = new ArrayList<>();
        ArrayList<House> result = new ArrayList<>();
        Class.forName("org.postgresql.Driver");
        DbConnection conn = new DbConnection("jdbc:postgresql://localhost:5432/db_project", "baddie",
                "g0yi8o1s");
        PreparedStatement statement = conn.getConn().prepareStatement("SELECT image FROM house_images WHERE house = ?");
        statement.setInt(1, model.getId());
        ResultSet rs = statement.executeQuery();
        while (rs.next()) {
            res.add(rs.getString("image"));
        }
        return res;
    }

    public House findByName(String name) throws SQLException, ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        DbConnection conn = new DbConnection("jdbc:postgresql://localhost:5432/db_project", "baddie",
                "g0yi8o1s");
        PreparedStatement statement = conn.getConn().prepareStatement("SELECT * FROM old_house_table where name = ?");
        statement.setString(1,name);
        ResultSet rs = statement.executeQuery();
        if (rs.next()) {
            return new House(
                    rs.getInt("id"),
                    rs.getString("name"),
                    new UserRepository().findByID(rs.getInt("creator")),
                    rs.getString("cre_date"),
                    new HouseClassRepository().findByID(rs.getInt("class_ref")),
                    rs.getString("description"),
                    new LocationRepository().findByID(rs.getInt("location")));
        }
        else return null;

    }
    @Override
    public void create(House model) throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        DbConnection conn = new DbConnection("jdbc:postgresql://localhost:5432/db_project", "baddie",
                "g0yi8o1s");
        PreparedStatement statement = conn.getConn().prepareStatement("INSERT INTO old_house_table VALUES " +
                "(DEFAULT, ?, ?, ?, ?, ?, ?)");
        statement.setString(1,model.getName());
        statement.setInt(2, model.getCreator().getId());
        statement.setString(3,model.getCreationDate());
        statement.setInt(4, model.getHouseClass().getId());
        statement.setString(5, model.getDescription());
        statement.setInt(6, model.getLocation().getId());
        statement.executeUpdate();

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
            return new House(
                    rs.getInt("id"),
                    rs.getString("name"),
                    new UserRepository().findByID(rs.getInt("creator")),
                    rs.getString("cre_date"),
                    new HouseClassRepository().findByID(rs.getInt("class_ref")),
                    rs.getString("description"),
                    new LocationRepository().findByID(rs.getInt("location")));
        }
        else return null;

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
                    new LocationRepository().findByID(rs.getInt("location"))));
        }
        return result;
    }

    @Override
    public void update(House model) throws ClassNotFoundException, SQLException {

    }

}
