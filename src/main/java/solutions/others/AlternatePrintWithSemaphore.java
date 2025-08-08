package solutions.others;

import java.util.concurrent.Semaphore;

public class AlternatePrintWithSemaphore {
    private static volatile int x = 1; // 理论上不需要volatile，但加了更直观
    private static final int limit = 30;

    private static final Semaphore odd = new Semaphore(1);
    private static final Semaphore even = new Semaphore(0);

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            while (true) {
                try {
                    odd.acquire();
                    if (x > limit) {
                        even.release();
                        break;
                    }
                    System.out.printf("%s: %d%n", Thread.currentThread().getName(), x++);
                    even.release();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }, "T1");

        Thread t2 = new Thread(() -> {
            while (true) {
                try {
                    even.acquire();
                    if (x > limit) {
                        odd.release();
                        break;
                    }
                    System.out.printf("%s: %d%n", Thread.currentThread().getName(), x++);
                    odd.release();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }, "T2");

        t1.start();
        t2.start();
    }
}
