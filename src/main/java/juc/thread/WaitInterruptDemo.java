package juc.thread;

public class WaitInterruptDemo {
    private static final Object MONITOR = new Object();

    public static void main(String[] args) throws InterruptedException {
        Thread waiter = new Thread(() -> {
            synchronized (MONITOR) {
                try {
                    System.out.println("T1 enter wait");
                    MONITOR.wait();                 // ⬅️ 立即释放锁
                    System.out.println("T1 resumed normally");
                } catch (InterruptedException e) {
                    System.out.println("T1 interrupted while waiting");
                }
            }
        });
        waiter.start();

        Thread.sleep(1_000);
        System.out.println("Main interrupt T1");
        waiter.interrupt();                         // ⬅️ 中断而非 notify

        // 再做一次正常 notify，发现等待队列是空的
        synchronized (MONITOR) {
            MONITOR.notify();
        }
    }
}
