package it.uniroma3.projectBD.util;

import java.sql.*;
import java.util.Vector;

public class RDTest {
    private static final String URL = "jdbc:clickhouse://localhost:8123/extracted_data";
    private static final String USER = "default";
    private static final String PASSWORD = "";

    public static void main(String[] args) {
        Vector<Object> vec = new Vector<>();
        String id = "a0c7d3640d374a7281146785312ec90f";
        String page_url = "file:/local/cetorelli/exp_naruto/SWDE/restaurant/restaurant_frommers(2000)/0000.htm";

        Connection connection = null;
        Statement statement = null;

        try {
            // register JDBC drive
            Class.forName("ru.yandex.clickhouse.ClickHouseDriver");
            // Open the connection
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connected database successfully");
            // Execute the queries
            statement = connection.createStatement();
            System.out.println("Execute the query");
            String sql = "SELECT * FROM extracted_data." + id + " WHERE page_url = '" + page_url + "'";
            System.out.println(sql);
            ResultSet rs = statement.executeQuery(sql);
            ResultSetMetaData rsmd = rs.getMetaData();

            while (rs.next()) {
                for (int i = 1; i < rsmd.getColumnCount(); i++){
                    System.out.println(i);
                    vec.add(rs.getObject(i));
                }
            }
            System.out.println("This is the result vector:\n");
            System.out.println(vec + "\n");


        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {

            // Release resources
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

}
