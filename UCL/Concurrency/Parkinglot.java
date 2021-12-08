public void start() {
    CarParkControl c = new DisplayCarPark(carDisplay,Places);
    arrivals.start(new Arrivals(c));
    departures.start(new Departures(c));
}

class Arrivals implements Runnable {
    CarParkControl carpark;
    Arrivals(CarParkControl c) {carpark = c;}
    public void run() {
        try {
            while(true) {
                ThreadPanel.rotate(330);
                carpark.arrive();
                ThreadPanel.rotate(30);
            }
        } catch (InterruptedException e){}
    }
}

class CarParkControl {
    private int spaces;
    private int capacity;
    CarParkControl(int n) 
    {capacity = spaces = n;}
    synchronized void arrive() throws InterruptedException {
        while (spaces==0) wait();
        --spaces;
        notifyAll();
    }
    synchronized void depart() throws InterruptedException {
        while (spaces==capacity) wait();
        ++spaces;
        notifyAll();
    }
}