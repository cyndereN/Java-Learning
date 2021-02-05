package stackdatastructure;

import java.util.ArrayList;

public class IntStack {

    private ArrayList<Integer> values;

    public IntStack() {
        values = new ArrayList<>();
    }

    public boolean isEmpty() {
        return values.isEmpty();
    }

    public void push(int i) {
        values.add(i);
    }

    public int pop() {
        return values.remove(getLastIndex());
    }

    private int getLastIndex() {
        return values.size() - 1;   //extract
    }

    public int top() {
        return values.get(getLastIndex());
    }
}
