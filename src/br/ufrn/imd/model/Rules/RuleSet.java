package br.ufrn.imd.model.Rules;

import br.ufrn.imd.model.Board.Board;

public interface RuleSet {
    public boolean isSpecialMove (Move move, Side turn);
    public boolean isValidMove   (Move move, Side turn);
    public Board initializeBoard ();
}
