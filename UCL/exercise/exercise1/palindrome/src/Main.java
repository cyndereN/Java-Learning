public class Main {
    // Return a String which is the reverse of the argument String
    private String reverse(final String s)
    {
        String result = new String();
        int position = 0;
        while (position < s.length())
        {
            result = new Character(s.charAt(position)).toString() + result;
            position = position + 1;
        }
        return result;
    }
    // Check to see if the two argument Strings are the reverse of each
    // other. Return true if they are and false otherwise..
    private boolean check(final String s1, final String s2)
    {
        String s = reverse(s2);
        if (s1.compareTo(s) == 0) {
            return true;
        }
        else
        {
            return false;
        }
    }
    // Get user to input a String.
    private String getInput()
    {
        Input in = new Input ();
        System.out.print("Enter text to check: ");
        return in.nextLine();
    }

    private String tidyString(final String s)
    {
        return s.replaceAll(" ","").toLowerCase();
    }
    // Do the palindrome test and display the result
    public void testForPalindrome(final String s)
    {
        if (check(tidyString(s), tidyString(s)))
        {
            System.out.println("String is a palindrome.");
        }
        else
        {
            System.out.println("String is not a palindrome.");
        }
    }
    public void go()
    {
        testForPalindrome(getInput());
    }
    public static void main(final String[] args)
    {
        new Main().go();
    }
}

