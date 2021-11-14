package uk.ac.ucl.comp0010.template;

/**
 * Base class of social network.
 */
public abstract class Network {
    String userName;
    String password;

    Network() {}

    /**
     * Publish the data to whatever network.
     */
    public boolean post(String message) {
        // Authenticate before posting. Every network uses a different
        // authentication method.
        if (logIn(this.userName, this.password)) {
            // Send the post data.
            boolean result =  sendData(message.getBytes());
            logOut();
            return result;
        }
        return false;
    }

    abstract boolean logIn(String userName, String password);
    abstract boolean sendData(byte[] data);
    abstract void logOut();
}


@Test
public void testTemplate() throws Exception {
    Random rd = new Random();
    boolean choice = rd.nextBoolean();
    Network network;
    if (choice) {
        network = new Facebook("john", "12345");
    } else {
        network = new Twitter("john", "12345");
    }
    network.post("what's up?");
    assertTrue(true);
}