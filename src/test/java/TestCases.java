import org.testng.annotations.Test;

import java.sql.*;

public class TestCases {
    @Test
    public void test() throws SQLException {
        String host = "jdbc:mysql://database-techno.c771qxmldhez.us-east-2.rds.amazonaws.com:3306/";
        String database = "muharremustun_students_db";
        String url = host.concat(database);
//      String url = "jdbc:mysql://database-techno.c771qxmldhez.us-east-2.rds.amazonaws.com:3306/muharremustun_students_db";
        String user = "muharremustun";
        String password = "muharremustun@gmail.com";
        Connection connection = DriverManager.getConnection(url, user, password);
        Statement statement = connection.createStatement();
        ResultSet rs= statement.executeQuery("SELECT first_name, last_name, email FROM students;");
        while (rs.next()){
            String name =rs.getString(1);
            String lastName =rs.getString(2);
            String email =rs.getString(3);
            System.out.println(name + " " + lastName + " " + email);
        }


    }
}
