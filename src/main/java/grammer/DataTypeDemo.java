package grammer;

import java.util.ArrayList;
import java.util.List;

public class DataTypeDemo {
    public static void main(String[] args) {
        int a = 0;
        long b = 0;
        // 当超出int范围后，必须加上l/L
        long c = 12345678910L;
        float d = 0;
        // 申明float浮点型，必须加上f/F
        float e = 0.0f;
        double f = 0.0;
        /**
         * 比较集合中元素（包装类型），记得使用equals
         */
        List<Integer> arr1 = new ArrayList<>();
        List<Integer> arr2 = new ArrayList<>();
        arr1.add(1000); arr2.add(1000);
        System.out.println(arr1.get(0) == arr2.get(0)); // false
        /**
         * Double集合不能直接添加int数
         */
        List<Double> ld = new ArrayList<>();
        ld.add((double)1000);
        /**
         * 科学计数法表示的数是double类型
         */
        int N = (int)1e9 + 7;
        /**
         * 在 Java 中，long 转 int 需要显式强制转换，转换时可能会发生截断（溢出）。
         * 在表达式中，如果有 long 和 int 混合运算，int 会自动提升为 long。
         */
        int long2int = (int)c;
        System.out.println(long2int); // -539222978
        long int2long = a + c;
    }
}
