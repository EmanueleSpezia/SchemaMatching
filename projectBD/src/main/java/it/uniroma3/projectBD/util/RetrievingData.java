package it.uniroma3.projectBD.util;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import java.sql.*;
import java.util.Vector;

@Controller
public class RetrievingData {

    /**
     * This REST method take a table id and a page_url to return the associated
     * object vector of the ClickHouse row (a row of the id table and with column
     * page_url specified).
     * The basic idea is to use the resulting vector as an input to the REST findMatch method.
     *
     * N.B. Remember that the localhost is used, so a tunneling with the clickhouse database must first be started!
     */
    @RequestMapping(value = "/retrieveData", method = RequestMethod.GET)
    public Vector<Object> retrieveData(@RequestParam("id") String id, @RequestParam("page_url") String page_url) {
        final String URL = "jdbc:clickhouse://localhost:8123/extracted_data";
        final String USER = "default";
        final String PASSWORD = "";

        Vector<Object> vec = new Vector<>();
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
                for (int i = 1; i < rsmd.getColumnCount(); i++) {
                    System.out.println(i);
                    vec.add(rs.getObject(i));
                }
            }

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

        return vec;
    }
}
