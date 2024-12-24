package br.ufrn.imd.model;

public class Position2D {
    int x = 0;
    int y = 0;

    public Position2D(int x, int y) {
        this.x = x; this.y = y;
    }

    public Position2D() {}

    public int getX() { return x; }

    public int getY() { return y; }

    public void setY(int y) { this.y = y; }

    public void setX(int x) { this.x = x; }


}
