public class Main {

    static char[] chs = new char[36];
    static {
        for(int i = 0; i < 10 ; i++) {
            chs[i] = (char)('0' + i);
        }
        for(int i = 10; i < chs.length; i++) {
            chs[i] = (char)('A' + (i - 10));
        }
    }

    public void toBase(int a, int to){
        StringBuilder sb = new StringBuilder();

        while (a != 0){
            sb.append(chs[a%to]);
            a = a / to;
        }

        System.out.println(sb.reverse().toString());
    }

    public static void main(String[] args) {
        new Main().toBase(3,2);
        new Main().toBase(69,35);
    }
}
