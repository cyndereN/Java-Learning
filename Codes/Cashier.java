import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class Main {
    public static void main(String[] args) throws IOException {
        InputStreamReader reader = new InputStreamReader(System.in);
        BufferedReader in = new BufferedReader(reader);

        try {
            double purchasePrice = Double.parseDouble(in.readLine());
            double cash = Double.parseDouble(in.readLine());
            Main.calculateChange(purchasePrice, cash);
        } catch (Exception e) {
            System.out.println(e);
    }
    }

    public static void calculateChange(double purchasePrice, double cash) {
        if (purchasePrice > cash) {
            System.out.println("ERROR")；
            return;
        }
        else if (purchasePrice == cash) {
            System.out.println("Zero")；
            return;
        } 
        else{
            double change = cash - purchasePrice;
            double[]notes = new double[]{50,20,10,5,2,1,0.5,0.2,0.1,0.05,0.02,0.01};
            String[]names = new String[]{"Fifty Pounds","Twenty Pounds","Ten Pounds",
                        "Five Pounds","Two Pounds","One Pound","Fifty Pence",
                        "Twenty Pence","Ten Pence","Five Pence","Two Pence","One Pence",
                        };

            while(change>0){
                for(int i=0;i<notes.length;i++){
                    if(change>=notes[i]){
                        change=change-notes[i];
                        System.out.println(names[i]);
                        break;
                    }
                }
            }
            return;
        }
    }
}