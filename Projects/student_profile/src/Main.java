public class Main {
    public static void main(String[] args) {
        Profile profileOne = new Profile("Sally", "Salmon", "Film", 3.75, 2022);
        Profile profileTwo = new Profile("Max", "Tiffon", "Computer Science", 3.45, 2021);

        profileTwo.incrementExpectedGraduationYear();
        System.out.println(profileTwo.expectedYearToGraduate);
    }
}
