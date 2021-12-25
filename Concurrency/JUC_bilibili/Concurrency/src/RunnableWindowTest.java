// 也存在线程安全问题

/*
* 开发中优先选择实现runnable接口方式
* 原因: 实现的方式没有类的单继承性的局限性
*      实现的方式更适合处理多个线程有共享数据的情况
* */

class Window1 implements Runnable{
    private int ticket = 100; // 不用加static
    @Override
    public void run() {
        while (true){
            if(ticket>0){
                System.out.println(Thread.currentThread().getName()+": 卖票，票号为"+ticket);
                ticket--;
            } else {
                break;
            }
        }
    }
}

public class RunnableWindowTest {
    public static void main(String[] args) {
        Window1 w = new Window1();

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
