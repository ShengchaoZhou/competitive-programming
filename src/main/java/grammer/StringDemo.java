package grammer;

public class StringDemo {
    public static void main(String[] args) {
        String str = "Hello World!";
        /*----------------------常用--------------------*/
        str.length();
        str.equals("hello world!");
        str.equalsIgnoreCase("hello world!");
        str.toCharArray();
        new String(new char[10]); // 字符数组转换成字符串
        new String(new char[10], 0, 10); // 偏移量和数量
        str.charAt(0);
        str.substring(0, 5); // [start, end)，end不能超过长度
        str.isEmpty();
        Character.isDigit('5'); // 只有判断数字函数，没有判断字母函数
        Character.isLetter('你'); // 只要Unicode把某个码点标记为 Letter（汉字、希腊文、俄文、日文假名……）

        /*----------------------偶尔--------------------*/
        int cmp = "abc".compareTo("abd"); // 字符串按字典顺序比较大小，返回结果为int类型
        String.valueOf(123); // 重载了各种类型
        Integer.parseInt("123"); // 字符串转化为int类型
        Integer.valueOf("123");
        str.contains("World"); // 判断是否包含某个子串
        String csv = "apple,banana,orange";
        csv.split(","); // 按指定分隔符分割字符串，注意是正则表达式

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
