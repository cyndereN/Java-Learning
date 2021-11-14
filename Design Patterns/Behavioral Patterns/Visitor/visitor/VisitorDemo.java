package uk.ac.ucl.comp0010.visitor;

public class VisitorDemo {

    public interface Expression {
        public int accept(ExpressionVisitor visitor);
    }

    public class Num implements Expression {
        int value;
        public Num(int value) {
            this.value = value;
        }
        public int accept(ExpressionVisitor visitor) {
            return visitor.visit(this);
        }

    }

    public class Plus implements Expression {
        Expression left;
        Expression right;
        public Plus(Expression left, Expression right) {
            this.left = left;
            this.right = right;
        }
        public int accept(ExpressionVisitor visitor) {
            return visitor.visit(this);
        }
    }

    public class Minus implements Expression {
        Expression left;
        Expression right;
        public Minus(Expression left, Expression right) {
            this.left = left;
            this.right = right;
        }
        public int accept(ExpressionVisitor visitor) {
            return visitor.visit(this);
        }
    }

}
