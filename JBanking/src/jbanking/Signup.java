package jbanking;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

public class Signup extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JTextField fullNameField;
    private JTextField emailField;
    private JTextField phoneNumberField;
    private JComboBox<String> accountTypeComboBox;
    private JTextField initialDepositField;

    public Signup() {
        setTitle("Sign Up");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(8, 2));

        JLabel usernameLabel = new JLabel("Username:");
        panel.add(usernameLabel);

        usernameField = new JTextField();
        panel.add(usernameField);

        JLabel passwordLabel = new JLabel("Password:");
        panel.add(passwordLabel);

        passwordField = new JPasswordField();
        panel.add(passwordField);

        JLabel fullNameLabel = new JLabel("Full Name:");
        panel.add(fullNameLabel);

        fullNameField = new JTextField();
        panel.add(fullNameField);

        JLabel emailLabel = new JLabel("Email:");
        panel.add(emailLabel);

        emailField = new JTextField();
        panel.add(emailField);

        JLabel phoneNumberLabel = new JLabel("Phone Number:");
        panel.add(phoneNumberLabel);

        phoneNumberField = new JTextField();
        panel.add(phoneNumberField);

        JLabel accountTypeLabel = new JLabel("Account Type:");
        panel.add(accountTypeLabel);

        String[] accountTypes = {"Savings", "Checking"}; // Example account types
        accountTypeComboBox = new JComboBox<>(accountTypes);
        panel.add(accountTypeComboBox);

        JLabel initialDepositLabel = new JLabel("Initial Deposit:");
        panel.add(initialDepositLabel);

        initialDepositField = new JTextField();
        panel.add(initialDepositField);

        JButton signUpButton = new JButton("Sign Up");
        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                String username = usernameField.getText();
                char[] passwordChars = passwordField.getPassword();
                String password = new String(passwordChars);
                String fullName = fullNameField.getText();
                String email = emailField.getText();
                String phoneNumber = phoneNumberField.getText();
                String accountType = (String) accountTypeComboBox.getSelectedItem();
                double initialDeposit = Double.parseDouble(initialDepositField.getText());

                // Check if the initial deposit meets the minimum requirement
                if (initialDeposit < 500) {
                    JOptionPane.showMessageDialog(null, "Minimum initial deposit is 500.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Generate account number
                long accountNumber = generateAccountNumber();

                // Check if the username already exists
                if (isUsernameTaken(username)) {
                    JOptionPane.showMessageDialog(null, "Username already exists. Please choose a different one.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Insert user data into the database
                try (Connection connect = Conn.getConnection()) {
                    // Insert into signup_data table
                    String sql = "INSERT INTO sign_up_data (username, password, full_name, email, phone_number) VALUES (?, ?, ?, ?, ?)";
                    PreparedStatement ptstmt = connect.prepareStatement(sql);
                    ptstmt.setString(1, username);
                    ptstmt.setString(2, password);
                    ptstmt.setString(3, fullName);
                    ptstmt.setString(4, email);
                    ptstmt.setString(5, phoneNumber);
                    ptstmt.executeUpdate();

                    // Insert into accounts table
                    String insertAccountSql = "INSERT INTO accounts (account_number, account_type, balance, owner_username) VALUES (?, ?, ?, ?)";
                    PreparedStatement insertAccountStmt = connect.prepareStatement(insertAccountSql);
                    insertAccountStmt.setLong(1, accountNumber);
                    insertAccountStmt.setString(2, accountType);
                    insertAccountStmt.setDouble(3, initialDeposit);
                    insertAccountStmt.setString(4, username);
                    insertAccountStmt.executeUpdate();

                    // Update log_in_data table
                    String updateLoginSql = "INSERT INTO login_data (username, password) VALUES (?, ?)";
                    PreparedStatement updateLoginStmt = connect.prepareStatement(updateLoginSql);
                    updateLoginStmt.setString(1, username);
                    updateLoginStmt.setString(2, password);
                    updateLoginStmt.executeUpdate();

                    JOptionPane.showMessageDialog(null, "Data has been inserted successfully. Your account number is: " + accountNumber);
                    new Login().setVisible(true); // Open login page
                    dispose(); // Close the sign-up window
                } catch (SQLException e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(null, "An error occurred while inserting data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        panel.add(signUpButton);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the sign-up window
            }
        });
        panel.add(cancelButton);

        add(panel);

        setVisible(true);
    }

    private boolean isUsernameTaken(String username) {
        try (Connection connection = Conn.getConnection()) {
            String query = "SELECT * FROM sign_up_data WHERE username = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next(); // Returns true if the username exists
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "An error occurred while checking username availability: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    private long generateAccountNumber() {
        // Generate a random 9-digit account number
        Random random = new Random();
        return 100000000 + random.nextInt(900000000);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Signup().setVisible(true);
            }
        });
    }
}
