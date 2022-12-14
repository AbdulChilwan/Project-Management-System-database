/**
 * This class is to keep track of projects, saving their information.
 * @author Abdul Aleem Chilwan
 * @version 3.2
 */

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Project {
    // Attributes
    int projNo;
    String projName;
    // Adding a customer object as an attribute
    Customer customer;
    Architect architect;
    Contactor contactor;
    String buildingDesign;
    String address;
    String ERFNumber;
    String startDate;
    String dueDate;
    double feeCharged;
    double amountPaid;
    boolean finalised;
    String completionDate;

    // Constructor
    public Project(int projNo, String projName,String buildingDesign, String address,
                   String ERFNumber, String startDate, String dueDate, double feeCharged, double amountPaid) {
        this.projNo = projNo;
        this.projName = projName;
        this.buildingDesign = buildingDesign;
        this.address = address;
        this.ERFNumber = ERFNumber;
        this.startDate = startDate;
        this.dueDate = dueDate;
        this.feeCharged = feeCharged;
        this.amountPaid = amountPaid;
        // Automatically setting these two below values
        this.finalised = false;
        this.completionDate = null;
    }

    public Project(int projNo, String projName,String buildingDesign, String address,
                   String ERFNumber, String startDate, String dueDate, double feeCharged, double amountPaid, boolean finalised,
                   String completionDate) {
        this.projNo = projNo;
        this.projName = projName;
        this.buildingDesign = buildingDesign;
        this.address = address;
        this.ERFNumber = ERFNumber;
        this.startDate = startDate;
        this.dueDate = dueDate;
        this.feeCharged = feeCharged;
        this.amountPaid = amountPaid;
        this.finalised = finalised;
        this.completionDate = completionDate;
    }

    // Getters and Setters
    public int getProjNo() {
        return projNo;
    }

    public void setProjNo(int projNo) {
        this.projNo = projNo;
    }

    public String getProjName() {
        return projName;
    }

    public void setProjName(String projName) {
        this.projName = projName;
    }

    public boolean isFinalised() {
        return finalised;
    }

    public void setFinalised(boolean finalised) {
        this.finalised = finalised;
    }

    public String getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(String completionDate) {
        this.completionDate = completionDate;
    }

    public String getBuildingDesign() {
        return buildingDesign;
    }

    public void setBuildingDesign(String buildingDesign) {
        this.buildingDesign = buildingDesign;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getERFNumber() {
        return ERFNumber;
    }

    public void setERFNumber(String ERFNumber) {
        this.ERFNumber = ERFNumber;
    }

    public double getFeeCharged() {
        return feeCharged;
    }

    public void setFeeCharged(double feeCharged) {
        this.feeCharged = feeCharged;
    }

    public double getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(double amountPaid) {
        this.amountPaid = amountPaid;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }


    public Architect getArchitect() {
        return architect;
    }

    public void setArchitect(Architect architect) {
        this.architect = architect;
    }

    public Contactor getContactor() {
        return contactor;
    }

    public void setContactor(Contactor contactor) {
        this.contactor = contactor;
    }

    // Method to update project information
    public void updateChangeInformation(){

    }

    // method to update the current amount paid.
    public double updateAmountPaid(){

        return this.amountPaid;
    }



    // method to finalise the project.
    public void finaliseProject(){
        if(!finalised){
            this.finalised = true;

            if(amountPaid < feeCharged){
                double totalOwed = feeCharged - amountPaid;
                System.out.println("Customer : " + customer.name);
                System.out.println("Customer Contact Number: " + customer.tellNo);
                System.out.println("---------------------------------------------------");
                System.out.println("Total Still Owes : R" + totalOwed );

            }
            else {
                System.out.println("----- All funds has been paid -----");
            }

        // Setting the completion date to today's date.
        setCompletionDate(String.valueOf(java.time.LocalDate.now()));
        }

        // Displaying a message to say this project has already been finalised.
        else{
            System.out.println("---------------------------------------------------");
            System.out.println("This project has been finalised already.");
            }
    }

    // toString
    public String toString() {
        return  "-----------------------------------------" + '\n'+
                "------- Project Details -------"+ '\n'+
                "projNo: " + projNo + '\n' +
                "projName: " + projName + '\n' +
                "buildingDesign: " + buildingDesign + '\n' +
                "address: " + address + '\n' +
                "ERFNumber: " + ERFNumber + '\n' +
                "Date Started: " + startDate + '\n' +
                "Due Date: " + dueDate + '\n' +
                "feeCharged: " + feeCharged + '\n'+
                "amountPaid: " + amountPaid + '\n'+
                "finalised: " + finalised + '\n'+
                "Completion Date: " + completionDate+ '\n'+
                "-----------------------------------------" + '\n'+
                "------- Customer Details -------"+ '\n'+
                customer.toString() + '\n'+
                "-----------------------------------------" + '\n'+
                "------- Architect Details -------"+ '\n'+
                architect.toString() + '\n'+
                "-----------------------------------------" + '\n'+
                "------- Contactor Details -------"+ '\n'+
                contactor.toString() + '\n' +
                '\n';

    }
}
