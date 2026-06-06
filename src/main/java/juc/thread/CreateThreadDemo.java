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
                System.out.println("匿名内部类，继承Thread");
            }
        };
        t1.start();

        //2.实现Runnable接口
        Runnable r = new Runnable() {
            @Override
            public void run() {
                System.out.println("running Ruunable interface");
            }
        };
        Thread t2 = new Thread(r, "t2");
        t2.start();

        // 3. 实现Callable接口，并自己封装FutureTask
        // Callable接口中的泛型String不能省略，接口名字叫call，要抛出异常
        Callable<String> c = new Callable<String>() {
            @Override
            public String call() throws Exception {
                return "running Callable interface";
            }
        };
        FutureTask<String> ft1 = new FutureTask<>(c);
        FutureTask<String> ft2 = new FutureTask<>(r, "Runnable已完成");
        new Thread(ft1).start();
        new Thread(ft2).start();
        System.out.println(ft1.get());
        System.out.println(ft2.get());

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