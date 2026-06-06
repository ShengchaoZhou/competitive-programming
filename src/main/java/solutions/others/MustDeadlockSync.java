package solutions.others;

import java.util.concurrent.CountDownLatch;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

public class MustDeadlockSync {
    static final Object A = new Object();
    static final Object B = new Object();
    static final CountDownLatch bothGotFirst = new CountDownLatch(2);

    public static void main(String[] args) {
        new Thread(() -> lockInOrder(A, B), "T1").start();
        new Thread(() -> lockInOrder(B, A), "T2").start();
        new Thread(MustDeadlockSync::detectDeadlock, "Deadlock-Detector").start();
    }

    static void lockInOrder(Object first, Object second) {
        synchronized (first) {
            System.out.println(Thread.currentThread().getName() + " locked " + (first==A?"A":"B"));
            bothGotFirst.countDown();
            try { bothGotFirst.await(); } catch (InterruptedException ignored) {}
            System.out.println(Thread.currentThread().getName() + " trying " + (second==A?"A":"B"));
            synchronized (second) { // <-- 永远卡在这里（形成环形等待）
                System.out.println("never printed");
            }
        }
    }

    static void detectDeadlock() {
        ThreadMXBean mxBean = ManagementFactory.getThreadMXBean();
        for (;;) {
            long[] ids = mxBean.findDeadlockedThreads(); // 也能检测 monitor 死锁
            if (ids != null) {
                System.out.println("\n=== Deadlock Found ===");
                for (ThreadInfo ti : mxBean.getThreadInfo(ids, true, true)) {
                    System.out.println(ti);
                }
                return;
            }
            try { Thread.sleep(300); } catch (InterruptedException ignored) {}
        }
    }
}
