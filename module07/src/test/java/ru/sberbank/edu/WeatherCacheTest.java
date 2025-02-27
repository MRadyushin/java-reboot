package ru.sberbank.edu;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


class WeatherCacheTest {

    @InjectMocks
    private WeatherCache weatherCache;
    @Mock
    private WeatherProvider weatherProvider;
    private WeatherInfo weatherInfo;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        weatherCache = new WeatherCache(weatherProvider);
        weatherInfo = new WeatherInfo();
        weatherInfo.setCity("Moscow");
        weatherInfo.setExpiryTime(LocalDateTime.now().plusMinutes(5));
        weatherInfo.setTemperature(25.5);
        weatherInfo.setWindSpeed(17.5);
        weatherInfo.setDescription("Тестовое описание");
        weatherInfo.setPressure(1000);
        weatherInfo.setShortDescription("Тестовое короткое описание");
    }

    @Test
    void weatherInfoNotInCacheTest() {
        when(weatherProvider.get(anyString())).thenReturn(weatherInfo);
        WeatherInfo moscow = weatherCache.getWeatherInfo("Moscow");
        assertEquals(weatherInfo, moscow);
        verify(weatherProvider, times(1)).get(anyString());
    }


    @Test
    void weatherInfoInCacheTest() throws NoSuchFieldException, IllegalAccessException {

        String city = "London";
        WeatherInfo weatherInfo = new WeatherInfo();
        weatherInfo.setCity(city);
        weatherInfo.setExpiryTime(LocalDateTime.now().plusMinutes(5));
        weatherInfo.setTemperature(25.5);
        weatherInfo.setWindSpeed(17.5);
        weatherInfo.setDescription("Тестовое описание");
        weatherInfo.setPressure(1000);
        weatherInfo.setShortDescription("Тестовое короткое описание");

        Map<String, WeatherInfo> cache = new HashMap<>();
        cache.put(city, weatherInfo);

        java.lang.reflect.Field cacheField = WeatherCache.class.getDeclaredField("cache");
        cacheField.setAccessible(true);
        cacheField.set(weatherCache, cache);

        WeatherInfo result = weatherCache.getWeatherInfo(city);

        assertNotNull(result);
        assertEquals(city, result.getCity());
        assertEquals(weatherInfo, result);

        verifyNoInteractions(weatherProvider);
    }

    @Test
    void weatherInfoNotInCache_ReturnsNullTest() {
        String city = "qwertyuiop";
        when(weatherProvider.get(city)).thenReturn(null);
        WeatherInfo result = weatherCache.getWeatherInfo(city);
        assertNull(result);
        verify(weatherProvider).get(city);
    }

    @Test
    void cityNotFoundTest() {
        String city = "nonexistentCity";
        when(weatherProvider.get(city)).thenThrow(new RuntimeException("City not found"));
        Exception exception = assertThrows(RuntimeException.class, () -> weatherCache.getWeatherInfo(city));
        assertEquals("City not found", exception.getMessage());
        verify(weatherProvider).get(city);
    }

    @Test
    void removesWeatherInfoFromCacheTest() {
        String city = "Tokyo";
        WeatherInfo weatherInfo = new WeatherInfo();
        weatherInfo.setCity(city);
        weatherInfo.setExpiryTime(LocalDateTime.now().plusMinutes(5));
        weatherInfo.setTemperature(25.5);
        weatherInfo.setWindSpeed(17.5);
        weatherInfo.setDescription("Тестовое описание");
        weatherInfo.setPressure(1000);
        weatherInfo.setShortDescription("Тестовое короткое описание");

        Map<String, WeatherInfo> cache = new HashMap<>();
        cache.put(city, weatherInfo);

        try {
            java.lang.reflect.Field cacheField = WeatherCache.class.getDeclaredField("cache");
            cacheField.setAccessible(true);
            cacheField.set(weatherCache, cache);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        weatherCache.removeWeatherInfo(city);
        assertFalse(cache.containsKey(city));
    }

    @Test
    void returnsUpdatedWeatherInfoTest() {
        WeatherInfo outdatedWeatherInfo = new WeatherInfo();
        outdatedWeatherInfo.setCity("London");
        outdatedWeatherInfo.setExpiryTime(LocalDateTime.now().minusMinutes(1));
        outdatedWeatherInfo.setTemperature(18.0);
        outdatedWeatherInfo.setWindSpeed(8.0);
        outdatedWeatherInfo.setDescription("Устаревшее описание для Лондона");
        outdatedWeatherInfo.setPressure(1005);
        outdatedWeatherInfo.setShortDescription("Устаревшее короткое описание для Лондона");

        WeatherInfo updatedWeatherInfo = new WeatherInfo();
        updatedWeatherInfo.setCity("London");
        updatedWeatherInfo.setExpiryTime(LocalDateTime.now().plusMinutes(5));
        updatedWeatherInfo.setTemperature(20.0);
        updatedWeatherInfo.setWindSpeed(10.0);
        updatedWeatherInfo.setDescription("Обновленное описание для Лондона");
        updatedWeatherInfo.setPressure(1010);
        updatedWeatherInfo.setShortDescription("Обновленное короткое описание для Лондона");

        when(weatherProvider.get("London")).thenReturn(updatedWeatherInfo);
        try {
            java.lang.reflect.Field cacheField = WeatherCache.class.getDeclaredField("cache");
            cacheField.setAccessible(true);
            Map<String, WeatherInfo> cache = new HashMap<>();
            cache.put("London", outdatedWeatherInfo);
            cacheField.set(weatherCache, cache);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

        WeatherInfo result = weatherCache.getWeatherInfo("London");

        assertEquals(updatedWeatherInfo, result);
        verify(weatherProvider).get("London");
    }
}
