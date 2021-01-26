public class AddressBookEntry {
    String name;
    String phone;
    String email;
    public AddressBookEntry(String name, String phone, String email){
        this.name = name;
        this.phone = phone;
        this.email = email;
    }
    public String getName(){
        return this.name;
    }
    public String getPhone(){
        return this.phone;
    }
    public String getEmail(){
        return this.email;
    }

    public void print(){
        System.out.println(name+':'+' '+phone+' '+email);
    }
}
