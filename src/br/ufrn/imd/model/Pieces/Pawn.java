package br.ufrn.imd.model.Pieces;

import br.ufrn.imd.model.Matrices.Position2D;
import br.ufrn.imd.model.Rules.Side;

public class Pawn extends Piece {

    public Pawn (Position2D new_position, Side new_side) {
        this.current_position = new Position2D(new_position);
        this.side             = new_side; // vamos ter problemas com referencia aqui?
        this.value            = 1;
    }

    @Override
    public boolean movable(Position2D candidate_position) {
        return this.current_position.isOneYBehind(candidate_position);
    }
}
