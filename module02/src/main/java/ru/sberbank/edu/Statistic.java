package ru.sberbank.edu;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

// интерфейс можно менять
public interface Statistic {

    int getLineCount(FileReader fileReader) throws IOException;
    int getSpaceCount(FileReader fileReader) throws IOException;
    String getLongestLine(FileReader fileReader) throws IOException;
    File save(File fileReader) throws IOException;

}
