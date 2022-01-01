package communication;

/*
* 说明：
* 1. wait(), notify(), notifyAll()三个方法必须使用在同布代码块或同步方法中
* 2. -----------------------------三个方法的调用者必须是同布代码块或同步方法的同步监视器
* 3. 都是定义在java.lang.Object中
*
* 问题：sleep()和wait()异同?
* 1. 相同点：一旦执行方法，都可以使得当前的线程进入阻塞状态
* 2. 不同点: 1) 两个方法声明的位置不同，Thread中声明sleep()，Object类中声明wait()
*           2) 调用要求不同, sleep()可以在任意场景下使用，wait()必须使用在同步代码块或同步方法
*           3) 若都使用在同步代码块或者同步方法中，sleep()不会释放锁，wait()会
*
* */


class Number implements Runnable{
    private int number = 1;

    @Override
    public void run() {
        while (true) {
            synchronized (this) {

                notify();  // 唤醒一个优先级高的被wait()的线程

                if (number<=100){
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + ": " + number++);

                    try {
                        // 使得调用如下wait()方法的线程进入阻塞状态，释放锁（同步监视器）
                        wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                } else {
                    break;
                }
            }
        }
    }
}

public class CommunicationTest {
    public static void main(String[] args) {
        Number number = new Number();
        Thread t1 = new Thread(number);
        Thread t2 = new Thread(number);

        t1.setName("线程1");
        t2.setName("线程2");

        t1.start();
        t2.start();
    }
}
