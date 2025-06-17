package juc.producerandconsumer;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * @author Shengchao Zhou
 * @date 2025/5/21 19:38
 */
public class test {
    public static void main(String[] args) throws InterruptedException {
        ArrayBlockingQueue<String> q = new ArrayBlockingQueue<>(5);
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    System.out.println("producer put element" + i);
                    q.put("element" + i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread t2 = new Thread(() -> {
            int cnt = 0;
            for (int i = 0; i < 10; i++) {
                try {
                    System.out.println("consumer get " + q.take());
                    if (++cnt > 10) break;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        t1.start();
        t2.start();

        t1.join();
        t2.join();
    }
}
