/**
 * This code is a project management system, which keeps tracks of projects, customers, contractors and architects.
 * <p>
 * It can save projects, modify its details and generate reports.
 * @author Abdul Aleem Chilwan
 * @version 3.2
 */

// Importing necessary classes/modules
import java.io.*;
import java.sql.*;
import java.util.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Poised {
    
    // Setting static final variables for the DB's URL, Username and Password.
    static final String DB_URL = "jdbc:mysql://localhost/";
    static final String USER = "root";
    static final String PASS = "password123";
    
    
    public void runDB() throws SQLException {
        /**
         * Establishing a DB connection
         */
        String mysqlUrl = "jdbc:mysql://localhost/poisepms";
        String sqlUseDatabase = "USE poisepms";
        Connection con = DriverManager.getConnection(mysqlUrl, USER, PASS );
        Statement stmt = con.createStatement();
        stmt.execute(sqlUseDatabase);
    }
    
    // Main method.
    public static void main(String[] args) {
        
        // Creating DB if not exist
        Helper.createDB();
        System.out.println("Working");
        
        //Initialising Scanner
        Scanner scn = new Scanner(System.in);
        
        int choice;
        int lineAmount = 0;
        int projNumber = 0;
        
        while(true){
            try {
                // Asking the user to select their options, inside a while loop
                System.out.println("Welcome to Poised!".toUpperCase());
                while (true) {
                    System.out.println("===========================================================================");
                    System.out.println("Please select an option: ".toUpperCase());
                    System.out.println("1 - Create a project \n2 - Change due date of the project " +
                            "\n3 - Change amount paid to date \n4 - Change contractor's contact details \n5 - Finalise project " +
                            "\n6 - Search project \n7 - View incomplete projects \n8 - View projects past due date " +
                            "\n0 - Exit");
                    
                    // Recording the user's option
                    choice = scn.nextInt();
                    int customerLink = 0;
                    int architectLink = 0;
                    int conLink = 0 ;
                    scn.nextLine();
                    
                    /**
                     * If user choice option 1, they will be prompted to create a projecet which will be linked to a customer, architect and contractor.
                     */
                    if (choice == 1) {
                        
                        // Establishing a connection.
                        String mysqlUrl = "jdbc:mysql://localhost/poisepms";
                        String sqlUseDatabase = "USE poisepms";
                        Connection con = DriverManager.getConnection(mysqlUrl, USER, PASS );
                        Statement stmte = con.createStatement();
                        
                        // using the poise DB
                        stmte.execute(sqlUseDatabase);
                        
                        // Initialising project information placeholders
                        String finalisedName = null;
                        String BuildingDesign = null;
                        String Address = null;
                        String ERFNumber = null;
                        Date start = null;
                        Date end = null;
                        Double feeCharged = 0.0;
                        Double paid = 0.0;
                        Boolean finalised = null;
                        Date complete = null;
                        int custID = 0;
                        int projManID = 0;
                        int archID = 0;
                        
                        while (true) {
                            try {
                                while (true) {
                                    
                                    /**
                                     * Asking user to fill in the project details, then adding the project to the DB.
                                     */
                                    System.out.println("----- Please enter the below information to successfully create a project -----");
                                    try {
                                        System.out.println("Enter the project's name: ");
                                        finalisedName = scn.nextLine();
                                        System.out.println("Enter the building design: ");
                                        BuildingDesign = scn.nextLine();
                                        System.out.println("Enter the address of the project: ");
                                        Address = scn.nextLine();
                                        System.out.println("Enter the ERF number: ");
                                        ERFNumber = scn.nextLine();
                                        System.out.println("Enter the start date: (YYYY-MM-DD)");
                                        String startDate = scn.nextLine();
                                        if (!Helper.isDate(startDate)) {
                                            System.out.println("Please enter YYYY-MM-DD, Please try again.");
                                            throw new Exception();
                                        }
                                        
                                        System.out.println("Enter the due date: (YYYY-MM-DD)");
                                        String dueDate = scn.nextLine();
                                        if (!Helper.isDate(dueDate)) {
                                            System.out.println("Telephone number should only contain numerical values, Please try again.");
                                            throw new Exception();
                                        }
                                        
                                        System.out.println("Enter the amount charged: ");
                                        feeCharged = scn.nextDouble();
                                        System.out.println("Enter the amount paid to date: ");
                                        paid = scn.nextDouble();
                                        scn.nextLine();
                                        
                                        String addProject = "insert into project(Proj_name, Building_des, Address, ERF_num, Start_date, " +
                                                "Due_date, Fee_charged, Paid)" +
                                                " VALUES('"+finalisedName+"', '"+BuildingDesign+"', '"+Address+"', '"+ERFNumber+"', '"+startDate+"', '"+dueDate+"', " +
                                                "'" +feeCharged+ "', '"+paid+ "')";
                                        stmte.executeUpdate(addProject);
                                        
                                        System.out.println("------------------------------------------");
                                        System.out.println(" ------Project Successfully Entered ------");
                                        System.out.println("------------------------------------------");
                                        break;
                                    } catch (Exception e) {
                                        System.out.println("Please try again... ");
                                        System.out.println("==========================================");
                                    }
                                }
                                break;
                            } catch (Exception e) {
                                System.out.println("Please try again.");
                            }
                        }
                        
                        String custName = "";
                        String custTel = "";
                        String custEmail = "";
                        String custAddress= "";
                        
                        /**
                         * After creating a project, a customer should be linked to the project.
                         */
                        while (true) {
                            try {
                                // Asking the user which method to use to a customer to the project.
                                System.out.println("------ Create a customer which will be linked to this project ------");
                                System.out.println("1 - Create new customer");
                                System.out.println("2 - Choose from existing customer");
                                int customerChoice = scn.nextInt();
                                scn.nextLine();
                                if(customerChoice == 1) {
                                    while (true) {
                                        
                                        try {
                                            System.out.println("Please enter the client's name: ");
                                            custName = scn.nextLine();
                                            System.out.println("Please enter the telephone number: ");
                                            custTel = scn.nextLine();
                                            if (!Helper.isNumeric(custTel)) {
                                                System.out.println("Telephone number should only contain numerical values, Please try again.");
                                                throw new Exception();
                                            }
                                            
                                            System.out.println("Please enter the email address: ");
                                            custEmail = scn.nextLine();
                                            if (!Helper.isEmail(custEmail)) {
                                                System.out.println("Telephone number should only contain numerical values, Please try again.");
                                                throw new Exception();
                                            }
                                            
                                            System.out.println("Please enter the client's physical address: ");
                                            custAddress = scn.nextLine();
                                            
                                            // Adding the customer details to the DB
                                            String addCustomer = "insert into customer(Name, Tel_num, Email, Address)" +
                                                    " VALUES('" + custName + "', '" + custTel + "', '" + custEmail + "', '" + custAddress + "')";
                                            stmte.executeUpdate(addCustomer);
                                            
                                            // Linking the customer to the project
                                            ResultSet results = stmte.executeQuery("SELECT Customer_id FROM customer where name = '" +custName+"' " +
                                                    "AND Tel_num = '" +custTel+"'");
                                            int customerNumber = 0;
                                            while (results.next()) {
                                                customerNumber = (results.getInt("Customer_id"));
                                            }
                                            
                                            String updateTable = "UPDATE project set Customer_id = '" + customerNumber + "' WHERE Address = '" + Address+"'";
                                            stmte.executeUpdate(updateTable);
                                            break;
                                            
                                        } catch (Exception e) {
                                            System.out.println(e);
                                            System.out.println("================================");
                                        }
                                    }
                                    
                                    
                                    break;
                                }
                                // Adding an existing customer information to the project to be linked
                                else if (customerChoice == 2){
                                    int customerID = 1;
                                    
                                    ResultSet results = stmte.executeQuery("SELECT * FROM customer");
                                    String customerName = null;
                                    
                                    // Getting a list of already made customers
                                    while (results.next()) {
                                        
                                        customerName = (results.getString("Name"));
                                        System.out.println(customerID + " - " + customerName);
                                        customerID++;
                                        
                                    }
                                    // Updating the linkage.
                                    customerLink = scn.nextInt();
                                    scn.nextLine();
                                    String updateTable = "UPDATE project set Customer_id = '" + customerLink + "' WHERE Address = '" + Address +"'";
                                    stmte.executeUpdate(updateTable);
                                    
                                    results = stmte.executeQuery("SELECT * FROM customer WHERE Customer_id = '" + customerLink +"'");
                                    while (results.next()) {
                                        custName = (results.getString("Name"));
                                    }
                                    break;
                                }
                            } catch (Exception e) {
                                System.out.println(e);
                                System.out.println("Please try again.");
                            }
                            
                        }
                        // If the name of the project is blank, it will set the name based on the client's name and building design.
                        if (finalisedName.isBlank() || finalisedName.isBlank()){
                            String mixedName = BuildingDesign + " " + custName.substring(custName.lastIndexOf(" ")+ 1);
                            String updateTable = "UPDATE project set proj_name = '" + mixedName + "' WHERE Address = '" + Address +"'";
                            stmte.executeUpdate(updateTable);
                        }
                        
                        String archName = "";
                        String archTel = "";
                        String archEmail = "";
                        String archAddress= "";
                        
                        while (true) {
                            try {
                                // Asking the user which method to use to add an architect to the project.
                                System.out.println("------ Create an architect which will be linked to this project ------");
                                System.out.println("1 - Create new architect");
                                System.out.println("2 - Choose from existing architect");
                                int architectChoice = scn.nextInt();
                                scn.nextLine();
                                if(architectChoice == 1) {
                                    while (true) {
                                        try {
                                            System.out.println("Please enter the architect name: ");
                                            archName = scn.nextLine();
                                            System.out.println("Please enter the telephone number: ");
                                            archTel = scn.nextLine();
                                            if (!Helper.isNumeric(archTel)) {
                                                System.out.println("Telephone number should only contain numerical values, Please try again.");
                                                throw new Exception();
                                            }
                                            
                                            System.out.println("Please enter the email address: ");
                                            archEmail = scn.nextLine();
                                            if (!Helper.isEmail(archEmail)) {
                                                System.out.println("Telephone number should only contain numerical values, Please try again.");
                                                throw new Exception();
                                            }
                                            
                                            System.out.println("Please enter the client's physical address: ");
                                            archAddress = scn.nextLine();
                                            
                                            String addArchitect = "insert into architect(Name, Tel_num, Email, Address)" +
                                                    " VALUES('" + archName + "', '" + archTel + "', '" + archEmail + "', '" + archAddress + "')";
                                            stmte.executeUpdate(addArchitect);
                                            
                                            ResultSet results = stmte.executeQuery("SELECT Architect_id FROM architect where name = '" +archName+"' " +
                                                    "AND Tel_num = '" +archTel+"'");
                                            int archNumber = 0;
                                            
                                            while (results.next()) {
                                                
                                                archNumber = (results.getInt("Architect_id"));
                                            }
                                            
                                            String updateTable = "UPDATE project set Architect_id = '" + archNumber + "' WHERE Address = '" + Address+"'";
                                            stmte.executeUpdate(updateTable);
                                            break;
                                            
                                        } catch (Exception e) {
                                            System.out.println(e);
                                            System.out.println("================================");
                                        }
                                    }
                                    
                                    break;
                                }
                                else if (architectChoice == 2){
                                    int architectID = 1;
                                    
                                    ResultSet results = stmte.executeQuery("SELECT * FROM architect");
                                    String architectName = null;
                                    
                                    while (results.next()) {
                                        
                                        architectName = (results.getString("Name"));
                                        System.out.println(architectID + " - " + architectName);
                                        architectID++;
                                        
                                    }
                                    architectLink = scn.nextInt();
                                    scn.nextLine();
                                    String updateTable = "UPDATE project set architect_id = '" + architectLink + "' WHERE Address = '" + Address+"'";
                                    stmte.executeUpdate(updateTable);
                                    break;
                                }
                            } catch (Exception e) {
                                System.out.println("Please try again.");
                            }
                        }
                        
                        String conName = "";
                        String conTel = "";
                        String conEmail = "";
                        String conAddress= "";
                        while (true) {
                            try {
                                // Asking the user which method to use to add a contractor to the project.
                                System.out.println("------ Create an contractor which will be linked to this project ------");
                                System.out.println("1 - Create new contractor");
                                System.out.println("2 - Choose from existing contractor");
                                int contractorChoice = scn.nextInt();
                                scn.nextLine();
                                if(contractorChoice == 1) {
                                    while (true) {
                                        try {
                                            System.out.println("Please enter the contractor name: ");
                                            conName = scn.nextLine();
                                            System.out.println("Please enter the telephone number: ");
                                            conTel = scn.nextLine();
                                            if (!Helper.isNumeric(conTel)) {
                                                System.out.println("Telephone number should only contain numerical values, Please try again.");
                                                throw new Exception();
                                            }
                                            
                                            System.out.println("Please enter the email address: ");
                                            conEmail = scn.nextLine();
                                            if (!Helper.isEmail(conEmail)) {
                                                System.out.println("Telephone number should only contain numerical values, Please try again.");
                                                throw new Exception();
                                            }
                                            
                                            System.out.println("Please enter the client's physical address: ");
                                            conAddress = scn.nextLine();
                                            
                                            String addContractor = "insert into proj_manager(Name, Tel_num, Email, Address)" +
                                                    " VALUES('" + conName + "', '" + conTel + "', '" + conEmail + "', '" + conAddress + "')";
                                            stmte.executeUpdate(addContractor);
                                            
                                            ResultSet results = stmte.executeQuery("SELECT Projman_id FROM proj_manager where name = '" +conName+"' " +
                                                    "AND Tel_num = '" +conTel+"'");
                                            int conNumber = 0;
                                            
                                            while (results.next()) {
                                                
                                                conNumber = (results.getInt("Projman_id"));
                                            }
                                            String updateTable = "UPDATE project set Projman_id = '" + conNumber + "' WHERE Address = '" + Address+"'";
                                            stmte.executeUpdate(updateTable);
                                            
                                            break;
                                            
                                        } catch (Exception e) {
                                            System.out.println(e);
                                            System.out.println("================================");
                                        }
                                    }
                                    break;
                                }
                                else if (contractorChoice == 2){
                                    int conID = 1;
                                    
                                    ResultSet results = stmte.executeQuery("SELECT * FROM proj_manager");
                                    conName = null;
                                    
                                    while (results.next()) {
                                        
                                        conName = (results.getString("Name"));
                                        System.out.println(conID + " - " + conName);
                                        conID++;
                                        
                                    }
                                    conLink = scn.nextInt();
                                    scn.nextLine();
                                    String updateTable = "UPDATE project set projman_id = '" + conLink + "' WHERE Address = '" + Address+"'";
                                    stmte.executeUpdate(updateTable);
                                    break;
                                }
                            } catch (Exception e) {
                                System.out.println("Please try again.");
                            }
                        }
                        // Closing connections
                        stmte.close();
                        con.close();
                    }
                    
                    /**
                     * If the user choice option 2, They will get a list of projects from the DB and be asked which one to change
                     */
                    else if (choice == 2) {
                        
                        Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                        Statement stmt = conn.createStatement();
                        
                        System.out.println("------ Which project's due date would you like to change? (YYYY-MM-DD) ------");
                        
                        String mysqlUrl = "jdbc:mysql://localhost/poisepms";
                        String sqlUseDatabase = "USE poisepms";
                        Connection con = DriverManager.getConnection(mysqlUrl, USER, PASS );
                        Statement stmte = con.createStatement();
                        
                        stmte.execute(sqlUseDatabase);
                        
                        
                        
                        System.out.println("========================");
                        ResultSet results = stmt.executeQuery("SELECT Proj_num, Proj_name FROM project");
                        while (results.next()) {
                            System.out.println("Project Number : " +
                                    results.getInt("Proj_num") + " -  "
                                    + results.getString("Proj_name")
                            );
                        }
                        
                        /**
                         * Displaying details and asking for the new due date.
                         */
                        System.out.println("Enter your choice: ");
                        int dueDateChoice = scn.nextInt();
                        scn.nextLine();
                        results = stmt.executeQuery("SELECT * FROM project where Proj_num = '" + dueDateChoice + "'");
                        String dueDateName = null;
                        Date dueDate = null;
                        while (results.next()) {
                            
                            dueDateName = (results.getString("Proj_name"));
                            dueDate = (results.getDate("Due_date"));
                            
                        }
                        System.out.println("You selected : " + dueDateName);
                        System.out.println("The current due date is: " + dueDate);
                        while (true) {
                            try {
                                System.out.println("===========================================================================");
                                System.out.println("------ What would you like the new date to be? (YYYY-MM-DD) ------");
                                String newDueDate = scn.nextLine();
                                
                                /**
                                 * Saving the new due date for that project.
                                 */
                                String updateTable = "UPDATE project set Due_date = '" + newDueDate + "' WHERE Proj_num = " + dueDateChoice;
                                stmte.executeUpdate(updateTable);
                                break;
                                
                            } catch (Exception e) {
                                System.out.println("Please try again...");
                            }
                        }
    
                        // Closing connections
                        stmte.close();
                        con.close();
                    }
                    
                    /**
                     * If the user choice option 3, They will be shown a list of projects from the DB and be asked
                     * for the new amount to be entered.
                     */
                    else if (choice == 3) {
                        
                        String mysqlUrl = "jdbc:mysql://localhost/poisepms";
                        String sqlUseDatabase = "USE poisepms";
                        Connection con = DriverManager.getConnection(mysqlUrl, USER, PASS );
                        Statement stmte = con.createStatement();
                        
                        stmte.execute(sqlUseDatabase);
                        
                        System.out.println("------ Which project's paid amount would you like to change? ------");
                        System.out.println("========================");
                        ResultSet results = stmte.executeQuery("SELECT Proj_num, Proj_name FROM project");
                        while (results.next()) {
                            System.out.println("Project Number : " +
                                    results.getInt("Proj_num") + " -  "
                                    + results.getString("Proj_name")
                            );
                        }
                        
                        /**
                         * Displaying details and asking for the new amount.
                         */
                        System.out.println("Enter your choice: ");
                        int changeProjectPaidAmount = scn.nextInt();
                        scn.nextLine();
                        results = stmte.executeQuery("SELECT * FROM project where Proj_num = '" + changeProjectPaidAmount + "'");
                        String amountName = null;
                        double newAmount = 0;
                        while (results.next()) {
                            
                            amountName = (results.getString("Proj_name"));
                            newAmount = (results.getDouble("Paid"));
                            
                        }
                        
                        /**
                         *  Displaying the data for the new amount.
                         */
                        System.out.println("You selected : " + amountName);
                        System.out.println("The current paid amount is: " + newAmount);
                        
                        while (true) {
                            try {
                                System.out.println("------ What would you like the new paid amount to be? ------");
                                double newPaidAmount = scn.nextDouble();
                                
                                /**
                                 * Saving the new paid amount for that project.
                                 */
                                String updateTable = "UPDATE project set Paid = '" + newPaidAmount + "' WHERE Proj_num = " + changeProjectPaidAmount;
                                stmte.executeUpdate(updateTable);
                                break;
                                
                            } catch (Exception e) {
                                System.out.println("Please try again...");
                            }
                        }
                        // Closing connections
                        stmte.close();
                        con.close();
                    }
                    
                    /**
                     *  Showing a list of contractors from the DB and which details they would like to update.
                     */
                    else if (choice == 4) {
                        String mysqlUrl = "jdbc:mysql://localhost/poisepms";
                        String sqlUseDatabase = "USE poisepms";
                        Connection con = DriverManager.getConnection(mysqlUrl, USER, PASS );
                        Statement stmte = con.createStatement();
                        
                        stmte.execute(sqlUseDatabase);
                        
                        // Displaying list of contractors
                        System.out.println("------ Please select a contractor to update their details: ------");
                        System.out.println("========================");
                        ResultSet results = stmte.executeQuery("SELECT Projman_id, Name FROM proj_manager");
                        while (results.next()) {
                            System.out.println("Project Number : " +
                                    results.getInt("Projman_id") + " -  "
                                    + results.getString("Name")
                            );
                        }
                        
                        /**
                         *  Asking for the user's choice on which contractor they want to update.
                         */
                        System.out.println("Enter your choice: ");
                        int contractorSelected = scn.nextInt();
                        scn.nextLine();
                        results = stmte.executeQuery("SELECT * FROM proj_manager where Projman_id = '" + contractorSelected + "'");
                        String contractorName = null;
                        while (results.next()) {
                            contractorName = (results.getString("Proj_name"));
                        }
                        
                        /**
                         *  Displaying the data and asking for the new amount.
                         */
                        System.out.println("You selected : " + contractorName);
                        
                        System.out.println("------ Which details would you like to update? ------");
                        System.out.println("1 - Telephone Number \n2 - Email Address \n3 - Physical Address");
                        System.out.println("Enter your choice: ");
                        int selectedUpdateDetails = scn.nextInt();
                        
                        /**
                         *  Based off the user's choice, asking them to prompt the new data and saving it to that contractor's object.
                         */
                        
                        if (selectedUpdateDetails == 1) {
                            System.out.println("Please enter the updated telephone number: ");
                            String newContratorNumber = scn.next();
                            String updateTable = "UPDATE proj_manager set Tel_num = '" + newContratorNumber + "' WHERE Projman_id = " + contractorSelected;
                            stmte.executeUpdate(updateTable);
                        }
                        if (selectedUpdateDetails == 2) {
                            System.out.println("Please enter the updated email: ");
                            String newContratorEmail = scn.next();
                            String updateTable = "UPDATE proj_manager set Email = '" + newContratorEmail + "' WHERE Projman_id = " + contractorSelected;
                            stmte.executeUpdate(updateTable);
                        }
                        if (selectedUpdateDetails == 3) {
                            System.out.println("Please enter the updated physical address: ");
                            String newContratorPhysAddress = scn.next();
                            System.out.println(newContratorPhysAddress);
                            String updateTable = "UPDATE proj_manager set Address = '" + newContratorPhysAddress + "' WHERE Projman_id = " + contractorSelected;
                            stmte.executeUpdate(updateTable);
                        }
                        System.out.println("------------------- Successfully Updated ----------------------------");
    
                        // Closing connections
                        stmte.close();
                        con.close();
                    }
                    
                    /**
                     *  Displaying project which could be finalised.
                     */
                    else if (choice == 5) {
                        
                        String mysqlUrl = "jdbc:mysql://localhost/poisepms";
                        String sqlUseDatabase = "USE poisepms";
                        Connection con = DriverManager.getConnection(mysqlUrl, USER, PASS );
                        Statement stmte = con.createStatement();
                        
                        stmte.execute(sqlUseDatabase);
                        
                        System.out.println("------ Which project would you like to finalise? ------");
                        System.out.println("========================");
                        ResultSet results = stmte.executeQuery("SELECT Proj_num, Proj_name FROM project");
                        while (results.next()) {
                            System.out.println("Project Number : " +
                                    results.getInt("Proj_num") + " -  "
                                    + results.getString("Proj_name")
                            );
                        }
                        
                        /**
                         * Displaying details and asking for the new due date.
                         */
                        System.out.println("Enter your choice: ");
                        int finaliseProject = scn.nextInt();
                        scn.nextLine();
                        results = stmte.executeQuery("SELECT * FROM project where Proj_num = '" + finaliseProject + "'");
                        String finalisedName = null;
                        Double paid = 0.0;
                        Double owed = 0.0;
                        while (results.next()) {
                            
                            finalisedName = (results.getString("Proj_name"));
                            owed = (results.getDouble("Paid"));
                            paid = (results.getDouble("Fee_charged"));
                            
                        }
                        
                        /**
                         *  Displaying the data and showing the balance, if any.
                         */
                        System.out.println("You selected : " + finalisedName);
                        if(owed - paid > 0) {
                            Double balance = owed - paid;
                            System.out.println("Balance still owed: " + balance);
                        }
                        
                        /**
                         *  Finalising the project, marking it.
                         */
                        String updateTable = "UPDATE project set Finalised = TRUE WHERE Proj_num = " + finaliseProject;
                        stmte.executeUpdate(updateTable);
                        updateTable = "UPDATE project set Completion_date = CURDATE() WHERE Proj_num = " + finaliseProject;
                        stmte.executeUpdate(updateTable);
                        System.out.println("------------------- Successfully Updated ----------------------------");
    
                        // Closing connections
                        stmte.close();
                        con.close();
                    }
                    
                    // Searching for a project
                    else if (choice == 6) {
                        String mysqlUrl = "jdbc:mysql://localhost/poisepms";
                        String sqlUseDatabase = "USE poisepms";
                        Connection con = DriverManager.getConnection(mysqlUrl, USER, PASS );
                        Statement stmte = con.createStatement();
                        
                        stmte.execute(sqlUseDatabase);
                        
                        // Asking user which option they would like to use to search
                        System.out.println("------ Please pick an option on how to search for a project: ------");
                        System.out.println("1 - Project number.");
                        System.out.println("2 - Project name.");
                        int projectChoice = scn.nextInt();
                        scn.nextLine();
                        
                        if (projectChoice == 1) {
                            // Getting all the information on the project based off the project number
                            System.out.println("------ Please enter the project number: ------");
                            int projectNumberChoice = scn.nextInt();
                            try {
                                ResultSet results = stmte.executeQuery("SELECT * FROM project where Proj_num = '" + projectNumberChoice + "'");
                                String finalisedName = null;
                                String BuildingDesign = null;
                                String Address = null;
                                String ERFNumber = null;
                                Date start = null;
                                Date end = null;
                                Double feeCharged = 0.0;
                                Double paid = 0.0;
                                Boolean finalised = null;
                                Date complete = null;
                                int custID = 0;
                                int projManID = 0;
                                int archID = 0;
                                
                                while (results.next()) {
                                    
                                    finalisedName = (results.getString("Proj_name"));
                                    BuildingDesign = (results.getString("Building_des"));
                                    Address = (results.getString("Address"));
                                    ERFNumber = (results.getString("ERF_num"));
                                    start = (results.getDate("Start_date"));
                                    end = (results.getDate("Due_date"));
                                    feeCharged = (results.getDouble("Fee_charged"));
                                    paid = (results.getDouble("Paid"));
                                    finalised = (results.getBoolean("finalised"));
                                    complete = (results.getDate("Completion_date"));
                                    custID = (results.getInt("Customer_id"));
                                    projManID = (results.getInt("Projman_id"));
                                    archID = (results.getInt("Architect_id"));
                                    
                                }
                                
                                results = stmte.executeQuery("SELECT * FROM customer where Customer_id = '" + custID + "'");
                                String customerName = null;
                                while (results.next()) {
                                    customerName = (results.getString("Name"));
                                }
                                results = stmte.executeQuery("SELECT * FROM proj_manager where projman_id = '" + projManID + "'");
                                String ConName = null;
                                while (results.next()) {
                                    ConName = (results.getString("Name"));
                                }
                                results = stmte.executeQuery("SELECT * FROM architect where Architect_id = '" + archID + "'");
                                String ArchName = null;
                                while (results.next()) {
                                    ArchName = (results.getString("Name"));
                                }
                                
                                System.out.println("Project Details: ");
                                System.out.println("Name: " + finalisedName);
                                System.out.println("Building Type: " + BuildingDesign);
                                System.out.println("Address: " + Address);
                                System.out.println("ERF: " + ERFNumber);
                                System.out.println("Start Date: " + start);
                                System.out.println("End Date: " + end);
                                System.out.println("Charged Amount: " + feeCharged);
                                System.out.println("Amount Paid: " + paid);
                                System.out.println("Finalised: " + finalised);
                                System.out.println("Completion Date: " + complete);
                                System.out.println("Customer: " + customerName);
                                System.out.println("Contractor: " + ConName);
                                System.out.println("Architect: " + ArchName);
                                
                            } catch (Exception e) {
                                System.out.println("Project does not exist.");
                            }
                            
                        }
                        // Getting all the information on the project based off the project name
                        if (projectChoice == 2) {
                            System.out.println("------ Please enter the project name: ------");
                            String projName = scn.nextLine();
                            try {
                                ResultSet results = stmte.executeQuery("SELECT * FROM project where Proj_name = '" + projName + "'");
                                int finalisedNum = 0;
                                String BuildingDesign = null;
                                String Address = null;
                                String ERFNumber = null;
                                Date start = null;
                                Date end = null;
                                Double feeCharged = 0.0;
                                Double paid = 0.0;
                                Boolean finalised = null;
                                Date complete = null;
                                int custID = 0;
                                int projManID = 0;
                                int archID = 0;
                                
                                while (results.next()) {
                                    
                                    finalisedNum = (results.getInt("Proj_num"));
                                    BuildingDesign = (results.getString("Building_des"));
                                    Address = (results.getString("Address"));
                                    ERFNumber = (results.getString("ERF_num"));
                                    start = (results.getDate("Start_date"));
                                    end = (results.getDate("Due_date"));
                                    feeCharged = (results.getDouble("Fee_charged"));
                                    paid = (results.getDouble("Paid"));
                                    finalised = (results.getBoolean("finalised"));
                                    complete = (results.getDate("Completion_date"));
                                    custID = (results.getInt("Customer_id"));
                                    projManID = (results.getInt("Projman_id"));
                                    archID = (results.getInt("Architect_id"));
                                    
                                }
                                
                                results = stmte.executeQuery("SELECT * FROM customer where Customer_id = '" + custID + "'");
                                String customerName = null;
                                while (results.next()) {
                                    customerName = (results.getString("Name"));
                                }
                                results = stmte.executeQuery("SELECT * FROM proj_manager where projman_id = '" + projManID + "'");
                                String ConName = null;
                                while (results.next()) {
                                    ConName = (results.getString("Name"));
                                }
                                results = stmte.executeQuery("SELECT * FROM architect where Architect_id = '" + archID + "'");
                                String ArchName = null;
                                while (results.next()) {
                                    ArchName = (results.getString("Name"));
                                }
                                
                                System.out.println("Project Details: ");
                                System.out.println("Project Number: " + finalisedNum);
                                System.out.println("Building Type: " + BuildingDesign);
                                System.out.println("Address: " + Address);
                                System.out.println("ERF: " + ERFNumber);
                                System.out.println("Start Date: " + start);
                                System.out.println("End Date: " + end);
                                System.out.println("Charged Amount: " + feeCharged);
                                System.out.println("Amount Paid: " + paid);
                                System.out.println("Finalised: " + finalised);
                                System.out.println("Completion Date: " + complete);
                                System.out.println("Customer: " + customerName);
                                System.out.println("Contractor: " + ConName);
                                System.out.println("Architect: " + ArchName);
                                
                            } catch (Exception e) {
                                System.out.println("Project does not exist.");
                            }
                            
                        }
                        // Closing connections
                        stmte.close();
                        con.close();
                    }
                    
                    /**
                     *  Displaying all incomplete projects.
                     */
                    else if (choice == 7) {
                        String mysqlUrl = "jdbc:mysql://localhost/poisepms";
                        String sqlUseDatabase = "USE poisepms";
                        Connection con = DriverManager.getConnection(mysqlUrl, USER, PASS );
                        Statement stmte = con.createStatement();
                        
                        stmte.execute(sqlUseDatabase);
                        
                        int inCompleted = 0;
                        System.out.println("------ Displaying all incomplete projects: ------ ");
                        ResultSet results = stmte.executeQuery("SELECT * FROM project where Finalised = False");
                        String finalisedName = null;
                        while (results.next()) {
                            
                            finalisedName = (results.getString("Proj_name"));
                            System.out.println(finalisedName + " - Currently incomplete.");
                            inCompleted++;
                        }
                        
                        if (inCompleted == 0) {
                            System.out.println("------ There are currently no incomplete projects ------");
                        }
                        // Closing connections
                        stmte.close();
                        con.close();
                    }
                    
                    /**
                     *  Comparing the due date to current date, and displaying them if they are past the due date.
                     */
                    if (choice == 8) {
                        String mysqlUrl = "jdbc:mysql://localhost/poisepms";
                        String sqlUseDatabase = "USE poisepms";
                        Connection con = DriverManager.getConnection(mysqlUrl, USER, PASS );
                        Statement stmte = con.createStatement();
                        
                        stmte.execute(sqlUseDatabase);
                        int pastDueDate = 0;
                        LocalDate dateObj = LocalDate.now();
                        
                        /**
                         *  Create a date object in format YYYY-MM-DD
                         */
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                        String date = dateObj.format(formatter);
                        LocalDate currDate = LocalDate.parse(date);
                        
                        System.out.println("------ Displaying all projects past its due date: ------");
                        ResultSet results = stmte.executeQuery("SELECT * FROM project where Due_date < '" + currDate +"' AND Finalised = FALSE" );
                        String finalisedName = null;
                        while (results.next()) {
                            
                            finalisedName = (results.getString("Proj_name"));
                            System.out.println(finalisedName + " - Currently overdue.");
                            pastDueDate++;
                        }
                        if (pastDueDate == 0) {
                            System.out.println("------ There are currently no projects past the due date. ------ ");
                        }
                        // Closing connections
                        stmte.close();
                        con.close();
                    }
                    // Exiting program
                    if(choice == 0){
                        
                        System.out.println(" --- Good Bye! ---");
                        System.exit(1);
                    }
                    
                    // Asking user to enter an option again.
                    else {
                        System.out.println("===========================================================================");
                        System.out.println("Please select a number on which action to execute. ");
                    }
                }
            }catch(Exception e){
                System.out.println(e);
                System.out.println("Please try again.");
                scn.nextLine();
            }
        }
    }
}
