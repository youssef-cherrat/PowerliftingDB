package org.powerlifting;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;


public class PowerliftingController implements Initializable {
    PowerliftingService service;

    @FXML private Label messageLabel;
    @FXML private AnchorPane loginRegisterScreen;
    @FXML private Button closeButton;
    @FXML private GridPane loginPane;
    @FXML private GridPane changePasswordPane;
    @FXML private TextField email;
    @FXML private PasswordField password;
    @FXML private TextField verifyEmail;
    @FXML private PasswordField oldPassword;
    @FXML private PasswordField newPassword;
    @FXML private PasswordField confirmNewPassword;
    @FXML private Button changePasswordButton;
    @FXML private Label addMemberMessageLabel;

    @FXML private AnchorPane memberSearchPane;
    @FXML private Button searchMemberButton;
    @FXML private Button logoutButton;
    @FXML private Button addMemberButton;
    @FXML private Label searchMessageLabel;
    @FXML private TextField searchFirstName;
    @FXML private TextField searchLastName;
    @FXML private TextField searchGender;
    @FXML private TextField searchEmail;
    @FXML private TextField searchWeight;
    @FXML private TextField searchResult;
    @FXML private TableView<Member> memberRosterTable;
    @FXML private TableColumn<Member, String> memberFirstNameColumn;
    @FXML private TableColumn<Member, String> memberLastNameColumn;
    @FXML private TableColumn<Member, String> memberGenderColumn;
    @FXML private TableColumn<Member, String> memberEmailColumn;
    @FXML private TableColumn<Member, Float>  memberWeightClassColumn;
    @FXML private TableColumn<Member, Float>  memberBestResultColumn;
    @FXML private TableColumn<Member, Integer> memberAttendanceColumn;

    @FXML private AnchorPane addMemberPane;
    @FXML private Button backButton1;

    @FXML private TextField firstNameField;
    @FXML private TextField lastNameField;
    @FXML private TextField genderField;
    @FXML private TextField dobField;
    @FXML private TextField gradDateField;
    @FXML private TextField weightClassField;
    @FXML private TextField bestResultField;
    @FXML private TextField attendanceField;
    @FXML private TextField emailField;

    @FXML private AnchorPane alumniPane;
    @FXML private Button viewAlumniButton;
    @FXML private Button backFromAlumniButton;


    private String loginEmail;

    public void setService(PowerliftingService service) {
        this.service = service;
    }

    public void displayChangePasswordPane() {
        loginPane.setVisible(false);
        verifyEmail.setText("");
        oldPassword.setText("");
        newPassword.setText("");
        confirmNewPassword.setText("");
        messageLabel.setText("");
        changePasswordPane.setVisible(true);
    }

