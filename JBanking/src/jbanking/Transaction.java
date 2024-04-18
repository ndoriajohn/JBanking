package jbanking;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Transaction extends JFrame {
    private String senderUsername;
    private JTextField receiverUsernameField;
    private JTextField amountField;
    private JButton transferButton;
    private JButton backButton;

    private Connection connection;

    public Transaction(String senderUsrname) {
        this.senderUsername=senderUsername;
        
        setTitle("Transactions");
        setSize(400, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));

        JLabel receiverUsernameLabel = new JLabel("Receiver Username:");
        panel.add(receiverUsernameLabel);
        receiverUsernameField = new JTextField();
        panel.add(receiverUsernameField);

        JLabel amountLabel = new JLabel("Amount:");
        panel.add(amountLabel);
        amountField = new JTextField();
        panel.add(amountField);

        transferButton = new JButton("Transfer");
        transferButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                transfer();
            }
        });
        panel.add(transferButton);
        backButton = new JButton("Back"); // Initialize the back button
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

   private void transfer() {
    String receiverUsername = receiverUsernameField.getText();
    double amount = Double.parseDouble(amountField.getText());
    String senderUsername = SessionManager.getLoggedInUsername(); // Get logged-in username
    if (senderUsername.equals(receiverUsername)) {
        JOptionPane.showMessageDialog(this, "You cannot transfer funds to yourself.", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    try {
        // Check if receiver username exists in the database
        String receiverSql = "SELECT * FROM accounts WHERE owner_username = ?";
        PreparedStatement receiverStatement = connection.prepareStatement(receiverSql);
        receiverStatement.setString(1, receiverUsername);
        ResultSet receiverResult = receiverStatement.executeQuery();
        if (!receiverResult.next()) {
            JOptionPane.showMessageDialog(this, "Receiver username not found.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Update sender's balance
        double senderBalance = getBalance(senderUsername);
        if (senderBalance < amount) {
            JOptionPane.showMessageDialog(this, "Insufficient balance.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        double newSenderBalance = senderBalance - amount;
        updateBalance(senderUsername, newSenderBalance);

        // Update receiver's balance
        double receiverBalance = receiverResult.getDouble("balance");
        double newReceiverBalance = receiverBalance + amount;
        updateBalance(receiverUsername, newReceiverBalance);

        // Insert transaction data into transactions table
        insertTransaction(senderUsername, receiverUsername, amount);

        JOptionPane.showMessageDialog(this, "Transaction successful!");
    } catch (SQLException ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(this, "An error occurred. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
    }
}

private void insertTransaction(String senderUsername, String receiverUsername, double amount) throws SQLException {
    String insertTransactionSql = "INSERT INTO transactions (sender_username, receiver_username, amount) VALUES (?, ?, ?)";
    PreparedStatement insertTransactionStatement = connection.prepareStatement(insertTransactionSql);
    insertTransactionStatement.setString(1, senderUsername);
    insertTransactionStatement.setString(2, receiverUsername);
    insertTransactionStatement.setDouble(3, amount);
    insertTransactionStatement.executeUpdate();
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Transaction("senderUsername").setVisible(true);
            }
        });
    }
}
