package juc.thread;

import java.util.concurrent.*;

/**
 * @author Shengchao Zhou
 * @date 2025/5/20 22:16
 */
public class CreateThreadDemo {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // 1.继承Thread，重写run方法
        Thread t1 = new Thread() {
            @Override
            public void run() {
                System.out.println("继承Thread");
            }
        };
        t1.start();

        //2.实现Runnable接口
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("实现Runnable接口");
            }
        });
        thread1.start();

        // 3.自己封装FutureTask
        FutureTask<String> futureTask = new FutureTask<>(new Callable<String>() {
            @Override
            public String call() throws Exception {
                return "Hello";
            }
        });
        new Thread(futureTask).start();
        String res = futureTask.get();
        System.out.println(res);

        // 4.实现Callable接口，线程池自动封装成FutureTask
        ExecutorService service = Executors.newSingleThreadExecutor();
        Future<String> future = service.submit(new Callable() { // submit可以提交Callable和Runnable
            @Override
            public String call() throws Exception {
                return "通过实现Callable接口";
            }
        });
        String result = future.get();
    }

}