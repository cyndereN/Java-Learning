public class SecretHolder extends Thread{
    private int secret = 0;
    private final int MAX_VALUE = 5;
    private synchronized void checkSecret(){
    if (secret < 0 || secret > MAX_VALUE) {
    throw new AssertionError("Secret out of bounds!");
    }
    }
    public synchronized void upOne() throws InterruptedException {
    if (secret == MAX_VALUE) {
    wait();
    }
    secret++;
    System.out.println(secret);
    checkSecret();
    notify();
    }
    public synchronized void downOne() throws InterruptedException {
    if (secret == 0) {
    wait();
    }
    secret--;
    System.out.println(secret);
    checkSecret();
    notify();
    }
    public synchronized void upTwo() throws InterruptedException {
    while (secret >= MAX_VALUE - 1) {
    wait();
    }
    secret = secret + 2;
    checkSecret();
    notifyAll();
    }

    public synchronized void downTwo() throws InterruptedException {
        while (secret <= 1) {
        wait();
        }
        secret = secret - 2;
        checkSecret();
        notifyAll();
    }

    @Override
    public void run() {
        try {
            downOne();
            downOne();
            upOne();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
