package br.ufrn.imd.model.Pieces;

import br.ufrn.imd.model.Matrices.Position2D;
import br.ufrn.imd.model.Rules.Side;
/**
 * Classe de definição da peça Rei
 * @author Joao Lucas
 *
 */
public class King extends Piece  implements Cloneable, BeginnersLucky {

    boolean has_made_the_first_move = false;

    public King (Position2D new_position, Side new_side) {
        this.current_position = new Position2D(new_position);
        this.side             = new_side; // vamos ter problemas com referencia aqui?
        this.value            = -1;

    }

    @Override
    public boolean movable(Position2D candidate_position) {
        return this.current_position.isSurroundedBy( candidate_position );
    }

    @Override
    public Piece clone() {
        return new King (new Position2D(current_position), this.side);
    }

    @Override
    public boolean isTheFirstMove() {
        return !has_made_the_first_move;
    }

    @Override
    public void registerMoveAsMade() {
        has_made_the_first_move = true;
    }
}
