package dkit.oop;

//  Connect to Database and execute embedded SELECT Query.
//  Required:
//  You MUST Start the MySql Server (from XAMPP) and create and populate
//  the table "Customers",  with fields: custID, firstName, lastName, dob
//  The SQL code is given below (and is available in text file format on Moodle)
//
// DROP TABLE IF EXISTS customers;
//
// CREATE TABLE customers(custID int NOT NULL AUTO_INCREMENT,
//             firstName VARCHAR(20),
//             lastName VARCHAR(50),
//             dob date,
//	PRIMARY KEY (custID));
//
// INSERT INTO customers VALUES (101, 'Tom', 'Brady', '2018-11-11' );
// INSERT INTO customers VALUES (107, 'Mary', 'Reilly', '2019-6-12');
// INSERT INTO customers VALUES (105, 'James', 'Ryan', '2019-9-23');
// INSERT INTO customers VALUES (106, 'Alice', 'O''Brien', '2019-12-1');


import java.sql.*;

public class App
{
    public static void main(String[] args) {
        App app = new App();
        app.start();
    }
    public void start()  {
        System.out.println("MySQL - Example 2: Connect to Database and execute a 'raw' SELECT Statement");

        String url = "jdbc:mysql://localhost/";
        String dbName = "test";
        String userName = "root";
        String password = "";

        // try with resources
        try( Connection conn = DriverManager.getConnection(url + dbName, userName, password) )
        {
            System.out.println("\nConnected to the database.");

            // Statements allow us to issue SQL queries to the database
            Statement statement = conn.createStatement();

            // ResultSet stores the result from the SQL query
            String sqlQuery = "select * from customers";
            ResultSet resultSet = statement.executeQuery( sqlQuery );

            // The ResultSet is a Java object that stores the results returned
            // from the database (after executing the query)
            // The ResultSet provides methods that allow us to access the
            // results (rows of data) using Java code.

            // Iterate over the resultSet to process every row
            while ( resultSet.next() )
            {
                // Columns can be identified by column name OR by number
                // The first column is number 1   e.g. resultSet.getString(1);

                int customerId = resultSet.getInt("custID");
                String firstName = resultSet.getString("firstName");
                // resultSet.getXXX methods must match by type.
                // ie. getInt() for INTEGER columns etc.
                String lastName = resultSet.getString(3);  // get third value using index, i.e lastName
                Date dob = resultSet.getDate("dob");

                System.out.print("Customer ID = " + customerId + ", ");
                System.out.print("First Name = " + firstName + ", ");
                System.out.print("Last Name = " + lastName + ", ");
                System.out.println("Date of Birth : " + dob);
            }
            System.out.println("\nFinished - Disconnected from database");
        }
        catch (SQLException ex)
        {
            System.out.println("SQL Failed - check MySQL Server is running and that you are using the correct database details");
            ex.printStackTrace();
        }
    }
}
