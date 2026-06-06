package designpattern.SimpleFactory;
public interface Product {
    void work();
}

class concreteProductA implements Product{
    @Override
    public void work() {
        System.out.println("product A is working");
    }
}

class concreteProductB implements Product{
    @Override
    public void work() {
        System.out.println("product B is working");
    }
}

class ProductFactory {
    public Product createProduct(String type) {
        if (type.equals("A")) {
            System.out.println("create product A");
            return new concreteProductA();
        } else if (type.equals("B")) {
            System.out.println("create product B");
            return new concreteProductB();
        } else {
            throw new RuntimeException();
        }
    }
}

class Client {
    public static void main(String[] args) {
        ProductFactory pf = new ProductFactory();

        Product a = pf.createProduct("A");
        a.work();

        Product b = pf.createProduct("B");
        b.work();
    }
}