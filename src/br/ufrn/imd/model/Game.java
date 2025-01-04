package br.ufrn.imd.model;

import br.ufrn.imd.model.Board.Board;
import br.ufrn.imd.model.Matrices.Position2D;
import br.ufrn.imd.model.Pieces.BeginnersLucky;
import br.ufrn.imd.model.Pieces.Piece;
import br.ufrn.imd.model.Rules.*;
import br.ufrn.imd.view.GameManager;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
/**
 * Classe que gerencia o jogo
 * @author Joao Lucas
 *
 */
public class Game {
    private Board             board;
    private RuleSet           rules;
    private LinkedList<Move>  move_list = new LinkedList<>();
    private GameState        game_state = GameState.Playing;

    // Consideremos isso absoluto pra todos os jogos p/ simplificar.
    private Side turn = Side.WhiteSide;

    // Mais um problema com estaticidade
    /**
     * Método getter de Turn
     * @return
     */
    public Side getTurn() {
        return this.turn;
    }

    // acabou sendo necessario!!
    /**
     * Método para trocar de turno
     */
    public void swapTurn () {
        this.turn = turn.OponentSide();
    }

    public Game(RuleSet new_rules) {
        this.rules = new_rules;
        this.board = rules.initializeBoard();
    }

    /**
     * Método para realizar movimentos no jogo
     * @param move
     */
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
        this.move_list.addFirst(new Move (
                new Board (move.getBoardBeforeMove()),
                move.getInitialPosition(),
                move.getFinalPosition())
        );

        StringBuilder writed_moves = new StringBuilder();
        for (Move m : this.move_list) {
            writed_moves
                    .append(m.getInitialPosition()
                    .toChessNotation())
                    .append("-")
                    .append(m.getFinalPosition()
                    .toChessNotation())
                    .append("  ");            
        }

        System.out.println(String.valueOf(move_list.stream()
                .map(Move::getBoardBeforeMove)
                .collect(Collectors.toCollection(LinkedList::new))));


        System.out.println(writed_moves);
        System.out.println();


    }

    /**
     * Método para conseguir uma peça em uma posição
     * @param col
     * @param row
     * @return
     */
    public Optional<Piece> getPiece(int col, int row) {
        return board.getPiece(col, row);
    }


    // TODO: Discover about possible side effects here!!
    public Board getBoard() {
        return this.board;
    }

    /**
     * Método getter de GameState
     * @return
     */
    public GameState getGameState() {
        return game_state;
    }


    // TODO: refatorar pra esse ser o padraozao!!!!!
    /**
     * Método getter de BoardRF
     * @return
     */
    public Board getBoardRf() {
        return this.board;
    }

    /**
     * Método getter de Rules
     * @return
     */
    public RuleSet getRules() {
        return this.rules;
    }

    /**
     * Método getter de MoveList
     * @return
     */
    public LinkedList<Move> getMoveList() {
        return move_list;
    }
    // SETTER's


    /**
     * Método setter de updateGameState
     */
    public void updateGameState () {
        game_state = rules.getGameState(this);
    }

}
