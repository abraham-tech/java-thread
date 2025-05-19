/*
 * Copyright (c) 2025. Michael Pogrebinsky - Top Developer Academy
 * https://topdeveloperacademy.com
 * All rights reserved
 */

import java.util.concurrent.atomic.AtomicInteger;

public class MyClass {
    public static void main(String[] args) throws InterruptedException {
        InventoryCounter inventoryCounter = new InventoryCounter();

        DecreementThread decreementThread = new DecreementThread(inventoryCounter);
        IncrementThread incrementThread = new IncrementThread(inventoryCounter);

        decreementThread.start();
        incrementThread.start();

        incrementThread.join();
        decreementThread.join();

        System.out.println("We currently have: " + inventoryCounter.getItems() + " items. ");



    }

    public static class DecreementThread extends Thread {
        private InventoryCounter inventoryCounter;

        public DecreementThread(InventoryCounter inventoryCounter) {
            this.inventoryCounter = inventoryCounter;
        }

        @Override
        public void run(){
            for (int i = 0; i < 10000; i++) {
                inventoryCounter.decrement();
            }
        }

    }

    public static class IncrementThread extends Thread {
        private InventoryCounter inventoryCounter;
        public IncrementThread(InventoryCounter inventoryCounter) {
            this.inventoryCounter = inventoryCounter;
        }

        @Override
        public void run() {
            for (int i = 0; i < 10000; i++) {
                inventoryCounter.increment();
            }
        }

    }

    private static class InventoryCounter {
        private AtomicInteger items = new AtomicInteger(0);

        public void increment() {
            items.incrementAndGet();
        }
        public void decrement() {
            items.decrementAndGet();
        }
        public int getItems() {
            return items.get();
        }
    }
}
