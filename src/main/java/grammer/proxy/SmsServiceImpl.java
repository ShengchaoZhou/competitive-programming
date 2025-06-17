package grammer.proxy;

/**
 * @author Shengchao Zhou
 * @date 2025/5/20 15:41
 */
public class SmsServiceImpl implements SmsService {
    @Override
    public String send(String message) {
        System.out.println("send message:" + message);
        return message;
    }
}