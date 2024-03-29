package synchronize;

class Window2 extends Thread {

    private static int ticket = 100;

    private static Object obj = new Object();

    @Override
    public void run() {

        while (true) {
            show();
            if (ticket==0){
                break;
            }
        }
    }
    private static synchronized void show () {  //同步监视器: window2.class
        if (ticket > 0) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + ": 卖票，票号为" + ticket);  // 静态里只能调静态的
            ticket--;
        }
    }
}

public class WindowTest2 {
    public static void main(String[] args) {
        Window2 t1 = new Window2();
        Window2 t2 = new Window2();
        Window2 t3 = new Window2();
        t1.setName("窗口1");
        t2.setName("窗口2");
        t3.setName("窗口3");
        t1.start();
        t2.start();
        t3.start();
    }
}
