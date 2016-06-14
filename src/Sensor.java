import java.util.ArrayList;

/**
 * Created by RyotoTomioka on 2016/06/02.
 */
public class Sensor {
    static int ID = 0;

    private int id;
    private double x, y;
    private ArrayList<Integer> lum_history;

    public Sensor(double x, double y) {
        id = ID++;
        this.x = x;
        this.y = y;

        lum_history = new ArrayList<>();
    }

    public void appendHistory(int l) {
        lum_history.add(l);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public int getLum(int s) {
        return lum_history.get(s);
    }

    public ArrayList<Integer> getLum_history() {
        return lum_history;
    }
}
