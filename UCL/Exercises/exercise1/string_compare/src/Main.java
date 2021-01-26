import java.util.Arrays;
import java.util.Scanner;

public class Main {
    private int[] frequency(char[] s){
        int[] Char = new int[26];
        for (char c : s) {
            Char[c - 97]++;
        }
        return Char;
    }

    private boolean string_compare(char[]s, char[]b){
        return Arrays.equals(frequency(s), frequency(b));
    }

    private void go(){
        Scanner in = new Scanner(System.in);
        String s1 = in.nextLine();
        String s2 = in.nextLine();
        System.out.println(string_compare(s1.toCharArray(), s2.toCharArray()));
    }
    public static void main(String[] args) {
        new Main().go();
    }
}
