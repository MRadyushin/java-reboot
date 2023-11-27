package ru.sberbank.edu;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import java.util.Objects;
import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

/**
 * Weather provider.
 */
public class WeatherProvider {

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Download ACTUAL weather info from internet. You should call GET
     * <a href="http://api.openweathermap.org/data/2.5/weather?q=">...</a>{city name}&appid={API key} You should use
     * Spring Rest Template for calling requests

     *
     * @param city - city
     * @return weather info or null
     */
    @SneakyThrows
    public WeatherInfo get(String city) {
        String api =
                "https://api.openweathermap.org/data/2.5/weather?q=" + city
                        + "&appid=59f8b24af4788e7aaf8eba8877e89fbe&units=metric";
        ResponseEntity<String> response;
        try {
            response = restTemplate.getForEntity(api, String.class);
        } catch (RestClientException e) {
            throw new RuntimeException("City not found");
        }
        if (Objects.requireNonNull(response.getBody()).isEmpty()) {
            return null;
        }
        JsonNode jsonNode = objectMapper.readTree(response.getBody());

        WeatherInfo weatherInfo = new WeatherInfo();
        weatherInfo.setCity(city);
        weatherInfo.setDescription("Информация: " + jsonNode.get("weather").get(0).get("main"));
        weatherInfo.setExpiryTime(LocalDateTime.now().plusMinutes(5));
        weatherInfo.setPressure(jsonNode.get("main").get("pressure").asDouble());
        weatherInfo.setTemperature(jsonNode.get("main").get("temp").asDouble());
        weatherInfo.setWindSpeed(jsonNode.get("wind").get("speed").asDouble());
        weatherInfo.setFeelsLikeTemperature(jsonNode.get("main").get("feels_like").asDouble());
        weatherInfo.setShortDescription(" погода: " + jsonNode.get("weather").get(0).get("description"));
        return weatherInfo;
    }
}

