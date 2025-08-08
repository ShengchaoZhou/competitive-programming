package solutions.others;

public class Singleton2 {
    private Singleton2() {};

    public static Singleton2 getObj() {
        return SingletonInner.obj;
    }

    private static class SingletonInner {
        private static final Singleton2 obj = new Singleton2();
    }

    public static void main(String[] args) {
        Singleton obj1 = Singleton.getObj();
        Singleton obj2 = Singleton.getObj();
        System.out.println(obj1 == obj2);
    }
}
