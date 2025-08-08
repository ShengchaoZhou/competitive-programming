package solutions.others;

public class AlternatePrint {
    private static final Object lock = new Object();
    private static int x = 1;
    private static final int limit = 30;

    public static void main(String[] args) {
        Runnable task = () -> {
            while (true) {
                synchronized (lock) {
                    if (x > limit) {
                        lock.notify();
                        break;
                    }
                    System.out.printf("%s: %d%n", Thread.currentThread().getName(), x++);
                    lock.notify(); // 叫醒对方
                    try {
                        if (x <= limit) lock.wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        };

        Thread t1 = new Thread(task, "T1");
        Thread t2 = new Thread(task, "T2");
        t1.start();
        t2.start();
    }
}
