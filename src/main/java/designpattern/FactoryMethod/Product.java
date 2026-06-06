package designpattern.FactoryMethod;

interface Product { void work(); }

class ConcreteProductA implements Product {
    @Override
    public void work() { System.out.println("product A is working"); }
}
class ConcreteProductB implements Product {
    @Override
    public void work() { System.out.println("product B is working"); }
}

interface ProductFactory {
    Product createProduct();
}

class ConcreteFactoryA implements ProductFactory {
    @Override
    public Product createProduct() {
        System.out.println("create product A");
        return new ConcreteProductA();
    }
}
class ConcreteFactoryB implements ProductFactory {
    @Override
    public Product createProduct() {
        System.out.println("create product B");
        return new ConcreteProductB();
    }
}

class Client {
    public static void main(String[] args) {
        ProductFactory fa = new ConcreteFactoryA();
        Product a = fa.createProduct();
        a.work();

        ProductFactory fb = new ConcreteFactoryB();
        Product b = fb.createProduct();
        b.work();
    }
}
