package juc.thread;

public class SleepInterruptDemo {
    private static final Object LOCK = new Object();

    public static void main(String[] args) throws InterruptedException {
        Thread sleeper = new Thread(() -> {
            synchronized (LOCK) {                    // ⬅️ 持有锁
                System.out.println("T1 got lock, sleep 10s …");
                try {
                    Thread.sleep(10_000);           // ⬅️ 不释放锁
                } catch (InterruptedException e) {
                    System.out.println("T1 is interrupted while sleeping");
                }
                System.out.println("T1 exits sync block");
            }
        });
        sleeper.start();

        // 确保 T1 先拿到锁
        Thread.sleep(500);

        Thread blocked = new Thread(() -> {
            synchronized (LOCK) {                   // 一直卡在这里
                System.out.println("T2 acquired lock");
            }
        });
        blocked.start();

        Thread.sleep(2_000);                        // 2 秒后尝试取消
        System.out.println("Main interrupt T1");
        sleeper.interrupt();
    }
}
