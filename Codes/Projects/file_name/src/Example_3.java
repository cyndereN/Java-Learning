import java.io.*;
import java.util.*;

public class Example_3 {

    private void displayFileContent(String filename){
        try{
            Scanner sc = new Scanner(new File(filename));
            while(sc.hasNextLine()){
                System.out.println(sc.nextLine());
            }
            sc.close(); // Always close a file after it has been used.
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    private String getFileName()
    {
        Scanner in = new Scanner(System.in);
        System.out.print("Enter filename: ");
        String filename = in.nextLine();
        return filename;
    }
    public void showFile()
    {
        String filename = getFileName();
        displayFileContent(filename);
        // Could have written displayFileContent(getFileName());
    }
    public static void main(String[] args) {
        new Example_3().showFile();
    }
}
