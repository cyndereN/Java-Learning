package uk.ac.ucl.comp0010.visitor;

public class NoVisitorDemo {

    public interface Expression {
        int calculate();
    }

    public class Num implements Expression {
        int value;
        public Num(int value) {
            this.value = value;
        }
        public int calculate() {
            return value;
        }
    }

    public class Plus implements Expression {
        Expression left;
        Expression right;
        public Plus(Expression left, Expression right) {
            this.left = left;
            this.right = right;
        }
        public int calculate() {
            return left.calculate() + right.calculate();
        }
    }

    public class Minus implements Expression {
        Expression left;
        Expression right;
        public Minus(Expression left, Expression right) {
            this.left = left;
            this.right = right;
        }
        public int calculate() {
            return left.calculate() - right.calculate();
        }

    }


}