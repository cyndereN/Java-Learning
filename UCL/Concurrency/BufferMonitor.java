class BufferImpl<E> implements Buffer<E> {
    …
    public synchronized void put(E o) throws InterruptedException {
        while (count==size) wait();
        buf[in] = o; ++count; in=(in+1)%size;
        notifyAll();
    }
    public synchronized E get() throws InterruptedException {
        while (count==0) wait();
        E o = buf[out]; 
        buf[out]=null; --count; out=(out+1)%size;
        notifyAll();
        return (o);
    }
}

class Producer implements Runnable {
    Buffer buf;
    String alphabet= "abcdefghijklmnopqrstuvwxyz";
    Producer(Buffer b) {buf = b;}
    public void run() {
    try {
        int ai = 0;
        while(true) {
            ThreadPanel.rotate(12);
            buf.put(alphabet.charAt(ai));
            ai=(ai+1) % alphabet.length();
            ThreadPanel.rotate(348);
        }
    } catch (InterruptedException e){}
    }
}
