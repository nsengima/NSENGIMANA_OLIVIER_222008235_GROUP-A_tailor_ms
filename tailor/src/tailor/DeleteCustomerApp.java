package tailor;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteCustomerApp extends JFrame {

    private static final long serialVersionUID = 1L;
    private JTextField customerIDField;

    public DeleteCustomerApp() {
        setTitle("Delete Customer App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(2, 2));

        add(new JLabel("Customer ID:"));
        customerIDField = new JTextField();
        add(customerIDField);

        JButton deleteButton = new JButton("Delete Customer");
        deleteButton.addActionListener(e -> deleteCustomer());
        add(deleteButton);

        pack();
        setLocationRelativeTo(null);
    }

    private void deleteCustomer() {
        String customerID = customerIDField.getText().trim();
        if (customerID.isEmpty()) {
            showMessage("Please enter a Customer ID.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirmResult = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to delete the customer?",
                "Confirmation",
                JOptionPane.YES_NO_OPTION
        );
        
        if (confirmResult == JOptionPane.YES_OPTION) {
            try (Connection connection = DatabaseConnection.connect()) {
                // Delete from appointment
                String appointmentQuery = "DELETE FROM appointment WHERE customer_id = ?";
                try (PreparedStatement appointmentStatement = connection.prepareStatement(appointmentQuery)) {
                    appointmentStatement.setString(1, customerID);
                    appointmentStatement.executeUpdate();
                }

                // Delete from order1
                String orderQuery = "DELETE FROM order1 WHERE customer_id = ?";
                try (PreparedStatement orderStatement = connection.prepareStatement(orderQuery)) {
                    orderStatement.setString(1, customerID);
                    orderStatement.executeUpdate();
                }

                // Delete from customer
                String customerQuery = "DELETE FROM customer WHERE customer_id = ?";
                try (PreparedStatement customerStatement = connection.prepareStatement(customerQuery)) {
                    customerStatement.setString(1, customerID);
                    int rowsAffected = customerStatement.executeUpdate();

                    if (rowsAffected > 0) {
                        showMessage("Customer and related records deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                        customerIDField.setText("");
                    } else {
                        showMessage("Customer not found.", "Not Found", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            } catch (SQLException ex) {
                handleDatabaseError(ex);
            }
        }

    }

    private void showMessage(String message, String title, int messageType) {
        JOptionPane.showMessageDialog(this, message, title, messageType);
    }

    private void handleDatabaseError(SQLException ex) {
        ex.printStackTrace();
        showMessage("Database Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            DeleteCustomerApp app = new DeleteCustomerApp();
            app.setVisible(true);
        });
    }
}
