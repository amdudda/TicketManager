package com.amdudda;

import javax.swing.*;
import javax.swing.text.html.Option;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by amdudda on 11/9/2015.
 */
public class OptionsMenu extends JFrame {
    private JButton enterATroubleTicketButton;
    private JButton deleteByTicketIDButton;
    private JButton deleteByIssueButton;
    private JButton searchByNameButton;
    private JButton displayTicketsButton;
    private JButton quitButton;
    private JPanel rootPanel;
    private JTextField searchByNameTextField;
    private JList<Ticket> TicketList;
    private JButton deleteSelectedTicketButton;

    private DefaultListModel<Ticket> ticketListModel;

    public OptionsMenu() {
        super("Options Menu");
        setContentPane(rootPanel);
        pack();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

        ticketListModel = new DefaultListModel<Ticket>();
        TicketList.setModel(ticketListModel);

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
                OptionsMenu.this.ticketListModel.clear();
                for (Ticket t : TicketManager.ticketQueue) {
                    OptionsMenu.this.ticketListModel.addElement(t);
                }
            }
        });

        deleteSelectedTicketButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // this deletes the selected ticket from TicketList
                Ticket toDelete = OptionsMenu.this.TicketList.getSelectedValue();
                // Move to resolved queue
                TicketManager.ticketQueue.remove(toDelete);
                TicketManager.resolvedTickets.add(toDelete);
                // and update the display
                OptionsMenu.this.ticketListModel.removeElement(toDelete);
            }
        });
    }
}
