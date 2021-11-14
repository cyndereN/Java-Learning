package uk.ac.ucl.comp0010.visitor;

public class Calculator implements ExpressionVisitor {
    public int visit(VisitorDemo.Num number) {
        return number.value;
    }
    public int visit(VisitorDemo.Plus plus) {
        return plus.left.accept(this) + plus.right.accept(this);
    };
    public int visit(VisitorDemo.Minus minus) {
        return minus.left.accept(this) - minus.right.accept(this);
    };

}
