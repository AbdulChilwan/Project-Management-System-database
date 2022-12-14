import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Helper {
    
    /**
     *
     * Creating DB if not exist
     */
    public static void createDB(){
        final String DB_URL = "jdbc:mysql://localhost/";
        final String USER = "root";
        final String PASS = "password123";
        
        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt = conn.createStatement();
        ) {
            // Creating the database if it does not already exist
            String sql = "CREATE DATABASE if not exists poisepms";
            stmt.executeUpdate(sql);
            
            // Now that we have the DB created, we can use that URL (includes the DB's name in the URL)
            String mysqlUrl = "jdbc:mysql://localhost/poisepms";
            Connection con = DriverManager.getConnection(mysqlUrl, USER, PASS );
            
            // Use the poisepms DB
            String sqlUseDatabase = "USE poisepms";
            stmt.execute(sqlUseDatabase);
            
            //Query to create a table.
            stmt.executeUpdate("CREATE TABLE if not exists Customer ("
                    + "Customer_id int NOT NULL auto_increment primary key,"
                    + "Name varchar(20) NOT NULL ,"
                    + "Tel_num varchar(14) NOT NULL,"
                    + "Email varchar(20) not null,"
                    + "Address varchar(30) not null);");
            
            // Query to insert data into the table.
            String updateTable = "INSERT INTO Customer (Name, Tel_num, Email, Address) VALUES" +
                    "('Samuel', '021 859 8532', 'Samuel@gmail.com', '84 Trar Road Fordways')," +
                    "('Tate', '032 483 8394', 'Tate@gmail.com', '32 Tivot Road Grassy Park');";
            stmt.executeUpdate(updateTable);
            
            stmt.executeUpdate("CREATE TABLE if not exists Architect ("
                    + "Architect_id int NOT NULL auto_increment primary key,"
                    + "Name varchar(20) NOT NULL ,"
                    + "Tel_num varchar(14) NOT NULL,"
                    + "Email varchar(20) not null,"
                    + "Address varchar(30) not null);");
            
            // Query to insert data into the table.
            updateTable = "INSERT INTO Architect (Name, Tel_num, Email, Address) VALUES" +
                    "('Tracy', '012 938 2883', 'Tracy@gmail.com', '84 Trar Road Fordways')," +
                    "('Damon', '083 828 4833', 'Damon@gmail.com', '74 Trarem Street Cape Town');";
            stmt.executeUpdate(updateTable);
            
            stmt.executeUpdate("CREATE TABLE if not exists proj_manager ("
                    + "Projman_id int NOT NULL auto_increment primary key,"
                    + "Name varchar(20) NOT NULL ,"
                    + "Tel_num varchar(14) NOT NULL,"
                    + "Email varchar(20) not null,"
                    + "Address varchar(30) not null);");
            
            // Query to insert data into the table.
            updateTable = "INSERT INTO proj_manager (Name, Tel_num, Email, Address) VALUES" +
                    "('Retero', '023 493 9293', 'Retero@gmail.com', '29 Knockford Road Fordways')," +
                    "('Tarem', '048 928 9284', 'Tarem@gmail.com', '38 Waird Street Cape Town');";
            stmt.executeUpdate(updateTable);
            
            
            stmt.executeUpdate("CREATE TABLE if not exists Project ("
                    + "Proj_num int not null auto_increment primary key,"
                    + "Proj_name varchar(20) not null,"
                    + "Building_des varchar(20) not null,"
                    + "Address varchar(30) not null,"
                    + "ERF_num varchar(20) not null,"
                    + "Start_date Date not null,"
                    + "Due_date Date not null,"
                    + "Fee_charged decimal(9,2) not null,"
                    + "Paid decimal(9,2) not null,"
                    + "Finalised Boolean not null default false,"
                    + "Completion_date Date,"
                    + "Customer_id int,"
                    + "Projman_id int,"
                    + "Architect_id int,"
                    + "FOREIGN KEY(Customer_id) REFERENCES Customer(Customer_id),"
                    + "FOREIGN KEY(Projman_id) REFERENCES Proj_Manager(Projman_id),"
                    + "FOREIGN KEY(Architect_id) REFERENCES Architect(Architect_id));");
            
            // Query to insert data into the table.
            updateTable = "INSERT INTO Project (Proj_name, Building_des, Address, ERF_num, Start_date, Due_date, Fee_charged, Paid, finalised, " +
                    "Completion_date, Customer_id, Projman_id, Architect_id) VALUES" +
                    "('Munny Mansions', 'Complex', '12 Red Road Laneway', '6436247', '2022-02-14', '2023-05-15', 4700000.00, 0, false, null, 1, 2, 2)," +
                    "('Rowle Flats', 'Flats', '43 Greenway Cape Town', '9482824', '2022-06-03', '2023-11-11', 2400000.00, 0, false, null, 2, 1, 1);";
            stmt.executeUpdate(updateTable);
            
            // Closing the connections and Statement
            con.close();
            conn.close();
            stmt.close();
            
            // Catches the error and displays the error message.
        } catch (SQLException e) {
            System.out.println(e);;
        }
    }
    
    /**
     *
     * @param inputString user's input string.
     * @return returns true or false whether the input were numeric values.
     */
    public static boolean isNumeric(String inputString) {
        if (inputString == null || inputString.length() == 0) {
            return false;
        } else {
            for (char c : inputString.toCharArray()) {
                if (!Character.isDigit(c)) {
                    return false;
                }
            }
        }
        return true;
    }
    
    /**
     *
     * @param inputEmail user's input email.
     * @return returns true or false whether the input contains a "@".
     */
    // Method to check if value is an email, by checking if it contains a "@"
    public static boolean isEmail(String inputEmail) {
        if (inputEmail == null || inputEmail.length() == 0) {
            return false;
        } else if (!inputEmail.contains("@")) {
            return false;
        } else {
            return true;
        }
    }
    
    /**
     *
     * @param inputDate user's date.
     * @return returns true or false whether the input contains a date value.
     */
    // Method to check if value co-insides with the scope of date values.
    public static boolean isDate(String inputDate){
        // Splitting the Date into year, month, date of type integer.
        String[] date = inputDate.split("-");
        int year = Integer.parseInt(date[0]);
        int month = Integer.parseInt(date[1]);
        int day = Integer.parseInt(date[2]);
        
        // If the date is empty, return false.
        if (inputDate == null || inputDate.length() == 0) {
            return false;}
        // If the date does not contain "-" ... it is not in the correct format
        if ((!inputDate.contains("-"))) {
            return false;}
        // Checking if the year entered is between 2000 and 2050 for it to be a valid timeframe.
        if (year < 1999) {
            return false;}
        if (year > 2051) {
            return false;}
        // Checking if a valid month
        if (month < 1) {
            return false;}
        if (month > 13) {
            return false;}
        // Checking if a valid date.
        if (day < 1) {
            return false;}
        if (day > 31) {
            return false;}
        
        return true;
    }
    
    
}
