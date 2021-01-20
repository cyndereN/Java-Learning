import java.util.Scanner;

public class Main {
    private void fac_slice(){
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int m = in.nextInt();
        System.out.println(fac(n,m));
    }

    private int fac(int a, int b){
        if (a == b) {
            if (a == 0) a = 1;
            return a;
        }
        else return fac(a,b-1)*b;
    }

    public static void main(String[] args) {
        new Main().fac_slice();
    }
}
