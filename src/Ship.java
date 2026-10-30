public class Ship {
    private int size;
    private int hits;

    public Ship(int size) {
        this.size = size;
        this.hits = 0;
    }

    public void hit() {
        if (!isSunk()) {
            hits++;
        }
    }

    public boolean isSunk() {
        return hits >= size;
    }

    public int getSize() {
        return size;
    }

    public int getHits() {
        return hits;
    }
}
