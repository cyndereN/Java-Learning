import java.util.Scanner;

public class Example_1 {
    public int sumOfDigits(int n)
    {
        int sum = 0;
        n = Math.abs(n);
        while (n > 0)
        {
            sum += n % 10;
            n /= 10;
        }
        return sum;
    }

    public void inputAndProcess()
    {
        Scanner in = new Scanner(System.in);
        System.out.print("Type an integer: ");
        int n = in.nextInt();
        System.out.print("The sum of the digits of: " + n);
        System.out.println(" is: " + sumOfDigits(n));
    }

    public static void main(String[] args)
    {
        new Example_1().inputAndProcess();
    }
}
