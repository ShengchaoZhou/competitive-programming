import java.util.*;

class Main {
    static int rand7() {
        Random r = new Random();
        return r.nextInt(7) + 1;
    }
    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            int res = 0;

            while (true) {
                int tmp = (rand7() - 1) * 7 + rand7();
                if (tmp < 40) {
                    res = tmp % 10 + 1;
                    break;
                }
            }
            System.out.println(res);
        }
    }
}