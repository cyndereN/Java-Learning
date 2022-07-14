/** Hide the nakedness of Node */
public class SLList {
    // Nested class
    private static class IntNode { // if IntNode never uses any details of SLList class
        public int item;
        public IntNode next;
    
        public IntNode(int item, IntNode next) {
            this.item = item;
            this.next = next;
        }
    }

    // fisrt node is just sentinel
    private IntNode sentinel;
    private int size;

    public SLList() {
        sentinel = new IntNode(63, null);   // any number
        size = 0;
    }


    public SLList(int x) {
        sentinel = new IntNode(63, null);
        sentinel.next = new IntNode(x, null);
        size = 1;
    }

    public void addFirst(int x){
        sentinel.next = new IntNode(x, sentinel.next);
        size = size + 1;
    }

    public int getFirst(){
        return sentinel.next.item;
    }

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
        SLList L = new SLList(15);
        L.addFirst(10);
        L.addFirst(5);
        L.addLast(20);
        System.out.println(L.getFirst());
        System.out.println(L.size());
    }
}