import java.util.Scanner;

public class Main {
    private double get_value(){
        Scanner in = new Scanner(System.in);
        double n = in.nextDouble();
        return n;
    }

    /*
    private void add_double(){
        double a = get_value();
        double b = get_value();
        System.out.println(a+b);
    }
     */

    private void add_double(double a, double b){
        System.out.println(a+b);
    }

    public static void main(String[] args) {
        //new Main().add_double();
        Main m = new Main();
        double a = m.get_value();
        double b = m.get_value();
        m.add_double(a, b);
    }
}
