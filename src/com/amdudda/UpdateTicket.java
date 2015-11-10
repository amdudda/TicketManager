package com.amdudda;

import oracle.jrockit.jfr.JFR;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;

/**
 * Created by tk0654wm on 11/10/2015.
 */
public class UpdateTicket extends JFrame {
    private JTextField updateTicketTextField;
    private JTextField reportedByTextField;
    private JTextArea descriptionTextArea1;
    private JButton updateTicketButton;
    private JComboBox<Integer> priorityComboBox;
    private JPanel rootPanel;
    private JLabel DateOpenedLabel;
    private JTextArea resolutionTextArea;
    private JComboBox<String> StatusComboBox;

    public UpdateTicket(Ticket selectedTicket) {
        super("Update Ticket");
        setContentPane(rootPanel);
        pack();
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setVisible(true);

        // set up combo boxes
        for (int i=1; i<=5; i++) {
            priorityComboBox.addItem(i);
        }
        StatusComboBox.addItem("active");
        StatusComboBox.addItem("resolved");

        // populate our values
        updateTicketTextField.setText("Update Ticket #" + selectedTicket.getTicketID());
        reportedByTextField.setText(selectedTicket.getReporter());
        descriptionTextArea1.setText(selectedTicket.getDescription());
        DateOpenedLabel.setText(selectedTicket.getDateReported().toString());
        resolutionTextArea.setText(selectedTicket.getResolution());
        priorityComboBox.setSelectedItem(selectedTicket.getPriority());
        StatusComboBox.setSelectedItem(selectedTicket.getStatus());

        // and an action listener
        updateTicketButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedTicket.setPriority(UpdateTicket.this.priorityComboBox.getSelectedIndex()+1);
                selectedTicket.setReporter(UpdateTicket.this.reportedByTextField.getText());
                selectedTicket.setDescription(UpdateTicket.this.descriptionTextArea1.getText());
                selectedTicket.setResolution(UpdateTicket.this.resolutionTextArea.getText());
                selectedTicket.setStatus(UpdateTicket.this.StatusComboBox.getSelectedItem().toString());

                // if ticket is resolved, need to move ticket to closed queue
                if (UpdateTicket.this.StatusComboBox.getSelectedItem().equals("resolved")) {
                    selectedTicket.setDateResolved(LocalDateTime.now());
                    TicketManager.ticketQueue.remove(selectedTicket);
                    TicketManager.resolvedTickets.add(selectedTicket);
                }

                // and close the window
                dispose();
            }
        });
    }
}
