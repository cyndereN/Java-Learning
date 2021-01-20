
public class Main {

    private void frequency(){
        FileInput fileIn = new FileInput("src/1.txt");
        int[] Char = new int[26];
        while (fileIn.hasNextLine())
        {
            String s = fileIn.nextLine();
            for(int i=0 ; i<s.length() ; i++){
                Char[s.toLowerCase().charAt(i)-97]++;
            }
        }
        fileIn.close();

        for(int num: Char) System.out.print( num+" " );
    }

    public static void main(String[] args) {
        new Main().frequency();
    }
}
