package designpattern.AbstractFactory;

// ====== 产品族的两个维度 ======
interface ProductA { void workA(); }
interface ProductB { void workB(); }

// ====== Alpha 风格的一组产品 ======
class AlphaProductA implements ProductA {
    @Override public void workA() { System.out.println("Alpha A working"); }
}
class AlphaProductB implements ProductB {
    @Override public void workB() { System.out.println("Alpha B working"); }
}

// ====== Beta 风格的一组产品 ======
class BetaProductA implements ProductA {
    @Override public void workA() { System.out.println("Beta A working"); }
}
class BetaProductB implements ProductB {
    @Override public void workB() { System.out.println("Beta B working"); }
}

// ====== 抽象工厂：定义“成套创建接口” ======
interface AbstractFactory {
    ProductA createProductA();
    ProductB createProductB();
}

// ====== 具体工厂：各自产出同一风格的一整套产品 ======
class AlphaFactory implements AbstractFactory {
    @Override public ProductA createProductA() {
        System.out.println("create Alpha A");
        return new AlphaProductA();
    }
    @Override public ProductB createProductB() {
        System.out.println("create Alpha B");
        return new AlphaProductB();
    }
}

class BetaFactory implements AbstractFactory {
    @Override public ProductA createProductA() {
        System.out.println("create Beta A");
        return new BetaProductA();
    }
    @Override public ProductB createProductB() {
        System.out.println("create Beta B");
        return new BetaProductB();
    }
}

// ====== 工厂选择器（可按配置/环境切换整套风格） ======
enum Theme { ALPHA, BETA; }

final class FactoryProvider {
    static AbstractFactory get(Theme theme) {
        if (theme == null) {
            throw new IllegalArgumentException("theme == null");
        }
        switch (theme) {
            case ALPHA:
                return new AlphaFactory();
            case BETA:
                return new BetaFactory();
            default:
                throw new IllegalArgumentException("Unknown theme: " + theme);
        }
    }
}

// ====== 客户端：拿到一套兼容的产品一起用 ======
class Client {
    public static void main(String[] args) {
        AbstractFactory f = FactoryProvider.get(Theme.ALPHA);
        ProductA a = f.createProductA();
        ProductB b = f.createProductB();
        a.workA();
        b.workB();

        // 一键切换另一套风格
        AbstractFactory f2 = FactoryProvider.get(Theme.BETA);
        f2.createProductA().workA();
        f2.createProductB().workB();
    }
}
