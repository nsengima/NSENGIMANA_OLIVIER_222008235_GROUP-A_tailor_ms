package tailor;

import javax.swing.*;

import java.awt.GridLayout;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Login extends JFrame {
 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
private JTextField usernameField;
 private JPasswordField passwordField;

 public Login() {
     setTitle("Login");
     setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
     setLayout(new GridLayout(4, 2, 10, 10));

     add(new JLabel("Username:"));
     usernameField = new JTextField();
     add(usernameField);

     add(new JLabel("Password:"));
     passwordField = new JPasswordField();
     add(passwordField);

     JButton loginButton = new JButton("Login");
     loginButton.addActionListener(e -> login());
     add(loginButton);

     pack();
     setLocationRelativeTo(null);
 }

 private void login() {
     String username = usernameField.getText();
     String password = new String(passwordField.getPassword());
     
     if(username.trim().isEmpty() || password.trim().isEmpty()) {
    	 showMessage("Username or Password can not be empty!");
    	 return;
     }

     boolean isValidUser = validateUser(username, password);

     if (isValidUser) {
    	 SwingUtilities.invokeLater(()->{
    		 Main main = new Main();
    		 main.setVisible(true);
    	 });
     } else {
         showMessage("Incorrect credentials. Please try again.");
     }
 }

 private void showMessage(String message) {
     JOptionPane.showMessageDialog(this, message);
 }

 public static void main(String[] args) {
     SwingUtilities.invokeLater(() -> {
         Login login = new Login();
         login.setVisible(true);
     });
 }
 

	public boolean validateUser(String username, String password) {
	  String query = "SELECT * FROM users WHERE username = ? AND password = ?";
	  try (Connection connection = DatabaseConnection.connect();
	       PreparedStatement preparedStatement = connection.prepareStatement(query)) {
	
	      preparedStatement.setString(1, username);
	      preparedStatement.setString(2, password);
	
	      try (ResultSet resultSet = preparedStatement.executeQuery()) {
	          return resultSet.next();
	      }
	
	  } catch (SQLException ex) {
	      ex.printStackTrace();
	      showMessage("Error occured: " + ex.getMessage());
	      return false;
	  }
	}


}
