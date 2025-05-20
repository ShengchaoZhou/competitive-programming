package grammer;

/**
 * Java对基本数据类型只能值传递，如何模拟引用传递
 */
public class PassingByReference {
    // 方法一：使用全局变量
    static int x = 0;

    // 方法二：使用数组模拟
    static void cal(int[] y, Z z, Integer t) {
        x = 1;
        y[0] = 1;
        z.val = 1;
        t = 1;
    }

    // 方法三：使用内部类封装
    static class Z {
        int val;
    }

    public static void main(String[] args) {
        int[] y = {0};
        Z z = new Z();
        Integer t = 0; // 包装类型是引用传递，但是不行，因为底层是new
        cal(y, z, t);
        System.out.println(x);
        System.out.println(y[0]);
        System.out.println(z.val);
        System.out.println(t);
    }
}
