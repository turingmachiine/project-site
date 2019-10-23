package orm;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public interface CrudRepository<T> {
    void save(T model);
    void create(T model) throws ClassNotFoundException, SQLException;
    T findByID(int id) throws SQLException, ClassNotFoundException;
    void delete(T model);
    ArrayList<T> findAll() throws SQLException, ClassNotFoundException;
    void update(T model) throws ClassNotFoundException, SQLException;
}
