package br.ufrn.imd.model.Rules;

import br.ufrn.imd.model.Board.Board;
import br.ufrn.imd.model.Matrices.Position2D;

// O java vai cuidar dos getters e tudo mais.
public record Move (Board getBoardBeforeMove, Position2D getInitialPosition, Position2D getFinalPosition) {

}