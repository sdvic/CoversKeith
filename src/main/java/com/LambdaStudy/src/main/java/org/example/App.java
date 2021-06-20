package org.example;
import java.util.ArrayList;
/**
 * Hello world!
 */
public class App
{
    public static void main(String[] args)
    {
        ArrayList<String> list1 = new ArrayList<>();
        list1.add("1");
        list1.add("2");
        list1.add("3");
        ArrayList<String> list2 = new ArrayList<>();
        list2.add("4");
        list2.add("5");
        list2.add("6");
        ArrayList<ArrayList> lists = new ArrayList<>();
        lists.add(list1);
        lists.add(list1);
        lists.add(list1);
        lists.add(list1);
        lists.add(list1);
        lists.add(list2);
        lists.add(list2);
        lists.add(list2);
        lists.add(list2);
        lists.add(list2);
        lists.add(list2);
        for (ArrayList a : lists)
        {
            for (ArrayList aa : lists)
            {
                System.out.println(a);
            }
            System.out.println("Next ArrayList => ");
        }
    }
}
