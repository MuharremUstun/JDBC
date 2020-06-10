import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.sql.*;

public class DataProviderTest {

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

    @DataProvider(name = "students")
    public Object[][] studentData() throws SQLException {
        ResultSet resultSet = statement.executeQuery(
                "SELECT first_name, last_name, email, gender, country, fee FROM students;");
        resultSet.last();
        int rowCount = resultSet.getRow();
//        resultSet.beforeFirst();
        Object[][] resultData = new Object[rowCount][6];
        for (int i = 0; i < rowCount; i++) {
            resultSet.absolute(i+1);
            for (int j = 0; j < 6; j++) {
                resultData[i][j] = resultSet.getString(j+1);
            }
        }
        return resultData;
    }

    @Test (dataProvider = "students")
    public void test(String c1, String c2, String c3, String c4, String c5, String c6){
        System.out.println(c1 + ", " + c2 + ", " + c3+ ", " + c4 + ", " + c5 + ", " + c6);
    }
}
