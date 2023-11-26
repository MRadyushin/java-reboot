package ru.sberbank.edu;

import java.io.File;
import java.io.IOException;

/**
 * Save statistic in file
 *
 */
public class App 
{
    public static void main( String[] args ) throws IOException {
        StatisticImpl statistic = new StatisticImpl();
        statistic.save(new File("test.txt"));
    }
}
