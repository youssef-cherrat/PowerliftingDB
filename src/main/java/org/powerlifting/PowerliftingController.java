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
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;
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

    @FXML private AnchorPane memberDetailsPane;
    @FXML private Label memberDetailsNameLabel;
    @FXML private Label memberDetailsAgeLabel;
    @FXML private Label memberDetailsGenderLabel;
    @FXML private Label memberDetailsWeightLabel;
    @FXML private Label memberDetailsBestTotalLabel;

    @FXML private TextField addEventType;
    @FXML private TextField addEventDate;
    @FXML private TextField addEventLocation;
    @FXML private Button addEventButton;
    @FXML private Label addEventMessageLabel;

    @FXML private TableView<Event> eventsLogTable;
    @FXML private TableColumn<Event, String> eventTypeColumn;
    @FXML private TableColumn<Event, String> eventDateColumn;
    @FXML private TableColumn<Event, String> eventLocationColumn;

    @FXML private TableView<Alumni> alumniRosterTable;
    @FXML private TableColumn<Alumni, String> alumniFirstNameColumn;
    @FXML private TableColumn<Alumni, String> alumniLastNameColumn;
    @FXML private TableColumn<Alumni, String> alumniEmailColumn;
    @FXML private TableColumn<Alumni, Integer> alumniClassYearColumn;

    //search alum
    @FXML private TextField searchClassYear;
    @FXML private Button searchAlumniButton;
    @FXML private TextField searchFirstName1;
    @FXML private TextField searchLastName1;
    @FXML private TextField searchEmail1;




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
        memberDetailsPane.setVisible(false);
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

            // int totalPracticesAttended = Integer.parseInt(attendanceField.getText());
            // Validate date format
            if (!dob.matches("\\d{4}-\\d{2}-\\d{2}") || !gradDate.matches("\\d{4}-\\d{2}-\\d{2}")) {
                throw new IllegalArgumentException("Invalid date format. Please use YYYY-MM-DD.");
            }

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

            showMessage(addMemberMessageLabel, "Member added successfully!", Color.GREEN);
        } catch (SQLException e) {
            e.printStackTrace();
            // Show an error message if needed
            showMessage(addMemberMessageLabel, "Failed to add member. Please try again.", Color.RED);
        } catch (IllegalArgumentException e) {
            showMessage(addMemberMessageLabel, "Please enter valid member details.", Color.RED);
        }
    }


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
        displayAlumni();
    }

    public void backFromAlumni() {
        alumniPane.setVisible(false);
        loginRegisterScreen.setVisible(true);
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

    public void displayMemberDetailsAction() {
        Member member = memberRosterTable.getSelectionModel().getSelectedItem();
        if (member != null) {
            displayMemberDetailsActionHelper(member);
        }
    }

    public void displayMemberDetailsActionHelper(Member member) {
        searchMessageLabel.setText("");
        memberSearchPane.setVisible(false);
        memberDetailsPane.setVisible(true);
        displayMemberDetails(member);
        displayMemberEvents(member);
    }

    public void displayMemberDetails(Member member) {
        String name = member.getFirst_Name() + " " + member.getLast_Name();
        String dob = member.getDate_of_Birth();
        LocalDate birthDate = LocalDate.parse(dob);
        int age = calculateAge(birthDate);
        String gender = member.getGender();
        float weight = member.getWeight_Class();
        float best_total = member.getBest_Total_KG();

        memberDetailsNameLabel.setText(name);
        memberDetailsAgeLabel.setText(String.valueOf(age));
        memberDetailsGenderLabel.setText(gender);
        memberDetailsWeightLabel.setText(String.valueOf(weight));
        memberDetailsBestTotalLabel.setText(String.valueOf(best_total));
    }

    public int calculateAge (LocalDate birthDate) {
        LocalDate currentDate = LocalDate.now();
        if (currentDate != null && birthDate != null) {
            return Period.between(birthDate, currentDate).getYears();
        }
        return 0;
    }

    public void displayMemberEvents(Member member) {
        int memberId = member.getMember_ID();
        List<Event> eventsList = service.searchMemberEvents(memberId);
        eventsLogTable.getItems().setAll(eventsList);
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

        // Initialize alumni table columns
        alumniFirstNameColumn.setCellValueFactory(new PropertyValueFactory<>("First_Name"));
        alumniLastNameColumn.setCellValueFactory(new PropertyValueFactory<>("Last_Name"));
        alumniEmailColumn.setCellValueFactory(new PropertyValueFactory<>("Email"));
        alumniClassYearColumn.setCellValueFactory(new PropertyValueFactory<>("Class_Year"));

        eventTypeColumn.setCellValueFactory(new PropertyValueFactory<Event, String>("Event_Type"));
        eventDateColumn.setCellValueFactory(new PropertyValueFactory<Event, String>("Event_Date"));
        eventLocationColumn.setCellValueFactory(new PropertyValueFactory<Event, String>("Event_Location"));
    }

    //display alum
    public void displayAlumni() {
        List<Alumni> alumniList = service.getAlumniData();
        alumniRosterTable.getItems().setAll(alumniList);
    }

    //search alum
    public void searchAlumniAction() {
        String firstName = searchFirstName1.getText();
        String lastName = searchLastName1.getText();
        String email = searchEmail1.getText();
        Integer classYear = null;
        if (!searchClassYear.getText().isBlank()) {
            classYear = Integer.parseInt(searchClassYear.getText());
        }

        List<Alumni> alumniList = service.searchAlumni(firstName, lastName, email, classYear);
        if (alumniList.isEmpty()) {
            showMessage(searchMessageLabel, "No alumni found with the given search criteria.", Color.RED);
        } else {
            displayAlumni(alumniList);
        }
    }

    public void displayAlumni(List<Alumni> alumniList) {
        alumniRosterTable.getItems().setAll(alumniList);
    }

    //to delete member
    public void deleteMemberAction() {
        Member member = memberRosterTable.getSelectionModel().getSelectedItem();
        if (member != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Member");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to delete this member?");

            ButtonType buttonTypeYes = new ButtonType("Yes", ButtonBar.ButtonData.YES);
            ButtonType buttonTypeNo = new ButtonType("No", ButtonBar.ButtonData.NO);

            alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == buttonTypeYes) {
                try {
                    service.deleteMember(member.getMember_ID());
                    displayAllMembers();
                    showMessage(searchMessageLabel, "Member deleted successfully.", Color.GREEN);
                    backButtonAction();
                } catch (SQLException e) {
                    e.printStackTrace();
                    showMessage(searchMessageLabel, "Failed to delete member. Please try again.", Color.RED);
                }
            }
        }
    }


    public void addEventAction() {
        String eventType = addEventType.getText();
        String eventDate = addEventDate.getText();
        String eventLocation = addEventLocation.getText();
        if (eventType.equalsIgnoreCase("practice")) {

        } else if (eventType.equalsIgnoreCase("competition")) {

        } else {
            showMessage(addEventMessageLabel, "Please enter valid practice or competition", Color.RED);
        }

    }


}