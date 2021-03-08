import java.util.ArrayList;
import java.util.List;

public class ThreadExample {
    public static Thread startThread(String name) {
        Thread t = new Thread(new MyRunnable(name));
        t.start();
        return t;
    }
    public static void main(String args[]) {
        List<Thread> threads = new ArrayList<>();
        threads.add(startThread("Thread One"));
        threads.add(startThread("Thread Two"));
        threads.add(startThread("Thread Three"));
        try {
            for (Thread t : threads) {
                t.join();
            }
        } catch (InterruptedException e) {
            System.out.println("Main thread Interrupted");
        }
        System.out.println("Main thread exiting.");
    }
}