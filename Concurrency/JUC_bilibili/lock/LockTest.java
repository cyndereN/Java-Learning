package lock;

/*
* 解决线程安全问题的方式3 Lock锁
*
* synchronized自动释放同步监视器
* Lock手动启动同步，手动结束
* */

import java.util.concurrent.locks.ReentrantLock;

class Window implements Runnable{

    private int ticket = 100;

    private ReentrantLock lock = new ReentrantLock();    // true公平lock，按先后顺序执行

    @Override
    public void run() {
        while (true){
            try {
                //  调用lock方法
                lock.lock();
                if (ticket > 0) {

                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    System.out.println(Thread.currentThread().getName() + ": 售票，票号为" + ticket--);
                } else {
                    break;
                }
            } finally {
                // 调用解锁方法: unlock
                lock.unlock();
            }
        }
    }
}

public class LockTest {
    public static void main(String[] args) {
        Window w = new Window();
        Thread t1 = new Thread(w);
        Thread t2 = new Thread(w);
        Thread t3 = new Thread(w);
        t1.setName("窗口1");
        t2.setName("窗口2");
        t3.setName("窗口3");
        t1.start();
        t2.start();
        t3.start();
    }

}
