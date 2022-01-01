package communication;
/*
* 生产者/消费者问题
*
* 生产者（producer）将产品交给店员（clerk），而消费者（customer）从店员处取走产品，
* 店员一次只能持有固定数量产品。如果大于此数量或小于零，会分别告诉生产者/消费者停一下
*
* 可能出现的问题：
* 1. 生产者比消费者快，消费者会漏掉一些数据没有取到
* 2. 消费者比生产者快，消费者会取相同数据
*
* 分析：
* 1. 是多线程，生产者/消费者；有共享数据，店员，产品
* 2. 如何解决线程安全问题？同步机制
* 3. 是否涉及通讯？是
* */

class Clerk{

    private int productCount = 0;

    public synchronized void consumeProduct() {
        if (productCount>0){
            System.out.println(Thread.currentThread().getName()+": 开始消费第" + productCount-- + "个产品");

            notify();
        }else {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized void produceProduct() {
        if (productCount<20){
            System.out.println(Thread.currentThread().getName()+": 开始生产第" + ++productCount + "个产品");

            notifyAll();
        } else {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class Producer extends Thread{

    private Clerk clerk;

    public Producer(Clerk clerk){
        this.clerk = clerk;
    }

    @Override
    public void run() {
        System.out.println(getName()+": 开始生产......");


        while(true){
            try {
                Thread.sleep(40);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            clerk.produceProduct();
        }
    }
}

class Consumer extends Thread{

    private Clerk clerk;

    public Consumer(Clerk clerk){
        this.clerk = clerk;
    }

    @Override
    public void run() {
        System.out.println(getName()+": 开始消费......");



        while(true){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            clerk.consumeProduct();
        }
    }
}

public class ProductTest {

    public static void main(String[] args) {
        Clerk clerk = new Clerk();

        Producer p1 = new Producer(clerk);
        p1.setName("生产者1");

        Consumer c1 = new Consumer(clerk);
        c1.setName("消费者1");
        Consumer c2 = new Consumer(clerk);
        c2.setName("消费者2");

        p1.start();
        c1.start();
        c2.start();
    }

}
