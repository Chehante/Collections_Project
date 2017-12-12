package com.itmo.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class Bank {

    HashMap<Integer, User> usersMap = new HashMap<>();
    HashMap<Integer, Account> accountMap = new HashMap<>();

    public static void main(String[] args) {

        Bank bank = new Bank();

        User usr1 = new User(111, "Kirill");
        bank.addUser(usr1);
        User usr2 = new User(222, "Dima");
        bank.addUser(usr2);

        Account acc1 = new Account(121212, 1000, 111);
        bank.addAccount(acc1);
        Account acc2 = new Account(232323, 1000, 111);
        bank.addAccount(acc2);
        Account acc3 = new Account(343434, 1000, 222);
        bank.addAccount(acc3);
        Account acc4 = new Account(454545, 1000, 222);
        bank.addAccount(acc4);

        for (int i = 0; i < 10; i++) {
            Random rndm = new Random();
            List<Integer> keys = new ArrayList<Integer>(bank.accountMap.keySet());
            int k = rndm.nextInt(bank.accountMap.size());
            int m = k;
            while (m == k)
                m = rndm.nextInt(bank.accountMap.size());
            Account accSrc = bank.accountMap.get(keys.get(k));
            Account accDest = bank.accountMap.get(keys.get(m));
            int ammount = rndm.nextInt(200);
            Thread thread = bank.new Transaction(accSrc, accDest, ammount);
            thread.start();
        }
    }

    private TxResult transferMoney(Account src, Account dest, int amount){

        if (amount > src.getBalance()){
            System.out.println("It's not enough money on " + src.getId());
            return TxResult.NOT_ENOUGH;
        }

        User usr1 = usersMap.get(src.getUserID());
        User usr2 = usersMap.get(dest.getUserID());

        Thread thrd1 = new Thread(new Mailer(usr1, amount * -1, src));
        thrd1.start();

        Thread thrd2 = new Thread(new Mailer(usr2, amount, dest));
        thrd2.start();

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        synchronized (src) {
            int srcBalance = src.getBalance();
            src.setBalance(srcBalance - amount);
            synchronized (dest) {
                dest.setBalance(dest.getBalance() + amount);
            }
        }

        synchronized (usr1){
            usr1.notify();
        }

        synchronized (usr2){
            usr2.notify();
        }

        return TxResult.SUCCESS;
    }

    private class Mailer implements Runnable{

        User usr;
        int amount;
        Account acc;

        public Mailer(User usr, int amount, Account acc) {
            this.usr = usr;
            this.amount = amount;
            this.acc = acc;
        }

        @Override
        public void run() {
            synchronized (usr) {
                try {
                    usr.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                usr.notification(acc, amount);
            }
        }
    }

    private void addUser(User usr){
        usersMap.put(usr.getId(), usr);
    }

    private void addAccount(Account acc){
        accountMap.put(acc.getId(), acc);
    }

    private class Transaction extends Thread{

        Account src;
        Account dest;
        int amount;

        public Transaction(Account src, Account dest, int amount) {
            this.src = src;
            this.dest = dest;
            this.amount = amount;
        }

        @Override
        public void run() {
            transferMoney(src, dest, amount);
        }
    }
}
