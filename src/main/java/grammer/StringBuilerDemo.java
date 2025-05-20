package grammer;

public class StringBuilerDemo {
    public static void main(String[] args) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 100; i++) {
            sb.append((char)('a' + (i % 26)));
        }
        sb.setCharAt(5, 'a');

        sb.reverse();
        sb.charAt(0);
        sb.length();
        sb.append('a').append('b'); // 可以连加
        sb.substring(0); // 注意是小写
        sb.toString();
    }
}
