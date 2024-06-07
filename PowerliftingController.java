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
