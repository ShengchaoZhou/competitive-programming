package grammer;

public class Segment {
    int l, r;
    Segment(int l, int r) { this.l = l; this.r = r; }
    @Override
    public String toString() {
        return "(" + l + ", " + r + ")";
    }
}
