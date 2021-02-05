package stacktest;

import org.junit.Before;
import org.junit.Test;
import stackdatastructure.GenericStack;

import static org.junit.Assert.*;

public class GenericStackTest {

    private GenericStack<String> aStack;  //Fixture

    @Before
    public void setUp() throws Exception {
        aStack = new GenericStack<>();
    }

    @Test
    public void anEmptyStackShouldBeEmpty() {
        assertTrue(aStack.isEmpty());
    }

    @Test
    public void pushingAValueOntoAStackShouldGiveANonEmptyStack() {
        aStack.push("1");
        assertFalse(aStack.isEmpty());
    }

    @Test //alt+insert
    public void pushingThenPoppingShouldGiveAnEmptyStack() {
        aStack.push("1");
        aStack.pop();
        assertTrue(aStack.isEmpty());
    }

    @Test
    public void topShouldBePushedValue() {
        aStack.push("1");
        assertEquals("1", aStack.top());
    }

    @Test
    public void pushingThenPoppingTwoValuesShouldGiveAnEmptyStack() {
        aStack.push("1");
        aStack.push("2");
        assertEquals("2", aStack.pop()); //Usually only one assert
        assertEquals("1", aStack.pop());
        assertTrue(aStack.isEmpty());
    }
}