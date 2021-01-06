package sk.servlets;

import java.sql.*;
import java.util.ArrayList;

/** class to handle database operations */
public class DbHelper {

    /** return collection containing rate searches */
    public static ArrayList<HistoryEntity> getHistory(){
        ArrayList<HistoryEntity> result = new ArrayList();
        try {
            // create objects to connect to database and get data
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/rates","root","root");
            // create sql command for retrieving data
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("select * from history order by date");
            // if there are any records iterate to populate result collection
            while (rs.next()) {
                result.add(new HistoryEntity(
                    rs.getInt("id"),
                    rs.getTimestamp("ts"),
                    rs.getDate("date"),
                    rs.getDouble("rate")
                ));
            }
            // close connection to avoid any memory leaks
            con.close();
        } catch(Exception e) {
            System.out.println(e);
        }
        return result;
    }

    /** log search rate data in database */
    public static boolean logRequest(RateData data) {
        boolean result = false;
        try {
            // create objects to connect to database and get data
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/rates","root","root");
            // define timestamp value
            java.sql.Timestamp ts = new java.sql.Timestamp(System.currentTimeMillis());
            // define sql command string to insert new record in database history table
            String query = " insert into history (ts, date, rate) values (?, ?, ?)";
            // execute sql command
            PreparedStatement prepareStatement = con.prepareStatement(query);
            prepareStatement.setTimestamp(1, ts);
            prepareStatement.setString(2, data.date);
            prepareStatement.setDouble(3, data.rate);
            result = prepareStatement.execute();
            // close connection to avoid any memory leaks
            con.close();
        } catch(Exception e) {
            System.out.println(e);
        }
        return result;
    }

}
