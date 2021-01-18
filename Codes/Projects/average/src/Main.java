import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private ArrayList<Double> numbers = new ArrayList<Double>();
    int count;

    private void getNumbers(){
        Scanner in = new Scanner(System.in);
        System.out.print("How many numbers to input? ");
        count = in.nextInt();
        for (int n = 0; n < count; n++)
        {
            System.out.print("Enter number (" + n + ") : ");
            double number = in.nextDouble();
            numbers.add(number);
        }
    }
    private void printAverage(){
        getNumbers();
        double sum = 0;
        for (Double i : numbers)
        {
            sum += i;
        }
        System.out.println(sum/count);
    }

    public static void main(String[] args) {
        new Main().printAverage();
    }
}
