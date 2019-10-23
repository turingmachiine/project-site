package orm;

import help.DbConnection;
import model.Location;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class LocationRepository implements CrudRepository<Location> {
    @Override
    public void save(Location model) {

    }

    @Override
    public void create(Location model) {

    }

    @Override
    public Location findByID(int id) throws SQLException, ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        DbConnection conn = new DbConnection("jdbc:postgresql://localhost:5432/db_project", "baddie",
                "g0yi8o1s");
        PreparedStatement statement = conn.getConn().prepareStatement("SELECT * FROM location_table where id = ?");
        statement.setInt(1,id);
        ResultSet rs = statement.executeQuery();
        if (rs.next()) {
            return new Location(
                    rs.getInt("id"),
                    rs.getFloat("latitude"),
                    rs.getFloat("longitude"),
                    rs.getString("city"));
        }
        else return null;

    }

    @Override
    public void delete(Location model) {

    }

    @Override
    public ArrayList<Location> findAll() throws SQLException, ClassNotFoundException {
        ArrayList<Location> result = new ArrayList<>();
        Class.forName("org.postgresql.Driver");
        DbConnection conn = new DbConnection("jdbc:postgresql://localhost:5432/db_project", "baddie",
                "g0yi8o1s");
        Statement statement = conn.getConn().createStatement();
        ResultSet rs = statement.executeQuery("SELECT * FROM location_table");
        while(rs.next()) {
            result.add(new Location(
                    rs.getInt("id"),
                    rs.getFloat("latitude"),
                    rs.getFloat("longitude"),
                    rs.getString("city")));
        }
        return result;
    }

    @Override
    public void update(Location model) {

    }

}
