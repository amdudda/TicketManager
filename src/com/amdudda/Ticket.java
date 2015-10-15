package com.amdudda;
import java.util.Date;

/*
    Base code copied from lab slides
 */

import java.util.Date;
import java.util.LinkedList;

public class Ticket {

    private int priority;
    private String reporter; //Stores person or department who reported issue
    private String description;
    private Date dateReported;
    //STATIC Counter - accessible to all Ticket objects.
    //If any Ticket object modifies this counter, all Ticket objects will have the modified value
    //Make it private - only Ticket objects should have access
    private static int staticTicketIDCounter = 1;
    //The ID for each ticket - instance variable. Each Ticket will have it's own ticketID variable
    protected int ticketID;
    // added these in response to Problem 5.
    private String status;  // not required by question, but prompted by my response to essay portion.
    private String resolution;
    private Date dateResolved;

    // constructor
    public Ticket(String desc, int p, String rep, Date date) {
        this.description = desc;
        this.priority = p;
        this.reporter = rep;
        this.dateReported = date;
        this.status = "active";
        this.ticketID = staticTicketIDCounter;
        staticTicketIDCounter++;
    }

    // getters
    protected int getPriority() {
        return this.priority;
    }

    protected int getTicketID() {
        return this.ticketID;
    }

    protected String getDescription() { return this.description; }

    protected String getReporter() { return this.reporter; }

    // new getters & setters for resolved ticket values
    protected Date getDateResolved() {
        return dateResolved;
    }

    public void setDateResolved(Date dateResolved) {
        this.dateResolved = dateResolved;
    }

    protected String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    protected String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }
    // end getters & setters for Problem 5

    public String toString(){
        return("ID= " + this.ticketID + " Issued: " + this.description + " Priority: " + this.priority + " Reported by: "
                + this.reporter + " Reported on: " + this.dateReported +
                " Status: " + this.status + " Resolved on: " + this.dateResolved
                + " Resolution: " + this.resolution);
    }

    public String toTabDelimited() {
        return(this.ticketID + "\t" + this.description + "\t" + this.priority +
                "\t" + this.reporter + "\t" + this.dateReported +
                "\t"+ this.status + "\t" + this.dateResolved
                + "\t" + this.resolution + "\n");
    }
}

