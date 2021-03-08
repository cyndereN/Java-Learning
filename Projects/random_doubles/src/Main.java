public class Main {
    private void run(){
        double sum = 0;
        double max = -0.9999999;
        double min = 0.9999999;
        for(int i=0; i<10000; i++){
            double n = Math.random()>0.5 ? Math.random() : -Math.random();
            sum += n;
            if(n>max) max = n;
            if(n<min) min = n;
        }
        System.out.println(sum/10000);
        System.out.println(min);
        System.out.println(max);

    }

    public static void main(String[] args) {
        new Main().run();
    }
}
