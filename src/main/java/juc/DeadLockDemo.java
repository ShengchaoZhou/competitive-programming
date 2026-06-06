package juc;

import java.util.concurrent.CountDownLatch;

public class DeadLockDemo {
    private static final Object resource1 = new Object(); // 资源1
    private static final Object resource2 = new Object(); // 资源2
    private static final CountDownLatch latch = new CountDownLatch(2);

    public static void main(String[] args) {
        new Thread(() -> {
            synchronized (resource1) {
                System.out.println(Thread.currentThread().getName() + " get resource1");
                latch.countDown();        // 告知：我已拿到第一个锁
                try {
                    latch.await();        // 等待对方也拿到它的第一个锁
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                System.out.println(Thread.currentThread().getName() + " waiting get resource2");
                synchronized (resource2) {
                    System.out.println(Thread.currentThread().getName() + " get resource2");
                }
            }
        }, "线程 1").start();

        new Thread(() -> {
            synchronized (resource2) {
                System.out.println(Thread.currentThread().getName() + " get resource2");
                latch.countDown();        // 告知：我已拿到第一个锁
                try {
                    latch.await();        // 等待对方也拿到它的第一个锁
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                System.out.println(Thread.currentThread().getName() + " waiting get resource1");
                synchronized (resource1) {
                    System.out.println(Thread.currentThread().getName() + " get resource1");
                }
            }
        }, "线程 2").start();
    }
}
