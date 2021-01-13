import java.util.Scanner;

public class Example_2 {
    public int sumOfDigits(int n) {
        int sum = 0;
        n = Math.abs(n);
        while (n > 0) {
            sum += n % 10;
            n /= 10;
        }
        return sum;
    }

    public void inputAndProcess() {
        int n = 0;
        Scanner in = new Scanner(System.in);
        while (true) {
            System.out.print("Type an integer: ");
            if (in.hasNextInt()) {
                n = in.nextInt();
                break;
            }
            in.nextLine();
            System.out.println("You did not type an integer, try again.");
        }
    }

    public static void main(String[] args)
    {
        new Example_1().inputAndProcess();
    }
}
