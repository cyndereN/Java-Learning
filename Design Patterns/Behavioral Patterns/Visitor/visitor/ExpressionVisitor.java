package uk.ac.ucl.comp0010.visitor;

public interface ExpressionVisitor {
    public int visit(VisitorDemo.Num number);
    public int visit(VisitorDemo.Plus plus);
    public int visit(VisitorDemo.Minus minus);
}


@Test
public void testVisitor() throws Exception {
    NoVisitorDemo d1 = new NoVisitorDemo();
    NoVisitorDemo.Expression one = d1.new Num(1);
    NoVisitorDemo.Expression plus = d1.new Plus(one, one);

    assertEquals(2, plus.calculate());

    VisitorDemo d2 = new VisitorDemo();
    VisitorDemo.Expression one2 = d2.new Num(1);
    VisitorDemo.Expression plus2 = d2.new Plus(one2, one2);

    assertEquals(2, plus2.accept(new Calculator()));


}