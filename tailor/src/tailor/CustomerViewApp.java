package tailor;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerViewApp extends JFrame {

    private static final long serialVersionUID = 1L;
    private JTextField customerIDField;
    private JTextArea customerInfoArea;

    public CustomerViewApp() {
        setTitle("Customer View App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(3, 2));

        add(new JLabel("Customer ID:"));
        customerIDField = new JTextField();
        add(customerIDField);

        JButton displayButton = new JButton("Display Customer Information");
        displayButton.addActionListener(e -> displayCustomerInformation());
        add(displayButton);

        customerInfoArea = new JTextArea();
        customerInfoArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(customerInfoArea);
        add(scrollPane);

        pack();
        setLocationRelativeTo(null);
    }

    private void displayCustomerInformation() {
        String customerID = customerIDField.getText().trim();
        if (customerID.isEmpty()) {
            showMessage("Please enter a Customer ID.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (Connection connection = DatabaseConnection.connect()) {
            String query = "SELECT * FROM customer WHERE customer_id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, customerID);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        int customerId = resultSet.getInt("customer_id");
                        String names = resultSet.getString("names");
                        String contact = resultSet.getString("contact");
                        String orderHistory = resultSet.getString("order_history");
                        String fabricChoice = resultSet.getString("preferred_fabric_choice");
                        String paymentInfo = resultSet.getString("payment_information");

                        StringBuilder result = new StringBuilder();
                        result.append("Customer ID: ").append(customerId).append("\n");
                        result.append("Names: ").append(names).append("\n");
                        result.append("Contact: ").append(contact).append("\n");
                        result.append("Order History: ").append(orderHistory).append("\n");
                        result.append("Preferred Fabric Choice: ").append(fabricChoice).append("\n");
                        result.append("Payment Information: ").append(paymentInfo).append("\n");

                        customerInfoArea.setText(result.toString());
                    } else {
                        showMessage("Customer not found.", "Not Found", JOptionPane.INFORMATION_MESSAGE);
                        customerInfoArea.setText("");
                    }
                }
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
            CustomerViewApp app = new CustomerViewApp();
            app.setVisible(true);
        });
    }
}
