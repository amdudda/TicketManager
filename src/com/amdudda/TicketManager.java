package com.amdudda;

import java.util.*;

/*
    Base code copied from lab slides
 */

public class TicketManager {

    /*
    I made ticketQueue a public variable so I can modify ticketQueue
    when I need to delete stuff from it, rather than refactoring
    everything to pass two LinkedList variables.
    */
    private static LinkedList<Ticket> ticketQueue;
    private static LinkedList<Ticket> resolvedTickets;

    public static void main(String[] args) {
        ticketQueue = new LinkedList<Ticket>();
        resolvedTickets = new LinkedList<Ticket>();
        Scanner scan = new Scanner(System.in);
        boolean keepgoing = true;
        while (keepgoing) {
            System.out.println("1. Enter Ticket\n" +
                    "2. Delete by ID\n" +
                    "3. Delete by Issue\n" +
                    "4. Search by Name\n" +
                    "5. Display All Tickets\n" +
                    "6. Quit");
            int task = Integer.parseInt(scan.nextLine());
            switch (task) {
                case 1: {
                    //Call addTickets, which will let us enter any number of new tickets
                    addTickets(ticketQueue);
                    break;
                }
                case 2: {
                    //delete a ticket by ID
                    deleteTicket(ticketQueue);
                    break;
                }
                case 3: {
                    //delete a ticket by Issue
                    deleteByIssue(ticketQueue);
                    break;
                }
                case 4: {
                    //Find a ticket by reporter's name
                    searchByName(ticketQueue);
                    break;
                }
                case 6: {
                    //Quit. Future prototype may want to save all tickets to a file
                    System.out.println("Quitting program");
                    keepgoing = false;
                    break;
                }
                default: {
                    //this will happen for 3 or any other selection that is a valid int
                    //TODO Program crashes if you enter anything else - please fix
                    //Default will be print all tickets
                    printAllTickets(ticketQueue);
                }
            } // end switch-case

        } // end while-keepgoing

        scan.close();
    }

    private static void searchByName(LinkedList<Ticket> tQ) {
        LinkedList<Ticket> searchResults = new LinkedList<Ticket>();
        Scanner input = new Scanner(System.in);
        System.out.println("Enter a string to search for in the \"reporter\" field:");
        String name = input.nextLine();
        for (Ticket t:tQ) {
            if (t.getReporter().contains(name)) {
                // if the description contains the search string, add it to searchResults list
                searchResults.add(t);
            }
        } // end for each

        if (searchResults.isEmpty()) {
            // if nothing was found, tell the user that.
            System.out.println("No matches found.");
        } else {
            //
            printAllTickets(searchResults);
        } // end if-else

    } // end searchByName

    private static LinkedList<Ticket> searchByIssue(LinkedList<Ticket> tQ) {
        LinkedList<Ticket> searchResults = new LinkedList<Ticket>();
        Scanner input = new Scanner(System.in);
        System.out.println("Enter a string to search for in the \"description\" field:");
        String desc = input.nextLine();
        for (Ticket t:tQ) {
            if (t.getDescription().contains(desc)) {
                // if the description contains the search string, add it to searchResults list
                searchResults.add(t);
            }
        } // end for each

        /* retained for debugging purposes
        if (searchResults.isEmpty()) {
            // if nothing was found, tell the user that.
            System.out.println("No matches found.");
        } else {
            printAllTickets(searchResults);
        } // end if-else
        */

        return searchResults;

    } // end searchByName

    private static void deleteTicket(LinkedList<Ticket> tQ) {
        //What to do here? Need to delete ticket, but how do we identify the ticket to delete?
        //DONE: implement this method
        printAllTickets(tQ);
        // DONE – re-write this method to ask for ID again if not found
        if (tQ.size() == 0) {
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
                    // ticketQueue.remove(t);
                    // problem 5: method to move tickets to Resolved queue
                    resolveTicket(t);
                }
            }

            if (!found) {
                System.out.println("Ticket ID not found.  Please enter an existing ticket number.");
                printAllTickets(tQ);
            }

        }  // end while-not-found loop

        // print updated list of tickets
        printAllTickets(tQ);

        // close our scanner
        // deleteScanner.close();
    }

    private static void resolveTicket(Ticket t) {
        // takes a ticket, marks it as resolved, gathers resolution data, and moves it to resolvedTickets linkedlist
        Scanner r = new Scanner(System.in);
        // mark the ticket as resolved and use the current time stamp as the resolution date
        t.setStatus("resolved");
        t.setDateResolved(new Date());
        // have the user enter a resolution for the issue
        System.out.println("Enter a resolution for the ticket:");
        String resolution = r.nextLine();
        t.setResolution(resolution);

        // add the ticket to resolved tickets and remove it from the ticket queue.
        resolvedTickets.add(t);
        // testing
        printAllTickets(resolvedTickets);
        ticketQueue.remove(t);
    }

    private static void deleteByIssue(LinkedList<Ticket> tQ) {
        // first get the list of issues the user wants to work with
        LinkedList<Ticket> matchingIssues = searchByIssue(tQ);
        // then take advantage of deleteTicket to let the user pick
        // the ticket # they actually want to delete.
        if (matchingIssues.isEmpty()) {
            System.out.println("No matches found!");
            return;
        } else {
            deleteTicket(matchingIssues);
        }
    }

    //Move the adding ticket code to a method
    protected static void addTickets(LinkedList<Ticket> tQ) {
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
            addTicketInPriorityOrder(tQ, t);
            //To test, let's print out all of the currently stored tickets
            printAllTickets(tQ);
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