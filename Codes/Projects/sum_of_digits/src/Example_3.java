import java.util.Scanner;

public class Example_3 {
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
        GUIInput input = new GUIInput();
        int n = input.readInt("Type an integer: ");
        System.out.print("The sum of the digits of: " + n);
        System.out.println(" is: " + sumOfDigits(n));
    }


    public static void main(String[] args)
    {
        new Example_1().inputAndProcess();
    }
}
