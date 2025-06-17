package grammer.proxy.JDKDynamicProxy;

import com.sun.deploy.net.proxy.ProxyUtils;
import grammer.proxy.SmsService;
import grammer.proxy.SmsServiceImpl;

import java.lang.annotation.Target;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author Shengchao Zhou
 * @date 2025/5/20 15:52
 */
public class Main {
    public static void main(String[] args) {
        /**
         * 分开写
         */
        SmsService smsService = (SmsService) JdkProxyFactory.getProxy(new SmsServiceImpl());
        smsService.send("java");

        /**
         * 整合在一起写
         */
        SmsServiceImpl target = new SmsServiceImpl();
        SmsService smsService1 = (SmsService) Proxy.newProxyInstance(
                Main.class.getClassLoader(),
                new Class[]{SmsService.class},
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        //调用方法之前，我们可以添加自己的操作
                        System.out.println("before method " + method.getName());
                        Object result = method.invoke(target, args);
                        //调用方法之后，我们同样可以添加自己的操作
                        System.out.println("after method " + method.getName());
                        return result;
                    }
                }
        );
        smsService1.send("Hello World!");
    }
}
