public class Terminator extends Thread {
    // private boolean crush = false;
    private volatile boolean crush = false;
    public void run() {
        while (true) {
            if (crush) {
                System.out.println(
                        "I see crush == true ... I'll be back!");
                break;
            }
            System.out.print("I am just doing what I was programmed to do ... \n");
        }
    }
    public void crush() {
        crush = true;
    }
}
