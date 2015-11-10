package com.amdudda;

import java.time.LocalDateTime;
import java.util.ArrayList;

/*
    Base code copied from lab slides
 */

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
        // but we want to make sure whatever the next new ticket number is, it's a brand new one
        // easiest way to do thatis to make sure that staticTicketIDCounter is larger than current ticket number
        if (this.ticketID >= staticTicketIDCounter) { staticTicketIDCounter = this.ticketID + 1; }
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

    protected LocalDateTime getDateReported() { return this.dateReported; }

    // add'l setters
    public void setPriority(int priority) {
        this.priority = priority;
    }

    public void setReporter(String reporter) {
        this.reporter = reporter;
    }

    public void setDescription(String description) {
        this.description = description;
    }

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
        String resOut, resDt;
        if (this.resolution == null || this.resolution.isEmpty()) {
            // this allows for future possibility of previously resolved tickets being reopened.
            resDt = "n/a";
            resOut = "UNRESOLVED";
        } else {
            resOut = this.resolution;
            resDt = this.dateResolved.toString();
        }
        return("ID= " + this.ticketID + " Issued: " + this.description + " Priority: " + this.priority + " Reported by: "
                + this.reporter + " Reported on: " + this.dateReported +
                " Status: " + this.status + " Resolved on: " +
                resDt + " Resolution: " + resOut);
    }

    public String toTabDelimited() {
        // generates a tab-delimited string that can be written to a file
        return(this.ticketID + "\t" + this.description + "\t" + this.priority +
                "\t" + this.reporter + "\t" + this.dateReported +
                "\t"+ this.status + "\t" + this.dateResolved
                + "\t" + this.resolution + "\n");
    }
}

