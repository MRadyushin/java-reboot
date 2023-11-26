package ru.sberbank.edu.impl;

import ru.sberbank.edu.CustomArray;

import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;

public class CustomArrayImpl<T> implements CustomArray<T> {

    private static final int CAPASITY = 10;
    private int size;
    private Object[] elements;

    public CustomArrayImpl() {
        this.elements = new Object[CAPASITY];
        this.size = 0;
    }

    /**
     * Конструктор CustomArrayImpl со стартовой вместимостью.
     *
     * @param startCapacity - стартовая вместимость массива
     * @throws IllegalArgumentException если стартовая вместимость отрицательная
     */
    public CustomArrayImpl(int startCapacity) {
        if (startCapacity < 0) {
            throw new IllegalArgumentException("Вместительность не может быть отрицательной: " + startCapacity);
        }
        this.elements = new Object[startCapacity];
        this.size = 0;
    }

    /**
     * Конструктор, создающий объект CustomArrayImpl на основе переданной коллекции.
     *
     * @param collection - коллекция
     * @throws IllegalArgumentException если переданная коллекция равна null
     */
    public CustomArrayImpl(Collection<T> collection) {
        if (collection == null) {
            throw new IllegalArgumentException("Коллекция не может быть null");
        }
        int size = collection.size();
        this.elements = new Object[size];
        this.size = size;
        int index = 0;
        for (T element : collection) {
            this.elements[index] = element;
            index++;
        }
    }

    /**
     * Вспомогательный метод для проверки выхода индекса за границы массива
     */
    private void checkingArrayBounds(int index) {
        if (index < 0 || index >= size) {
            throw new ArrayIndexOutOfBoundsException("Индекс находится за границами массива: " + index);
        }
    }

    /**
     * Метод возвращающий количество элементов в массиве
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Метод проверяет, является ли массив пустым
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Метод добавляет один элемент в массив
     *
     * @param item - элемент
     */
    @Override
    public boolean add(T item) {
        ensureCapacity(size + 1);
        elements[size++] = item;
        return true;
    }

    /**
     * Метод добавляет все элементы из массива в конец массива
     *
     * @param items - массив элементов
     * @throws IllegalArgumentException - если items равен null
     */
    @Override
    public boolean addAll(T[] items) {
        if (items == null) {
            throw new IllegalArgumentException("Массив не может быть равен null");
        }
        ensureCapacity(size + items.length);
        System.arraycopy(items, 0, elements, size, items.length);
        size += items.length;
        return true;
    }

    /**
     * Метод добавляет все элементы из коллекции в конец массива
     *
     * @param items - коллекция элементов
     * @throws IllegalArgumentException если items равен null
     */
    @Override
    @SuppressWarnings("unchecked")
    public boolean addAll(Collection<T> items) {
        if (items == null) {
            throw new IllegalArgumentException("Коллекция не может быть равна null");
        }
        return addAll((T[]) items.toArray());
    }

    /**
     * Метод добавляет все элементы в текущую позицию массива
     *
     * @param index - индекс
     * @param items - элементы
     * @throws ArrayIndexOutOfBoundsException если индекс выходит за пределы массива
     * @throws IllegalArgumentException       если items равен null
     */
    @Override
    public boolean addAll(int index, T[] items) {
        if (index < 0 || index > size) {
            throw new ArrayIndexOutOfBoundsException("Индекс находится за границами массива");
        }
        if (items == null) {
            throw new IllegalArgumentException("Массив не может быть равен null");
        }
        ensureCapacity(size + items.length);
        System.arraycopy(elements, index, elements, index + items.length, size - index);
        System.arraycopy(items, 0, elements, index, items.length);
        size += items.length;
        return true;
    }

    /**
     * Метод возвращает элемент по индексу
     *
     * @param index - индекс
     * @throws ArrayIndexOutOfBoundsException если индекс выходит за пределы массива
     */
    @Override
    @SuppressWarnings("unchecked")
    public T get(int index) {
        checkingArrayBounds(index);
        return (T) elements[index];
    }

    /**
     * Метод заменяет элемент по индексу новым элементом
     *
     * @param index - индекс
     * @param item  - элемент
     * @throws ArrayIndexOutOfBoundsException если индекс выходит за пределы массива
     */
    @Override
    @SuppressWarnings("unchecked")
    public T set(int index, T item) {
        checkingArrayBounds(index);
        T oldValue = (T) elements[index];
        elements[index] = item;
        return oldValue;
    }

    /**
     * Метод удаляет элемент по индексу
     *
     * @param index индекс
     * @throws ArrayIndexOutOfBoundsException если индекс выходит за пределы массива
     */
    @Override
    public void remove(int index) {
        checkingArrayBounds(index);
        System.arraycopy(elements, index + 1, elements, index, size - index - 1);
        elements[--size] = null;
    }

    /**
     * Метод удаляет элемента из массива
     *
     * @param item элемент
     */
    @Override
    public boolean remove(T item) {
        int index = indexOf(item);
        if (index != -1) {
            remove(index);
            return true;
        }
        return false;
    }

    /**
     * Метод проверяет, содержит ли массив указанный элемент
     *
     * @param item элемент
     */
    @Override
    public boolean contains(T item) {
        return indexOf(item) != -1;
    }

    /**
     * Метод возвращает индекс элемента в массиве
     *
     * @param item элемент
     */
    @Override
    public int indexOf(T item) {
        for (int i = 0; i < size; i++) {
            if (Objects.equals(item, elements[i])) {
                return i;
            }
        }

        return -1;
    }

    /**
     * Метод увеличивает вместимость массива
     *
     * @param newElementsCount количество новых элементов
     */
    @Override
    public void ensureCapacity(int newElementsCount) {
        if (newElementsCount > elements.length) {
            int newCapacity = Math.max(elements.length * 2, newElementsCount);
            elements = Arrays.copyOf(elements, newCapacity);
        }
    }

    /**
     * Метод возвращает текущую вместимость массива
     */
    @Override
    public int getCapacity() {
        return elements.length;
    }

    /**
     * Инвертирует порядок элементов в массиве
     */
    @Override
    @SuppressWarnings("unchecked")
    public void reverse() {
        for (int i = 0; i < size / 2; i++) {
            T temp = (T) elements[i];
            elements[i] = elements[size - 1 - i];
            elements[size - 1 - i] = temp;
        }
    }

    /**
     * Метод возвращает содержимое массива в формате '[ элемент1 элемент2 ... элементN ]' или '[ ]', если массив пустой.
     */
    @Override
    public String toString() {
        if (isEmpty()) {
            return "[ ]";
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("[ ");
            for (int i = 0; i < size; i++) {
                sb.append(elements[i]);
                sb.append(" ");
            }
            sb.append("]");
            return sb.toString();
        }
    }

    /**
     * Метод возвращает содержимое массива в формате '[ элемент1 элемент2 ... элементN ]' или '[ ]', если массив пустой.
     */
    @Override
    public Object[] toArray() {
        return Arrays.copyOf(elements, size);
    }
}