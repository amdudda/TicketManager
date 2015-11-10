package com.amdudda;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by amdudda on 11/9/2015.
 */
public class OptionsMenu extends JFrame {
    private JButton enterATroubleTicketButton;
    private JButton searchByIssueButton;
    private JButton searchByNameButton;
    private JButton displayTicketsButton;
    private JButton quitButton;
    private JPanel rootPanel;
    private JTextField searchByNameTextField;
    protected JList<Ticket> TicketList;
    private JButton deleteSelectedTicketButton;
    private JButton displayResolvedTicketsButton;

    protected DefaultListModel<Ticket> ticketListModel;

    public OptionsMenu() {
        super("Options Menu");
        setContentPane(rootPanel);
        pack();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        setSize(new Dimension(800, 500));

        ticketListModel = new DefaultListModel<Ticket>();
        TicketList.setModel(ticketListModel);
        for (Ticket t : TicketManager.ticketQueue) {
            OptionsMenu.this.ticketListModel.addElement(t);
        }
        OptionsMenu.this.TicketList.setSelectedIndex(0);

        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });


        displayTicketsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // want to list all open tickets for now, will refine later
                OptionsMenu.this.deleteSelectedTicketButton.setEnabled(true);
                OptionsMenu.this.ticketListModel.clear();
                for (Ticket t : TicketManager.ticketQueue) {
                    OptionsMenu.this.ticketListModel.addElement(t);
                }
            }
        });

        deleteSelectedTicketButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // opens window to update a ticket
                UpdateTicket updateTicket = new UpdateTicket(OptionsMenu.this.TicketList.getSelectedValue());
            }
        });

        displayResolvedTicketsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // turn off ability to delete tickets
                OptionsMenu.this.deleteSelectedTicketButton.setEnabled(false);
                // clear the list of tickets
                OptionsMenu.this.ticketListModel.clear();
                for (Ticket t : TicketManager.resolvedTickets) {
                    OptionsMenu.this.ticketListModel.addElement(t);
                }
            }
        });


        searchByNameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                OptionsMenu.this.deleteSelectedTicketButton.setEnabled(true);
                // modified from code in TicketManager
                ArrayList<Ticket> searchResults = new ArrayList<Ticket>();
                String name = OptionsMenu.this.searchByNameTextField.getText().toLowerCase();
                for (Ticket t: TicketManager.ticketQueue) {
                    if (t.getReporter().toLowerCase().contains(name)) {
                        // if the description contains the search string, add it to searchResults list
                        searchResults.add(t);
                    }
                } // end for each

                if (searchResults.isEmpty()) {
                    // if nothing was found, tell the user that.
                    JOptionPane.showMessageDialog(OptionsMenu.this, "No matches found!");
                } else {
                    // update the list of tickets with matches
                    OptionsMenu.this.ticketListModel.clear();
                    for (Ticket result : searchResults) {
                        OptionsMenu.this.ticketListModel.addElement(result);
                    }
                } // end if-else
            }
        });
        searchByIssueButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                OptionsMenu.this.deleteSelectedTicketButton.setEnabled(true);
                // modified from code in TicketManager
                ArrayList<Ticket> searchResults = new ArrayList<Ticket>();
                String name = OptionsMenu.this.searchByNameTextField.getText().toLowerCase();
                for (Ticket t: TicketManager.ticketQueue) {
                    if (t.getDescription().toLowerCase().contains(name)) {
                        // if the description contains the search string, add it to searchResults list
                        searchResults.add(t);
                    }
                } // end for each

                if (searchResults.isEmpty()) {
                    // if nothing was found, tell the user that.
                    JOptionPane.showMessageDialog(OptionsMenu.this, "No matches found!");
                } else {
                    // update the list of tickets with matches
                    OptionsMenu.this.ticketListModel.clear();
                    for (Ticket result : searchResults) {
                        OptionsMenu.this.ticketListModel.addElement(result);
                    }
                } // end if-else
            }
        });
        enterATroubleTicketButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                NewTicket enterTicket = new NewTicket();
            }
        });
    }
}
