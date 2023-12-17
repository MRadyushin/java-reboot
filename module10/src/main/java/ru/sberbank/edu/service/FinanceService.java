package ru.sberbank.edu.service;

import org.springframework.stereotype.Component;

@Component
public class FinanceService {

    public double complexPercent(String sum, String percentage, String years) {
        double sumValue, percentageValue;
        int yearsValue;
        try {
            sumValue = Double.parseDouble(sum);
            percentageValue = Double.parseDouble(percentage);
            yearsValue = Integer.parseInt(years);

            if (sumValue <= 0 || percentageValue <= 0 || yearsValue <= 0) {
                throw new IllegalArgumentException("Неверный формат данных. Скорректируйте значения");
            }

            if (sumValue < 50000) {
                throw new IllegalArgumentException("Минимальная сумма на момент открытия вклада 50 000 рублей");
            }

            else {
                return (double) Math.round((sumValue * Math.pow(1 + (percentageValue / 100), yearsValue)) * 100) / 100;
            }

        } catch (NumberFormatException | NullPointerException ex) {
            throw new IllegalArgumentException("Неверный формат данных. Скорректируйте значения", ex);
        }
    }

}