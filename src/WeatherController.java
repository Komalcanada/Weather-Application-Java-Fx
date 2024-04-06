import com.google.gson.JsonObject;
import javafx.application.Platform;

public class WeatherController {

    private final WeatherModel model;
    private final WeatherView view;

    public WeatherController(WeatherModel model, WeatherView view) {
        this.model = model;
        this.view = view;

        fetchWeatherDataAndUpdateView();
    }

    private void fetchWeatherDataAndUpdateView() {
        new Thread(() -> {
            JsonObject weatherData = model.fetchWeatherData();
            if (weatherData != null) {
                Platform.runLater(() -> view.displayWeather(weatherData));
            } else {
                System.out.println("Failed to fetch weather data");
            }
        }).start();
    }
}