import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class WeatherView {

    private final ObservableList<XYChart.Data<Number, Number>> temperatureData = FXCollections.observableArrayList();
    private final ObservableList<XYChart.Data<Number, Number>> humidityData = FXCollections.observableArrayList();

    public WeatherView(Stage primaryStage) {
        primaryStage.setTitle("Weather App");

        // Create LineChart for temperature
        NumberAxis temperatureXAxis = new NumberAxis();
        NumberAxis temperatureYAxis = new NumberAxis();
        temperatureXAxis.setLabel("Time");
        LineChart<Number, Number> temperatureChart = new LineChart<>(temperatureXAxis, temperatureYAxis);
        temperatureChart.setTitle("Temperature Trend");
        temperatureChart.getData().add(new XYChart.Series<>("Temperature", temperatureData));

        // Create LineChart for humidity
        NumberAxis humidityXAxis = new NumberAxis();
        NumberAxis humidityYAxis = new NumberAxis();
        humidityXAxis.setLabel("Time");
        LineChart<Number, Number> humidityChart = new LineChart<>(humidityXAxis, humidityYAxis);
        humidityChart.setTitle("Humidity Trend");
        humidityChart.getData().add(new XYChart.Series<>("Humidity", humidityData));

        // StackPane to stack LineCharts
        StackPane stackPane = new StackPane(temperatureChart, humidityChart);
        stackPane.setAlignment(Pos.CENTER);
        Scene scene = new Scene(stackPane, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void displayWeather(JsonObject weatherData) {
        temperatureData.clear();
        humidityData.clear();

        if (weatherData.has("list")) {
            JsonArray forecastList = weatherData.getAsJsonArray("list");

            for (int i = 0; i < forecastList.size(); i++) {
                JsonObject forecast = forecastList.get(i).getAsJsonObject();

                if (forecast.has("main")) {
                    double temperature = forecast.get("main").getAsJsonObject().has("temp") ? forecast.get("main").getAsJsonObject().get("temp").getAsDouble() - 273.15 : 0.0;
                    double humidity = forecast.get("main").getAsJsonObject().has("humidity") ? forecast.get("main").getAsJsonObject().get("humidity").getAsDouble() : 0.0;
                    int time = i; // Index as time

                    temperatureData.add(new XYChart.Data<>(time, temperature));
                    humidityData.add(new XYChart.Data<>(time, humidity));
                }
            }
        }
    }
}