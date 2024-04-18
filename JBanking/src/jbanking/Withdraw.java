/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jbanking;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Withdraw extends JFrame {
    private JTextField amountField;
    private JButton withdrawButton;
    private SessionManager sessionManager;
    private JButton backButton;

    public Withdraw(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
        setTitle("Withdraw");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 2));

        JLabel amountLabel = new JLabel("Amount to Withdraw:");
        panel.add(amountLabel);

        amountField = new JTextField();
        panel.add(amountField);

        withdrawButton = new JButton("Withdraw");
        withdrawButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                withdraw();
            }
        });
        panel.add(withdrawButton);
        
        backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Action to perform when the back button is clicked
                new BankingMenu().setVisible(true); // Open banking menu
                dispose(); // Close the current window
            }
        });
        panel.add(backButton);

        add(panel);

        setVisible(true);
    }

    private void withdraw() {
        double amount = Double.parseDouble(amountField.getText());
        String username = sessionManager.getLoggedInUsername();

        try (Connection connect = Conn.getConnection()) {
            // Update balance in accounts table
            String updateBalanceSql = "UPDATE accounts SET balance = balance - ? WHERE owner_username = ?";
            PreparedStatement updateBalanceStmt = connect.prepareStatement(updateBalanceSql);
            updateBalanceStmt.setDouble(1, amount);
            updateBalanceStmt.setString(2, username);
            int rowsAffected = updateBalanceStmt.executeUpdate();

            if (rowsAffected > 0) {
                // Insert record into transactions table
                String insertTransactionSql = "INSERT INTO transactions (sender_username, receiver_username, amount) VALUES (?, ?, ?)";
                PreparedStatement insertTransactionStmt = connect.prepareStatement(insertTransactionSql);
                insertTransactionStmt.setString(1, username);
                insertTransactionStmt.setString(2, "Withdrawn");
                insertTransactionStmt.setDouble(3, amount);
               
                insertTransactionStmt.executeUpdate();

                JOptionPane.showMessageDialog(null, "Withdrawal successful.");
            } else {
                JOptionPane.showMessageDialog(null, "Insufficient balance.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "An error occurred: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        // Assuming you have a session manager instance available
        SessionManager sessionManager = new SessionManager(); // Replace this with your actual session manager instantiation
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Withdraw(sessionManager).setVisible(true);
            }
        });
    }
}
