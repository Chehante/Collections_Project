package com.itmo.multithreading.Sinchronized;

public class Test {

    public int cnt = 0;

    public static void main(String[] args) throws InterruptedException {

        Test sin = new Test();

        Thread thr1 = sin.new incrementor();
        Thread thr2 = sin.new incrementor();

        thr1.run();
//        thr1.start();
        thr2.start();

        Thread.sleep(1000);

        System.out.println(Integer.toString(sin.cnt));

    }

    public class incrementor extends Thread {
        @Override
        public synchronized void run() {
            for (int i = 0; i < 20000; i++) {
                    cnt++;
            }
        }

    }
}
