package help;

import java.sql.*;

public class DbConnection {
    Connection conn;

    public Connection getConn() {
        return conn;
    }

    public void setConn(Connection conn) {
        this.conn = conn;
    }

    public DbConnection(String url, String user, String password) throws SQLException {
        this.conn = DriverManager.getConnection(url, user, password);
    }
}
