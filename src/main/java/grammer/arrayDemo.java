package grammer;

import java.util.*;

/**
 * 一、数组的常见操作
 * 二、Arrays工具类的使用
 * java.util.Arrays是一个工具类，用于对数组进行各种操作，提供了很多静态方法来简化数组的处理
 * 三、与ArrayList的对比
 */
public class arrayDemo {
    static int N = 10;
    /**
     * 数组的创建
     */
    static int[] arr1 = new int[N];
    static Integer[] arr2 = new Integer[N];
    static List<Integer> arr3 = new ArrayList<>();
    static int[][] arr4 = {
            {5, 2, 9},
            {3, 7, 6},
            {8, 1, 4}
    };

    // 简写 static int[] arr5 = {1, 2, 3, 4};
    static int[] arr5 = new int[]{1, 2, 3, 4};

    // 另一种写法：static int[][] arr6 = new int[N][2]; 直接确定二维矩阵，推荐
    static int[][] arr6 = new int[N][];

    public static void main(String[] args) {
        for (int i = 0; i < N; i++) {
            arr1[i] = arr2[i] = i;
            arr3.add(i); // ArrayList
        }

        /**
         * 二维数组初始化问题
         */
        // 错误写法1：arr6[0] = new int[2]{1, 2};
        // 错误写法2：arr6[0] = {1, 2};
        // 错误写法3：for (int[] r : arr6) r = new int[2]; for-each的r只是一个引用
        arr6[0] = new int[]{1, 2};

        /**
         * 排序
         */
        Arrays.sort(arr1); // int[] 数组只能升序！
        Arrays.sort(arr1, 0, arr1.length - 1); // 排序指定区间
        // 想要降序，需要先转化成Integer[]数组
        Integer[] array = Arrays.stream(arr1).boxed().toArray(Integer[]::new);

        // Integer[] 数组降序写法1
        Arrays.sort(arr2, new Comparator<Integer>() {
            @Override
            public int compare(Integer a, Integer b) {
                return b - a;
            }
        });

        // Integer[] 数组降序写法2
        Arrays.sort(arr2, (a, b) -> b - a);
        Arrays.sort(arr2, 0, arr2.length, (a, b) -> b - a); // 排序指定区间[from, end)

        // ArrayList 降序写法（升序也要传入比较器）
        arr3.sort((a, b) -> b - a);

        // 二维数组自定义排序，就算是按第一个元素升序也得传入比较器
        Arrays.sort(arr4, (a, b) -> a[0] != b[0] ? a[0] - b[0] : a[1] - b[1]);

        /**
         * 打印
         */
        System.out.println(Arrays.toString(arr1)); // int[]
        System.out.println(Arrays.toString(arr2)); // Integer[]
        System.out.println(Arrays.deepToString(arr4)); // int[][]
        System.out.println(arr3); // ArrayList

        /**
         * 填充
         */
        Arrays.fill(arr1, 1); // int[]
        Arrays.fill(arr2, 2); // Integer[]
        for (int[] r : arr4) Arrays.fill(r, 4); // int[][]

        /**
         * 数组转化为集合
         */
        List<Integer> list1 = Arrays.asList(1, 2, 3);
        List<Integer> list = Arrays.asList(arr2); // int[]不行，Integer[]可以
        System.out.println(list);

        /**
         * 集合转化为数组
         */
        List<Integer> list2 = Arrays.asList(1, 2, 3);
        Integer[] array2 = list2.toArray(new Integer[list2.size()]);
        List<int[]> list3 = Arrays.asList(new int[]{1, 2}, new int[]{3, 4});
        int[][] array3 = list3.toArray(new int[0][]);

        /**
         * 比较两个数组值是否相同
         */
        Arrays.equals(arr1, new int[]{1, 2}); // 比较int[]
        Arrays.deepEquals(arr4, new int[][]{{1, 2}, {3, 4}}); // 比较二维及以上数组

        /**
         * 复制数组（只能复制一维！）
         */
        Arrays.copyOf(arr1, arr1.length); // 可以复制
        arr1.clone(); // 不能复制对象数组，Integer

        // 匿名初始化
        System.out.println(new int[]{1, 2, 3, 4, 5}[2]);  // 输出3
        System.out.println(Arrays.toString(new int[]{1, 2, 3, 4, 5})); // 只适合用于输出
    }
}
