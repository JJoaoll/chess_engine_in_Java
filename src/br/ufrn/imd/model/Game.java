package br.ufrn.imd.model;

import br.ufrn.imd.model.Board.Board;
import br.ufrn.imd.model.Rules.ClassicalRules;
import br.ufrn.imd.model.Rules.GameState;
import br.ufrn.imd.model.Rules.Move;
import br.ufrn.imd.model.Rules.RuleSet;
import br.ufrn.imd.view.GameManager;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class Game {
    private Board            board;
    private RuleSet          rules;
    private List<Move>   move_list = new LinkedList<>();
    private GameState   game_state = GameState.Playing;

    private static Game instance;

    private Game(RuleSet new_rules) {
        this.rules = new_rules;
        this.board = rules.initializeBoard();
    }

    // TODO: remove
    /*public Game(RuleSet new_rules) {
        this.rules = new_rules;
        this.board = rules.initializeBoard();
    }*/

    public static Game getInstance() {
        if (Objects.isNull(instance)) {
            synchronized (Game.class) {
                if (Objects.isNull(instance)) {
                    instance = new Game(new ClassicalRules());
                }
            }
        }
        return instance;
    }


    public void makeMove (Move move) {

    }

    // GETTER's

    // TODO: custom Getter's


    // SETTER's

    // TODO: Custom Setter's

}
