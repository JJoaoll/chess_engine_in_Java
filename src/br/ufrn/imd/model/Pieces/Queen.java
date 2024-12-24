package br.ufrn.imd.model.Pieces;

import br.ufrn.imd.model.Position2D;
import br.ufrn.imd.model.Side;

public class Queen extends Piece {

    public Queen (Position2D new_position, Side new_side) {
        this.current_position = new Position2D(new_position);
        this.side             = new_side; // vamos ter problemas com referencia aqui?
        this.value            = 9;

    }

    @Override
    public boolean movable(Position2D candidate_position) {
        return this.current_position.isAlignedWith         (candidate_position) ||
               this.current_position.isInTheSameDiagonalOf (candidate_position);
    }
}
