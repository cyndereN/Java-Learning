import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Range m = new Range(1,10);
        ArrayList<Integer> a = m.getValues();
        for(int tmp:a)
            System.out.print(tmp+" ");
        System.out.println("\n");
    }
}
