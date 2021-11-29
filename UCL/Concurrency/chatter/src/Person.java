public class Person extends Thread {
    private String name;

    public Person(String name){
        this.name = name;
    }

    public void run(){
        for (int i=0; i<1000 ; i++){
            System.out.println("Hi! My name is " + name);
        }
    }
}
