package grammer;

public class StringBuilerDemo {
    public static void main(String[] args) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 100; i++) {
            sb.append((char)('a' + (i % 26)));
        }
        sb.setCharAt(5, 'a');
        sb.delete(0, 1); // 删除[start, end)
        sb.deleteCharAt(sb.length() - 1);

        sb.reverse();
        sb.charAt(0);
        sb.length();
        sb.append('a').append('b'); // 可以连加，可以直接拼接基本数据类型
        char[] cs = new char[]{'a', 'b', 'c'};
        sb.append(cs, 0, 2); // 特别得，这里传入长度
        sb.substring(0); // 注意是小写s
        sb.substring(0, sb.length()); // [start, end)
        sb.toString(); // 注意是大写S
    }
}
