import java.util.Scanner;

class sum_of_cubes{
    public static void main( String[] args ){
        Scanner scanner = new Scanner(System.in);
        System.out.print("请输入一个数:");
        int a = scanner.nextInt();
        int  x[]=new int[a];
        long sum = 0;
        int i=0,j=0,k=0;
        for (i = 0; i < a; i++)// 把0到a之间的的数的3次方赋值到对应下标的数组内
            x[i] = i * i * i;
        for (i = 2; i < a; i++)//作为数组下标
            for (j = 1; j < i; j++)//作为开始算的最小值
                for (k = j; k < i; k++)//不断增大最左边值
                {
                    sum += x[k];
                    if (sum == x[i])
                    {
                        System.out.printf("%d^3 + ... + %d^3 = %d^3\n", j, k, i);
                        sum = 0;
                        break;
                    }
                    if (k + 1 == i)
                    {
                        sum = 0;
                        break;
                    }

                }
    }
}