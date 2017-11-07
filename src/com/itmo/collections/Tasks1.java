package com.itmo.collections;

import com.itmo.collections.inner.MessageGenerator;
import com.itmo.collections.inner.Message;
import com.itmo.collections.inner.MessagePriority;

import javax.swing.text.html.HTMLDocument;
import java.util.*;

/**
 * Created by xmitya on 17.10.16.
 */
public class Tasks1 {

    public static void main(String[] args) {
        MessageGenerator generator = new MessageGenerator();

        List<Message> messages = generator.generate(100);

        countEachPriority(messages);
        countCountEachCode(messages);
        countUniqueMessages(messages);

        System.out.println("Genuine messages in natural order: \n" + genuineMessagesInOriginalOrder(messages));

        removeEach(generator.generate(100), MessagePriority.LOW);
        removeOther(generator.generate(100), MessagePriority.URGENT);
 }

    private static void countEachPriority(List<Message> messages) {

        HashMap<MessagePriority, Integer> hashMap = new HashMap<>();

        for (Message message : messages){
            MessagePriority currentPriority = message.getPriority();
            if (hashMap.containsKey(currentPriority))
                hashMap.put(currentPriority, hashMap.get(currentPriority) + 1);
            else
                hashMap.put(currentPriority, 1);
        }

        for (Map.Entry<MessagePriority, Integer> me : hashMap.entrySet()){
            System.out.println(me.getKey() + ": " + me.getValue());
        }

    }

    private static void countCountEachCode(List<Message> messages) {
        HashMap<Integer, Integer> hashMap = new HashMap<>();

        for (Message message : messages){
            Integer currentInt = message.getCode();
            if (hashMap.containsKey(currentInt))
                hashMap.put(currentInt, hashMap.get(currentInt) + 1);
            else
                hashMap.put(currentInt, 1);
        }

        for (Map.Entry<Integer, Integer> me : hashMap.entrySet()){
            System.out.println("Code " + me.getKey() + " meet " + me.getValue() + " times");
        }

    }

    private static void countUniqueMessages(List<Message> messages) {
        HashMap<Message, Integer> hashMap = new HashMap<>();

        for (Message message : messages){
            if (hashMap.containsKey(message))
                hashMap.put(message, hashMap.get(message) + 1);
            else
                hashMap.put(message, 1);
        }

        for (Map.Entry<Message, Integer> me : hashMap.entrySet()){
            System.out.println("Unique message " + me.getKey().toString() + " meet " + me.getValue() + " times");
        }
    }

    private static List<Message> genuineMessagesInOriginalOrder(List<Message> messages) {

        LinkedHashMap<Message, Integer> hashMap = new LinkedHashMap<>();

        for (Message message : messages){
            if (!hashMap.containsKey(message))
                hashMap.put(message, 1);
        }

        messages.clear();

        for (Map.Entry<Message, Integer> me : hashMap.entrySet()){
            messages.add(me.getKey());
        }

        return messages;
    }

    private static void removeEach(Collection<Message> messages, MessagePriority priority) {
        // Удалить из коллекции каждое сообщение с заданным приоритетом.

        System.out.printf("Before remove each: %s, %s\n", priority, messages);

        Iterator itr = messages.iterator();
        while (itr.hasNext()) {
            Message m = (Message) itr.next();
            if (m.getPriority() == priority)
                itr.remove();
        }

        System.out.printf("After remove each: %s, %s\n", priority, messages);
    }

    private static void removeOther(Collection<Message> messages, MessagePriority priority) {
        // Удалить из коллекции все сообщения, кроме тех, которые имеют заданный приоритет.
        System.out.printf("Before remove other: %s, %s\n", priority, messages);

        Iterator itr = messages.iterator();
        while (itr.hasNext()) {
            Message m = (Message) itr.next();
            if (m.getPriority() != priority)
                itr.remove();
        }

        System.out.printf("After remove other: %s, %s\n", priority, messages);
    }
}
