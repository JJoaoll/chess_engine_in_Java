package br.ufrn.imd.model;

//TODO: Lidar com valores nulos nessa clase:

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

    public Position2D () {}

    public boolean isInTheSameColumnOf (Position2D another_position) {
        return this.x == another_position.getX();
    }

    public boolean isInTheSameRowOf (Position2D another_position) {
        return this.y == another_position.getY();
    }

    public boolean isAlignedWith (Position2D another_position) {
        return this.isInTheSameColumnOf(another_position) ||
               this.isInTheSameRowOf(another_position);
    }

    public boolean isInTheSameRightDiagonalOf (Position2D another_position) {
        return (this.x - this.y) == (another_position.getX() - another_position.getY());
    }



    public boolean isInTheSameLeftDiagonalOf (Position2D another_position) {
        return (this.x + this.y) == (another_position.getX() + another_position.getY());
    }


    public boolean isInTheSameDiagonalOf (Position2D another_position) {
        return this.isInTheSameRightDiagonalOf (another_position) ||
               this.isInTheSameLeftDiagonalOf  (another_position);
    }

    public boolean isInAKnightsAwayOf (Position2D another_position) {
        return Math.abs(this.x - another_position.getX()) * Math.abs(this.y - another_position.getY()) == 2;
    }

    // Inclui a propria posicao
    public boolean isSurroundedBy (Position2D another_position) {
        return Math.abs(this.x - another_position.getX()) <= 1
            && Math.abs(this.y - another_position.getY()) <= 1;
    }

    public boolean isOneYBehind (Position2D another_position) {
        return this.y + 1 == another_position.getY();
    }


    public int getX () { return x; }

    public int getY () { return y; }

    public void setY (int y) { this.y = y; }

    public void setX (int x) { this.x = x; }

}
