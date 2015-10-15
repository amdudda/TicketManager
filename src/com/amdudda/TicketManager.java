package com.amdudda;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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

    public static void main(String[] args) throws IOException {
        ticketQueue = new LinkedList<Ticket>();
        resolvedTickets = new LinkedList<Ticket>();

        readTicketData();

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
                    writeTicketData();  // doesn't need to pass anything - the two linkedlists are global variables
                    keepgoing = false;
                    break;
                }
                default: {
                    //this will happen for 5 or any other selection that is a valid int
                    //TODO Program crashes if you enter anything else - please fix
                    //Default will be print all tickets
                    printAllTickets(ticketQueue);
                }
            } // end switch-case

        } // end while-keepgoing

        scan.close();
    }

    private static void readTicketData() throws IOException {
        // reads in data from open_tickets.txt
        // location of our file is expected to be in package's data subdirectory
        String fpath = "./data/open_tickets.txt";

        try {
            // set up our filestreams
            File f = new File(fpath);
            FileReader fR = new FileReader(f);
            BufferedReader bR = new BufferedReader(fR);

        // read in the first line of data
        String line = bR.readLine();

        // Margaret and Malcolm clued me in to using Collections to turn a string into an array
        // googling got me this page which showed me how: http://javarevisited.blogspot.com/2011/06/converting-array-to-arraylist-in-java.html
        while (line != null) {
            // read in the data
            ArrayList<String> attribs = new ArrayList<>();
            Collections.addAll(attribs, line.split("\t"));
            // create a new ticket object and add it to ticketQueue
            Ticket t = new Ticket(attribs);
            ticketQueue.add(t);
            // read the next line
            line = bR.readLine();
        }

        // close our filestreams
        bR.close();
        fR.close();
        }
        catch (FileNotFoundException fnfe) {
            // if the file isn't found, return back to the program without reading any data
            return;
        }
        catch (Exception e) {
            System.out.println("The file exists, but something else is wrong with it.");
            System.out.println(e);
        }
    }

    private static void writeTicketData() throws IOException {
        // writes ticket data to files at program close
        // need to consider how to store it so we can read the data in again later.
        // info on date time extraction adduced from https://docs.oracle.com/javase/8/docs/api/index.html?java/time/LocalDate.html
        // format with leading zeroes taken from http://stackoverflow.com/questions/4051887/how-to-format-a-java-string-with-leading-zero
        // generate a time stamp for our resolved tickets output
        LocalDate datestamp = LocalDate.now();
        LocalTime hourstamp = LocalTime.now();
        String timestamp = String.format("%s-%02d%02d\n",datestamp, hourstamp.getHour(), hourstamp.getMinute());

        // create the filenames for our reports
        String dir = "./data/";  // data directory to store output to
        String resolvedPath = dir + "ResolvedTickets" + timestamp + ".txt";
        String openPath = dir + "open_tickets.txt";

        // and generate the output data - let's create a single module that we call twice
        // once for resolved tickets and once for open tickets
        generateReport(resolvedPath,resolvedTickets);
        generateReport(openPath,ticketQueue);
    }

    private static void generateReport(String destination, LinkedList<Ticket> queue) throws IOException {
        // writes out a report of tickets to the designated file
        // create our output streams
        File f = new File(destination);
        FileWriter fW = new FileWriter(f);
        BufferedWriter bW = new BufferedWriter(fW);

        // write the header column so we can read that in for our data generation
        /* dropping this - we don't need the header info after all
        bW.write("ticketID\tdescription\tpriority\treporter\tdateReported\t"
                + "status\tdateResolved\tresolution\n");
        */

        // for each ticket in the designated queue, gather its attributes in tab-delimited format
        // and write it to the destination file.
        for (Ticket t:queue) {
            bW.write(t.toTabDelimited());
        }

        System.out.println("Report written to " + destination);
        // close our data streams
        bW.close();
        fW.close();
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
                    break;
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
        t.setDateResolved(LocalDateTime.now());
        // have the user enter a resolution for the issue
        System.out.println("Enter a resolution for the ticket:");
        String resolution = r.nextLine();
        t.setResolution(resolution);

        // add the ticket to resolved tickets and remove it from the ticket queue.
        resolvedTickets.add(t);
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
        LocalDateTime dateReported = LocalDateTime.now(); //Default constructor creates date with current date/time
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
        if (tickets.isEmpty()) {
            // say there are no tickets and exit printAllTickets
            System.out.println("No tickets in queue!");
            return;
        }
        System.out.printf(" ------- All %s tickets ----------\n", tickets.get(0).getStatus());
        for (Ticket t : tickets) {
            System.out.println(t); //Write a toString method in Ticket class
//println will try to call toString on its argument
        }
        System.out.println(" ------- End of ticket list ----------");
    }
}