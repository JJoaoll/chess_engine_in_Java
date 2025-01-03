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

public class Game {
    private Board             board;
    private RuleSet           rules;
    private LinkedList<Move>  move_list = new LinkedList<>();
    private GameState        game_state = GameState.Playing;

    // Consideremos isso absoluto pra todos os jogos p/ simplificar.
    private Side turn = Side.WhiteSide;

    // Mais um problema com estaticidade
    public Side getTurn() {
        return this.turn;
    }

    // acabou sendo necessario!!
    public void swapTurn () {
        this.turn = turn.OponentSide();
    }

    public Game(RuleSet new_rules) {
        this.rules = new_rules;
        this.board = rules.initializeBoard();
    }

    public void makeMove (Move move) {
        int col1 = move.getInitialPosition().getX();
        int row1 = move.getInitialPosition().getY();

        // TODO: um overload cairia bem
        Optional<Piece> opt_piece = this.board.getPiece(col1, row1);
        //System.out.println(opt_piece.isPresent());

        int col2 = move.getFinalPosition().getX();
        int row2 = move.getFinalPosition().getY();

        // TODO: um overload cairia bem aqui tbm
        this.board.replacePiece(col1, row1, Optional.empty());
        this.board.replacePiece(col2, row2, opt_piece);

        if (opt_piece.isPresent()) {
            Piece piece = opt_piece.get();
            piece.setCurrent_position(new Position2D(col2, row2));
            if (piece instanceof BeginnersLucky lucky_piece) {
                lucky_piece.registerMoveAsMade();
            }
        }

        this.swapTurn();
        // Essas convencoes do java....
        this.move_list.addFirst(move);
    }

    public Optional<Piece> getPiece(int col, int row) {
        return board.getPiece(col, row);
    }


    // TODO: Discover about possible side effects here!!
    public Board getBoard() {
        return this.board;
    }

    public GameState getGameState() {
        return game_state;
    }


    // TODO: refatorar pra esse ser o padraozao!!!!!
    public Board getBoardRf() {
        return this.board;
    }

    public RuleSet getRules() {
        return this.rules;
    }

    public LinkedList<Move> getMoveList() {
        return move_list;
    }
    // SETTER's


    public void updateGameState () {
        game_state = rules.getGameState(this);
    }

}