    public void displayLoginPane() {
        loginRegisterScreen.setVisible(true);
        changePasswordPane.setVisible(false);
        loginPane.setVisible(true);
        email.setText("");
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

    public static String hashString(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(input.getBytes("UTF-8"));
            return bytesToHex(hashBytes);
        } catch (NoSuchAlgorithmException | java.io.UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    public void loginAction() {
        String enteredEmail = email.getText();
        String enteredPassword = password.getText();
        if (enteredEmail.isBlank() || enteredPassword.isBlank()) {
            showMessage(messageLabel, "Please enter a valid username and password.", Color.RED);
        } else {
//            check if creds are valid, dependent on dbDriver
//            if valid, display member search screen and set login screen to invis
            //hash entered password
            String hashed_password = hashString(enteredPassword);
            boolean validCreds = service.checkCredentials(enteredEmail, hashed_password);
            if (validCreds) {
                loginEmail = enteredEmail;
                loginRegisterScreen.setVisible(false);
                displayMemberSearch();
            } else {
                showMessage(messageLabel, "Invalid username or password. Please enter a valid combination.", Color.RED);
            }
        }
    }

//    public void createUserAction() {
//        String enteredEmail = verifyEmail.getText();
//        String enteredPassword = newPassword.getText();
////        For testing purposes:
////        System.out.println("Entered Username: " + enteredUsername);
////        System.out.println("Entered Password: " + enteredPassword);
//        if (enteredEmail.isBlank() || enteredPassword.isBlank()) {
//            showMessage(messageLabel, "Please enter a valid username and password.", Color.RED);
//        } else {
////            boolean userAlreadyExists = service.userExists(enteredUsername);
//            boolean placeholder_userExists = true;
//            if (placeholder_userExists) {
//                showMessage(messageLabel, "Username already exists. Please choose another username.", Color.RED);
//            } else {
////              Placeholder to create and add user to database, needs to be implemented using dbDriver and service
//                boolean placeholder_addUser = false;
//                if (placeholder_addUser) {
//                    displayLoginPane();
//                    showMessage(messageLabel, "Registration successful. Please log in with your new credentials.", Color.GREEN);
//                }
//            }
//        }
//    }

    public void logoutAction() {
        memberSearchPane.setVisible(false);
        messageLabel.setText("");
        displayLoginPane();
    }

    public void displayMemberSearch() {
        memberSearchPane.setVisible(true);
        changePasswordPane.setVisible(false);
        loginPane.setVisible(true);
        email.setText("");
        password.setText("");
        displayAllMembers();
    }

    public void displayMembers(List<Member> membersList) {
        memberRosterTable.getItems().setAll(membersList);
    }

    public void displayAllMembers() {
        List<Member> membersList = service.getMemberData();
        displayMembers(membersList);
    }

    public void addMemberScreenChange() {
        displayAddMemberPane();
    }

    public void backButtonAction() {
        loginRegisterScreen.setVisible(false);
        addMemberPane.setVisible(false);
        displayMemberSearch();
    }


    public void addMemberAction() {
        try {
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            String gender = genderField.getText();
            String email = emailField.getText();
            String dob = dobField.getText();
            String gradDate = gradDateField.getText();
            float weightClass = Float.parseFloat(weightClassField.getText());
            float bestResult = Float.parseFloat(bestResultField.getText());
            String password1 = firstName+lastName; // set to first name + last name for now when adding a new member
            int semesterId = 3; // Assuming a default semester ID, change as needed

    //        int totalPracticesAttended = Integer.parseInt(attendanceField.getText());

            Member newMember = new Member(semesterId, firstName, lastName, dob, gradDate, weightClass, bestResult, gender, email);

            service.addMember(newMember);
            // Clear the fields after adding
            firstNameField.clear();
            lastNameField.clear();
            genderField.clear();
            emailField.clear();
            dobField.clear();
            gradDateField.clear();
            weightClassField.clear();
            bestResultField.clear();
            attendanceField.clear();

            showMessage(addMemberMessageLabel, "Member added successfully!", Color.GREEN);
        } catch (SQLException e) {
            e.printStackTrace();
            // Show an error message if needed
            showMessage(addMemberMessageLabel, "Failed to add member. Please try again.", Color.RED);
        } catch (IllegalArgumentException e) {
            showMessage(addMemberMessageLabel, "Please enter valid member details.", Color.RED);
        }
    }


//    public void updateMemberAction() {
//        String memberName = newEmail.getText();
//        String memberDetails = newPassword.getText();
//        if (memberName.isBlank() || memberDetails.isBlank()) {
//            showMessage(messageLabel, "Please enter valid member details.", Color.RED);
//        } else {
//            boolean placeholder_updateMember = true;
//            if (placeholder_updateMember) {
//                showMessage(messageLabel, "Member updated successfully.", Color.GREEN);
//            } else {
//                showMessage(messageLabel, "Failed to update member. Please try again.", Color.RED);
//            }
//        }
//    }
//
//    public void deleteMemberAction() {
//        String memberName = newEmail.getText();
//        if (memberName.isBlank()) {
//            showMessage(messageLabel, "Please enter a valid member name.", Color.RED);
//        } else {
//            boolean placeholder_deleteMember = true;
//            if (placeholder_deleteMember) {
//                showMessage(messageLabel, "Member deleted successfully.", Color.GREEN);
//            } else {
//                showMessage(messageLabel, "Failed to delete member. Please try again.", Color.RED);
//            }
//        }
//    }


    public void displayAddMemberPane() {
        memberSearchPane.setVisible(false);
        addMemberPane.setVisible(true);
    }

    public void displayAlumniPane() {
        loginRegisterScreen.setVisible(false);
        alumniPane.setVisible(true);
    }

    public void backFromAlumni() {
        alumniPane.setVisible(false);
        loginRegisterScreen.setVisible(true);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        displayLoginPane();
        memberFirstNameColumn.setCellValueFactory(new PropertyValueFactory<Member, String>("First_Name"));
        memberLastNameColumn.setCellValueFactory(new PropertyValueFactory<Member, String>("Last_Name"));
        memberGenderColumn.setCellValueFactory(new PropertyValueFactory<Member, String>("Gender"));
        memberEmailColumn.setCellValueFactory(new PropertyValueFactory<Member, String>("Email"));
        memberWeightClassColumn.setCellValueFactory(new PropertyValueFactory<Member, Float>("Weight_Class"));
        memberBestResultColumn.setCellValueFactory(new PropertyValueFactory<Member, Float>("Best_Total_KG"));
        memberAttendanceColumn.setCellValueFactory(new PropertyValueFactory<Member, Integer>("Total_Practices_Attended"));
    }

    public void searchMemberAction() {
        String firstName = searchFirstName.getText();
        String lastName = searchLastName.getText();
        String email = searchEmail.getText();
        String gender = searchGender.getText();
        Float weight = null;
        if (!searchWeight.getText().isBlank()) {
            weight = Float.parseFloat(searchWeight.getText());
        }
        Float result = null;
        if (!searchResult.getText().isBlank()) {
            result = Float.parseFloat(searchResult.getText());
        }

        List<Member> members = service.searchMembers(firstName, lastName, email, gender, weight, result);
        if (members.isEmpty()) {
            showMessage(searchMessageLabel, "No members found with the given search criteria.", Color.RED);
        } else {
            displayMembers(members);
        }
    }

    public void changePasswordAction() {
        String e = verifyEmail.getText();
        String oldPW = oldPassword.getText();
        String newPW = newPassword.getText();
        String hashednewPW = hashString(newPW);
        String confirmPW = confirmNewPassword.getText();
        String hashedconfirmPW = hashString(confirmPW);
        boolean validCreds = service.checkCredentials(e, oldPW);
        if (e.isBlank() || oldPW.isBlank() || newPW.isBlank() || confirmPW.isBlank()) {
            showMessage(messageLabel, "Please enter a valid username and password.", Color.RED);
        }
        else {
            if (validCreds && (newPW.equals(confirmPW))) {
                service.changePassword(e, oldPW, hashednewPW);
                showMessage(messageLabel, "Successfully updated password!", Color.GREEN);

            } else {
                if (validCreds) {
                    showMessage(messageLabel, "Email and old password match, but please ensure new password matches with confirmation.", Color.RED);
                } else {
                    showMessage(messageLabel, "Please ensure email and old password match.", Color.RED);
                }
            }
        }
    }

}