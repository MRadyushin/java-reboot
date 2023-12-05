package ru.sberbank.edu;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Weather cache.
 */
@Getter
@Component
public class WeatherCache {

    private final Map<String, WeatherInfo> cache = new HashMap<>();

    @Autowired
    WeatherProvider weatherProvider;

    /**
     * Get ACTUAL weather info for current city or null if current city not found. If cache doesn't contain weather info
     * OR contains NOT ACTUAL info then we should download info If you download weather info then you should set expiry
     * time now() plus 5 minutes. If you can't download weather info then remove weather info for current city from
     * cache.
     *
     * @param city - city
     * @return actual weather info
     */


    public synchronized WeatherInfo getWeatherInfo(String city) {
        WeatherInfo cachedWeatherInfo = cache.get(city);
        if (cachedWeatherInfo != null
                && cachedWeatherInfo.getExpiryTime() != null
                && cachedWeatherInfo.getExpiryTime().isAfter(LocalDateTime.now())) {
            return cachedWeatherInfo;
        }
        WeatherInfo weatherInfo = weatherProvider.get(city);

        if (weatherInfo == null) {
            removeWeatherInfo(city);
            return null;
        } else {
            cache.put(weatherInfo.getCity(), weatherInfo);
            return weatherInfo;
        }

    }

    /**
     * Remove weather info from cache.
     **/
    public synchronized void removeWeatherInfo(String city) {
        cache.remove(city);
    }
}

