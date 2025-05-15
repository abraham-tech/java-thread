/*
 * Copyright (c) 2025. Michael Pogrebinsky - Top Developer Academy
 * https://topdeveloperacademy.com
 * All rights reserved
 */


class Counter {
    private int count = 0;

    public synchronized void  increment() {
        count ++;
    }

    public int getCount() {
        return count;
    }
}

class Incrementer implements Runnable {
    private Counter counter;

    public Incrementer(Counter counter) {
        this.counter = counter;
    }

    @Override
    public void run() {
        for (int i = 0; i < 1000; i++){
            counter.increment();
        }

    }
}

public class NonAtomicExample {
    public static void main(String[] args) throws InterruptedException {
        Counter sharedCounter = new Counter();
        Thread thread1 = new Thread(new Incrementer(sharedCounter));
        Thread thread2 = new Thread(new Incrementer(sharedCounter));

        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();

        System.out.println("Final Count: " + sharedCounter.getCount());

    }
}
