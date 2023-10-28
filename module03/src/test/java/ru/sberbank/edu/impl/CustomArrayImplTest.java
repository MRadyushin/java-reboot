package ru.sberbank.edu.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.sberbank.edu.CustomArray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CustomArrayImplTest {

    /**
     * Тестирование метода size()
     */
    @Test
    public void testSize() {
        CustomArrayImpl<Integer> list = new CustomArrayImpl<>();
        Assertions.assertEquals(0, list.size());

        list.add(1);
        list.add(2);
        list.add(3);
        Assertions.assertEquals(3, list.size());

        list.remove(0);
        Assertions.assertEquals(2, list.size());
    }

    /**
     * Тестирование метода isEmpty()
     */
    @Test
    public void testIsEmpty() {
        CustomArrayImpl<String> list = new CustomArrayImpl<>();
        Assertions.assertTrue(list.isEmpty());

        list.add("a");
        Assertions.assertFalse(list.isEmpty());

        list.remove(0);
        Assertions.assertTrue(list.isEmpty());
    }

    /**
     * Тестирование метода add()
     */
    @Test
    public void testAdd() {
        CustomArrayImpl<Integer> list = new CustomArrayImpl<>();
        Assertions.assertTrue(list.add(1));
        Assertions.assertTrue(list.add(2));
        Assertions.assertTrue(list.add(3));
        Assertions.assertEquals(3, list.size());
        Assertions.assertEquals(Integer.valueOf(1), list.get(0));
        Assertions.assertEquals(Integer.valueOf(2), list.get(1));
        Assertions.assertEquals(Integer.valueOf(3), list.get(2));
    }

    /**
     * Тестирование метода addAll() с передачей массива в качестве аргумента
     */
    @Test
    public void testAddAllFromArray() {
        CustomArrayImpl<Integer> list = new CustomArrayImpl<>();
        Integer[] array = {1, 2, 3};
        Assertions.assertTrue(list.addAll(array));
        Assertions.assertEquals(3, list.size());
        Assertions.assertEquals(Integer.valueOf(1), list.get(0));
        Assertions.assertEquals(Integer.valueOf(2), list.get(1));
        Assertions.assertEquals(Integer.valueOf(3), list.get(2));
    }

    /**
     * Тестирование метода addAll() с передачей коллекции в качестве аргумента
     */
    @Test
    public void testAddAllFromCollection() {
        CustomArrayImpl<String> list = new CustomArrayImpl<>();
        list.add("a");
        list.add("b");
        list.add("c");

        Assertions.assertTrue(list.addAll(Arrays.asList("d", "e", "f")));
        Assertions.assertEquals(6, list.size());
        Assertions.assertEquals("a", list.get(0));
        Assertions.assertEquals("b", list.get(1));
        Assertions.assertEquals("c", list.get(2));
        Assertions.assertEquals("d", list.get(3));
        Assertions.assertEquals("e", list.get(4));
        Assertions.assertEquals("f", list.get(5));
    }

    /**
     * Тестирование метода addAll() с передачей массива в качестве аргумента и указанием индекса
     */

    @Test
    public void testAddAllAtIndexFromArray() {
        CustomArrayImpl<Integer> list = new CustomArrayImpl<>();
        list.add(1);
        list.add(2);
        list.add(3);

        Integer[] array = {4, 5};

        Assertions.assertTrue(list.addAll(1, array));
        Assertions.assertEquals(5, list.size());
        Assertions.assertEquals(Integer.valueOf(1), list.get(0));
        Assertions.assertEquals(Integer.valueOf(4), list.get(1));
        Assertions.assertEquals(Integer.valueOf(5), list.get(2));
        Assertions.assertEquals(Integer.valueOf(2), list.get(3));
        Assertions.assertEquals(Integer.valueOf(3), list.get(4));
    }

    /**
     * Тестирование метода get()
     */
    @Test
    public void testGet() {
        CustomArrayImpl<String> list = new CustomArrayImpl<>();
        list.add("a");
        list.add("b");
        list.add("c");

        Assertions.assertEquals("a", list.get(0));
        Assertions.assertEquals("b", list.get(1));
        Assertions.assertEquals("c", list.get(2));
    }

    /**
     * Тестирование метода set()
     */
    @Test
    public void testSet() {
        CustomArrayImpl<String> list = new CustomArrayImpl<>();
        list.add("a");
        list.add("b");
        list.add("c");

        Assertions.assertEquals("b", list.set(1, "d"));
        Assertions.assertEquals("d", list.get(1));
    }

    /**
     * Тестирование метода remove() по индексу
     */
    @Test
    public void testRemoveByIndex() {
        CustomArrayImpl<Integer> list = new CustomArrayImpl<>();
        list.add(1);
        list.add(2);
        list.add(3);

        list.remove(1);

        Assertions.assertEquals(2, list.size());
        Assertions.assertEquals(Integer.valueOf(1), list.get(0));
        Assertions.assertEquals(Integer.valueOf(3), list.get(1));
    }

    /**
     * Тестирование метода remove() по значению
     */
    @Test
    public void testRemoveByValue() {
        CustomArrayImpl<String> list = new CustomArrayImpl<>();
        list.add("a");
        list.add("b");
        list.add("c");
        list.add("d");

        Assertions.assertTrue(list.remove("b"));
        Assertions.assertEquals(3, list.size());
        Assertions.assertEquals("a", list.get(0));
        Assertions.assertEquals("c", list.get(1));
        Assertions.assertEquals("d", list.get(2));
    }

    /**
     * Тестирование метода contains().
     */
    @Test
    public void testContains() {
        CustomArrayImpl<Integer> list = new CustomArrayImpl<>();
        list.add(1);
        list.add(2);
        list.add(3);

        Assertions.assertTrue(list.contains(2));
        Assertions.assertFalse(list.contains(4));
    }

    /**
     * Тестирование метода indexOf()
     */
    @Test
    public void testIndexOf() {
        CustomArrayImpl<String> list = new CustomArrayImpl<>();
        list.add("a");
        list.add("b");
        list.add("c");
        list.add("d");

        Assertions.assertEquals(0, list.indexOf("a"));
        Assertions.assertEquals(1, list.indexOf("b"));
        Assertions.assertEquals(2, list.indexOf("c"));
        Assertions.assertEquals(3, list.indexOf("d"));
        Assertions.assertEquals(-1, list.indexOf("e"));

    }

    /**
     * Тестирование метода ensureCapacity()
     */
    @Test
    public void testEnsureCapacity() {
        CustomArrayImpl<Integer> list = new CustomArrayImpl<>();
        list.ensureCapacity(10);

        Assertions.assertEquals(10, list.getCapacity());
    }

    /**
     * Тестирование метода getCapacity().
     */
    @Test
    public void testGetCapacity() {
        CustomArrayImpl<String> list = new CustomArrayImpl<>();
        Assertions.assertEquals(10, list.getCapacity());

        list.add("a");
        list.add("b");
        list.add("c");
        Assertions.assertEquals(10, list.getCapacity());
    }

    /**
     * Тестирование метода reverse()
     */
    @Test
    public void testReverse() {
        CustomArrayImpl<Integer> list = new CustomArrayImpl<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);

        list.reverse();

        Assertions.assertEquals(Integer.valueOf(5), list.get(0));
        Assertions.assertEquals(Integer.valueOf(4), list.get(1));
        Assertions.assertEquals(Integer.valueOf(3), list.get(2));
        Assertions.assertEquals(Integer.valueOf(2), list.get(3));
        Assertions.assertEquals(Integer.valueOf(1), list.get(4));
    }

    /**
     * Тестирование метода toArray()
     */
    @Test
    public void testToArray() {
        CustomArrayImpl<String> list = new CustomArrayImpl<>();
        list.add("a");
        list.add("b");
        list.add("c");

        Object[] array = list.toArray();

        Assertions.assertEquals(3, array.length);
        Assertions.assertEquals("a", array[0]);
        Assertions.assertEquals("b", array[1]);
        Assertions.assertEquals("c", array[2]);
    }

    /**
     * Тестирование конструктора с указанием стартового значения
     */
    @Test
    public void testConstructorWithValidCapacity() {
        int initialCapacity = 10;
        CustomArrayImpl<Integer> array = new CustomArrayImpl<>(initialCapacity);

        Assertions.assertEquals(initialCapacity, array.getCapacity());
        Assertions.assertEquals(0, array.size());
    }

    /**
     * Тестирование конструктора с отрицательным стартовым значением
     */
    @Test
    public void testConstructorWithNegativeCapacity() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            int initialCapacity = -1;
            new CustomArrayImpl<>(initialCapacity);
        });
    }

    /**
     * Тестирование конструктора с нулевым стартовым значением
     */
    @Test
    public void testConstructorWithZeroCapacity() {
        int initialCapacity = 0;
        CustomArrayImpl<String> array = new CustomArrayImpl<>(initialCapacity);

        Assertions.assertEquals(initialCapacity, array.getCapacity());
        Assertions.assertEquals(0, array.size());
    }

    /**
     * Тестирование метода toString() для пустого массива
     */
    @Test
    public void testToString_EmptyArray() {
        CustomArray<Integer> array = new CustomArrayImpl<>();
        String expected = "[ ]";
        String actual = array.toString();
        Assertions.assertEquals(expected, actual);
    }

    /**
     * Тестирование метода toString() для непустого массива
     */
    @Test
    public void testToString_NonEmptyArray() {
        CustomArray<Integer> array = new CustomArrayImpl<>();
        array.add(1);
        array.add(2);
        array.add(3);
        String expected = "[ 1 2 3 ]";
        String actual = array.toString();
        Assertions.assertEquals(expected, actual);
    }

    /**
     * Тестирование конструктора, принимающего коллекцию
     */
    @Test
    public void testConstructorWithCollection() {
        List<String> collection = new ArrayList<>();
        collection.add("a");
        collection.add("b");
        collection.add("c");

        CustomArrayImpl<String> array = new CustomArrayImpl<>(collection);

        Assertions.assertEquals(3, array.size());
        Assertions.assertEquals("a", array.get(0));
        Assertions.assertEquals("b", array.get(1));
        Assertions.assertEquals("c", array.get(2));
    }

    /**
     * Тестирование конструктора, принимающего коллекцию равную null
     */

    @Test()
    public void testConstructorWithNullCollection() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> new CustomArrayImpl<>(null));
    }
}