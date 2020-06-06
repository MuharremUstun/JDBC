import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.sql.*;

public class TestCases {

    private Statement statement;
    private Connection connection;

    @BeforeClass
    public void connect() throws SQLException {
        String host = "jdbc:mysql://database-techno.c771qxmldhez.us-east-2.rds.amazonaws.com:3306/";
        String database = "muharremustun_students_db";
        String url = host.concat(database);
//      String url = "jdbc:mysql://database-techno.c771qxmldhez.us-east-2.rds.amazonaws.com:3306/muharremustun_students_db";
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

        ResultSet rs;
        System.out.println("Before the update of the db.");
        rs = statement.executeQuery("SELECT first_name, gender, fee FROM students limit 20;");
        while (rs.next()) {
            String name = rs.getString(1);
            String gender = rs.getString(2);
            String fee = rs.getString(3);
            System.out.println(name + " " + gender + " " + fee);
        }

//        statement.executeUpdate("UPDATE students SET fee = (fee * .9) WHERE gender = 'Female';");
        PreparedStatement preparedStatement = connection.prepareStatement(
                "UPDATE students SET fee = (fee * ?) WHERE gender = ?;"
        );
        preparedStatement.setDouble(1, 1.15);
        preparedStatement.setString(2, "Male");
        preparedStatement.executeUpdate();

        System.out.println("\nAfter the update of the db.");
        rs = statement.executeQuery("SELECT first_name, gender, fee FROM students limit 20;");
        while (rs.next()) {
            String name = rs.getString(1);
            String gender = rs.getString(2);
            String fee = rs.getString(3);
            System.out.println(name + " " + gender + " " + fee);
        }
    }
}
