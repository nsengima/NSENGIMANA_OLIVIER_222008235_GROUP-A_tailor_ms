package tailor;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class InventoryItemViewApp extends JFrame {

    private static final long serialVersionUID = 1L;
    private JTextArea inventoryItemInfoArea;

    public InventoryItemViewApp() {
        setTitle("Inventory Item View App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(1, 1));

        JButton displayButton = new JButton("Display Inventory Item Information");
        displayButton.addActionListener(e -> displayInventoryItemInformation());
        add(displayButton);

        inventoryItemInfoArea = new JTextArea();
        inventoryItemInfoArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(inventoryItemInfoArea);
        add(scrollPane);

        pack();
        setLocationRelativeTo(null);
    }

    private void displayInventoryItemInformation() {
        try (Connection connection = DatabaseConnection.connect()) {
            String query = "SELECT * FROM inventory_item";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query);
                 ResultSet resultSet = preparedStatement.executeQuery()) {
                StringBuilder result = new StringBuilder();
                while (resultSet.next()) {
                    int itemID = resultSet.getInt("item_id");
                    String itemName = resultSet.getString("item_name");
                    String description = resultSet.getString("description");
                    int quantityInStock = resultSet.getInt("quantity_in_stock");
                    int supplierID = resultSet.getInt("supplier_id");
                    int purchasingPrice = resultSet.getInt("purchasing_price");
                    int sellingPrice = resultSet.getInt("selling_price");

                    result.append("Item ID: ").append(itemID).append("\n");
                    result.append("Item Name: ").append(itemName).append("\n");
                    result.append("Description: ").append(description).append("\n");
                    result.append("Quantity in Stock: ").append(quantityInStock).append("\n");
                    result.append("Supplier ID: ").append(supplierID).append("\n");
                    result.append("Purchasing Price: ").append(purchasingPrice).append("\n");
                    result.append("Selling Price: ").append(sellingPrice).append("\n");
                    result.append("----------------------------\n");
                }
                showMessage(result.toString(), "Inventory Item Information", JOptionPane.INFORMATION_MESSAGE);
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
            InventoryItemViewApp app = new InventoryItemViewApp();
            app.setVisible(true);
        });
    }
}
