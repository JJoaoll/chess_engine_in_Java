package br.ufrn.imd.model;

public class Position2D {
    private int x = 0;
    private int y = 0;

    public Position2D(int new_x, int new_y) {
        this.x = new_x;
        this.y = new_y;
    }

    public Position2D(Position2D pos) {
        this.x = pos.getX();
        this.y = pos.getY();
    }

    public Position2D() {}

    public int getX() { return x; }

    public int getY() { return y; }

    public void setY(int y) { this.y = y; }

    public void setX(int x) { this.x = x; }


}
