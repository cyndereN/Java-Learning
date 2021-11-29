public class Main {
    public static void main(String[] args) throws InterruptedException {
        Terminator robot = new Terminator();
        robot.start();
        Thread.sleep(5000);
        System.out.println("Crushing the robot ...\n");
        robot.crush();
    }
}