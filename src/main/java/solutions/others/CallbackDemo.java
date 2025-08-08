package solutions.others;

import java.util.concurrent.*;

// 简单的回调接口


public class CallbackDemo {
    interface CallBack {
        void onSuccess(String result);
        default void onError(Throwable t) { t.printStackTrace(); }
    }

    // 用线程池 & 定时任务模拟“异步I/O”
    private static final ScheduledExecutorService IO = Executors.newScheduledThreadPool(4);

    // 1) 异步获取用户信息
    static void getUserInfo(CallBack cb) {
        IO.schedule(() -> {
            try {
                // 模拟耗时 I/O
                String user = "zhangsan"; // 假装是从服务端拿到的用户ID/昵称
                cb.onSuccess(user);
            } catch (Exception e) {
                cb.onError(e);
            }
        }, 200, TimeUnit.MILLISECONDS);
    }

    // 2) 异步根据用户获取好友列表
    static void getFriendList(String user, CallBack cb) {
        IO.schedule(() -> {
            try {
                if (user == null) throw new IllegalArgumentException("user 为空");
                // 模拟返回的好友列表（这里用字符串代替）
                String friendList = "[lilei, hanmeimei]";
                cb.onSuccess(friendList);
            } catch (Exception e) {
                cb.onError(e);
            }
        }, 300, TimeUnit.MILLISECONDS);
    }

    // 3) 异步根据好友列表获取动态/Feed
    static void getFeedList(String friendList, CallBack cb) {
        IO.schedule(() -> {
            try {
                if (friendList == null) throw new IllegalArgumentException("friendList 为空");
                // 模拟返回的动态
                String feed = "feed of " + friendList + " => [post1, post2]";
                cb.onSuccess(feed);
            } catch (Exception e) {
                cb.onError(e);
            }
        }, 400, TimeUnit.MILLISECONDS);
    }

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch done = new CountDownLatch(1); // 只是为了演示时主线程别提前结束

        // —— 这里是“回调地狱”的写法：一层套一层 ——
        getUserInfo(new CallBack() {
            @Override
            public void onSuccess(String user) {
                System.out.println("user = " + user);
                getFriendList(user, new CallBack() {
                    @Override
                    public void onSuccess(String friendList) {
                        System.out.println("friendList = " + friendList);
                        getFeedList(friendList, new CallBack() {
                            @Override
                            public void onSuccess(String feed) {
                                System.out.println("feed = " + feed);
                                done.countDown();
                            }
                            @Override
                            public void onError(Throwable t) {
                                System.err.println("getFeedList 出错: " + t.getMessage());
                                done.countDown();
                            }
                        });
                    }
                    @Override
                    public void onError(Throwable t) {
                        System.err.println("getFriendList 出错: " + t.getMessage());
                        done.countDown();
                    }
                });
            }
            @Override
            public void onError(Throwable t) {
                System.err.println("getUserInfo 出错: " + t.getMessage());
                done.countDown();
            }
        });

        // 等待演示完成再退出
        if (!done.await(5, TimeUnit.SECONDS)) {
            System.err.println("超时未完成");
        }
        IO.shutdown();
    }
}
