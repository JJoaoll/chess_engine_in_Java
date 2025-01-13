package br.ufrn.imd.view;

import br.ufrn.imd.model.Board.Board;
import br.ufrn.imd.model.Game;
import br.ufrn.imd.model.Matrices.Position2D;
import br.ufrn.imd.model.Pieces.*;
import br.ufrn.imd.model.Rules.ClassicalChessRules;
import br.ufrn.imd.model.Rules.Move;
import br.ufrn.imd.model.Rules.RuleSet;
import br.ufrn.imd.model.Rules.Side;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import java.util.Optional;

/**
 * Classe verificadora das regras
 * @author Joao Lucas
 * @author Felipe Augusto
 *
 */

public class Referee {
    RuleSet rules;

    Referee(RuleSet rules) {
        this.rules = rules;
    }

    /**
     * método para iniciar tela de escolha para a promoção do peão
     * @return peça que o peão ira virar
     */
    private String promotionScreenSelect (LinkedList<Piece> options_to_promote) {
        final JDialog dialog = new JDialog((Frame) null, "Escolha a promoção", true);
        dialog.setSize(300, 150);
        dialog.setLayout(new FlowLayout());

        final String[] selected_piece = {null};

        JLabel label = new JLabel("Escolha qual será a promoção do peão:");
        dialog.add(label);

        LinkedList<JButton> buttons = new LinkedList<>();
        for (Piece piece : options_to_promote) {
            buttons.add(new JButton(piece.getClass().getSimpleName()));
        }

        for (JButton button : buttons) {
            button.addActionListener(e -> {
                selected_piece[0] = button.getText();
                dialog.dispose();
            });

            dialog.add(button);
        }

        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);

        return selected_piece[0];
    }



    /**
     * Método de funcionamento principal das regras especiais do xadrez clássico
     */
    private void makeItClassicallySpecial (Game game, Move move) throws IllegalArgumentException {
        Board board = game.getBoardRf();
        Side turn   = game.getTurn();


        ClassicalChessRules classical_rules = (ClassicalChessRules) this.rules;

        int x0 = move.getInitialPosition().getX();
        int y0 = move.getInitialPosition().getY();

        Optional<Piece> opt_piece = board.getPiece (x0, y0);
        if (opt_piece.isEmpty ())
            throw new IllegalArgumentException ("Piece not found");

        try {
            Piece piece = opt_piece.get();

            if (piece instanceof Pawn pawn) {
                if(classical_rules.isAPromotingPawn (pawn, move, turn)) {
                    
                    Position2D initial_position = move.getInitialPosition();
                    Position2D final_position   = move.getFinalPosition();

                    String chosenPiece = promotionScreenSelect(classical_rules.getPromotingOptions());

                    Piece promotedPiece;
                    switch (chosenPiece) {
                        case "Queen":
                            promotedPiece = new Queen(final_position, pawn.getSide());
                            break;
                        case "Rook":
                            promotedPiece = new Rook(final_position, pawn.getSide());
                            break;
                        case "Bishop":
                            promotedPiece = new Bishop(final_position, pawn.getSide());
                            break;
                        case "Knight":
                            promotedPiece = new Knight(final_position, pawn.getSide());
                            break;
                        default:
                            throw new IllegalArgumentException("Escolha inválida para promoção: " + chosenPiece);
                    }

                    board.replacePiece(initial_position.getX(), initial_position.getY(), Optional.empty());


                    board.replacePiece(final_position.getX(), final_position.getY(), Optional.of(promotedPiece) 
                    );
                }

                else if (classical_rules.isAnEnPassant (game, pawn, move)) {
                    
                    Move last_move = game.getMoveList().getFirst();

                    int side_walk = pawn.isWhite() ? -1 : 1;

                    int pawn_x = pawn.getCurrent_position().getX();
                    int pawn_y = pawn.getCurrent_position().getY();

                    board.replacePiece(pawn_x, pawn_y, Optional.empty());

                    if (last_move.getFinalPosition().isXBehindOf(pawn.getCurrent_position())) {

                        board.replacePiece(pawn_x - 1, pawn_y + side_walk, Optional.of(pawn));
                        board.replacePiece(pawn_x - 1, pawn_y, Optional.empty());

                    }

                    else {
                        board.replacePiece(pawn_x + 1, pawn_y + side_walk, Optional.of(pawn));
                        board.replacePiece(pawn_x + 1, pawn_y, Optional.empty());
                    }

                }
            }

            else if (piece instanceof King king) {
                if (classical_rules.isCastlingShort (king, move, game.getTurn())) {

                    String rook_side = king.isWhite() ? "h1" : "h8";
                    Position2D rook_position = Position2D.fromChessNotation(rook_side);
                    Position2D king_position = king.getCurrent_position();


                    board.replacePiece (king_position.getX(), king_position.getY(), Optional.empty());
                    board.replacePiece (rook_position.getX(), rook_position.getY(), Optional.empty());

                    String rook_spot = king.isWhite() ? "f1" : "f8";
                    String king_spot = king.isWhite() ? "g1" : "g8";

                    Position2D new_rook_position = Position2D.fromChessNotation(rook_spot);
                    Position2D new_king_position = Position2D.fromChessNotation(king_spot);

                    board.replacePiece (new_king_position.getX(), new_king_position.getY(), Optional.of(king));
                    board.replacePiece (new_rook_position.getX(), new_rook_position.getY(), Optional.of(new Rook(new_rook_position, king.getSide())));

                }

                else if (classical_rules.isCastlingLong   (king, move, game.getTurn())) {
                    if (classical_rules.isCastlingLong (king, move, game.getTurn())) {

                        String rook_side = king.isWhite() ? "a1" : "a8";
                        Position2D rook_position = Position2D.fromChessNotation(rook_side);
                        Position2D king_position = king.getCurrent_position();


                        board.replacePiece(king_position.getX(), king_position.getY(), Optional.empty());
                        board.replacePiece(rook_position.getX(), rook_position.getY(), Optional.empty());

                        String rook_spot = king.isWhite() ? "d1" : "d8";
                        String king_spot = king.isWhite() ? "c1" : "c8";

                        Position2D new_rook_position = Position2D.fromChessNotation(rook_spot);
                        Position2D new_king_position = Position2D.fromChessNotation(king_spot);

                        board.replacePiece(new_king_position.getX(), new_king_position.getY(), Optional.of(king));
                        board.replacePiece(new_rook_position.getX(), new_rook_position.getY(), Optional.of(new Rook(new_rook_position, king.getSide())));
                    }

                }
            }

            else {
                throw new IllegalArgumentException ("Not a special piece");
            }

            game.swapTurn();

        }

        catch (Exception e) {
            e.getMessage();
            throw e;
        }
    }

    public void makeItSpecial (Game game, Move move) {
        if (rules instanceof ClassicalChessRules classical_rules) {
            makeItClassicallySpecial(game, move);
        }

    }

    public RuleSet getRules() {
        return rules;
    }

    public void setRules(RuleSet rules) {
        this.rules = rules;
    }

    public boolean isSpecialMove(Game game, Move move) {
        return rules.isSpecialMove(game, move);
    }

    public boolean isValidMove(Move move, Side turn) {
        return rules.isValidMove(move, turn);
    }
}
