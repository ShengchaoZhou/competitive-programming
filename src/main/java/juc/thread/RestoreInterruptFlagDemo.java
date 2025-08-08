package juc.thread;

public class RestoreInterruptFlagDemo implements Runnable {
    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {   // 自己轮询
            try {
                Thread.sleep(2_000);                        // 阻塞点
                System.out.println("working …");
            } catch (InterruptedException e) {
                // 恢复标记，交给上层决定是否退出
                Thread.currentThread().interrupt();
            }
        }
        System.out.println("detected interrupt flag, exit gracefully");
    }

    public static void main(String[] args) throws InterruptedException {
        Thread t = new Thread(new RestoreInterruptFlagDemo());
        t.start();
        Thread.sleep(3_000);
        t.interrupt();                                      // 请求取消
    }
}
