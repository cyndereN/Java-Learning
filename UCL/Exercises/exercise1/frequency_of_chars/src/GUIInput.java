import javax.swing.JOptionPane;

public class GUIInput
{
    public int readInt(String prompt)
    {
        int n = 0;
        String message = prompt;
        while (true)
        {
            String input = JOptionPane.showInputDialog(message);
            if (input == null)
            {
                return 0;
            }
            try
            {
                n = Integer.parseInt(input);
                break;
            }
            catch(Exception e)
            {
                message = "The input was not an integer: " + input + "\n" + prompt;
            }
        }
        return n;
    }

    public long readLong(String prompt)
    {
        long n = 0;
        String message = prompt;
        while (true)
        {
            String input = JOptionPane.showInputDialog(message);
            if (input == null)
            {
                return 0;
            }
            try
            {
                n = Long.parseLong(input);
                break;
            }
            catch(Exception e)
            {
                message = "The input was not a long: " + input + "\n" + prompt;
            }
        }
        return n;
    }

    public double readDouble(String prompt)
    {
        double d = 0.0;
        String message = prompt;
        while (true)
        {
            String input = JOptionPane.showInputDialog(message);
            if (input == null)
            {
                return 0.0;
            }
            try
            {
                d = Double.parseDouble(input);
                break;
            }
            catch(Exception e)
            {
                message = "The input was not a double: " + input + "\n" + prompt;
            }
        }
        return d;
    }

    public float readFloat(String prompt)
    {
        float f = 0.0F;
        String message = prompt;
        while (true)
        {
            String input = JOptionPane.showInputDialog(message);
            if (input == null)
            {
                return 0.0F;
            }
            try
            {
                f = Float.parseFloat(input);
                break;
            }
            catch(Exception e)
            {
                message = "The input was not a float: " + input + "\n" + prompt;
            }
        }
        return f;
    }

    public String readString(String prompt)
    {
        String input = JOptionPane.showInputDialog(prompt);
        if (input == null)
        {
            return "";
        }
        return input;
    }

    public char readChar(String prompt)
    {
        char c = ' ';
        String message = prompt;
        while (true)
        {
            String input = JOptionPane.showInputDialog(message);
            if (input == null)
            {
                return ' ';
            }
            if (input.length() == 1)
            {
                return input.charAt(0);
            }
            message = "The input was not a single character: " + input + "\n" + prompt;
        }
    }
}