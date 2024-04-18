/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jbanking;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainClass extends JFrame implements ActionListener {
    private JButton loginButton;
    private JLabel welcomeLabel;
    private JButton signupButton;

    public MainClass() {
        setTitle("Welcome to Our System");
        setSize(400, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 1));

        welcomeLabel = new JLabel("Welcome to Our System");
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(welcomeLabel);

        loginButton = new JButton("Login");
        loginButton.addActionListener(this);
        panel.add(loginButton);
        
        signupButton = new JButton("Sign Up");
        signupButton.addActionListener(this);
        panel.add(signupButton);

        add(panel);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            // Open the login page
            new Login().setVisible(true);
            dispose(); // Close the current window
        }
    
        if (e.getSource() == signupButton) {
            // Open the login page
            new Signup().setVisible(true);
            dispose(); // Close the current window
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainClass().setVisible(true);
            }
        });
    }
}
