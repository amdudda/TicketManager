package com.amdudda;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutionException;

/*
    Base code copied from lab slides
 */

public class TicketManager {

    public static void main(String[] args) {
        LinkedList<Ticket> ticketQueue = new LinkedList<Ticket>();
        Scanner scan = new Scanner(System.in);
        while (true) {
            System.out.println("1. Enter Ticket\n2. Delete Ticket\n3. Display All Tickets\n4. Quit");
            int task = Integer.parseInt(scan.nextLine());
            if (task == 1) {
//Call addTickets, which will let us enter any number of new tickets
                addTickets(ticketQueue);
            } else if (task == 2) {
//delete a ticket
                deleteTicket(ticketQueue);
            } else if (task == 4) {
//Quit. Future prototype may want to save all tickets to a file
                System.out.println("Quitting program");
                break;
            } else {
//this will happen for 3 or any other selection that is a valid int
//TODO Program crashes if you enter anything else - please fix
//Default will be print all tickets
                printAllTickets(ticketQueue);
            }
        }
        scan.close();
    }

    protected static void deleteTicket(LinkedList<Ticket> ticketQueue) {
        //What to do here? Need to delete ticket, but how do we identify the ticket to delete?
        //DONE: implement this method
        printAllTickets(ticketQueue);
        // DONE – re-write this method to ask for ID again if not found
        if (ticketQueue.size() == 0) {
            // if no tickets available to delete say so and exit the method
            System.out.println("No tickets to delete!\n");
            return;
        }

        // Problem 2, part a
        boolean found = false;
        while (!found) {
            // get user to pick a ticket to delete
            Scanner deleteScanner = new Scanner(System.in);
            // Problem 2, part b
            boolean valid_input = false;
            int deleteID = -1;  // set it to negative so if something goes wrong, it's obvious.
            while (!valid_input) {
                try {
                    System.out.println("Enter ID of ticket to delete:");
                    deleteID = deleteScanner.nextInt();
                    if (deleteID > 0) { valid_input = true; }
                    else {
                        // this lets me use my exception code if someone tries a negative number.
                        throw new Exception();
                    }
                } catch (Exception e) {
                    // report the error - we don't really care about specific error types,
                    // just that we don't have valid input
                    System.out.println("That is not a valid ticket ID.  Please try again.");
                    // and clear the scanner
                    deleteScanner.nextLine();
                } // end try-catch block
            } // end while-not-valid loop

            // loop through the tickets and see if there is a match
            for (Ticket t : ticketQueue) {
                // if a match found, delete it and set found to true
                if (t.getTicketID() == deleteID) {
                    found = true;
                    ticketQueue.remove(t);
                }
            }

            if (!found) {
                System.out.println("Ticket ID not found.  Please enter an existing ticket number.");
                printAllTickets(ticketQueue);
            }

        }  // end while-not-found loop

        // print updated list of tickets
        printAllTickets(ticketQueue);

        // close our scanner
        // deleteScanner.close();
    }

    //Move the adding ticket code to a method
    protected static void addTickets(LinkedList<Ticket> ticketQueue) {
        Scanner sc = new Scanner(System.in);
        boolean moreProblems = true;
        String description;
        String reporter;
        //let's assume all tickets are created today, for testing. We can change this later if needed
        Date dateReported = new Date(); //Default constructor creates date with current date/time
        int priority;
        while (moreProblems) {
            System.out.println("Enter problem");
            description = sc.nextLine();
            System.out.println("Who reported this issue?");
            reporter = sc.nextLine();
            System.out.println("Enter priority of " + description);
            priority = Integer.parseInt(sc.nextLine());
            Ticket t = new Ticket(description, priority, reporter, dateReported);
            // ticketQueue.add(t);
            addTicketInPriorityOrder(ticketQueue, t);
            //To test, let's print out all of the currently stored tickets
            printAllTickets(ticketQueue);
            System.out.println("More tickets to add?");
            String more = sc.nextLine();
            if (more.equalsIgnoreCase("N")) {
                moreProblems = false;
            }
        }
    }

    protected static void addTicketInPriorityOrder(LinkedList<Ticket> tickets, Ticket newTicket) {
        //Logic: assume the list is either empty or sorted
        if (tickets.size() == 0) {//Special case - if list is empty, add ticket and return
            tickets.add(newTicket);
            return;
        }
        //Tickets with the HIGHEST priority number go at the front of the list. (e.g. 5=server on fire)
        //Tickets with the LOWEST value of their priority number (so the lowest priority) go at the end
        int newTicketPriority = newTicket.getPriority();
        for (int x = 0; x < tickets.size(); x++) { //use a regular for loop so we know which element we are looking at
            //if newTicket is higher or equal priority than the this element, add it in front of this one, and return
            if (newTicketPriority >= tickets.get(x).getPriority()) {
                tickets.add(x, newTicket);
                return;
            }
        }
        //Will only get here if the ticket is not added in the loop
        //If that happens, it must be lower priority than all other tickets. So, add to the end.
        tickets.addLast(newTicket);
    }

    protected static void printAllTickets(LinkedList<Ticket> tickets) {
        System.out.println(" ------- All open tickets ----------");
        for (Ticket t : tickets) {
            System.out.println(t); //Write a toString method in Ticket class
//println will try to call toString on its argument
        }
        System.out.println(" ------- End of ticket list ----------");
    }
}