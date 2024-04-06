import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherModel {

    private final static String APIKEY = "7cbdae89ef57ac183613ae700c387c80";
    private static final String API_URL = "https://api.openweathermap.org/data/2.5/forecast?lat=48.8913162&lon=-135.5231164&appid=" + APIKEY;

    public JsonObject fetchWeatherData() {
        try {
            URL url = new URL(API_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            int responseCode = conn.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder response = new StringBuilder();
                String inputLine;

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }

                in.close();

                return new JsonParser().parse(response.toString()).getAsJsonObject();
            } else {
                System.out.println("Failed to fetch weather data. Response code: " + responseCode);
                return null;
            }

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
