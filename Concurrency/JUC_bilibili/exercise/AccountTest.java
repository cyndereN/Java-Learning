package exercise;

/*
* 两个储户分别向一个账户存3k，每次1k，存三次
* */

class Account {
    private double balance;

    public Account(double balance){
        this.balance = balance;
    }

    // 存钱
    public synchronized void deposit(double amt){
        if (amt>0){
            balance += amt;
            System.out.println(Thread.currentThread().getName()+"存钱成功，余额: " + balance);
        }
    }
}

class Customer extends Thread{
    private Account acc;
    public Customer(Account acc){
        this.acc = acc;
    }

    @Override
    public void run() {
        for (int i = 0; i < 3; i++) {

            acc.deposit(1000);

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }    
        }
    }
}
public class AccountTest {
    public static void main(String[] args) {
        Account acc = new Account(0);
        Customer c1 = new Customer(acc);
        Customer c2 = new Customer(acc);
        c1.setName("甲");
        c2.setName("乙");
        c1.start();
        c2.start();
    }

}
