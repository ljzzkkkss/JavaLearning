package testthread;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
public class CyclicBarrierSample {
    public static void main(String[] args) throws InterruptedException {
       	CyclicBarrier barrier = new CyclicBarrier(5, () -> System.out.println("Action...GO again!"));
       	for (int i = 0; i < 5; i++) {
               	Thread t = new Thread(new CyclicWorker(barrier));
               	t.start();
       	}
    }
    static class CyclicWorker implements Runnable {
       	private CyclicBarrier barrier;
       	public CyclicWorker(CyclicBarrier barrier) {
               	this.barrier = barrier;
       	}
       	@Override
       	public void run() {
               	try {
                       	for (int i=0; i<3 ; i++){
                               	System.out.printf("Executed%s!\n",i);
                               	barrier.await();
                       	}
               	} catch (BrokenBarrierException e) {
                       	e.printStackTrace();
               	} catch (InterruptedException e) {
                       	e.printStackTrace();
               	}
 	     }
    }
}

