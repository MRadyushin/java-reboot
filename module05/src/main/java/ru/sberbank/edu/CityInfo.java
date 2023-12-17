package ru.sberbank.edu;

import java.util.Objects;

/**
 * City info
 */
public class CityInfo {

    private String name;
    private GeoPosition position;

    /**
     * Ctor.
     *
     * @param name     - city name
     * @param position - position
     */
    public CityInfo(String name, GeoPosition position) {
        this.name = name;
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public GeoPosition getPosition() {
        return position;
    }

    public void setPosition(GeoPosition position) {
        this.position = position;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CityInfo cityInfo = (CityInfo) o;
        if (!name.equals(cityInfo.name)) return false;
        return name.equals(cityInfo.getName()) && position.equals(cityInfo.getPosition());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, position);
    }

    @Override
    public String toString() {
        return "CityInfo{" +
                "name='" + name + '\'' +
                ", position=" + position +
                '}';
    }
}