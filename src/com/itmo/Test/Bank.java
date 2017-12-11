package com.itmo.Test;

import java.util.HashMap;

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

        bank.transferMoney(acc1, acc2, 100);
    }

    public TxResult transferMoney(Account src, Account dest, int amount){

        if (amount > src.getBalance()){
            System.out.println("It's not enough money on " + src.getId());
            return TxResult.NOT_ENOUGH;
        }

        User usr1 = usersMap.get(src.getId());
        User usr2 = usersMap.get(src.getId());

        Mailer mailerSrc = new Mailer(usr1, amount * -1, src);
        mailerSrc.run();

        Mailer mailerDest = new Mailer(usr2, amount, dest);
        mailerDest.run();

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

    public void addUser(User usr){
        usersMap.put(usr.getId(), usr);
    }

    public void addAccount(Account acc){
        accountMap.put(acc.getId(), acc);
    }

}
