package br.ufrn.imd.model;

import br.ufrn.imd.model.Board.Board;
import br.ufrn.imd.model.Matrices.Position2D;
import br.ufrn.imd.model.Pieces.BeginnersLucky;
import br.ufrn.imd.model.Pieces.Piece;
import br.ufrn.imd.model.Rules.ClassicalRules;
import br.ufrn.imd.model.Rules.GameState;
import br.ufrn.imd.model.Rules.Move;
import br.ufrn.imd.model.Rules.RuleSet;
import br.ufrn.imd.view.GameManager;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

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

    // TODO: Devo continuar statizando as coisas?
    public static void makeMove (Move move) {
        Game game = Game.getInstance();
        int col1 = move.getInitialPosition().getX();
        int row1 = move.getInitialPosition().getY();

        // TODO: um overload cairia bem
        Optional<Piece> opt_piece = game.board.getPiece(col1, row1);
        //System.out.println(opt_piece.isPresent());

        int col2 = move.getFinalPosition().getX();
        int row2 = move.getFinalPosition().getY();

        // TODO: um overload cairia bem aqui tbm
        game.board.replacePiece(col1, row1, Optional.empty());
        game.board.replacePiece(col2, row2, opt_piece);

        if (opt_piece.isPresent()) {
            Piece piece = opt_piece.get();
            piece.setCurrent_position(new Position2D(col2, row2));
            if (piece instanceof BeginnersLucky lucky_piece) {
                lucky_piece.registerMoveAsMade();
            }
        }
    }

    public Optional<Piece> getPiece(int col, int row) {
        return board.getPiece(col, row);
    }


    // GETTER's   // TODO: custom Getter's
    // TODO: Discover about possible side effects here!!
    public static Board getBoard() {
        // redundante?
        Game game = Game.getInstance();
        return game.board;
    }

    public static RuleSet getRules() {
        Game game = Game.getInstance();
        return game.rules;
    }




    // SETTER's

    // TODO: Custom Setter's

}
