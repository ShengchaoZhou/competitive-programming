package grammer;

public class IntegerDemo {
    public static void main(String[] args) {
        int maxValue = Integer.MAX_VALUE;
        int minValue = Integer.MIN_VALUE;
        int stoi = Integer.parseInt("12345");
        int stoi2 = Integer.valueOf("12345");
        Integer x = 100;
        // ==> Integer x = Integer.valueOf(100);编译器转换
        // ==> x = IntegerCache.cache[100 + 128]; 命中缓存池
        x = 1000;
        // x = new Integer(1000); 超出范围，不是缓存，是新对象
        Long a = -2147483648l;
        int b = (int)(long)a;
    }
}
