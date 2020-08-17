package jp.petrolingus.particlesystem.domain.algorithmes;

public class Event implements Comparable<Event> {

    public int id;
    public int id1;
    public int id2;
    public double time;

    public Event() {
        this(-1, 0, 0, Double.MAX_VALUE);
    }

    public Event(int id, int id1, int id2, double time) {
        this.id = id;
        this.id1 = id1;
        this.id2 = id2;
        this.time = time;
    }

    @Override
    public int compareTo(Event o) {
        return Double.compare(this.time, o.time);
    }
}
