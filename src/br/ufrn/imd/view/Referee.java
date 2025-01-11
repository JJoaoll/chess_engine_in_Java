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

        dialog.setVisible(true);

        return selected_piece[0];
    }



    // CONTEM MUITOS SIDE-EFFECTS!!!!!!!
    /**
     * Método de funcionamento principal das regras clássicas
     */
    private void makeItClassicallySpecial (Game game, Move move) throws IllegalArgumentException {
        Board board = game.getBoardRf(); // BUSCANDO SIDE EFFECTS!!!! //TODO: tem refatoracão de base aqui!!!!
        Side turn   = game.getTurn(); // TODO: acople!!!


        // TRUST:
        ClassicalChessRules classical_rules = (ClassicalChessRules) this.rules;

        int x0 = move.getInitialPosition().getX();
        int y0 = move.getInitialPosition().getY();

        Optional<Piece> opt_piece = board.getPiece (x0, y0);
        if (opt_piece.isEmpty ())
            throw new IllegalArgumentException ("Piece not found");

        // Nao vamos reverificar nada aqui!
        try {
            Piece piece = opt_piece.get();

            // Como nenhum dos casos conflitam:
            if (piece instanceof Pawn pawn) {
                if(classical_rules.isAPromotingPawn (pawn, move, turn)) {
                    // TRUST!
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

                    // TODO: DEIXAR O JOGADOR ESCOLHER QUAL PECA!!!! TODO TODO TODO

                    board.replacePiece(final_position.getX(), final_position.getY(), Optional.of(promotedPiece) // TODO: perde em eficiencia
                    );
                }

                else if (classical_rules.isAnEnPassant (game, pawn, move)) {
                    // TRUST: (Nao ha caso de en-passant duplo)
                    Move last_move = game.getMoveList().getFirst();
                    // Trust: (Nao havera caso de en-passant fora do
                    // tabuleiro por causa da estrutura do codigo ate aqui.)

                    int side_walk = pawn.isWhite() ? -1 : 1;

                    int pawn_x = pawn.getCurrent_position().getX();
                    int pawn_y = pawn.getCurrent_position().getY();

                    board.replacePiece(pawn_x, pawn_y, Optional.empty());

                    //TODO: fix this bad name again!!
                    // LEFT LEFT LEFT
                    if (last_move.getFinalPosition().isXBehindOf(pawn.getCurrent_position())) {
                        // TRUST: Nunca ha o caso onde o peao corta outra peca pq
                        // se o peao inimigo avancou, entao a casa, agora 'atras' dele, esta vazia.

                        board.replacePiece(pawn_x - 1, pawn_y + side_walk, Optional.of(pawn));
                        board.replacePiece(pawn_x - 1, pawn_y, Optional.empty());

                    }

                    // Big trust!!!! TODO: Fix??!!
                    else {
                        board.replacePiece(pawn_x + 1, pawn_y + side_walk, Optional.of(pawn));
                        board.replacePiece(pawn_x + 1, pawn_y, Optional.empty());
                    }

                }
            }

            else if (piece instanceof King king) {
                // Eh mais comum que o rei roque curto :)
                if (classical_rules.isCastlingShort (king, move, game.getTurn())) { // TODO: FIXAR ESSE METODO ESTATICO PRA NAO SER!

                    String rook_side = king.isWhite() ? "h1" : "h8";
                    Position2D rook_position = Position2D.fromChessNotation(rook_side);
                    Position2D king_position = king.getCurrent_position();

                    /*//
                    Rook rook = (Rook) board.getPiece(rook_position.getX(), rook_position.getY()).get();*/

                    // deletando:
                    board.replacePiece (king_position.getX(), king_position.getY(), Optional.empty());
                    board.replacePiece (rook_position.getX(), rook_position.getY(), Optional.empty());

                    String rook_spot = king.isWhite() ? "f1" : "f8";
                    String king_spot = king.isWhite() ? "g1" : "g8";

                    Position2D new_rook_position = Position2D.fromChessNotation(rook_spot);
                    Position2D new_king_position = Position2D.fromChessNotation(king_spot);

                    // Recolocando:
                    board.replacePiece (new_king_position.getX(), new_king_position.getY(), Optional.of(king));
                    board.replacePiece (new_rook_position.getX(), new_rook_position.getY(), Optional.of(new Rook(new_rook_position, king.getSide())));

                }

                else if (classical_rules.isCastlingLong   (king, move, game.getTurn())) {
                    // Eh mais comum que o rei roque curto :)
                    if (classical_rules.isCastlingLong (king, move, game.getTurn())) { // TODO: FIXAR ESSE METODO ESTATICO PRA NAO SER!

                        String rook_side = king.isWhite() ? "a1" : "a8";
                        Position2D rook_position = Position2D.fromChessNotation(rook_side);
                        Position2D king_position = king.getCurrent_position();

                    /*//
                    Rook rook = (Rook) board.getPiece(rook_position.getX(), rook_position.getY()).get();*/

                        // deletando:
                        board.replacePiece(king_position.getX(), king_position.getY(), Optional.empty());
                        board.replacePiece(rook_position.getX(), rook_position.getY(), Optional.empty());

                        String rook_spot = king.isWhite() ? "d1" : "d8";
                        String king_spot = king.isWhite() ? "c1" : "c8";

                        Position2D new_rook_position = Position2D.fromChessNotation(rook_spot);
                        Position2D new_king_position = Position2D.fromChessNotation(king_spot);

                        // Recolocando:
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

        // Aqui outras regras
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
