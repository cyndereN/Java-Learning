import java.util.ArrayList;

public class Range {
    int lower;
    int upper;

    public Range(int lower, int upper){
        this.lower = lower;
        this.upper = upper;
    }

    public int getLower(){
        return lower;
    }

    public int getUpper(){
        return upper;
    }

    public boolean contains(int n){
        return (lower <= n && n <= upper);
    }

    public ArrayList<Integer> getValues(){
        ArrayList<Integer> a = new ArrayList<>();
        for(int i = lower; i <= upper; i++)
            a.add(i);
        return a;
    }
}
