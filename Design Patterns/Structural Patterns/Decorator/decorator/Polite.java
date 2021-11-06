package uk.ac.ucl.comp0010.decorator;

public class Polite implements Speaker {
    Speaker speaker;
    public Polite(Speaker speaker) {
        this.speaker = speaker;
    }

    public String speak() {
        return "Excuse me, but " + speaker.speak();
    }
}

@Test
public void testDecorator() throws Exception {
    Speaker politeCat = new Polite(new Cat());
    assertEquals("Excuse me, but Meow", politeCat.speak());
}