package testthread;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by ljzzkkkss on 2019/3/24.
 */
public class CountDownLatchSample {
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch latch  = new CountDownLatch(3);

        ExecutorService service = Executors.newFixedThreadPool(3);

        for(int i = 0; i < 3; i++) {
            service.submit(() -> {
                System.out.printf("Thread  %s executed!\n", Thread.currentThread().getName());
                latch.countDown();
            });
        }

        latch.await();
        System.out.println("down");

        service.shutdown();
    }
}
