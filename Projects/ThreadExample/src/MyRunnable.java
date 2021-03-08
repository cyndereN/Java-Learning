import java.util.Random;

public class MyRunnable implements Runnable {
    private static Random random = new Random();
    private String name;
    public MyRunnable(String threadName) {
        name = threadName;
        System.out.println("New thread: " + threadName);
    }
    public void run() {
        try
        {
            for (int i = 1; i < 25; i++) {
                System.out.println(name + ": " + i);
                Thread.sleep(random.nextInt(1000));
            }
        } catch (InterruptedException e) {
            System.out.println(name + "Interrupted");
        }
        System.out.println(name + " exiting.");
    }
}