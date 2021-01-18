import java.util.ArrayList;
import java.util.Collections;

public class Main {
    private final ArrayList<String> words = new ArrayList<>();
    private void displayStrings()
    {
        for (String i : words)
        {
            System.out.println(i);
        }
    }
    private void inputStrings()
    {
        Input in = new Input();
        System.out.print("How many strings to input? ");
        int count = in.nextInt();
        for (int n = 0; n < count; n++)
        {
            System.out.print("Enter string (" + n + ") : ");
            String word = in.next();
            words.add(word);
        }
    }
    private void sortStrings()
    {
        Collections.sort(words);
        Collections.reverse(words);
    }
    public void run()
    {
        inputStrings();
        sortStrings();
        displayStrings();
    }
    public static void main(String[] args) {
        new Main().run();
    }
}
