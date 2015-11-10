package com.amdudda;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;

/**
 * Created by amdudda on 11/9/2015.
 */
public class NewTicket extends JFrame {
    private JTextField addNewTicketTextField;
    private JTextField reportedByTextField;
    private JTextArea descriptionTextArea1;
    private JButton addTicketToSystemButton;
    private JPanel rootPanel;
    //private int[] priorityLevels = {1,2,3,4,5};
    private JComboBox<Integer> priorityComboBox;


    public NewTicket() {
        super("Enter New Ticket");
        setContentPane(rootPanel);
        pack();
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setVisible(true);

       // add values to the combo box and set default rating to 3.
        for (int i=1; i<=5; i++) {
            priorityComboBox.addItem(i);
        }
        priorityComboBox.setSelectedIndex(2);

        addTicketToSystemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // gather our values to pass into ticketQueue
                String descript = descriptionTextArea1.getText();
                int priInt = priorityComboBox.getSelectedIndex()+1;
                String rept = reportedByTextField.getText();
                //int priInt = Integer.parseInt(priText);
                Ticket toAdd = new Ticket(descript, priInt, rept, LocalDateTime.now());
                // add info as a new ticket in ticketQueue
                TicketManager.ticketQueue.add(toAdd);
                // close the window
                dispose();
            }
        });
    }

}
