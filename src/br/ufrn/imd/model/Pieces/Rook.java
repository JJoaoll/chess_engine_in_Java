package br.ufrn.imd.model.Pieces;

import br.ufrn.imd.model.Matrices.Position2D;
import br.ufrn.imd.model.Rules.Side;
/**
 * Classe de definição da peça Torre
 * @author Joao Lucas
 *
 */
public class Rook extends Piece implements Cloneable, BeginnersLucky {

    private boolean has_made_the_first_move = false;

    public Rook (Position2D new_position, Side new_side) {
        this.current_position = new Position2D(new_position);
        this.side             = new_side; 
        this.value            = 5;
    }

    public Rook () {}

    @Override
    public boolean movable(Position2D candidate_position) {
        return current_position.isAlignedWith(candidate_position);
    }

    @Override
    public Piece clone() {
        return new Rook (new Position2D(current_position), this.side);
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
