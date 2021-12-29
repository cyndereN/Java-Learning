/*
* 使用同步方法 解决runnable接口安全问题
* */

class Windoww2 implements Runnable{
    private int ticket = 100; // 不用加static

    @Override
    public void run() {
        while (true) {
            show();
            if (ticket==0){
                break;
            }
        }
    }

    private synchronized void show(){
            if (ticket > 0) {
                try{
                    Thread.sleep(50);
                } catch (InterruptedException e){
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + ": 卖票，票号为" + ticket);
                ticket--;
        }
    }
}

public class RunnableWindowTest2 {
    public static void main(String[] args) {
        Windoww2 w = new Windoww2();

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