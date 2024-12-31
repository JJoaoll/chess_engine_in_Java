package br.ufrn.imd.model.Rules;

import br.ufrn.imd.model.Board.Board;
import br.ufrn.imd.model.Game;

public interface RuleSet {

    // A ideia eh que ele vai fazer coisas magicas com o tabuleiro!!!!
    public void makeItSpecial (Game game, Move move);
    public boolean isSpecialMove (Game game, Move move);
    public boolean isValidMove   (Move move, Side turn);
    public Board initializeBoard ();
}
