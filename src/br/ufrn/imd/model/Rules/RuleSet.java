package br.ufrn.imd.model.Rules;

import br.ufrn.imd.model.Board.Board;

public interface RuleSet {
    public boolean isValidMove (Move move);
    public Board initializeBoard();
}
