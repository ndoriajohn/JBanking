package jbanking;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class DepositCash extends JFrame {
    private JTextField amountField;
    private JButton depositButton, backButton;

    private Connection connection;

    public DepositCash() {
        setTitle("Deposit Cash");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 2));

        JLabel amountLabel = new JLabel("Amount:");
        panel.add(amountLabel);
        amountField = new JTextField();
        panel.add(amountField);

        depositButton = new JButton("Deposit");
        depositButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                depositCash();
            }
        });
        panel.add(depositButton);

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

        // Initialize database connection
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/jbanking", "root", "");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void depositCash() {
        double amount = Double.parseDouble(amountField.getText());
        String username = SessionManager.getLoggedInUsername(); // Get logged-in username

        try {
            // Update user's balance
            double currentBalance = getBalance(username);
            double newBalance = currentBalance + amount;
            updateBalance(username, newBalance);

            // Insert transaction data into transactions table
            insertTransaction(username, "deposited", amount); // Pass "deposited" as the receiver

            JOptionPane.showMessageDialog(this, "Cash deposited successfully!");
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "An error occurred. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private double getBalance(String username) throws SQLException {
        String sql = "SELECT balance FROM accounts WHERE owner_username = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, username);
        ResultSet resultSet = statement.executeQuery();
        resultSet.next();
        return resultSet.getDouble("balance");
    }

    private void updateBalance(String username, double newBalance) throws SQLException {
        String sql = "UPDATE accounts SET balance = ? WHERE owner_username = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setDouble(1, newBalance);
        statement.setString(2, username);
        statement.executeUpdate();
    }

    private void insertTransaction(String username, String receiver, double amount) throws SQLException {
        String insertTransactionSql = "INSERT INTO transactions (sender_username, receiver_username, amount) VALUES (?, ?, ?)";
        PreparedStatement insertTransactionStatement = connection.prepareStatement(insertTransactionSql);
        insertTransactionStatement.setString(1, username);
        insertTransactionStatement.setString(2, receiver); // Use "deposited" as the receiver
        insertTransactionStatement.setDouble(3, amount);
        insertTransactionStatement.executeUpdate();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new DepositCash().setVisible(true);
            }
        });
    }
}
