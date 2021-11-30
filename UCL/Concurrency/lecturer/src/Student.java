public class Student extends Thread {
    private Lecturer lecturer;
    private int number_planes;

    public Student(Lecturer lecturer, int number_planes){
        this.lecturer = lecturer;
        this.number_planes = number_planes;
    }
    public void run(){
        for (int i=0 ; i<number_planes ; i++){
            lecturer.catchPlane();
        }

        System.out.println("I've thrown " + number_planes + " planes");
    }
}
