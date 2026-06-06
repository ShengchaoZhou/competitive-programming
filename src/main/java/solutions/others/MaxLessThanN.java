package solutions.others;

import java.util.*;

public class MaxLessThanN {
    static TreeSet<Integer> all, head;
    static int[] num;

    static void init(int[] digits, int n) {
        all = new TreeSet<>();
        head = new TreeSet<>();
        for (int d : digits) {
            all.add(d);
            if (d != 0) head.add(d);
        }

        char[] s = String.valueOf(n).toCharArray();
        num = new int[s.length];
        for (int i = 0; i < s.length; i++) num[i] = s[i] - '0';
    }
    static int maxNumBelowN(int[] digits, int n) {
        if (n <= 0 || digits.length == 0) return -1;

        init(digits, n);

        if (all.size() == 1 && all.first() == 0) return 0;

        if (num.length == 1) {
            Integer t = all.lower(num[0]);
            if (t == null) return -1;
            return t;
        }

        int sameLen = trySameLength();
        if (sameLen != -1) return sameLen;
        return tryShortLength();
    }

    static int trySameLength() {
        int len = num.length;
        int[] res = new int[len];
        for (int i = 0; i < len; i++) {
            Integer t = (i == 0 ? head : all).floor(num[i]);
            if (t == null) {
                if (!backoff(res, i - 1)) return -1;
                return toInt(res);
            }
            res[i] = t;
            if (t < num[i]) {
                for (int j = i + 1; j < len; j++) res[j] = all.last();
                return toInt(res);
            }
        }

        if (!backoff(res, len - 1)) return -1;
        return toInt(res);
    }

    static  int tryShortLength() {
        int len = num.length - 1;
        int[] res = new int[len];
        for (int i = 0; i < len; i++) {
            int t = (i == 0 ? head : all).last();
            res[i] = t;
        }
        return toInt(res);
    }

    static boolean backoff(int[] res, int pos) {
        for (int i = pos; i >= 0; i--) {
            Integer t = (i == 0 ? head : all).lower(res[i]);
            if (t != null) {
                res[i] = t;
                for (int j = i + 1; j < res.length; j++) res[j] = all.last();
                return true;
            }
        }
        return false;
    }

    static int toInt(int[] arr) {
        int res = 0;
        for (int x : arr) res = res * 10 + x;
        return res;
    }

    // ----------- quick test -----------
    static void t(int[] digits, int n, int expect) {
        int got = maxNumBelowN(digits, n);
        System.out.printf("digits=%s, n=%d -> %s  %s%n",
                Arrays.toString(digits), n, got, expect == got ? "✔" : ("✘ expect " + expect));
    }

    public static void main(String[] args) {
        // 你原来的用例（允许返回 0 的语义）
        t(new int[]{2,3,5,8},  23121, 22888);
        t(new int[]{0,1},      1000,  111);
        t(new int[]{0,2,3},    1000,  333);
        t(new int[]{0},        10,    0);   // 允许 0
        t(new int[]{5,6,7},    5,     -1);
        t(new int[]{0,1},      0,     -1);  // n<=0 无解

        // 补充：一位数场景
        t(new int[]{0},        1,     0);
        t(new int[]{0,4,9},    5,     4);
        t(new int[]{4,9},      4,     -1);

        // 更短一位降到 1 位
        t(new int[]{0,1},      20,    11);  // 短一位=2位 -> 11
        t(new int[]{0,1},      10,    1);   // 短一位=1位 -> 1(=maxAll)
    }
}
