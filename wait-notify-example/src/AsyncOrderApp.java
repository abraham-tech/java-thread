import java.util.concurrent.*;

public class AsyncOrderApp {

    // Simulate an Order object
    static class Order {
        String orderId;
        double amount;

        boolean validated;
        boolean inventoryChecked;
        boolean paymentProcessed;

        public Order(String orderId, double amount) {
            this.orderId = orderId;
            this.amount = amount;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(4);
        Order order = new Order("ORD123", 99.99);

        CompletableFuture<Void> future = CompletableFuture.supplyAsync(() -> validateOrder(order), executor)
                .thenApplyAsync(AsyncOrderApp::checkInventory, executor)
                .thenApplyAsync(AsyncOrderApp::processPayment, executor)
                .thenAcceptAsync(AsyncOrderApp::sendNotification, executor)
                .exceptionally(ex -> {
                    System.out.println("Something went wrong: " + ex.getMessage());
                    return null;
                });

        // Wait for async pipeline to complete before exiting main
        future.join();
        executor.shutdown();
    }

    static Order validateOrder(Order order) {
        System.out.println("Validating order: " + order.orderId);
        order.validated = true;
        sleep(500);
        return order;
    }

    static Order checkInventory(Order order) {
        if (!order.validated) throw new IllegalStateException("Order not validated");
        System.out.println("Checking inventory for: " + order.orderId);
        order.inventoryChecked = true;
        sleep(500);
        return order;
    }

    static Order processPayment(Order order) {
        if (!order.inventoryChecked) throw new IllegalStateException("Inventory not checked");
        System.out.println("Processing payment for: " + order.orderId);
        order.paymentProcessed = true;
        sleep(500);
        return order;
    }

    static void sendNotification(Order order) {
        if (order.paymentProcessed) {
            System.out.println("Notification sent for order: " + order.orderId);
        }
    }

    static void sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException ignored) {}
    }
}
