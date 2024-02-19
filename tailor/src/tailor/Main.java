package tailor;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton openCustomerButton;
    private JButton openAppointmentButton;
    private JButton openBillingButton;
    private JButton openCustomerSummaryButton;
    private JButton openCustomerViewButton;
    private JButton openDeleteCustomerButton;
    private JButton openFabricViewButton;
    private JButton openInventoryItemViewButton;
    private JButton openOrderViewButton;
    private JButton openStaffViewButton;
    private JButton openSupplierViewButton;
    private JButton RegistrationFormViewButton;

    public Main() {
        setTitle("Main Application");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(11, 1));

        openCustomerButton = createButton("Open Customer", this::openCustomerApp);
        openAppointmentButton = createButton("Open Appointment", this::openAppointmentApp);
        openBillingButton = createButton("Open Billing", this::openBillingApp);
        openCustomerSummaryButton = createButton("Open Customer Summary", this::openCustomerSummaryApp);
        openCustomerViewButton = createButton("Open Customer View", this::openCustomerViewApp);
        openDeleteCustomerButton = createButton("Delete Customer", this::openDeleteCustomerApp);
        openFabricViewButton = createButton("Open Fabric View", this::openFabricViewApp);
        openInventoryItemViewButton = createButton("Open Inventory Item View", this::openInventoryItemViewApp);
        openOrderViewButton = createButton("Open Order View", this::openOrderViewApp);
        openStaffViewButton = createButton("Open Staff View", this::openStaffViewApp);
        openSupplierViewButton = createButton("Open Supplier View", this::openSupplierViewApp);
        //openRegistrationFormViewButton = createButton("Open RegistrationForm View", this::openRegistrationFormViewApp);

        add(openCustomerButton);
        add(openAppointmentButton);
        add(openBillingButton);
        add(openCustomerSummaryButton);
        add(openCustomerViewButton);
        add(openDeleteCustomerButton);
        add(openFabricViewButton);
        add(openInventoryItemViewButton);
        add(openOrderViewButton);
        add(openStaffViewButton);
        add(openSupplierViewButton);
        //add(openRegistrationForm);

        pack();
        setLocationRelativeTo(null);
    }


    private JButton createButton(String label, ActionListener actionListener) {
        JButton button = new JButton(label);
        button.addActionListener(actionListener);
        return button;
    }

    public void openCustomerApp(ActionEvent actionEvent) {
        openApp(new CustomerApp());
    }

    private void openAppointmentApp(ActionEvent actionEvent) {
        openApp(new AppointmentApp());
    }

    private void openBillingApp(ActionEvent actionEvent) {
    	openApp(new BillingApp());
    }

    private void openCustomerSummaryApp(ActionEvent actionEvent) {
        openApp(new CustomerSummaryApp());
    }

    private void openCustomerViewApp(ActionEvent actionEvent) {
    	openApp(new CustomerSummaryApp());
    }

    private void openDeleteCustomerApp(ActionEvent actionEvent) {
    	openApp(new DeleteCustomerApp());
    }

    private void openFabricViewApp(ActionEvent actionEvent) {
    	openApp(new FabricViewApp());
    }

    private void openInventoryItemViewApp(ActionEvent actionEvent) {
    	openApp(new InventoryItemViewApp());
    }

    private void openOrderViewApp(ActionEvent actionEvent) {
    	openApp(new OrderViewApp());
    }

    private void openStaffViewApp(ActionEvent actionEvent) {
    	openApp(new StaffViewApp());
    }

    private void openSupplierViewApp(ActionEvent actionEvent) {
    	openApp(new SupplierViewApp());
    }

    private void openApp(JFrame app) {
        SwingUtilities.invokeLater(() -> {
            app.setVisible(true);
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Main mainApp = new Main();
            mainApp.setVisible(true);
        });
    }
}
