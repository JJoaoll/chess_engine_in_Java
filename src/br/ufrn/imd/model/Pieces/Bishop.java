package br.ufrn.imd.model.Pieces;

import br.ufrn.imd.model.Matrices.Position2D;
import br.ufrn.imd.model.Rules.Side;

public class Bishop extends Piece implements Cloneable {

    public Bishop (Position2D new_position, Side new_side) {
        this.current_position = new Position2D(new_position);
        this.side             = new_side; // vamos ter problemas com referencia aqui?
        this.value            = 3;

    }

    @Override
    public boolean movable(Position2D candidate_position) {
        return current_position.isInTheSameDiagonalOf(candidate_position);
    }

    @Override
    public Piece clone() {
        return new Bishop (new Position2D(current_position), this.side);
    }

}
