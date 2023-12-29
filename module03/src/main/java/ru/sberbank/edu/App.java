package ru.sberbank.edu;

import ru.sberbank.edu.impl.CustomArrayImpl;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        CustomArrayImpl<Integer> list = new CustomArrayImpl<>();
        list.add(1);
        list.add(2);
        list.add(2);
        list.add(3);

        System.out.println(list );
    }
}
