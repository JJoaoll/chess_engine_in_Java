package br.ufrn.imd.model.Pieces;

import br.ufrn.imd.model.Matrices.Position2D;
import br.ufrn.imd.model.Rules.Side;
/**
 * Classe de definição da peça Rainha
 * @author Joao Lucas
 *
 */
public class Queen extends Piece  implements Cloneable {

    public Queen (Position2D new_position, Side new_side) {
        this.current_position = new Position2D(new_position);
        this.side             = new_side; 
        this.value            = 9;

    }

    @Override
    public boolean movable(Position2D candidate_position) {
        return this.current_position.isAlignedWith         (candidate_position) ||
               this.current_position.isInTheSameDiagonalOf (candidate_position);
    }

    @Override
    public Piece clone() {
        return new Queen (new Position2D(current_position), this.side);
    }
}
