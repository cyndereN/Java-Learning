import java.io.*;
import java.util.Scanner;


public class Main {

    private void getStrings(){
        try {

            File newTextFile = new File("out.txt");
            FileWriter fw = new FileWriter(newTextFile);
            String q = "stop";

            Scanner in = new Scanner(System.in);
            System.out.print("Input content, 'stop' for quit: ");
            String str = in.nextLine();

            while(!str.equals(q)) {
                fw.write(str+'\n');
                in = new Scanner(System.in);
                System.out.print("Input content, 'stop' for quit: ");
                str = in.nextLine();
            }
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Main().getStrings();
    }
}
