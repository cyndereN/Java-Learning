package synchronize;/*
* 例子：创建三个窗口卖票，总票数为100张，使用runnable接口的方式
*
* 1. 问题：卖票过程中，出现重票，错票---->出现线程安全问题
* 2. 问题出现的原因：当某个线程操作车票的过程中，尚未完成操作时，其他线程参与进来，也操作车票
* 3. 如何解决：当一个线程a在操作ticket的时候，其他线程不能参与尽量。直到线程a操作完ticket时，其他线程才可以开始操作ticket。
*    这种情况即使a出现了阻塞，也不能被改变
* 4. 在Java中，我们通过同步机制，来解决线程的安全问题
*
* 方法1：同步代码块
*
*   synchronized(同步监视器){
*       //需要被同步的代码
*   }
*
*   说明：操作共享数据的代码，即为需要被同步的代码
*        共享数据：多个线程共同操作的变量。比如：ticket
*        同步监视器，俗称：锁。任何一个类的对象，都可以充当锁。要求多个线程必须要共用同一把锁。
*
* 方法2：同步方法
*   说明：仍涉及同步监视器，只不过不需要显示声明
*        非静态同步方法，同步监视器是：this
*        静态同步方法，同步监视器是当前类本身
*
* 5. 同步方式，解决了线程安全问题。 ->好处
*            操作同步代码时，只能有一个线程参与，其他线程等待，相当于是一个单线程的过程，效率低 ->缺点
*
*
* */

class Windoww1 implements Runnable{
    private int ticket = 100; // 不用加static
    Object obj = new Object();
    @Override
    public void run() {
        while (true) {
            synchronized (this) {    // 这里代表唯一的windows对象
            // synchronized(obj){
                if (ticket > 0) {
                    try{
                        Thread.sleep(100);
                    } catch (InterruptedException e){
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + ": 卖票，票号为" + ticket);
                    ticket--;
                } else {
                    break;
                }
            }
        }
    }
}

public class RunnableWindowTest1 {
    public static void main(String[] args) {
        Windoww1 w = new Windoww1();

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
