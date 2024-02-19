package tailor;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BillingApp extends JFrame {

    private static final long serialVersionUID = 1L;
    private JTextField orderIDField, issuedDateField, totalAmountField, paymentStatusField;

    public BillingApp() {
        setTitle("Billing Information App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(5, 2));

        add(new JLabel("Order ID:"));
        orderIDField = new JTextField();
        add(orderIDField);

        add(new JLabel("Issued Date:"));
        issuedDateField = new JTextField();
        add(issuedDateField);

        add(new JLabel("Total Amount:"));
        totalAmountField = new JTextField();
        add(totalAmountField);

        add(new JLabel("Payment Status:"));
        paymentStatusField = new JTextField();
        add(paymentStatusField);

        JButton saveButton = new JButton("Save Billing");
        saveButton.addActionListener(e -> saveBilling());
        add(saveButton);

        JButton displayButton = new JButton("Display Billings");
        displayButton.addActionListener(e -> displayBillings());
        add(displayButton);

        pack();
        setLocationRelativeTo(null);
    }

    private void saveBilling() {
        String orderID = orderIDField.getText();
        String issuedDate = issuedDateField.getText();
        String totalAmount = totalAmountField.getText();
        String paymentStatus = paymentStatusField.getText();

        try (Connection connection = DatabaseConnection.connect()) {
            String query = "INSERT INTO billing_and_invoice (order_id, invoice_issued_date, total_amount, payment_status) " +
                    "VALUES (?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setString(1, orderID);
                preparedStatement.setString(2, issuedDate);
                preparedStatement.setString(3, totalAmount);
                preparedStatement.setString(4, paymentStatus);

                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        int invoiceID = generatedKeys.getInt(1);
                        showMessage("Billing saved successfully! Invoice ID: " + invoiceID);
                        clearFields();
                    }
                } else {
                    showMessage("Failed to save billing.");
                }
            }
        } catch (SQLException ex) {
            handleDatabaseError(ex);
        }
    }

    private void displayBillings() {
        try (Connection connection = DatabaseConnection.connect()) {
            String query = "SELECT * FROM billing_and_invoice";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query);
                 ResultSet resultSet = preparedStatement.executeQuery()) {
                StringBuilder result = new StringBuilder();
                while (resultSet.next()) {
                    int invoiceID = resultSet.getInt("invoice_id");
                    String orderID = resultSet.getString("order_id");
                    String issuedDate = resultSet.getString("invoice_issued_date");
                    String totalAmount = resultSet.getString("total_amount");
                    String paymentStatus = resultSet.getString("payment_status");

                    result.append("Invoice ID: ").append(invoiceID).append("\n");
                    result.append("Order ID: ").append(orderID).append("\n");
                    result.append("Issued Date: ").append(issuedDate).append("\n");
                    result.append("Total Amount: ").append(totalAmount).append("\n");
                    result.append("Payment Status: ").append(paymentStatus).append("\n");
                    result.append("----------------------------\n");
                }
                showMessage(result.toString(), "Billing Information", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException ex) {
            handleDatabaseError(ex);
        }
    }

    private void clearFields() {
        orderIDField.setText("");
        issuedDateField.setText("");
        totalAmountField.setText("");
        paymentStatusField.setText("");
    }

    private void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
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
            BillingApp app = new BillingApp();
            app.setVisible(true);
        });
    }
}