package juc.thread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Main {

    // 按你给的实现小改：finally 解锁 + 公平锁可选
    static class FooBar {
        private final int n;
        // 公平锁可减少极端情况下的偏向（可改为 new ReentrantLock()）
        private final ReentrantLock lock = new ReentrantLock(true);
        private final Condition[] c = { lock.newCondition(), lock.newCondition() };
        private int turn = 0; // 0 先打印 foo，1 先打印 bar

        public FooBar(int n) {
            this.n = n;
        }

        public void foo(Runnable printFoo) throws InterruptedException {
            for (int i = 0; i < n; i++) {
                lock.lock();
                try {
                    while (turn != 0) c[0].await();
                    // printFoo.run() outputs "foo". Do not change or remove this line.
                    printFoo.run();
                    turn = 1;
                    c[1].signal();
                } finally {
                    lock.unlock();
                }
            }
        }

        public void bar(Runnable printBar) throws InterruptedException {
            for (int i = 0; i < n; i++) {
                lock.lock();
                try {
                    while (turn != 1) c[1].await();
                    // printBar.run() outputs "bar". Do not change or remove this line.
                    printBar.run();
                    turn = 0;
                    c[0].signal();
                } finally {
                    lock.unlock();
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        int n = 5; // 打印次数，可自行修改
        FooBar fooBar = new FooBar(n);

        Thread t1 = new Thread(() -> {
            try {
                fooBar.foo(() -> System.out.print("foo"));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        Thread t2 = new Thread(() -> {
            try {
                fooBar.bar(() -> System.out.print("bar"));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        t1.start();
        t2.start();
        t1.join();
        t2.join();

        System.out.println(); // 换行，便于观察输出
    }
}
