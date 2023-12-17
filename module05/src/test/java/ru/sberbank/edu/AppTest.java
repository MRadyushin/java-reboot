package ru.sberbank.edu;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

public class AppTest {

    @Test
    public void testConversionGradusToRadians() {
        String latitudeDegree = "55(45'07'')";
        String longitudeDegree = "59(57'00'')";
        GeoPosition geoPosition = new GeoPosition(latitudeDegree, longitudeDegree);
        Assertions.assertTrue(geoPosition.getLatitude() == 0.9730549949445164 &&
                geoPosition.getLongitude() == 1.0463248865706005);
    }
    @Test
    public void testAddCity() {
        TravelService travelService = new TravelService();
        CityInfo cityInfo1 = new CityInfo("Moscow", new GeoPosition("55(45'07'')", "36(24'17'')"));
        CityInfo cityInfo2 = new CityInfo("Ryazan`", new GeoPosition("59(57'00'')", "36(24'50'')"));
        travelService.add(cityInfo1);
        travelService.add(cityInfo2);
        Assertions.assertEquals(2, travelService.citiesNames().size());
    }

    @Test
    public void testAddDuplicatedCity() {
        TravelService travelService = new TravelService();
        CityInfo cityInfo1 = new CityInfo("Ryazan`", new GeoPosition("59(57'00'')", "36(24'50'')"));
        CityInfo cityInfo2 = new CityInfo("Ryazan`", new GeoPosition("59(57'00'')", "36(24'50'')"));
        travelService.add(cityInfo1);
        Assertions.assertThrows(IllegalArgumentException.class, () -> travelService.add(cityInfo2));
    }

    @Test
    public void testRemoveCity() {
        TravelService travelService = new TravelService();
        CityInfo cityInfo1 = new CityInfo("Moscow", new GeoPosition("55(45'07'')", "36(24'17'')"));
        CityInfo cityInfo2 = new CityInfo("Ryazan`", new GeoPosition("59(57'00'')", "36(24'50'')"));
        travelService.add(cityInfo1);
        travelService.add(cityInfo2);
        travelService.remove("Ryazan`");
        Assertions.assertEquals(1, travelService.citiesNames().size());
    }

    @Test
    public void testRemoveNotExistedCity() {
        TravelService travelService = new TravelService();
        CityInfo cityInfo1 = new CityInfo("Moscow", new GeoPosition("55(45'07'')", "36(24'17'')"));
        CityInfo cityInfo2 = new CityInfo("Ryazan`", new GeoPosition("59(57'00'')", "36(24'50'')"));
        travelService.add(cityInfo1);
        travelService.add(cityInfo2);
        Assertions.assertThrows(IllegalArgumentException.class, () -> travelService.remove("Piter"));
    }

    @Test
    public void testCitiesNames() {
        TravelService travelService = new TravelService();
        CityInfo cityInfo1 = new CityInfo("Moscow", new GeoPosition("55(45'07'')", "36(24'17'')"));
        CityInfo cityInfo2 = new CityInfo("Ryazan`", new GeoPosition("59(57'00'')", "36(24'50'')"));
        travelService.add(cityInfo1);
        travelService.add(cityInfo2);
        Assertions.assertEquals(List.of("Moscow", "Ryazan`"), travelService.citiesNames());
    }

    @Test
    public void testGetDistance() {
        TravelService travelService = new TravelService();
        CityInfo cityInfo1 = new CityInfo("Moscow", new GeoPosition("55(45'07'')", "36(24'17'')"));
        CityInfo cityInfo2 = new CityInfo("Ryazan`", new GeoPosition("59(57'00'')", "36(24'50'')"));
        travelService.add(cityInfo1);
        travelService.add(cityInfo2);
        Assertions.assertTrue(travelService.getDistance("Moscow", "Ryazan`") ==466);
    }
    @Test
    public void testGetDistanceNotFirstCity() {
        String result = null;

        try {
            TravelService travelService = new TravelService();
            CityInfo cityInfo1 = new CityInfo("Moscow", new GeoPosition("55(45'07'')", "36(24'17'')"));
            CityInfo cityInfo2 = new CityInfo("Ryazan`", new GeoPosition("59(57'00'')", "36(24'50'')"));
            travelService.add(cityInfo1);
            travelService.add(cityInfo2);
            travelService.getDistance("Moscow", "Piter");        }
        catch (IllegalArgumentException e) {
            result=e.getMessage();
        }
        Assertions.assertEquals("Города Piter нет в списке.",result);

    }
    @Test
    public void testGetDistanceNotAllCity() {
        String result = null;

        try {
            TravelService travelService = new TravelService();
            CityInfo cityInfo1 = new CityInfo("Moscow", new GeoPosition("55(45'07'')", "36(24'17'')"));
            CityInfo cityInfo2 = new CityInfo("Ryazan`", new GeoPosition("59(57'00'')", "36(24'50'')"));
            travelService.add(cityInfo1);
            travelService.add(cityInfo2);
            travelService.getDistance("Penza", "Piter");        }
        catch (IllegalArgumentException e) {
            result=e.getMessage();
        }
        Assertions.assertEquals("Городов Penza и Piter нет в списке.",result);

    }

    @Test
    public void testGetCitiesNear() {
        TravelService travelService = new TravelService();
        travelService.add(new CityInfo("Moscow", new GeoPosition("55(45'07'')", "36(24'17'')")));
        travelService.add(new CityInfo("Ryazan`", new GeoPosition("59(57'00'')", "36(24'50'')")));
        travelService.add(new CityInfo("Piter", new GeoPosition("59(56'29'')", "30(18'47'')")));
        Assertions.assertTrue(travelService.getCitiesNear("Moscow", 500).size() == 1 &&
                travelService.getCitiesNear("Moscow", 500).get(0).equals("Ryazan`"));
    }
}
