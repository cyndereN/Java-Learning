package math_test;

public class math_test {
    private int number;
    private int sum;

    public int setSum(){
        sum = sum+number*number*number;
        return sum;
    }

    public int setNumber(int number){
        this.number=number;
        return number;

    }

    public int getNumber(){
        return number;
    }

    public int getSum(){
        return sum;
    }
}
