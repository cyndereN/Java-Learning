/*
This program takes an input file as a dictionary, an input file or some strings in console.
It extracts each word in turn, checks whether exists in dictionary. Punctuation such as
full-stops and commas will need to be removed from the text being spell-checked.
The outputs are: a list of words that don't match along with its position,total word count,
overall accuracy and Frequency of each word if the input is a file.

- Test input file:
test1.txt,
test2.txt

- Test output file:
test1.txt - result.txt,
test2.txt - result.txt

*/

import java.io.*;
import java.util.Scanner;


public class Main {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        SpellChecker m = new SpellChecker();
        StringArray dictionary = m.dictionary();

        do{
            System.out.println("Please select: ");
            System.out.println("\t1.Input in console");
            System.out.println("\t2.Input from a file");
            System.out.print("Please input: ");

            switch (input.nextInt()) {
                case 1 -> {
                    System.out.print("Please input: ");
                    BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
                    try { //Read a whole line with space
                        String s = stdin.readLine();
                        m.stringSpellChecker(s, dictionary);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                case 2 -> {
                    System.out.print("Please input file name: ");
                    String fileName = input.next();
                    m.fileSpellChecker(fileName, dictionary);
                }
                default -> System.out.println("Invalid instruction!");
            }

            System.out.print("Continue?（y/n）：");

        } while("y".equals(input.next()));
    }
}
