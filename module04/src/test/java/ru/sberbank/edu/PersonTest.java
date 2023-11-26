package ru.sberbank.edu;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class PersonTest {

    /**
     * Проверяет, возвращает ли метод getName() правильное имя человека.
     */
    @Test
    void getName_ShouldReturnCorrectName() {
        Person person = new Person("Maks", "Ryazan`", 28);
        String name = person.getName();
        Assertions.assertEquals("Maks", name);
    }

    /**
     * Проверяет, возвращает ли метод getCity() правильный город проживания человека.
     */
    @Test
    void getCity_ShouldReturnCorrectCity() {
        Person person = new Person("Maks", "Ryazan`", 28);
        String city = person.getCity();
        Assertions.assertEquals("Ryazan`", city);
    }

    /**
     * Проверяет, возвращает ли метод getAge() правильный возраст человека.
     */
    @Test
    void getAge_ShouldReturnCorrectAge() {
        Person person = new Person("Maks", "Ryazan`", 28);
        person.setAge(29);
        int age = person.getAge();
        Assertions.assertEquals(29, age);
    }

    /**
     * Проверяет, возвращает ли метод equals() true при сравнении с другим Person объектом с теми же значениями полей.
     */
    @Test
    void equals_WithEqualPersons_ShouldReturnTrue() {
        Person person1 = new Person("Maks", "Ryazan`", 28);
        Person person2 = new Person("MAKS", "Ryazan`", 28);
        boolean result = person1.equals(person2);
        Assertions.assertTrue(result);
    }

    /**
     * Проверяет, возвращает ли метод equals() false при сравнении с другим Person объектом с разными значениями полей.
     */
    @Test
    void equals_WithDifferentPersons_ShouldReturnFalse() {
        Person person1 = new Person("Maks", "Ryazan`", 28);
        Person person2 = new Person("Alex", "Ryazan`", 28);
        boolean result = person1.equals(person2);
        Assertions.assertFalse(result);
    }

    @Test
    void hashCode_WithEqualPersons_ShouldReturnSameHashCode() {
        Person person1 = new Person("Maks", "Ryazan`", 28);
        Person person2 = new Person("MAKS", "Ryazan`", 28);
        int hashCode1 = person1.hashCode();
        int hashCode2 = person2.hashCode();
        Assertions.assertEquals(hashCode1, hashCode2);
    }

    /**
     * Проверяет, возвращает ли метод compareTo() отрицательное число, если текущий объект меньше переданного объекта.
     */
    @Test
    void compareTo_WithSmallerPerson_ShouldReturnNegativeNumber() {
        Person person1 = new Person("Alex", "Ryazan`", 28);
        Person person2 = new Person("Maks", "Ryazan`", 28);
        int result = person1.compareTo(person2);
        Assertions.assertTrue(result < 0);
    }

    /**
     * Проверяет, возвращает ли метод compareTo() положительное число, если текущий объект больше переданного объекта.
     */
    @Test
    void compareTo_WithGreaterPerson_ShouldReturnPositiveNumber() {
        Person person1 = new Person("Maks", "Ryazan`", 28);
        Person person2 = new Person("Alex", "Ryazan`", 28);
        int result = person1.compareTo(person2);
        Assertions.assertTrue(result > 0);
    }

    /**
     * Проверяет, возвращает ли метод compareTo() ноль, если текущий объект равен переданному объекту.
     */
    @Test
    void compareTo_WithEqualPersons_ShouldReturnZero() {
        Person person1 = new Person("Maks", "Ryazan`", 28);
        Person person2 = new Person("MAKS", "Ryazan`", 28);
        int result = person1.compareTo(person2);
        Assertions.assertEquals(0, result);
    }
}