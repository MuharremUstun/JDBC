import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.sql.*;

public class TestPractice {

    private Statement statement;
    private Connection connection;

    @BeforeClass
    public void connect() throws SQLException {
        String host = "jdbc:mysql://database-techno.c771qxmldhez.us-east-2.rds.amazonaws.com:3306/";
        String database = "muharremustun_students_db";
        String url = host.concat(database);
        String user = "muharremustun";
        String password = "muharremustun@gmail.com";
        connection = DriverManager.getConnection(url, user, password);
        statement = connection.createStatement();
    }

    @AfterClass
    public void disconnect() throws SQLException {
        connection.close();
    }

    @Test
    public void test() throws SQLException {
        ResultSet resultSet = statement.executeQuery(
                "SELECT CONCAT(first_name, ' ', last_name) AS name, country, city, postal_code FROM students;");
        while(resultSet.next()){
            String name = resultSet.getString("name");
            String country = resultSet.getString("country");
            String city = resultSet.getString("city");
            String postal_code = resultSet.getString("postal_code");
            System.out.println(name + " " + country + " " + city + " " + postal_code);
        }



    }

}
