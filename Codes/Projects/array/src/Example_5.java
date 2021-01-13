import java.util.ArrayList; // Need to import the class before it
                            // can be used.
import java.util.Collections; // Class Collections provides a sort
                              // method for Collection objects
                              // such as ArrayLists.

class Example_5
{
    private ArrayList<Integer> numbers = new ArrayList<>();
    private void displayIntegers()
    {
        for (int i : numbers)
        {
            System.out.println(i);
        }
    }
    private void inputIntegers()
    {
        Input in = new Input();
        System.out.print("How many integers to input? ");
        int count = in.nextInt();
        for (int n = 0; n < count; n++)
        {
            System.out.print("Enter integer (" + n + ") : ");
            int value = in.nextInt();
            numbers.add(value);
        }
    }
    private void sortIntegers()
    {
        // Easy if you can find and use a method in the class library!!
        Collections.sort(numbers);
    }
    public void run()
    {
        inputIntegers();
        sortIntegers();
        displayIntegers();
    }
    public static void main(String[] args) {
        new Example_5().run();
    }
}
