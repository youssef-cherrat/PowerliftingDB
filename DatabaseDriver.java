import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;

public class DatabaseDriver {

    // SQLite connection URL
    private static final String URL = "jdbc:sqlite:your_database.db";

    //java database connectivity variables for opening and managing connection
    private static Connection connection;
    private static Statement statement;
    private static ResultSet resultSet;

    public static void main(String[] args){
        try{

            //Load JDBC driver
            Class.forName("org.sqlite.JDBC");

            //Establish connection
            connection = DriverManager.getConnection(URL); //how do we get the URL for the database

            //Create a statement
            statement = connection.createStatement();

            // Execute a query
            String query = "CREATE TABLE Year(Year_ID INTEGER PRIMARY KEY, Start_Date DATE, End_Date DATE);";
            resultSet = statement.executeQuery(query);

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            // Close resources
            try{
                if(resultSet != null) resultSet.close();
                if(statement != null) statement.close();
                if(connection != null) connection.close();
            } catch(SQLException e){
                e.printStackTrace();
            }
        }



    }

}