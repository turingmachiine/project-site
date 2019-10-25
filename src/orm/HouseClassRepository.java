package orm;

import help.DbConnection;
import model.HouseClass;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class HouseClassRepository implements CrudRepository<HouseClass> {
    @Override
    public void save(HouseClass model) {

    }

    @Override
    public void create(HouseClass model) {

    }

    public HouseClass findByName(String name) throws SQLException, ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        DbConnection conn = new DbConnection("jdbc:postgresql://localhost:5432/db_project", "baddie",
                "g0yi8o1s");
        PreparedStatement statement = conn.getConn().prepareStatement("SELECT * FROM house_class where class = ?");
        statement.setString(1,name);
        ResultSet rs = statement.executeQuery();
        if (rs.next()) {
            return new HouseClass(rs.getInt("id"), rs.getString("class"));
        }
        else return null;
    }

    @Override
    public HouseClass findByID(int id) throws SQLException, ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        DbConnection conn = new DbConnection("jdbc:postgresql://localhost:5432/db_project", "baddie",
                "g0yi8o1s");
        PreparedStatement statement = conn.getConn().prepareStatement("SELECT * FROM house_class where id = ?");
        statement.setInt(1,id);
        ResultSet rs = statement.executeQuery();
        if (rs.next()) {
            return new HouseClass(rs.getInt("id"), rs.getString("class"));
        }
        else return null;
    }

    @Override
    public void delete(HouseClass model) {

    }

    @Override
    public ArrayList<HouseClass> findAll() throws SQLException, ClassNotFoundException {
        ArrayList<HouseClass> result = new ArrayList<>();
        Class.forName("org.postgresql.Driver");
        DbConnection conn = new DbConnection("jdbc:postgresql://localhost:5432/db_project", "baddie",
                "g0yi8o1s");
        Statement statement = conn.getConn().createStatement();
        ResultSet rs = statement.executeQuery("SELECT * FROM house_class");
        while(rs.next()) {
            result.add(new HouseClass(rs.getInt("id"), rs.getString("class")));
        }
        return result;

    }

    @Override
    public void update(HouseClass model) {

    }
}
