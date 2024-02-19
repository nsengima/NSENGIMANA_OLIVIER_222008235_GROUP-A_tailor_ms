package tailor;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerSummaryApp extends JFrame {

    private static final long serialVersionUID = 1L;

    public CustomerSummaryApp() {
        setTitle("Customer Summary App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(1, 1));

        JButton displayButton = new JButton("Display Customer Summary");
        displayButton.addActionListener(e -> displayCustomerSummary());
        add(displayButton);

        pack();
        setLocationRelativeTo(null);
    }

    private void displayCustomerSummary() {
        try (Connection connection = DatabaseConnection.connect()) {
            String query = "SELECT * FROM customer";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query);
                 ResultSet resultSet = preparedStatement.executeQuery()) {
                StringBuilder result = new StringBuilder();
                while (resultSet.next()) {
                    int customerID = resultSet.getInt("customer_id");
                    String names = resultSet.getString("names");
                    String contact = resultSet.getString("contact");
                    String orderHistory = resultSet.getString("order_history");
                    String fabricChoice = resultSet.getString("preferred_fabric_choice");
                    String paymentInfo = resultSet.getString("payment_information");

                    result.append("Customer ID: ").append(customerID).append("\n");
                    result.append("Names: ").append(names).append("\n");
                    result.append("Contact: ").append(contact).append("\n");
                    result.append("Order History: ").append(orderHistory).append("\n");
                    result.append("Preferred Fabric Choice: ").append(fabricChoice).append("\n");
                    result.append("Payment Information: ").append(paymentInfo).append("\n");
                    result.append("----------------------------\n");
                }
                showMessage(result.toString(), "Customer Summary", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException ex) {
            handleDatabaseError(ex);
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
            CustomerSummaryApp app = new CustomerSummaryApp();
            app.setVisible(true);
        });
    }
}
