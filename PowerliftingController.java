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
}
    public void closeAction() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    public void loginAction() {
        // Placeholder for login action logic
        messageLabel.setText("Login action performed");
        messageLabel.setVisible(true);
    }

    public void createUserAction() {
        // Placeholder for creating new user logic
        messageLabel.setText("User created successfully");
        messageLabel.setVisible(true);
    }

    public void logoutAction() {
        // Placeholder for logout action logic
        loginRegisterScreen.setVisible(true);
        messageLabel.setText("Logged out successfully");
        messageLabel.setVisible(true);
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

    public void backButtonAction() {
        // Placeholder for back button action logic
        messageLabel.setText("Navigated back");
        messageLabel.setVisible(true);
    }
}