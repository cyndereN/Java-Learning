package synchronize;

/*
Runnable
 */
//创建一个实现了runnable接口的类
class MThread implements Runnable{

    //　实现类去实现runnable中的抽象方法：run()
    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            if (i%2==0){
                System.out.println(Thread.currentThread().getName()+": "+i);
            }
        }
    }
}

public class NewThreadTest2 {
    public static void main(String[] args) {
        //创建实现类的对象
        MThread mThread = new MThread();
        // 将此对象作为参数传递到Thread类的构造器中创建Thread类的对象
        Thread t1 = new Thread(mThread);
        // 通过Thread类的对象调用start(): 启动线程 - 调用当前线程的run() - 调用了runnable类型的target的run()
        t1.start();

        //再启动一个
        Thread t2 = new Thread(mThread);
        t2.start();
    }

}
