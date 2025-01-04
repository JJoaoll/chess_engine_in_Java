package br.ufrn.imd.model.Rules;

import br.ufrn.imd.model.Board.Board;
import br.ufrn.imd.model.Game;
/**
 * Interface que normaliza os conjuntos de regra
 * @author Joao Lucas
 *
 */
public interface RuleSet {

    // A ideia eh que ele vai fazer coisas magicas com o tabuleiro!!!!
    public void    makeItSpecial (Game game, Move move);
    public boolean isSpecialMove (Game game, Move move);
    public boolean isValidMove   (Move move, Side turn);
    public Board initializeBoard ();
    public GameState getGameState    (Game game);

}
