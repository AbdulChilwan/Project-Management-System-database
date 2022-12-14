/**
 * This class is to keep track of Contractors, saving their information.
 * @author Abdul Aleem Chilwan
 * @version 3.2
 */

public class Contactor {
    // Attributes
    String name;
    String tellNo;
    String emailAddress;
    String physAddress;

    // Constructor
    public Contactor(String name, String tellNo, String emailAddress, String physAddress) {
        this.name = name;
        this.tellNo = tellNo;
        this.emailAddress = emailAddress;
        this.physAddress = physAddress;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTellNo() {
        return tellNo;
    }

    public void setTellNo(String tellNo) {
        this.tellNo = tellNo;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPhysAddress() {
        return physAddress;
    }

    public void setPhysAddress(String physAddress) {
        this.physAddress = physAddress;
    }

    // Method to find the project details, given the projectName or projectNo.
    public String findProject (String projectName, String projectNo){
        return null;
    }

    // Method to get all projects which are currently incomplete.
    public String projectsIncomplete(){
        return null;
    }

    // Method to get all projects which are currently past the due date.
    public String projectPastDueDate(){
        return null;
    }

    // toString
    public String toString() {
        return "name='" + name + '\n' +
                "tellNo='" + tellNo + '\n' +
                "emailAddress='" + emailAddress + '\n' +
                "physAddress='" + physAddress ;
    }
}

