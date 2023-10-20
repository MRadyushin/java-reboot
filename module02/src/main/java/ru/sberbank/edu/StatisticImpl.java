package ru.sberbank.edu;

import java.io.*;

/**
 * Класс StatisticImpl реализует интерфейс Statistic
 *
 * @author Максим Радюшин{
 */
public class StatisticImpl implements Statistic {
    /**
     * @Override - переопределение метода
     * Метод возвращает количество строк в файле
     */
    @Override
    public int getLineCount(FileReader fileReader) throws IOException {
        LineNumberReader count;
        try (BufferedReader br = new BufferedReader(fileReader)) {
            count = new LineNumberReader(br);
            while (count.skip(Long.MAX_VALUE) > 0) {
                /**
                 * Loop just in case the file is > Long.MAX_VALUE or skip() decides to not read the entire file
                 */
            }
        }

        System.out.println("Количество строк в файле: " + count.getLineNumber());
        return count.getLineNumber();
    }

    /**
     * @Override - переопределение метода
     * Метод возвращает количество пробелов в файле
     */
    @Override
    public int getSpaceCount(FileReader fileReader) throws IOException {
        int spaceCount = 0;
        String line;
        try (BufferedReader br = new BufferedReader(fileReader)) {
            while ((line = br.readLine()) != null) {
                for (char c : line.toCharArray()) {
                    if (c == ' ') {
                        spaceCount++;
                    }
                }
            }
        }
        System.out.println("Количество пробелов в файле: " + spaceCount);
        return spaceCount;
    }

    /**
     * @Override - переопределение метода
     * Метод возвращает самую длинную строку из файла
     */
    @Override
    public String getLongestLine(FileReader fileReader) throws IOException {
        int max = 0;
        String offer = "";

        try (BufferedReader br = new BufferedReader(fileReader)) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.length() > max) {
                    max = line.length();
                    offer = line;
                }
            }
        }
        System.out.println("Самая длинная строка в файле: " + offer + "\n" + "Содержит " + max + " символ(а)");
        return offer;
    }

    /**
     * @return
     * @Override - переопределение метода
     * Метод сохраняет в файл statistic.txt информацию,
     * сколько строк,
     * какая строка самая длинная и ее размерность,
     * сколько всего пробелов
     */
    @Override
    public File save(File fileReader) throws IOException {

        FileReader fileReader0 = new FileReader(fileReader);
        FileReader fileReader1 = new FileReader(fileReader);
        FileReader fileReader2 = new FileReader(fileReader);

        try (PrintWriter printWriter = new PrintWriter(new FileWriter("statistic.txt"))) {
            printWriter.println("Количество строк в файле: " + getLineCount(fileReader0));
            printWriter.println("Самая длинная строка в файле: " + getLongestLine(fileReader1));
            printWriter.println("Количество пробелов в файле: " + getSpaceCount(fileReader2));
        }

        System.out.println("Данные успешно записаны в файл statistic.txt ");
        return new File("statistic.txt");
    }
}
