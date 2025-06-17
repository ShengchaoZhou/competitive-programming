package grammer;

public class StringDemo {
    public static void main(String[] args) {
        String str = "Hello World!";
        /*----------------------常用--------------------*/
        str.length(); // 获取字符串长度
        str.equals("hello world!"); // 判断字符串是否相等（区分大小写）
        str.equalsIgnoreCase("hello world!"); // 判断字符串是否相等（忽略大小写）
        str.toCharArray(); // 转换为字符数组
        new String(new char[10]); // 字符数组转换成字符串
        new String(new char[10], 0, 10); // 偏移量和数量
        str.charAt(0);
        str.substring(0, 5); // [start, end)
        str.isEmpty();
        Character.isDigit('5');

        /*----------------------偶尔--------------------*/
        int cmp = "abc".compareTo("abd"); // 字符串按字典顺序比较大小，返回结果为int类型
        String.valueOf(123); // 重载了各种类型
        Integer.parseInt("123"); // 字符串转化为int类型
        Integer.valueOf("123");
        str.contains("World"); // 判断是否包含某个子串
        String csv = "apple,banana,orange";
        csv.split(","); // 按指定分隔符分割字符串

        /*----------------------不常用--------------------*/
        str.startsWith("Hello"); // 判断是否以指定前缀开头
        str.endsWith("!"); // 判断是否以指定后缀结尾

        str.toLowerCase(); // 转换为小写
        str.toUpperCase(); // 转换为大写

        str.indexOf("o"); // 返回首次出现指定子串的位置
        str.lastIndexOf("o"); // 返回最后一次出现指定子串的位置

        str.replace("o", "0"); // 替换字符或字符串
        str.trim(); // 去除字符串首尾空格

        String.join("-", "Java", "Python", "C++"); // 使用指定连接符连接多个字符串
    }
}
