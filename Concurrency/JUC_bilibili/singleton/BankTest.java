package singleton;

/*
* 使用同步机制将单例模式中的懒汉式改写为线程安全的
* */

class Bank{

    private Bank(){}

    private static Bank instance = null;

    public static Bank getInstance(){   // 方式0 改为synchronized方法
        // 方式1 效率较差
//        synchronized (Bank.class) {
//            if (instance == null){
//                instance = new Bank();
//            }
//            return instance;
//        }

        // 方式2
        synchronized (Bank.class) {
            if (instance == null){
                instance = new Bank();
            }
        }
        return instance;
    }
}

public class BankTest {
}
