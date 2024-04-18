/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jbanking;

/**
 *
 * @author admin
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.Date;
import jbanking.SessionManager;


public class Paybills extends JFrame {
    private JComboBox<String> billsComboBox;
    private JTextField amountField;
    private JButton payButton;
     private JButton backButton;

    private Connection connection;

    public Paybills() {
        setTitle("Pay Bills");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));

        JLabel billLabel = new JLabel("Select Bill:");
        panel.add(billLabel);
        String[] bills = {"Electricity", "Water", "Internet", "Phone"};
        billsComboBox = new JComboBox<>(bills);
        panel.add(billsComboBox);

        JLabel amountLabel = new JLabel("Amount:");
        panel.add(amountLabel);
        amountField = new JTextField();
        panel.add(amountField);

        payButton = new JButton("Pay");
        payButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                payBill();
            }
        });
        panel.add(payButton);
         backButton = new JButton("Back"); // Initialize the back button
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Action to perform when the back button is clicked
                new BankingMenu().setVisible(true); // Open login page
                        dispose();
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

    private void payBill() {
        String bill = (String) billsComboBox.getSelectedItem();
        double amount = Double.parseDouble(amountField.getText());
        String username = getUsername(); // Get username from login_data table
        int accountNumber = getAccountNumber(); // Get account number from accounts table

        if (username == null) {
            JOptionPane.showMessageDialog(this, "User not found.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            // Check if user has enough balance to pay the bill
            String sql = "SELECT * FROM accounts WHERE owner_username = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                double balance = resultSet.getDouble("balance");
                if (balance < amount) {
                    JOptionPane.showMessageDialog(this, "Insufficient balance to pay the bill.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Update the balance in the database after paying the bill
                double newBalance = balance - amount;
                String updateSql = "UPDATE accounts SET balance = ? WHERE owner_username = ?";
                PreparedStatement updateStatement = connection.prepareStatement(updateSql);
                updateStatement.setDouble(1, newBalance);
                updateStatement.setString(2, username);
                updateStatement.executeUpdate();

                // Store payment details in the paybills table
                Date currentDate = new Date();
                java.sql.Date sqlDate = new java.sql.Date(currentDate.getTime());
                String insertSql = "INSERT INTO paybills (bill_name, amount, payment_date, account_number) VALUES (?, ?, ?, ?)";
                PreparedStatement insertStatement = connection.prepareStatement(insertSql);
                insertStatement.setString(1, bill);
                insertStatement.setDouble(2, amount);
                insertStatement.setDate(3, sqlDate);
                insertStatement.setInt(4, accountNumber);
                insertStatement.executeUpdate();

                JOptionPane.showMessageDialog(this, "Bill paid successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "User not found.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "An error occurred. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private String getUsername() {
        // Get the username from the login_data table based on the logged-in user's credentials
        // You need to implement this method to fetch the username
        return  SessionManager.getLoggedInUsername(); // Replace with actual logic to retrieve username
    }

    private int getAccountNumber() {
        String username = getUsername(); // Assuming this method retrieves the logged-in username

    try {
        // Prepare SQL query to retrieve account number based on username
        String sql = "SELECT account_number FROM accounts WHERE owner_username = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, username);
        
        // Execute the query
        ResultSet resultSet = statement.executeQuery();

        // Check if any results were returned
        if (resultSet.next()) {
            // If yes, retrieve the account number from the result set
            return resultSet.getInt("account_number");
        } else {
            // If no results were returned, handle the case where the user does not have an account
            JOptionPane.showMessageDialog(this, "User does not have an account.", "Error", JOptionPane.ERROR_MESSAGE);
            return -1; // Return a default or error value
        }
    } catch (SQLException ex) {
        // Handle any SQL exceptions that may occur
        ex.printStackTrace();
        JOptionPane.showMessageDialog(this, "An error occurred while fetching account number. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
        return -1; // Return a default or error value
    }
}

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Paybills().setVisible(true);
            }
        });
    }
}

