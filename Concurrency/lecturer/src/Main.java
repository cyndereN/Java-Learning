public class Main {
    public static void main(String[] args) throws InterruptedException {
        Lecturer lecturer = new Lecturer();
        Student student1 = new Student(lecturer, 1000);
        Student student2 = new Student(lecturer, 1000);
        // More interference
        student1.start();
        student2.start();

        for(int i=0 ; i<10 ; i++){
            System.out.println("The lecturer has counted "+lecturer.getPlaneCount()+" planes");
            Thread.sleep(10);
        }
    }
}
