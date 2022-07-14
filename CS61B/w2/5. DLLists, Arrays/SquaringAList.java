public class SquaringAList {
    public static IntList square(IntList L) {
        if (L==null){
            return L;
        } else {
            IntList rest = square(L.rest);
            IntList M = new IntList(L.first*L.first);
            return M;
        }
    }


    public static IntList squareMutative(IntList L) {
        IntList B = L;
        while (B != null) {
            B.first *= B.first;
            B = B.rest;
        }
            return L;
        }
        
}
