package jbanking;
       
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class ViewAccount extends JFrame {
    private JLabel usernameLabel;
    private JLabel fullNameLabel;
    private JLabel emailLabel;
    private JLabel phoneNumberLabel;
    private JLabel balanceLabel;
    private JLabel accountNumberLabel;
    private JButton backButton; // Add the back button

    public ViewAccount(String username) {
        setTitle("View Account");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(7, 2));

        usernameLabel = new JLabel("Username:");
        fullNameLabel = new JLabel("Full Name:");
        emailLabel = new JLabel("Email:");
        phoneNumberLabel = new JLabel("Phone Number:");
        balanceLabel = new JLabel("Balance:");
        accountNumberLabel = new JLabel("Account Number:");

        panel.add(usernameLabel);
        panel.add(new JLabel(username));
        panel.add(fullNameLabel);
        panel.add(new JLabel());
        panel.add(emailLabel);
        panel.add(new JLabel());
        panel.add(phoneNumberLabel);
        panel.add(new JLabel());
        panel.add(balanceLabel);
        panel.add(new JLabel());
        panel.add(accountNumberLabel);
        panel.add(new JLabel());

        // Initialize and add the back button
        backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open the banking menu window
                new BankingMenu().setVisible(true);
                dispose(); // Close the current window
            }
        });
        panel.add(backButton);

        add(panel);

        fetchAccountDetails(username);

        setVisible(true);
    }

    private void fetchAccountDetails(String username) {
        try (Connection connection = Conn.getConnection()) {
            String query = "SELECT full_name, email, phone_number, balance, account_number FROM sign_up_data "
                    + "INNER JOIN accounts ON sign_up_data.username = accounts.owner_username WHERE sign_up_data.username = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String fullName = resultSet.getString("full_name");
                String email = resultSet.getString("email");
                String phoneNumber = resultSet.getString("phone_number");
                double balance = resultSet.getDouble("balance");
                int accountNumber = resultSet.getInt("account_number");

                fullNameLabel.setText("Full Name: " + fullName);
                emailLabel.setText("Email: " + email);
                phoneNumberLabel.setText("Phone Number: " + phoneNumber);
                balanceLabel.setText("Balance: $" + balance);
                accountNumberLabel.setText("Account Number: " + accountNumber);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "An error occurred while fetching account details: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ViewAccount("username").setVisible(true);
            }
        });
    }
}
