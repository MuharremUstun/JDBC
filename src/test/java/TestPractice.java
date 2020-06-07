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
        while (resultSet.next()) {
            String name = resultSet.getString("name");
            String country = resultSet.getString("country");
            String city = resultSet.getString("city");
            String postal_code = resultSet.getString("postal_code");
            System.out.println(name + " " + country + " " + city + " " + postal_code);
        }
    }

    @Test
    public void test20StudentsWithHighestFee() throws SQLException {
        ResultSet resultSet = statement.executeQuery(
                "SELECT first_name, last_name, fee FROM students ORDER BY fee DESC LIMIT 20;");
        while (resultSet.next()) {
            String firstName = resultSet.getString("first_name");
            String lastName = resultSet.getString("last_name");
            Double fee = resultSet.getDouble("fee");
            System.out.println(firstName + " " + lastName + " " + fee);
        }
    }

    @Test
    public void testFifthStudentsWithHighestFee() throws SQLException {
        ResultSet resultSet = statement.executeQuery(
                "SELECT first_name, last_name, fee FROM students ORDER BY fee DESC LIMIT 20;");
        resultSet.absolute(5);
        String firstName = resultSet.getString("first_name");
        String lastName = resultSet.getString("last_name");
        Double fee = resultSet.getDouble("fee");
        System.out.println(firstName + " " + lastName + " " + fee);
    }

    @Test
    public void testAvgFeeGroupByCurrencyAndCountry() throws SQLException {
        ResultSet resultSet = statement.executeQuery(
                "SELECT AVG(fee) AS avg_fee, currency, country FROM students GROUP BY currency, country;");
        while (resultSet.next()) {
            String currency = resultSet.getString("currency");
            String country = resultSet.getString("country");
            Double avgFee = resultSet.getDouble("avg_fee");
            Double increasedAvgFee = avgFee * 1.17;
            System.out.println(currency + " " + country + " " + avgFee + " " + increasedAvgFee);
        }
    }

    @Test
    public void test6() throws SQLException {
        ResultSet rs = statement.executeQuery(
                "SELECT fee FROM students WHERE gender = 'male' and country = 'United States' LIMIT 5;");
        while (rs.next()) {
            Double fee = rs.getDouble(1);
            System.out.println("Fee: " + fee);
        }

        statement.execute(
                "UPDATE students SET fee = fee + 10 WHERE gender = 'male' and country = 'United States'");
        System.out.println("---------------------------------");

        rs = statement.executeQuery(
                "SELECT fee FROM students WHERE gender = 'male' and country = 'United States' LIMIT 5;");
        while (rs.next()) {
            Double fee = rs.getDouble("fee");
            System.out.println("Fee: " + fee);
        }
    }

    @Test
    public void test7() throws SQLException {

        PreparedStatement psSelect = connection.prepareStatement(
                "SELECT fee FROM students WHERE gender = ? and country = ? LIMIT ?;");
//        psSelect.setString(1, "fee".substring(1,4));
        psSelect.setString(1, "male");
        psSelect.setString(2, "United States");
        psSelect.setInt(3, 5);
        ResultSet rs = psSelect.executeQuery();
        while (rs.next()) {
            Double fee = rs.getDouble("fee");
            System.out.println("Fee: " + fee);
        }

        PreparedStatement psUpdate = connection.prepareStatement(
                "UPDATE students SET fee = fee + ? WHERE gender = ? and country = ?");
//        psUpdate.setString(1, "fee");
        psUpdate.setInt(1,10);
        psUpdate.setString(2, "male");
        psUpdate.setString(3, "United States");
        psUpdate.executeUpdate();
        System.out.println("---------------------------------");

        rs = psSelect.executeQuery();
        while (rs.next()) {
            Double fee = rs.getDouble("fee");
            System.out.println("Fee: " + fee);
        }
    }

}
