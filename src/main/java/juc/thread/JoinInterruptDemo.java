package juc.thread;

public class JoinInterruptDemo {
    public static void main(String[] args) throws InterruptedException {
        Thread longTask = new Thread(() -> {
            try {
                Thread.sleep(5_000);                // 模拟长任务
                System.out.println("longTask done");
            } catch (InterruptedException e) {
                System.out.println("longTask was interrupted");
            }
        });
        longTask.start();

        Thread waiter = new Thread(() -> {
            try {
                System.out.println("T2 waiting longTask via join");
                longTask.join();                    // ⬅️ 内部是 while(isAlive()) wait()
                System.out.println("T2 resume after join");
            } catch (InterruptedException e) {
                System.out.println("T2 interrupted while join");
            }
        });
        waiter.start();

        Thread.sleep(1_000);
        System.out.println("Main interrupt T2");
        waiter.interrupt();                         // ⬅️ 强制终止顺序等待
    }
}
