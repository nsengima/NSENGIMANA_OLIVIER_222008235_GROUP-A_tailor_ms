package tailor;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderViewApp extends JFrame {

    private static final long serialVersionUID = 1L;
    private JTextArea orderInfoArea;

    public OrderViewApp() {
        setTitle("Order View App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(1, 1));

        JButton displayButton = new JButton("Display Order Information");
        displayButton.addActionListener(e -> displayOrderInformation());
        add(displayButton);

        orderInfoArea = new JTextArea();
        orderInfoArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(orderInfoArea);
        add(scrollPane);

        pack();
        setLocationRelativeTo(null);
    }

    private void displayOrderInformation() {
        try (Connection connection = DatabaseConnection.connect()) {
            String query = "SELECT * FROM order1";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query);
                 ResultSet resultSet = preparedStatement.executeQuery()) {
                StringBuilder result = new StringBuilder();
                while (resultSet.next()) {
                    int orderID = resultSet.getInt("order_id");
                    int customerID = resultSet.getInt("customer_id");
                    String orderDate = resultSet.getString("order_date");
                    String deliveryDate = resultSet.getString("delivery_date");
                    String status = resultSet.getString("status");
                    int totalAmount = resultSet.getInt("total_amount");
                    int fabricID = resultSet.getInt("fabric_id");

                    result.append("Order ID: ").append(orderID).append("\n");
                    result.append("Customer ID: ").append(customerID).append("\n");
                    result.append("Order Date: ").append(orderDate).append("\n");
                    result.append("Delivery Date: ").append(deliveryDate).append("\n");
                    result.append("Status: ").append(status).append("\n");
                    result.append("Total Amount: ").append(totalAmount).append("\n");
                    result.append("Fabric ID: ").append(fabricID).append("\n");
                    result.append("----------------------------\n");
                }
                showMessage(result.toString(), "Order Information", JOptionPane.INFORMATION_MESSAGE);
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
            OrderViewApp app = new OrderViewApp();
            app.setVisible(true);
        });
    }
}
