package com.itmo.collections;

import com.itmo.collections.inner.User;
import sun.reflect.generics.tree.Tree;

import java.util.*;

import static com.itmo.collections.inner.UserGenerator.generate;

/**
 * Created by xmitya on 20.10.16.
 */
public class Tasks2 {
    public static void main(String[] args) {
        NavigableSet<User> nsSortedCompanyName = sortedByCompanyAndName(generate(10));

        for (User u : nsSortedCompanyName)
            System.out.println(u.toString());

        System.out.println("/////////////////////////////////");

        NavigableSet<User> nsSortedSalaryName = sortedBySalaryAndName(generate(10));

        for (User u : nsSortedSalaryName)
            System.out.println(u.toString());

        System.out.println("/////////////////////////////////");

        NavigableSet<User> nsSalaryAgeCompanyAndName = sortedBySalaryAgeCompanyAndName(generate(10));

        for (User u : nsSalaryAgeCompanyAndName)
            System.out.println(u.toString());

        //view Iterator
        List<User> list1 = generate(5);
        List<User> list2 = generate(5);

        Iterator<User> commonItr = viewIterator(list1, list2);

        while (commonItr.hasNext())
            System.out.println(commonItr.next().toString());
    }

    private static NavigableSet<User> sortedByCompanyAndName(List<User> users) {

        class ComparatorCompanyName implements Comparator<User>{
            @Override
            public int compare(User o1, User o2) {
                int compareCom = o1.getCompany().compareTo(o2.getCompany());
                return compareCom == 0 ? o1.getName().compareTo(o2.getName()) : compareCom;
            }
        }

        NavigableSet<User> nsUser = new TreeSet<>(new ComparatorCompanyName());
        nsUser.addAll(users);

        return nsUser;

    }

    private static NavigableSet<User> sortedBySalaryAndName(List<User> users) {

        class ComparatorSalaryName implements Comparator<User>{
            @Override
            public int compare(User o1, User o2) {
                int compareSal = o1.getSalary() - o2.getSalary();
                return compareSal == 0 ? o1.getName().compareTo(o2.getName()) : compareSal;
            }
        }

        NavigableSet<User> nsUser = new TreeSet<>(new ComparatorSalaryName());
        nsUser.addAll(users);

        return nsUser;
    }

    private static NavigableSet<User> sortedBySalaryAgeCompanyAndName(List<User> users) {

        class ComparatorSalaryName implements Comparator<User>{
            @Override
            public int compare(User o1, User o2) {
                int result = 0;
                int compareSal = o1.getSalary() - o2.getSalary();
                int compareAge = o1.getAge() - o2.getAge();
                int compareComp = o1.getCompany().compareTo(o2.getCompany());
                int compareName = o1.getName().compareTo(o2.getName());
                if (compareSal == 0) {
                    if (compareAge == 0){
                        if (compareComp == 0)
                            return compareName;
                        else return compareComp;
                    }
                    return compareAge;
                }
                return compareSal;
            }
        }

        NavigableSet<User> nsUser = new TreeSet<>(new ComparatorSalaryName());
        nsUser.addAll(users);

        return nsUser;
    }

    private static <T> Iterator<T> viewIterator(Iterable<T> it1, Iterable<T> it2) {

        Iterator itrUsers = new Iterator() {
            Iterator itr1 = it1.iterator();
            Iterator itr2 = it2.iterator();
            Iterator currentIterator = itr1;

            @Override
            public boolean hasNext() {
                boolean b = currentIterator.hasNext();
                if (b)
                    return true;
                else {
                    if (currentIterator == itr2)
                        return false;
                    else {
                        currentIterator = itr2;
                        return itr2.hasNext();
                    }
                }
            }

            @Override
            public Object next() {
                return currentIterator.next();
            }
        };

        return itrUsers;
    }


}
