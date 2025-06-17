package juc.threadpool.mythreadpool;

/**
 * @author Shengchao Zhou
 * @date 2025/6/8 21:19
 */
@FunctionalInterface // 拒绝策略
interface RejectPolicy<T> {
    void reject(BlockingQueue<T> queue, T task);
}