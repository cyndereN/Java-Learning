class Lecturer {
    private int plane_count = 0;

    public synchronized void catchPlane() {
        plane_count++;
    }

    public int getPlaneCount() {
        return plane_count;
    }

}