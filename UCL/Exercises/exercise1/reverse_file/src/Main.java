import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Main {

    private void reverse() throws IOException {
        FileInput fileIn = new FileInput("src/1.txt");
        BufferedWriter out = new BufferedWriter(new FileWriter("1_r.txt"));
        while (fileIn.hasNextLine())
        {
            String s = fileIn.nextLine();
            char[] charArray = s.toCharArray();  //convert to char array
            for(int i=s.length()-1 ; i>=0 ; i--){
                out.write(charArray[i]);
            }
            out.write('\n');
        }
        fileIn.close();
        out.close();
        System.out.println("Succeed！");
    }

    public static void main(String[] args) throws IOException {
        new Main().reverse();
    }
}
