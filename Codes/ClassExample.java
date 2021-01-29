import java.util.ArrayList;
class Person {
    private String firstName;
    private String familyName;
    private ArrayList<String> emailAddresses;

    public Person(String firstName, String familyName) {
        this.firstName = firstName;
        this.familyName = familyName;
        this.emailAddresses = new ArrayList<String>();
    }

    public String getFullName() {
        return firstName + " " + familyName;
    }

    public void addEmailAddress(String address) {
        emailAddresses.add(address);
    }

    public ArrayList<String> getEmailAddresses() {
        return new ArrayList<String>(emailAddresses);
    }
}