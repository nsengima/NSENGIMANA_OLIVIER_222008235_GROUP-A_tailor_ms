package tailor;

import javax.swing.*;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RegistrationForm extends JFrame {
    private JTextField firstNameField;
    private JTextField secondNameField;
    private JTextField emailField;
    private JPasswordField createPasswordField;

    public RegistrationForm() {
        setTitle("REGISTER NOW");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(3, 3, 5, 5));

        add(new JLabel("Firstname:"));
        firstNameField = new JTextField();
        add(firstNameField);

        add(new JLabel("Secondname:"));
        secondNameField = new JTextField();
        add(secondNameField);

        add(new JLabel("Email:"));
        emailField = new JTextField();
        add(emailField);

        add(new JLabel("Create Password:"));
        createPasswordField = new JPasswordField();
        add(createPasswordField);

        JButton signUpButton = new JButton("SIGN UP");
        signUpButton.addActionListener(this::signUp);
        add(signUpButton);

        JButton createPasswordButton = new JButton("CREATE PASSWORD");
        createPasswordButton.addActionListener(this::createPassword);
        add(createPasswordButton);

        JButton forgotPasswordButton = new JButton("FORGOT PASSWORD");
        forgotPasswordButton.addActionListener(this::forgotPassword);
        add(forgotPasswordButton);

        JButton loginButton = new JButton("LOGIN");
        loginButton.addActionListener(this::login);
        add(loginButton);

        pack();
        setLocationRelativeTo(null);
    }

    private void signUp(ActionEvent e) {
        String firstName = firstNameField.getText();
        String secondName = secondNameField.getText();
        String email = emailField.getText();
        char[] passwordChars = createPasswordField.getPassword();
        String password = new String(passwordChars);

        // Establish a database connection
        try (Connection connection = DatabaseConnection.connect()) {
            // Perform database insertion
            String query = "INSERT INTO users (firstname, secondname, email, password) VALUES (?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, firstName);
                preparedStatement.setString(2, secondName);
                preparedStatement.setString(3, email);
                preparedStatement.setString(4, password);

                preparedStatement.executeUpdate();
            }

            showMessage("Sign Up successful");
        } catch (SQLException ex) {
            ex.printStackTrace();
            showMessage("Error occurred while signing up: " + ex.getMessage());
        }
    }

    private void createPassword(ActionEvent e) {
        // Handle create password logic here
        showMessage("Create Password button clicked");
    }

    private void forgotPassword(ActionEvent e) {
        // Handle forgot password logic here
        showMessage("Forgot Password button clicked");
    }

    private void login(ActionEvent e) {
        // Handle login logic here
        showMessage("Login button clicked");
    }

    private void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    public static void main(String[] args) {
        // Load the JDBC driver (you need to have the MySQL JDBC driver jar in your classpath)
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error: MySQL JDBC Driver not found!");
            System.exit(1);
        }

        SwingUtilities.invokeLater(() -> {
            RegistrationForm registrationForm = new RegistrationForm();
            registrationForm.setVisible(true);
        });
    }
}
