public class StringArrayTest {
    public static void main(String[] args) {

        StringArray a = new StringArray();

        for(int i=0; i<15; i++){
            a.add(i+"");
        }

        for (int i=0; i<a.size(); i++){
            System.out.println(a.get(i));

        }

        a.set(10,null);

        for (int i=0; i<a.size(); i++){
            System.out.println(a.get(i));
        }

        StringArray b = new StringArray(a);

        b.insert(13,"");
        b.remove(15);

        for (int i=0; i<b.size(); i++){
            System.out.println(b.get(i));
        }

        b.add("Hello");

        System.out.println(b.size());

        System.out.println(b.contains("HELLO"));
        System.out.println(b.containsMatchingCase("HELLO"));
        System.out.println(b.indexOf("HELLO"));
        System.out.println(b.indexOfMatchingCase("HELLO"));
        System.out.println(b.indexOfMatchingCase("Hello"));

        StringArray c = new StringArray();
        c.insert(0,"aa");

        System.out.println(c.get(0));
        System.out.println(c.size());
    }
}
