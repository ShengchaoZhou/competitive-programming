package io;

import java.util.*;

public class IODemo1 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        // 自动忽略任意数量的空格、制表符（\t）、换行符（\n）等 空白符,它在找到下一个非空白字符时开始读取，直到下一个空白符结束
        sc.next();
        sc.nextInt();
        // 后面只有空格、制表符、换行符这类空白字符，会返回false，因为这些不被当作有效的token
        sc.hasNext();
        sc.hasNextInt();
        String line = sc.nextLine();

        // 读取到最后一行 -1 -1 停止
        while (sc.hasNext()) {
            int a = sc.nextInt(), b = sc.nextInt();
            if (a == -1 && b == -1) break;
            /* ... */
        }

        // 每行

        System.out.print(0);
    }
}
