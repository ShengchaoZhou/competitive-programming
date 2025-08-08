package solutions.others;

public class Singleton {
    private static volatile Singleton obj;

    private Singleton(){}

    public static Singleton getObj() {
        if (obj == null) {
            synchronized (Singleton.class) {
                if (obj == null) {
                    obj = new Singleton();
                }
            }
        }
        return obj;
    }

    public static void main(String[] args) {
        Singleton obj1 = Singleton.getObj();
        Singleton obj2 = Singleton.getObj();
        System.out.println(obj1 == obj2);
    }
}
