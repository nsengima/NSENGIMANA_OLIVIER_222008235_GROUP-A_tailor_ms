package tailor;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FabricViewApp extends JFrame {

    private static final long serialVersionUID = 1L;
    private JTextArea fabricInfoArea;

    public FabricViewApp() {
        setTitle("Fabric View App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(1, 1));

        JButton displayButton = new JButton("Display Fabric Information");
        displayButton.addActionListener(e -> displayFabricInformation());
        add(displayButton);

        fabricInfoArea = new JTextArea();
        fabricInfoArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(fabricInfoArea);
        add(scrollPane);

        pack();
        setLocationRelativeTo(null);
    }

    private void displayFabricInformation() {
        try (Connection connection = DatabaseConnection.connect()) {
            String query = "SELECT * FROM fabric";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query);
                 ResultSet resultSet = preparedStatement.executeQuery()) {
                StringBuilder result = new StringBuilder();
                while (resultSet.next()) {
                    int fabricID = resultSet.getInt("fabric_id");
                    String fabricType = resultSet.getString("fabric_type");
                    String fabricColor = resultSet.getString("fabric_color");
                    String fabricDesign = resultSet.getString("fabric_design");
                    int pricePerMeter = resultSet.getInt("price_per_meter");

                    result.append("Fabric ID: ").append(fabricID).append("\n");
                    result.append("Fabric Type: ").append(fabricType).append("\n");
                    result.append("Fabric Color: ").append(fabricColor).append("\n");
                    result.append("Fabric Design: ").append(fabricDesign).append("\n");
                    result.append("Price Per Meter: ").append(pricePerMeter).append("\n");
                    result.append("----------------------------\n");
                }
                showMessage(result.toString(), "Fabric Information", JOptionPane.INFORMATION_MESSAGE);
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
            FabricViewApp app = new FabricViewApp();
            app.setVisible(true);
        });
    }
}
