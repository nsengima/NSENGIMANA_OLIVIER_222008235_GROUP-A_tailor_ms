package tailor;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AppointmentApp extends JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField customerIDField, appointmentDateField, purposeField, supplierIDField, statusField;
    private JButton saveButton, displayButton, editButton;

    public AppointmentApp() {
        setTitle("Appointment Information App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(7, 2));

        add(new JLabel("Customer ID:"));
        customerIDField = new JTextField();
        add(customerIDField);

        add(new JLabel("Appointment Date:"));
        appointmentDateField = new JTextField();
        add(appointmentDateField);

        add(new JLabel("Purpose:"));
        purposeField = new JTextField();
        add(purposeField);

        add(new JLabel("Supplier ID:"));
        supplierIDField = new JTextField();
        add(supplierIDField);

        add(new JLabel("Status:"));
        statusField = new JTextField();
        add(statusField);

        saveButton = new JButton("Save Appointment");
        saveButton.addActionListener(e -> saveAppointment());
        add(saveButton);

        displayButton = new JButton("Display Appointments");
        displayButton.addActionListener(e -> displayAppointments());
        add(displayButton);

        editButton = new JButton("Edit Appointment");
        editButton.addActionListener(e -> editAppointment());
        add(editButton);

        pack();
        setLocationRelativeTo(null);
    }

    private void saveAppointment() {
    	String customerID = customerIDField.getText();
        String appointmentDate = appointmentDateField.getText();
        String purpose = purposeField.getText();
        String supplierID = supplierIDField.getText();
        String status = statusField.getText();

        try (Connection connection = DatabaseConnection.connect()) {
            String query = "INSERT INTO appointment (customer_id, appointment_date, purpose, supplier_id, status) " +
                    "VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setString(1, customerID);
                preparedStatement.setString(2, appointmentDate);
                preparedStatement.setString(3, purpose);
                preparedStatement.setString(4, supplierID);
                preparedStatement.setString(5, status);

                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        int appointmentID = generatedKeys.getInt(1);
                        showMessage("Appointment saved successfully! Appointment ID: " + appointmentID);
                        clearFields();
                    }
                } else {
                    showMessage("Failed to save appointment.");
                }
            }
        } catch (SQLException ex) {
            handleDatabaseError(ex);
        }
    }

    private void displayAppointments() {
    	try (Connection connection = DatabaseConnection.connect()) {
            String query = "SELECT * FROM appointment";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query);
                 ResultSet resultSet = preparedStatement.executeQuery()) {
                StringBuilder result = new StringBuilder();
                while (resultSet.next()) {
                    int appointmentID = resultSet.getInt("appointment_id");
                    String customerID = resultSet.getString("customer_id");
                    String appointmentDate = resultSet.getString("appointment_date");
                    String purpose = resultSet.getString("purpose");
                    String supplierID = resultSet.getString("supplier_id");
                    String status = resultSet.getString("status");

                    result.append("Appointment ID: ").append(appointmentID).append("\n");
                    result.append("Customer ID: ").append(customerID).append("\n");
                    result.append("Appointment Date: ").append(appointmentDate).append("\n");
                    result.append("Purpose: ").append(purpose).append("\n");
                    result.append("Supplier ID: ").append(supplierID).append("\n");
                    result.append("Status: ").append(status).append("\n");
                    result.append("----------------------------\n");
                }
                showMessage(result.toString(), "Appointment Information", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException ex) {
            handleDatabaseError(ex);
        }
    }

    private void clearFields() {
        customerIDField.setText("");
        appointmentDateField.setText("");
        purposeField.setText("");
        supplierIDField.setText("");
        statusField.setText("");
    }
    
    private void editAppointment() {
        String appointmentIDToEdit = JOptionPane.showInputDialog(
                this,
                "Enter the Appointment ID to edit:",
                "Edit Appointment",
                JOptionPane.QUESTION_MESSAGE
        );

        if (appointmentIDToEdit != null && !appointmentIDToEdit.isEmpty()) {
            try (Connection connection = DatabaseConnection.connect()) {
                String query = "SELECT * FROM appointment WHERE appointment_id = ?";
                try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                    preparedStatement.setString(1, appointmentIDToEdit);

                    try (ResultSet resultSet = preparedStatement.executeQuery()) {
                        if (resultSet.next()) {
                            // Appointment found, proceed with editing
                            JTextField customerIDField = new JTextField(resultSet.getString("customer_id"));
                            JTextField appointmentDateField = new JTextField(resultSet.getString("appointment_date"));
                            JTextField purposeField = new JTextField(resultSet.getString("purpose"));
                            JTextField supplierIDField = new JTextField(resultSet.getString("supplier_id"));
                            JTextField statusField = new JTextField(resultSet.getString("status"));

                            Object[] fields = {
                                    "Customer ID:", customerIDField,
                                    "Appointment Date:", appointmentDateField,
                                    "Purpose:", purposeField,
                                    "Supplier ID:", supplierIDField,
                                    "Status:", statusField
                            };

                            int editResult = JOptionPane.showConfirmDialog(
                                    this,
                                    fields,
                                    "Edit Appointment",
                                    JOptionPane.OK_CANCEL_OPTION,
                                    JOptionPane.PLAIN_MESSAGE
                            );

                            if (editResult == JOptionPane.OK_OPTION) {
                                // User clicked "Done," update the appointment in the database
                                updateAppointmentInDatabase(
                                        appointmentIDToEdit,
                                        customerIDField.getText(),
                                        appointmentDateField.getText(),
                                        purposeField.getText(),
                                        supplierIDField.getText(),
                                        statusField.getText()
                                );
                            }
                        } else {
                            showMessage("Appointment with ID " + appointmentIDToEdit + " not found.", "Not Found", JOptionPane.INFORMATION_MESSAGE);
                        }
                    }
                }
            } catch (SQLException ex) {
                handleDatabaseError(ex);
            }
        }
    }

    private void updateAppointmentInDatabase(String appointmentID, String updatedCustomerID, String updatedAppointmentDate,
                                             String updatedPurpose, String updatedSupplierID, String updatedStatus) {
        try (Connection connection = DatabaseConnection.connect()) {
            String updateQuery = "UPDATE appointment SET customer_id = ?, appointment_date = ?, " +
                    "purpose = ?, supplier_id = ?, status = ? WHERE appointment_id = ?";
            try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
                updateStatement.setString(1, updatedCustomerID);
                updateStatement.setString(2, updatedAppointmentDate);
                updateStatement.setString(3, updatedPurpose);
                updateStatement.setString(4, updatedSupplierID);
                updateStatement.setString(5, updatedStatus);
                updateStatement.setString(6, appointmentID);

                int rowsAffected = updateStatement.executeUpdate();

                if (rowsAffected > 0) {
                    showMessage("Appointment updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    displayAppointments();  // Refresh the displayed appointments after update
                } else {
                    showMessage("Failed to update appointment.", "Error", JOptionPane.ERROR_MESSAGE);
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
            AppointmentApp app = new AppointmentApp();
            app.setVisible(true);
        });
    }
}
