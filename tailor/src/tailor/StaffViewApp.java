package tailor;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StaffViewApp extends JFrame {

    private static final long serialVersionUID = 1L;
    private JTextArea staffInfoArea;

    public StaffViewApp() {
        setTitle("Staff View App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(1, 1));

        JButton displayButton = new JButton("Display Staff Information");
        displayButton.addActionListener(e -> displayStaffInformation());
        add(displayButton);

        staffInfoArea = new JTextArea();
        staffInfoArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(staffInfoArea);
        add(scrollPane);

        pack();
        setLocationRelativeTo(null);
    }

    private void displayStaffInformation() {
        try (Connection connection = DatabaseConnection.connect()) {
            String query = "SELECT * FROM staff";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query);
                 ResultSet resultSet = preparedStatement.executeQuery()) {
                StringBuilder result = new StringBuilder();
                while (resultSet.next()) {
                    int staffID = resultSet.getInt("staff_id");
                    String firstName = resultSet.getString("first_name");
                    String lastName = resultSet.getString("last_name");
                    String role = resultSet.getString("role");
                    int contactInformation = resultSet.getInt("contact_information");

                    result.append("Staff ID: ").append(staffID).append("\n");
                    result.append("First Name: ").append(firstName).append("\n");
                    result.append("Last Name: ").append(lastName).append("\n");
                    result.append("Role: ").append(role).append("\n");
                    result.append("Contact Information: ").append(contactInformation).append("\n");
                    result.append("----------------------------\n");
                }
                showMessage(result.toString(), "Staff Information", JOptionPane.INFORMATION_MESSAGE);
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
            StaffViewApp app = new StaffViewApp();
            app.setVisible(true);
        });
    }
}
