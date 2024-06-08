import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;



public class PowerliftingController {
    @FXML private Label messageLabel;
    @FXML private AnchorPane loginRegisterScreen;
    @FXML private Button closeButton;
    @FXML private GridPane loginPane;
    @FXML private GridPane registerPane;
    @FXML private TextField username;
    @FXML private PasswordField password;
    @FXML private TextField newUser;
    @FXML private PasswordField newPassword;

    @FXML private GridPane memberSearchPane;

    public void displayRegisterPane() {
        loginPane.setVisible(false);
        newUser.setText("");
        newPassword.setText("");
        messageLabel.setText("");
        registerPane.setVisible(true);
    }

    public void displayLoginPane() {
        loginRegisterScreen.setVisible(true);
        registerPane.setVisible(false);
        loginPane.setVisible(true);
        username.setText("");
        password.setText("");
        messageLabel.setText("");
    }

    public void closeAction() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    public void showMessage(Label label, String message, Color color) {
        label.setText(message);
        label.setTextFill(color);
        label.setVisible(true);
    }


    public void loginAction() {
        String enteredUsername = username.getText();
        String enteredPassword = password.getText();
        boolean placeholder = false; // Placeholder for if statement conditional logic
        if (enteredUsername.isBlank() || enteredPassword.isBlank()) {
            showMessage(messageLabel, "Please enter a valid username and password.", Color.RED);
        } else {
//            check if creds are valid, dependent on dbDriver
//            if valid, display member search screen and set login screen to invis
            if (placeholder == true) {
//                loginUser = enteredUsername;
//                loginRegisterScreen.setVisible(false);
                //                Needs to be implemented
//                displayMemberSearch();
            } else {
                showMessage(messageLabel, "Invalid username or password. Please enter a valid combination.", Color.RED);
            }
        }
    }

    public void createUserAction() {
        String enteredUsername = newUser.getText();
        String enteredPassword = newPassword.getText();
//        For testing purposes:
//        System.out.println("Entered Username: " + enteredUsername);
//        System.out.println("Entered Password: " + enteredPassword);
        if (enteredUsername.isBlank() || enteredPassword.isBlank()) {
            showMessage(messageLabel, "Please enter a valid username and password.", Color.RED);
        } else {
//            boolean userAlreadyExists = service.userExists(enteredUsername);
            boolean placeholder_userExists = true;
            if (placeholder_userExists) {
                showMessage(messageLabel, "Username already exists. Please choose another username.", Color.RED);
            } else{
//              Placeholder to create and add user to database, needs to be implemented using dbDriver and service
                boolean placeholder_addUser = false;
                if (placeholder_addUser) {
                    displayLoginPane();
                    showMessage(messageLabel, "Registration successful. Please log in with your new credentials.", Color.GREEN);
                }
            }
        }
    }

    public void logoutAction() {
        memberSearchPane.setVisible(false);
        messageLabel.setText("");
        displayLoginPane();
    }

    public void backButtonAction() {
        loginRegisterScreen.setVisible(false);
//        displayMemberSearch(); (Again, needs to be implemented)
    }

    public void addMemberAction() {
        // Placeholder for adding a new member logic
        messageLabel.setText("Member added successfully");
        messageLabel.setVisible(true);
    }

    public void updateMemberAction() {
        // Placeholder for updating member information logic
        messageLabel.setText("Member updated successfully");
        messageLabel.setVisible(true);
    }

    public void deleteMemberAction() {
        // Placeholder for deleting member logic
        messageLabel.setText("Member deleted successfully");
        messageLabel.setVisible(true);
    }
}