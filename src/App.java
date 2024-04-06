import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application{
    public static void main(String[] args) throws Exception {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        WeatherModel model = new WeatherModel();
        WeatherView view = new WeatherView(primaryStage);
        WeatherController controller = new WeatherController(model, view);
    }
}
