package ru.sberbank.edu;

import java.util.ArrayList;
import java.util.List;

/**
 * Travel Service.
 */
public class TravelService {
    private static final double EARTH_RADIUS_KM = 6371.0;

    // do not change type
    private final List<CityInfo> cities = new ArrayList<>();

    /**
     * Append city info.
     *
     * @param cityInfo - city info
     * @throws IllegalArgumentException if city already exists
     */
    public void add(CityInfo cityInfo) {
        cities.stream().filter(cityInfo::equals).findAny().ifPresentOrElse(
                (ci) -> {
                    throw new IllegalArgumentException(String.format("Город %s уже содержится в списке.", cityInfo.getName()));
                },
                () -> {
                    cities.add(cityInfo);
                });
    }

    /**
     * remove city info.
     *
     * @param cityName - city name
     * @throws IllegalArgumentException if city doesn't exist
     */
    public void remove(String cityName) {
        cities.stream().filter(city -> (city.getName().equals(cityName))).findFirst().ifPresentOrElse(
                cities::remove,
                () -> {
                    throw new IllegalArgumentException("Город '" + cityName + "' отсутствует в списке.");
                }
        );
    }

    /**
     * Get cities names.
     */
    public List<String> citiesNames() {
        return cities
                .stream()
                .map(CityInfo::getName)
                .toList();
    }

    /**
     * Get distance in kilometers between two cities.
     * https://www.kobzarev.com/programming/calculation-of-distances-between-cities-on-their-coordinates/
     *
     * @param srcCityName  - source city
     * @param destCityName - destination city
     * @throws IllegalArgumentException if source or destination city doesn't exist.
     */
    public int getDistance(String srcCityName, String destCityName) {

        GeoPosition srcGeoPosition = cities
                .stream()
                .filter(city -> city.getName().equals(srcCityName))
                .map(CityInfo::getPosition)
                .findFirst()
                .orElse(null);
        GeoPosition destGeoPosition = cities
                .stream()
                .filter(city -> city.getName().equals(destCityName))
                .map(CityInfo::getPosition)
                .findFirst()
                .orElse(null);


        if (srcGeoPosition == null && destGeoPosition == null) {
            throw new IllegalArgumentException("Городов " + srcCityName + " и " + destCityName + " нет в списке.");
        } else if (srcGeoPosition == null) {
            throw new IllegalArgumentException("Города " + srcCityName + " нет в списке.");
        } else if (destGeoPosition == null) {
            throw new IllegalArgumentException("Города " + destCityName + " нет в списке.");
        } else {
            double latitudeSrc = srcGeoPosition.getLatitude();
            double longitudeSrc = srcGeoPosition.getLongitude();
            double latitudeDest = destGeoPosition.getLatitude();
            double longitudeDest = destGeoPosition.getLongitude();
            double deltaLongitude = longitudeDest - longitudeSrc;
            return (int) (EARTH_RADIUS_KM * Math.acos(Math.sin(latitudeSrc) * Math.sin(latitudeDest) +
                    Math.cos(latitudeSrc) * Math.cos(latitudeDest) * Math.cos(deltaLongitude)));
        }

    }

    /**
     * Get all cities near current city in radius.
     *
     * @param cityName - city
     * @param radius   - radius in kilometers for search
     * @throws IllegalArgumentException if city with cityName city doesn't exist.
     */
    public List<String> getCitiesNear(String cityName, int radius) {
        if (citiesNames().contains(cityName)) {
            return cities
                    .stream()
                    .map(CityInfo::getName)
                    .filter(city -> getDistance(city, cityName) <= radius)
                    .filter(city -> !city.equals(cityName))
                    .toList();
        } else {
            throw new IllegalArgumentException("Такого города нет в списке.");
        }
    }
}
