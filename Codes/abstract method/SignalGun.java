public class SignalGun extends Gun {
    public void shoot(){
        System.out.println("朝天发射");
    }
    @Override
    public void sound() {

    }

    @Override
    public boolean shift() {
        return false;
    }

}

