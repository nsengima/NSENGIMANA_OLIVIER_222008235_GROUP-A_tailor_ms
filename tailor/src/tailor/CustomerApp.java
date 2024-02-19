package tailor;

import javax.swing.*;

import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerApp extends JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField namesField, contactField, orderHistoryField, fabricChoiceField, paymentInfoField;
    private JButton saveButton, displayButton, editButton;

    public CustomerApp() {
        setTitle("Customer Information App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(8, 4));

        addLabelAndField("Names:", namesField = new JTextField());
        addLabelAndField("Contact:", contactField = new JTextField());
        addLabelAndField("Order History:", orderHistoryField = new JTextField());
        addLabelAndField("Preferred Fabric Choice:", fabricChoiceField = new JTextField());
        addLabelAndField("Payment Information:", paymentInfoField = new JTextField());

        addActionButton("Save Customer", this::saveCustomer);
        addActionButton("Display Customers", this::displayCustomers);
        addActionButton("Edit Customer", this::editCustomer);
        addActionButton("Delete Customer", this::deleteCustomer);

        pack();
        setLocationRelativeTo(null);
    }

    private void addLabelAndField(String label, JTextField textField) {
        add(new JLabel(label));
        add(textField);
    }

    private void addActionButton(String label, Runnable action) {
        JButton button = new JButton(label);
        button.addActionListener(e -> action.run());
        add(button);
    }



    private void saveCustomer() {
    	String names = namesField.getText();
        String contact = contactField.getText();
        String orderHistory = orderHistoryField.getText();
        String fabricChoice = fabricChoiceField.getText();
        String paymentInfo = paymentInfoField.getText();

        try (Connection connection = DatabaseConnection.connect()) {
            String query = "INSERT INTO customer (names, contact, order_history, preferred_fabric_choice, payment_information) " +
                    "VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setString(1, names);
                preparedStatement.setString(2, contact);
                preparedStatement.setString(3, orderHistory);
                preparedStatement.setString(4, fabricChoice);
                preparedStatement.setString(5, paymentInfo);

                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        int customerId = generatedKeys.getInt(1);
                        JOptionPane.showMessageDialog(this, "Customer saved successfully! Customer ID: " + customerId);
                        clearFields();
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to save customer.");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void displayCustomers() {
    	try (Connection connection = DatabaseConnection.connect()) {
            String query = "SELECT * FROM customer";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query);
                 ResultSet resultSet = preparedStatement.executeQuery()) {
                StringBuilder result = new StringBuilder();
                while (resultSet.next()) {
                    int customerId = resultSet.getInt("customer_id");
                    String names = resultSet.getString("names");
                    String contact = resultSet.getString("contact");
                    String orderHistory = resultSet.getString("order_history");
                    String fabricChoice = resultSet.getString("preferred_fabric_choice");
                    String paymentInfo = resultSet.getString("payment_information");

                    result.append("Customer ID: ").append(customerId).append("\n");
                    result.append("Names: ").append(names).append("\n");
                    result.append("Contact: ").append(contact).append("\n");
                    result.append("Order History: ").append(orderHistory).append("\n");
                    result.append("Preferred Fabric Choice: ").append(fabricChoice).append("\n");
                    result.append("Payment Information: ").append(paymentInfo).append("\n");
                    result.append("----------------------------\n");
                }
                JOptionPane.showMessageDialog(this, result.toString(), "Customer Information", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }
    
    private void clearFields() {
        namesField.setText("");
        contactField.setText("");
        orderHistoryField.setText("");
        fabricChoiceField.setText("");
        paymentInfoField.setText("");
    }

    private void editCustomer() {
        String customerIDToEdit = JOptionPane.showInputDialog(
                this,
                "Enter the Customer ID to edit:",
                "Edit Customer",
                JOptionPane.QUESTION_MESSAGE
        );

        if (customerIDToEdit != null && !customerIDToEdit.isEmpty()) {
            try (Connection connection = DatabaseConnection.connect()) {
                String query = "SELECT * FROM customer WHERE customer_id = ?";
                try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                    preparedStatement.setString(1, customerIDToEdit);

                    try (ResultSet resultSet = preparedStatement.executeQuery()) {
                        if (resultSet.next()) {
                            // Customer found, proceed with editing
                            JTextField namesField = new JTextField(resultSet.getString("names"));
                            JTextField contactField = new JTextField(resultSet.getString("contact"));
                            JTextField orderHistoryField = new JTextField(resultSet.getString("order_history"));
                            JTextField fabricChoiceField = new JTextField(resultSet.getString("preferred_fabric_choice"));
                            JTextField paymentInfoField = new JTextField(resultSet.getString("payment_information"));

                            Object[] fields = {
                                    "Names:", namesField,
                                    "Contact:", contactField,
                                    "Order History:", orderHistoryField,
                                    "Preferred Fabric Choice:", fabricChoiceField,
                                    "Payment Information:", paymentInfoField
                            };

                            int editResult = JOptionPane.showConfirmDialog(
                                    this,
                                    fields,
                                    "Edit Customer",
                                    JOptionPane.OK_CANCEL_OPTION,
                                    JOptionPane.PLAIN_MESSAGE
                            );

                            if (editResult == JOptionPane.OK_OPTION) {
                                // User clicked "Done," update the customer in the database
                                updateCustomerInDatabase(
                                        customerIDToEdit,
                                        namesField.getText(),
                                        contactField.getText(),
                                        orderHistoryField.getText(),
                                        fabricChoiceField.getText(),
                                        paymentInfoField.getText()
                                );
                            }
                        } else {
                            showMessage("Customer with ID " + customerIDToEdit + " not found.", "Not Found", JOptionPane.INFORMATION_MESSAGE);
                        }
                    }
                }
            } catch (SQLException ex) {
                handleDatabaseError(ex);
            }
        }
    }
    
    
    private void deleteCustomer() {
        String customerID = JOptionPane.showInputDialog(this, "Enter Customer ID to delete:");

        if (customerID != null && !customerID.isEmpty()) {
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
                            clearFields();
                        } else {
                            showMessage("Customer not found.", "Not Found", JOptionPane.INFORMATION_MESSAGE);
                        }
                    }
                } catch (SQLException ex) {
                    handleDatabaseError(ex);
                }
            }
        }
    }
    

    private void updateCustomerInDatabase(String customerID, String updatedNames, String updatedContact,
                                          String updatedOrderHistory, String updatedFabricChoice, String updatedPaymentInfo) {
        try (Connection connection = DatabaseConnection.connect()) {
            String updateQuery = "UPDATE customer SET names = ?, contact = ?, order_history = ?, " +
                    "preferred_fabric_choice = ?, payment_information = ? WHERE customer_id = ?";
            try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
                updateStatement.setString(1, updatedNames);
                updateStatement.setString(2, updatedContact);
                updateStatement.setString(3, updatedOrderHistory);
                updateStatement.setString(4, updatedFabricChoice);
                updateStatement.setString(5, updatedPaymentInfo);
                updateStatement.setString(6, customerID);

                int rowsAffected = updateStatement.executeUpdate();

                if (rowsAffected > 0) {
                    showMessage("Customer updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    displayCustomers();  // Refresh the displayed customers after update
                } else {
                    showMessage("Failed to update customer.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (SQLException ex) {
            handleDatabaseError(ex);
        }
    }

    private void showMessage(String message, String title, int messageType) {
        JOptionPane.showMessageDialog(this, message, title, messageType);
    }

    private void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    private void handleDatabaseError(SQLException ex) {
        ex.printStackTrace();
        showMessage("Database Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CustomerApp app = new CustomerApp();
            app.setVisible(true);
        });
    }
}
