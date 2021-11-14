package uk.ac.ucl.comp0010.visitor;

public class NumOfNodes implements ExpressionVisitor {
    public int visit(VisitorDemo.Num number) {
        return 1;
    }
    public int visit(VisitorDemo.Plus plus) {
        return plus.left.accept(this) + plus.right.accept(this) + 1;
    };
    public int visit(VisitorDemo.Minus minus) {
        return minus.left.accept(this) + minus.right.accept(this) + 1;
    };
  
}
