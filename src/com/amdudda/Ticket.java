package com.amdudda;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.*;

/*
    Base code copied from lab slides
 */

import java.util.Date;

public class Ticket {

    private int priority;
    private String reporter; //Stores person or department who reported issue
    private String description;
    private LocalDateTime dateReported;
    //STATIC Counter - accessible to all Ticket objects.
    //If any Ticket object modifies this counter, all Ticket objects will have the modified value
    //Make it private - only Ticket objects should have access
    private static int staticTicketIDCounter = 1;
    //The ID for each ticket - instance variable. Each Ticket will have it's own ticketID variable
    protected int ticketID;
    // added these in response to Problem 5.
    private String status;  // not required by question, but prompted by my response to essay portion.
    private String resolution;
    private LocalDateTime dateResolved;

    // constructor for manual input
    public Ticket(String desc, int p, String rep, LocalDateTime date) {
        this.description = desc;
        this.priority = p;
        this.reporter = rep;
        this.dateReported = date;
        this.status = "active";
        this.ticketID = staticTicketIDCounter;
        staticTicketIDCounter++;
    }

    // constructor for array input - yes, I'm overloading it.
    public Ticket(ArrayList<String> data) {
        this.ticketID = Integer.parseInt(data.get(0));
        this.description = data.get(1);
        this.priority = Integer.parseInt(data.get(2));
        this.reporter = data.get(3);
        this.dateReported = LocalDateTime.parse(data.get(4));
        this.status = data.get(5);
        // last two values should be null, so I'm not bothering to set them
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
    protected LocalDateTime getDateResolved() {
        return dateResolved;
    }

    public void setDateResolved(LocalDateTime dateResolved) {
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
        /*LocalDateTime dR = this.dateResolved;
        String f_date = "" + dR.getMonth() + "-" + dR.getDayOfMonth() + "-" + dR.getYear();
        f_date += " " + dateReported.getHour() + ":" + dateReported.getMinute();*/
        return("ID= " + this.ticketID + " Issued: " + this.description + " Priority: " + this.priority + " Reported by: "
                + this.reporter + " Reported on: " + this.dateReported +
                " Status: " + this.status + " Resolved on: " +
                this.dateReported + " Resolution: " + this.resolution);
    }

    public String toTabDelimited() {
        // generates a tab-delimited string that can be written to a file
        return(this.ticketID + "\t" + this.description + "\t" + this.priority +
                "\t" + this.reporter + "\t" + this.dateReported +
                "\t"+ this.status + "\t" + this.dateResolved
                + "\t" + this.resolution + "\n");
    }
}

