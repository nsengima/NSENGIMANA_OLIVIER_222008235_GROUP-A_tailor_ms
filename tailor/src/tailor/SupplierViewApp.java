package tailor;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SupplierViewApp extends JFrame {

    private static final long serialVersionUID = 1L;
    private JTextArea supplierInfoArea;

    public SupplierViewApp() {
        setTitle("Supplier View App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(1, 1));

        JButton displayButton = new JButton("Display Supplier Information");
        displayButton.addActionListener(e -> displaySupplierInformation());
        add(displayButton);

        supplierInfoArea = new JTextArea();
        supplierInfoArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(supplierInfoArea);
        add(scrollPane);

        pack();
        setLocationRelativeTo(null);
    }

    private void displaySupplierInformation() {
        try (Connection connection = DatabaseConnection.connect()) {
            String query = "SELECT * FROM supplier";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query);
                 ResultSet resultSet = preparedStatement.executeQuery()) {
                StringBuilder result = new StringBuilder();
                while (resultSet.next()) {
                    int supplierID = resultSet.getInt("supplier_id");
                    String supplierName = resultSet.getString("supplier_name");
                    int contactInformation = resultSet.getInt("contact_information");

                    result.append("Supplier ID: ").append(supplierID).append("\n");
                    result.append("Supplier Name: ").append(supplierName).append("\n");
                    result.append("Contact Information: ").append(contactInformation).append("\n");
                    result.append("----------------------------\n");
                }
                showMessage(result.toString(), "Supplier Information", JOptionPane.INFORMATION_MESSAGE);
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
            SupplierViewApp app = new SupplierViewApp();
            app.setVisible(true);
        });
    }
}