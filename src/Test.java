import help.DbConnection;
import model.User;
import orm.UserRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Test {

    public static void main(String[] args) throws SQLException {

        try {
            ArrayList<User> users = (ArrayList<User>) new UserRepository().findAll();
            for (User user: users) {
                System.out.print(user.toString());
                System.out.print("\n");
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.print("Ошибка в SQL");
        }

    }
}
