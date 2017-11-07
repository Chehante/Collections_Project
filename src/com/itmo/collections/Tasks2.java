package com.itmo.collections;

import com.itmo.collections.inner.Message;
import com.itmo.collections.inner.MessagePriority;
import com.itmo.collections.inner.User;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NavigableSet;

import static com.itmo.collections.inner.UserGenerator.generate;

/**
 * Created by xmitya on 20.10.16.
 */
public class Tasks2 {
    public static void main(String[] args) {
        System.out.println(generate(10));
    }

    private static void sortByPriority(List<Message> messages, MessagePriority priority) {

    }

    private static NavigableSet<User> sortedByCompanyAndName(List<User> users) {

        return Collections.emptyNavigableSet();
    }

    private static NavigableSet<User> sortedBySalaryAndName(List<User> users) {

        return Collections.emptyNavigableSet();
    }

    private static NavigableSet<User> sortedBySalaryAgeCompanyAndName(List<User> users) {

        return Collections.emptyNavigableSet();
    }

    private static <T> Iterator<T> viewIterator(Iterable<T> it1, Iterable<T> it2) {

        return null;
    }


}
