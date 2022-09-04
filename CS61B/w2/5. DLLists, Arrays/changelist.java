public class changelist {
    // 不能改
    public static void mulitplyBy3(int[] A) {
        for (int x: A) {
            x = x * 3;
        }
    }
    // 能改
    public static void multiplyBy2(int[] A) {
        int[] B = A;
        for (int i = 0; i < B.length; i+= 1) {
            B[i] *= 2;
        }
    }
        
}
