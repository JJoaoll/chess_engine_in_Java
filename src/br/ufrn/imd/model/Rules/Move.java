package br.ufrn.imd.model.Rules;

import br.ufrn.imd.model.Board.Board;
import br.ufrn.imd.model.Matrices.Position2D;

// O java vai cuidar dos getters e tudo mais.
public record Move(Board board_before_the_move, Position2D initial_position, Position2D final_position) {

}