package ru.sberbank.edu;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        GeoPosition city3 = new GeoPosition("55", "55(45'07'')");

        System.out.println(city3.getLongitude());
        System.out.println(city3.getLatitude());
    }
}
