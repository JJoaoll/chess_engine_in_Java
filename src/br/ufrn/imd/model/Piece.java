package br.ufrn.imd.model;

import javax.swing.text.Position;
import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Piece {

    protected Position2D position = new Position2D(0,0);

    protected Side         side  = null;
    protected int         value  = 0;

    public boolean  isWhite() {
        return side.isWhite();
    }

    public boolean  isBlack() {
        return side.isBlack();
    }

    public abstract boolean movable(Position2D candidate_position);


}
