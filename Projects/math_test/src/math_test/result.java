package math_test;
import java.util.Scanner;

public class result {

        public static void main(String[] args) {
            System.out.println("（基础部分）验证一些相续正整数的立方和正好等于另一个整数的立方。");
            Scanner scanner=new Scanner(System.in);
            System.out.print("请输入第一个正整数：");
            int a = scanner.nextInt();
            System.out.print("请输入最后一个正整数：");
            int b = scanner.nextInt();
            System.out.print("请输入需验证的正整数：");

            int n = scanner.nextInt();
            math_test test = new math_test();

            for(int i=a;i<=b;i++){
                test.setNumber(i);
                test.setSum();
            }
            if (test.getSum()==n*n*n){
                System.out.println("等式成立");
            }
            else
                System.out.println("等式不成立");
        }
}