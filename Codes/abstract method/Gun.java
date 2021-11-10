public abstract class Gun{
    private long length;
    public void shoot(String shootType){
        System.out.println("射击方法\n");
    }
    /*抽象方法，关键字abstract
        不能有方法体,但是方法的返回类型需要表明
    */

    public abstract void sound();
    public abstract boolean shift();
}