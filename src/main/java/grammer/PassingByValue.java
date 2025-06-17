package grammer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Java对象的传递只能引用传递，如何模拟值传递
 */
public class PassingByValue {
    static void cal(int[] arr, List<Integer> list, String str, Integer integer) {
        arr[0] = 1;
        list.set(0, 1);
        str = "1";
        integer = 1;
    }

    public static void main(String[] args) {
        int[] arr = new int[1];
        List<Integer> list = new ArrayList<>(1);
        list.add(0);
        String str = "0";
        Integer integer = 0;
        /**
         * 模拟值传递，其实就是新建一个对象，复制源对象
         * 对于 String 和 Integer，不需要显式复制，它们本身是 immutable（不可变）的
         */
        cal(Arrays.copyOf(arr, arr.length), new ArrayList<>(list), str, integer); // 拷贝数据
        System.out.println(Arrays.toString(arr));
        System.out.println(list);
        System.out.println(str);
        System.out.println(integer);
        /**
         * 作为对比，值传递，是会直接修改这个对象的
         */
        cal(arr, list, str, integer);
        System.out.println(Arrays.toString(arr));
        System.out.println(list);
        System.out.println(str);
        System.out.println(integer);
    }
}
