package ru.sberbank.edu;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Юнит-тесты для класса CustomDigitComparator.
 */
class CustomDigitComparatorTest {

    /**
     * Проверяет сравнение двух четных чисел.
     * Ожидается, что результат сравнения будет равен 0.
     */
    @Test
    public void testCompare_BothNumbersEven() {
        CustomDigitComparator comparator = new CustomDigitComparator();

        int result = comparator.compare(6, 8);

        Assertions.assertEquals(0, result);
    }

    /**
     * Проверяет сравнение двух нечетных чисел.
     * Ожидается, что результат сравнения будет равен 0.
     */
    @Test
    public void testCompare_BothNumbersOdd() {
        CustomDigitComparator comparator = new CustomDigitComparator();

        int result = comparator.compare(3, 5);

        Assertions.assertEquals(0, result);
    }

    /**
     * Проверяет сравнение четного и нечетного числа.
     * Ожидается, что результат сравнения будет отрицательным.
     */
    @Test
    public void testCompare_FirstNumberEvenSecondNumberOdd() {
        CustomDigitComparator comparator = new CustomDigitComparator();

        int result = comparator.compare(4, 7);

        Assertions.assertTrue(result < 0);
    }

    /**
     * Проверяет сравнение нечетного и четного числа.
     * Ожидается, что результат сравнения будет положительным.
     */
    @Test
    public void testCompare_FirstNumberOddSecondNumberEven() {
        CustomDigitComparator comparator = new CustomDigitComparator();

        int result = comparator.compare(9, 2);

        Assertions.assertTrue(result > 0);
    }

    /**
     * Проверяет сравнение, когда первое число равно null.
     * Ожидается, что будет выброшено исключение IllegalArgumentException.
     */
    @Test
    public void testCompare_FirstNumberNull() {
        CustomDigitComparator comparator = new CustomDigitComparator();

        Assertions.assertThrows(IllegalArgumentException.class, () -> comparator.compare(null, 5));
    }

    /**
     * Проверяет сравнение, когда второе число равно null.
     * Ожидается, что будет выброшено исключение IllegalArgumentException.
     */
    @Test
    public void testCompare_SecondNumberNull() {
        CustomDigitComparator comparator = new CustomDigitComparator();

        Assertions.assertThrows(IllegalArgumentException.class, () -> comparator.compare(9, null));
    }
}