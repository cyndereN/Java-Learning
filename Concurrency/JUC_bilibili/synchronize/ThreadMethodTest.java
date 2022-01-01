package synchronize;// stop() 强制结束
// isAlive() 判断是否活着
// sleep(long millisecond) 让当前线程"睡眠"指定的毫秒数，在指定毫秒时间内，当前线程是阻塞状态

/*
* 线程优先级
* MAX_PRIORITY: 10
* MIN_PRIORITY: 1
* NORM_PRIORITY: 5
*
* 高优先级抢占低优先级线程执行权，从概率上讲高优先级高概率优先执行，并不意味着只有
* 当高优先级的线程执行完成后，低优先级线程才执行
* */

public class ThreadMethodTest {
    public static void main(String[] args) {
        HelloThread h1 = new HelloThread("T1");
        // h1.setName("T1");
        h1.start();

        for (int i = 0; i < 100; i++) {
            if (i%2==0){
                System.out.println(i);
            }
            if(i == 20) {
                try{
                    h1.join();        //插队执行（阻塞其他）
                } catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        }
    }
}

class HelloThread extends Thread{
    @Override
    public void run() {
        super.run();
        // System.out.println("hello");

        // yield(); //释放当前cpu的执行权 （但之后cpu还可以再分配到此线程）
        for (int i = 0; i < 100; i++) {
            if (i%2==0){
                System.out.println(this.getName()+": "+i);
            }

        }
    }

    public HelloThread(String name){
        super(name);
    }
}
