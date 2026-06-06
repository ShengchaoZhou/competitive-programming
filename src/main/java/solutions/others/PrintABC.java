package solutions.others;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class PrintABC {
    static ReentrantLock lock = new ReentrantLock();
    static Condition[] c = { lock.newCondition(), lock.newCondition(), lock.newCondition() };
    static int rounds = 10;
    static int turn;

    static private void print(int u, String s) {
        for (int i = 0; i < rounds; i++) {
            lock.lock();
            try {
                while (turn != u) c[u].await();
                System.out.print(s + " ");
                turn = (turn + 1) % 3;
                c[turn].signal();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) {
        new Thread(()-> print(0, "A")).start();
        new Thread(()-> print(1, "B")).start();
        new Thread(()-> print(2, "C")).start();
    }
}
