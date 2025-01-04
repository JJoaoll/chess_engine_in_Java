package br.ufrn.imd.model.Pieces;

import br.ufrn.imd.model.Matrices.Position2D;
import br.ufrn.imd.model.Rules.Side;
/**
 * Classe de definição da peça Cavalo
 * @author Joao Lucas
 *
 */
public class Knight extends Piece  implements Cloneable {

    public Knight (Position2D new_position, Side new_side) {
        this.current_position = new Position2D(new_position);
        this.side             = new_side; // vamos ter problemas com referencia aqui?
        this.value            = 3;

    }

    @Override
    public boolean movable(Position2D candidate_position) {
        return current_position.isInAKnightsAwayOf (candidate_position);
    }

    @Override
    public Piece clone() {
        return new Knight (new Position2D(current_position), this.side);
    }
}
