/*
* 多线程的创建：方式1：继承于thread类
* 1. 创建一个继承于Thread类的子类
* 2. 重写Thread类的run()
* 3. 创建Thread类的子类的对象
* 4. 通过次对象调用start()
* */


// 遍历100以内的所有偶数

class myThread extends Thread{
    public void run(){
        for (int i = 0; i < 100; i++) {
            if (i%2==0){
                System.out.println(i);
            }
        }
    }
}
public class NewThreadTest {
    public static void main(String[] args) {
        myThread myThread1 = new myThread();
        myThread1.start();
        System.out.println("start");
    }
}
