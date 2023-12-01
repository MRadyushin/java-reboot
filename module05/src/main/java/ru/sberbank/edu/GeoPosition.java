package ru.sberbank.edu;

import java.util.Objects;

/**
 * Geo position.
 */
public class GeoPosition {

    /**
     * Широта в радианах.
     */
    private double latitude;

    /**
     * Долгота в радианах.
     */
    private double longitude;

    /**
     * Ctor.
     *
     * @param latitudeGradus  - latitude in gradus
     * @param longitudeGradus - longitude in gradus
     *                        Possible values: 55, 55(45'07''), 59(57'00'')
     */
    public GeoPosition(String latitudeGradus, String longitudeGradus) {
        this.latitude = conversionGradusToRadians(latitudeGradus);
        this.longitude = conversionGradusToRadians(longitudeGradus);
    }
    private double conversionGradusToRadians(String inputString) {
        String[] arrayString = inputString.replaceAll("[^0-9]", " ").split(" ");
        double result = 0;
        for (int i = 0; i < arrayString.length; i++) {
            result += Integer.parseInt(arrayString[i]) / Math.pow(60, i);
        }
        result *= Math.PI / 180;
        return result;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GeoPosition that = (GeoPosition) o;
        if (Double.compare(latitude, that.latitude) != 0) return false;
        return latitude == that.getLatitude() && longitude == that.getLongitude();    }

    @Override
    public int hashCode() {
        return Objects.hash(latitude, longitude);
    }
    @Override
    public String toString() {
        return "GeoPosition{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }

}