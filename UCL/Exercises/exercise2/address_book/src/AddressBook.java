import java.util.ArrayList;

public class AddressBook {
    private final ArrayList<AddressBookEntry> b = new ArrayList<>();

    public void add(String n, String p, String e){
        AddressBookEntry a = new AddressBookEntry(n, p, e);
        b.add(a);
    }

    public void search(String n){
        for(AddressBookEntry tmp:b){
            if (tmp.name.equals(n))
                tmp.print();
        }
    }

    public void remove(String n){
        b.removeIf(tmp -> tmp.name.equals(n));
    }

    public void print(){
        for(AddressBookEntry tmp:b){
                tmp.print();
        }
    }

    public static void main(String[] args) {
        AddressBook book1 = new AddressBook();
        book1.add("CC","18810815066", "caoce123@163.com");
        book1.add("CC","17710606019", "caoce123@163.com");
        book1.add("ZZ","110","cnm@uc.ac.uk");
        book1.search("CC");
        book1.search("ZZ");
        book1.remove("ZZ");
        book1.print();
    }
}
