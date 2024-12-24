package br.ufrn.imd.model.Pieces;

import br.ufrn.imd.model.Position2D;
import br.ufrn.imd.model.Side;

public abstract class Piece {

    protected Position2D position = new Position2D(0,0);

    protected Side side  = null;
    protected int         value  = 0;

    public boolean  isWhite() {
        return side.isWhite();
    }

    public boolean  isBlack() {
        return side.isBlack();
    }

    public abstract boolean movable(Position2D candidate_position);

    // GETTER'S

    public Position2D getPosition() { return position; }

    public int getValue() { return value; }

    public Side getSide() { return side; }

    // SETTER'S

    public void setPosition(Position2D position) { this.position = position; }

    public void setSide(Side side) { this.side = side; }

    public void setValue(int value) { this.value = value; }

}
