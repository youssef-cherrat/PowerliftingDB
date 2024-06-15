package org.powerlifting;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/powerlifting_proj.fxml"));
        Parent root = fxmlLoader.load();

        PowerliftingController controller = fxmlLoader.getController();
        DatabaseDriver db = new DatabaseDriver();
        PowerliftingService service = new PowerliftingService(db);
        controller.setService(service);
        service.convertMembersToAlumni(); // convert any existing members who graduated to alumni upon app launch

        Scene scene = new Scene(root);
        stage.setTitle("UVA Club Powerlifting Application");
        stage.setScene(scene);
        stage.show();
    }
}
