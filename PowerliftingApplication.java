import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class PowerliftingApplication extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("powerlifting_proj.fxml"));
        Parent root = fxmlLoader.load();

        PowerliftingController controller = fxmlLoader.getController();
//        Configuration configuration = new Configuration();
        DatabaseDriver db = new DatabaseDriver();
        PowerliftingService service = new PowerliftingService(db);
//        service.createTables();
        controller.setService(service);

        Scene scene = new Scene(root);
        stage.setTitle("UVA Club Powerlifting Application");
        stage.setScene(scene);
        stage.show();
    }

}