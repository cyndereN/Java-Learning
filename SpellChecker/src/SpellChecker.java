import java.io.*;
import java.text.NumberFormat;
import java.util.*;

public class SpellChecker {

    public StringArray dictionary(){
        StringArray words = new StringArray();
        FileInput input = new FileInput("words");
        while (input.hasNextLine()) {
            String s = input.nextLine();
            words.add(s);
        }
        return words;
    }

    private boolean isEnglishWord(String s){
        if(s == null || s.trim().equals(""))
            return false;
        String regex = "^[a-zA-Z]+$";
        return s.matches(regex);
    }

    private void handleString(String s, StringArray words){

        StringTokenizer st = new StringTokenizer(s, ",!' '.;\":@#$%^&*()-|\\/<>?`~");  //Tokenize the string
        while(st.hasMoreTokens()) {
            String str = st.nextToken();
            if(isEnglishWord(str)){
                //System.out.println(str);
                words.add(str);
            }
        }
    }

    public void stringSpellChecker(String s, StringArray dictionary){
        int correctWordCounter = 0;
        StringArray words = new StringArray();
        handleString(s, words);
        //System.out.println(s);
        for(int i=0 ; i<words.size() ; i++){
            if(dictionary.binarySearchContains(words.get(i)))      //More efficient since dictionary is ordered
                correctWordCounter += 1;
            else{
                System.out.println("Cannot find word: "+words.get(i) +"(index: "+i+") in dictionary.");
            }
        }

        System.out.println("Total words: "+ words.size());
        NumberFormat num = NumberFormat.getPercentInstance();  // Print in percent format
        num.setMaximumFractionDigits(2);  // Round up to 2 decimal places maximal
        float p = (float)correctWordCounter/words.size();
        System.out.println("Accuracy: "+ num.format(p));

    }

    public void fileSpellChecker(String f, StringArray dictionary){
        int rowCounter = 0;
        int totalWordCounter = 0;
        StringArray correctWords = new StringArray();
        try{
            Scanner in = new Scanner(new File(f));

            //Output a file that has statistical data in it
            File resultFile = new File(f + " - result.txt");
            FileWriter fw = new FileWriter(resultFile);
            fw.write(f);
            fw.write(" result\n\n");
            fw.write("Wrong-spelled words list: \n");

            while(in.hasNextLine()){
                StringArray words = new StringArray();
                rowCounter += 1;
                handleString(in.nextLine(), words);
                totalWordCounter += words.size();
                for(int i=0 ; i<words.size() ; i++){
                    if(dictionary.binarySearchContains(words.get(i)))
                        correctWords.add(words.get(i));
                    else{
                        fw.write("\t" + words.get(i) +"(row: "+rowCounter+",column: "+(i+1)+")\n");
                        System.out.println("Cannot find word: "+words.get(i) +"(row: "+rowCounter+",column: "+(i+1)+") in dictionary.");
                    }
                }
            }

            System.out.println("Total words: "+ totalWordCounter);
            NumberFormat num = NumberFormat.getPercentInstance();  // Print in percent format
            num.setMaximumFractionDigits(2);  // Round up to 2 decimal places maximal
            float p = (float)correctWords.size()/totalWordCounter;
            System.out.println("Accuracy: "+ num.format(p));

            fw.write("\nTotal words: "+ totalWordCounter + "\n");
            fw.write("Accuracy: "+ num.format(p));
            fw.write("\n\nFrequency table:\n");

            //Use a hashmap to store frequency of words
            HashMap<String, Integer> hashMap= new HashMap<>();
            Set<String> wordSet=hashMap.keySet();
            for(int i=0;i<correctWords.size();i++)
            {
                //If word already exists
                if(wordSet.contains(correctWords.get(i)))
                {
                    Integer number=hashMap.get(correctWords.get(i));
                    number++;
                    hashMap.put(correctWords.get(i), number);
                }
                else
                {
                    hashMap.put(correctWords.get(i), 1);
                }
            }


            //Use a iterator to write frequency in file
            for (String word : hashMap.keySet()) {
                fw.write("\t" + word +": " + hashMap.get(word) + "\n");
            }

            in.close(); // Always close a file after it has been used
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
