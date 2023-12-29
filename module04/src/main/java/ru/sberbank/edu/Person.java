package ru.sberbank.edu;

import java.util.Comparator;
import java.util.Objects;

/**
 * Класс для хранения данных о человеке.
 */
public class Person implements Comparable<Person> {
    private final String name;
    private final String city;
    private int age;

    /**
     * Конструктор класса Person.
     *
     * @param name Имя человека.
     * @param city Город проживания человека.
     * @param age  Возраст человека.
     */
    public Person(String name, String city, int age) {
        this.age = age;
        if (city == null || name == null) {
            throw new IllegalArgumentException("Имя и город должны быть заполнены");
        }
        if (age < 0) {
            throw new IllegalArgumentException("Возраст не может быть меньше 0");
        }
        this.name = name;
        this.city = city;
    }

    /**
     * Получить имя человека.
     *
     * @return Имя человека.
     */
    public String getName() {
        return name;
    }

    /**
     * Получить город проживания человека.
     *
     * @return Город проживания человека.
     */
    public String getCity() {
        return city;
    }

    /**
     * Получить возраст человека.
     *
     * @return Возраст человека.
     */
    public int getAge() {
        return age;
    }

    /**
     * Установить возраст человека.
     *
     * @param age Возраст человека.
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * Проверка на равенство с другим объектом Person.
     *
     * @param obj Объект, с которым производится сравнение.
     * @return true, если объекты равны, иначе false.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        Person person = (Person) obj;

        if (age != person.age) {
            return false;
        }
        if (!name.equalsIgnoreCase(person.name)) {
            return false;
        }
        return city.equalsIgnoreCase(person.city);
    }

    /**
     * Вычисление хеш-кода объекта Person.
     *
     * @return Хеш-код объекта.
     */
    @Override
    public int hashCode() {

        int nameHash = Objects.hashCode(name.toLowerCase());
        int cityHash = Objects.hashCode(city.toLowerCase());
        int ageHash = Objects.hashCode(age);
        return nameHash + cityHash + ageHash;
    }

    /**
     * Сравнение объектов Person для упорядочивания.
     * Сортировка сначала по полю city, а затем по полю name.
     *
     * @param obj Объект, с которым производится сравнение.
     * @return Результат сравнения: отрицательное число, если текущий объект меньше, положительное число, если текущий
     * объект больше, иначе 0.
     * @throws IllegalArgumentException Если имя или город объекта `obj` равны `null`.
     */
    @Override
    public int compareTo(Person obj) {
        return Comparator.comparing((Person p) -> p.getCity().toLowerCase())
                .thenComparing((Person p) -> p.getName().toLowerCase())
                .compare(this, obj);
    }
}