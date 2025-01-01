package br.ufrn.imd.model;

import br.ufrn.imd.model.Board.Board;
import br.ufrn.imd.model.Matrices.Position2D;
import br.ufrn.imd.model.Pieces.BeginnersLucky;
import br.ufrn.imd.model.Pieces.Piece;
import br.ufrn.imd.model.Rules.*;
import br.ufrn.imd.view.GameManager;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

// TODO: deixar de ser estatico (vai dar trabalho)
public class Game {
    private Board             board;
    private RuleSet           rules;
    private LinkedList<Move>  move_list = new LinkedList<>();
    private GameState        game_state = GameState.Playing;

    private static Game instance;

    // Consideremos isso absoluto pra todos os jogos p/ simplificar.
    private Side turn = Side.WhiteSide;

    // Mais um problema com estaticidade
    public static Side getTurn() {
        Game game = Game.getInstance();
        return game.turn;
    }

    // acabou sendo necessario!!
    public void swapTurn () {
        this.turn = turn.OponentSide();
    }

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

        game.swapTurn();
        // Essas convencoes do java....
        game.move_list.addFirst(move);
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

    public GameState getGameState() {
        return game_state;
    }


    // TODO: refatorar pra esse ser o padraozao!!!!!
    public Board getBoardRf() {
        return this.board;
    }

    public static RuleSet getRules() {
        Game game = Game.getInstance();
        return game.rules;
    }

    public LinkedList<Move> getMoveList() {
        return move_list;
    }





    // SETTER's

    // TODO: Custom Setter's

    public void updateGameState () {
        game_state = rules.getGameState(this);
    }

}
