public class M4 extends Gun {
    /*
    如果一个类继承自抽象类，则必须实现这个抽象类的所有的抽象方法

    实现  类似于重写，如果父类中没有对应的方法体，子类来完成这个方法就叫实现

    能不能重写？  也可以重写
     */
    @Override
    public void sound() {
        System.out.println("嘚嘚嘚");
    }

    @Override
    public boolean shift() {
        System.out.println("40");
        return true;
    }
}
