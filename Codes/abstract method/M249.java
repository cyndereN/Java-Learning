public class M249 extends Gun {
    @Override
    public void sound() {
        System.out.println("哒哒哒");
    }

    @Override
    public boolean shift() {
        System.out.println("100");
        return true;
    }
}