package br.ufrn.imd.model.Pieces;

import br.ufrn.imd.model.Position2D;

public class Rook extends Piece {

    public Rook(Position2D position) {}

    @Override
    public boolean movable(Position2D candidate_position) {
        return false;
    }


}
