/** Hide the nakedness of Node */
public class SLList_with_problem {
    // Nested class
    private static class IntNode { // if IntNode never uses any details of SLList class
        public int item;
        public IntNode next;
    
        public IntNode(int item, IntNode next) {
            this.item = item;
            this.next = next;
        }
    }

    private IntNode first;
    private int size;

    public SLList_with_problem() {
        first = null;
        size = 0;
    }


    public SLList_with_problem(int x) {
        first = new IntNode(x, null);
        size = 1;
    }

    public void addFirst(int x){
        first = new IntNode(x, first);
        size = size + 1;
    }

    public int getFirst(){
        return first.item;
    }

    
    //! We have a problem! can't deal with empty list
    // solution: sentinel node
    public void addLast(int x) {
        IntNode p = first;
        while (p.next != null) p = p.next;
        p.next = new IntNode(x, null);
        size = size + 1;
    }

    public int size() {
        return size;
    }

    public static void main(String[] args){
        SLList_with_problem L = new SLList_with_problem(15);
        L.addFirst(10);
        L.addFirst(5);
        L.addLast(20);
        System.out.println(L.getFirst());
        System.out.println(L.size());
    }
}