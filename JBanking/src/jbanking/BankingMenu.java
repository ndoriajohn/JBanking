package jbanking;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class BankingMenu extends JFrame implements ActionListener {
    private JButton depositCashBtn,withdrawBtn, viewAccountBtn, transactionBtn, payBillsBtn, logoutBtn, backToLoginBtn;

    public BankingMenu() {
        setTitle("Banking System Menu");
        setSize(400, 250);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(6, 1));

        depositCashBtn = new JButton("Deposit Cash");
        depositCashBtn.addActionListener(this);
        
        withdrawBtn = new JButton("Withdraw Cash");
        withdrawBtn.addActionListener(this);

        viewAccountBtn = new JButton("View Account");
        viewAccountBtn.addActionListener(this);

        transactionBtn = new JButton("Transactions");
        transactionBtn.addActionListener(this);

        payBillsBtn = new JButton("Pay Bills");
        payBillsBtn.addActionListener(this);

        logoutBtn = new JButton("Logout");
        logoutBtn.addActionListener(this);
        
        backToLoginBtn = new JButton("Back to Login");
        backToLoginBtn.addActionListener(this);

        add(depositCashBtn);
        add(withdrawBtn);
        add(viewAccountBtn);
        add(transactionBtn);
        add(payBillsBtn);
        add(logoutBtn);
        add(backToLoginBtn);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == depositCashBtn) {
            // Open  Deposit Cash page
            new DepositCash().setVisible(true);
            dispose(); // Close the current window
        } else if (e.getSource() == withdrawBtn) {
            // Open  Deposit Cash page
            SessionManager sessionManager = new SessionManager();
            new Withdraw(sessionManager).setVisible(true);
           
            dispose(); // Close the current window
        }else if (e.getSource() == viewAccountBtn) {
            // Open View Account page
            String loggedInUsername = SessionManager.getLoggedInUsername(); 
            new ViewAccount(loggedInUsername).setVisible(true);
            dispose(); // Close the current window
        } else if (e.getSource() == transactionBtn) {
            // Open Transactions page
            String loggedInUsername = SessionManager.getLoggedInUsername(); 
            new Transaction(loggedInUsername).setVisible(true);
            dispose(); // Close the current window
        } else if (e.getSource() == payBillsBtn) {
            // Open Pay Bills page
            new Paybills().setVisible(true);
            dispose(); // Close the current window
        } else if (e.getSource() == logoutBtn) {
            int choice = JOptionPane.showConfirmDialog(this, "Are you sure you want to logout?", "Logout", JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.YES_OPTION) {
                JOptionPane.showMessageDialog(this, "Logged out successfully!");
                System.exit(0);
            }
        } else if (e.getSource() == backToLoginBtn) {
            // Open login page
            new Login().setVisible(true);
            dispose(); // Close the current window
        }
    }

    public static void main(String[] args) {
        new BankingMenu();
    }
}
