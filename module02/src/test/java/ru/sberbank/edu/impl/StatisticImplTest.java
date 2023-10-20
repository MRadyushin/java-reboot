package ru.sberbank.edu.impl;

import junit.framework.Assert;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;
import ru.sberbank.edu.StatisticImpl;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;


public class StatisticImplTest {
    private final int expectedLineCounts = 6;
    private final int expectedSpaceCounts = 25;
    private final String expectedLongestLine = "выаыва ывпфывп ыпфывфыпфапФыафы фыва123";


    private final String fileName = "test.txt";
    private final File expectedFile = new File("ExpectedStatistic.txt");

    @Test
    public void getLineCountTest() throws IOException {

        int resultLineCounts = 0;

        StatisticImpl statistic = new StatisticImpl();
        resultLineCounts = statistic.getLineCount(new FileReader(fileName));

        Assert.assertEquals(expectedLineCounts, resultLineCounts);
    }

    @Test
    public void getSpaceCountTest() throws IOException {

        int resultSpaceCounts = 0;

        StatisticImpl statistic = new StatisticImpl();
        resultSpaceCounts = statistic.getSpaceCount(new FileReader(fileName));

        Assert.assertEquals(expectedSpaceCounts, resultSpaceCounts);
    }

    @Test
    public void getLongestLineTest() throws IOException {

        String resultLongestLine = "";

        StatisticImpl statistic = new StatisticImpl();
        resultLongestLine = statistic.getLongestLine(new FileReader(fileName));

        Assert.assertEquals(expectedLongestLine, resultLongestLine);
    }

    @Test
    public void getSaveStatisticInFileTest() throws IOException {


        StatisticImpl statistic = new StatisticImpl();
        File result = statistic.save(new File(fileName));

        Assert.assertEquals(FileUtils.readLines(expectedFile, StandardCharsets.UTF_8), FileUtils.readLines(result, StandardCharsets.UTF_8));

    }
}
