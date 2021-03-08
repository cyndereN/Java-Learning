package stackdatastructure;

import java.util.ArrayList;
import java.util.EmptyStackException;

public class GenericStack<T> {
    private ArrayList<T> values;

    public GenericStack() {
        values = new ArrayList<>();
    }

    public boolean isEmpty() {
        return values.isEmpty();
    }

    public void push(T i) {
        values.add(i);
    }

    public T pop() {
        if(values.size() == 0) { //empty
            System.out.println("Stack empty!");          //solution 1
            //System.exit(1); //Force program to stop    //solution 2
            //assert values.size()!=0;                   //solution 3
            return null;
        }
        return values.remove(getLastIndex());
    }

    public T pop() throws EmptyStackException{    //solution 4
        if(values.size() == 0)
            throw new EmptyStackException(); // no return needed here
        else
            return values.remove(getLastIndex());
    }

    private int getLastIndex() {
        return values.size() - 1;   //extract
    }

    public T top() {
        return values.get(getLastIndex());
    }
}
