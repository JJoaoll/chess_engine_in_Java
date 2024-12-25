package br.ufrn.imd.view;

import br.ufrn.imd.model.Board.Board;

import javax.swing.*;
import java.awt.*;

public class BoardView extends JPanel {
    private Board board = new Board();

    public BoardView(Board board) {
        this.board = board;
    }

    public void paintComponent (Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        // PAINT BOARD
        // SetCBoard Colors
        // TODO: Modularize
        for (int r = 0; r < board.getTiles().getCols(); r++) {
            for (int c = 0; c < board.getTiles().getRows(); c++) {
                // Inline if else (haskell's better)
                g2d.setColor((c+r) % 2 == 0 ? new Color(227, 198, 181) : new Color(157, 105, 53));
                g2d.fillRect(c * board.getTileSize(), r * board.getTileSize(), board.getTileSize(), board.getTileSize());
            }
        }

        /*// PAINT HIGHLIGHTS
        // TODO: fix this DRY ('U CAN MOVE HERE')
        // TODO: The current position should be less neutral!
        // TODO: Remove TRY CATCHS
        if (selectedPiece != null)
            for (int r = 0; r < rows; r++)
                for (int c = 0; c < cols; c++) {

                    try {
                        if(isValidMove(new Move(this, selectedPiece, c, r))) {
                            g2d.setColor(new Color(68, 180, 57, 190));
                            g2d.fillRect(c * tileSize, r * tileSize, tileSize, tileSize);
                        }
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }

        // PAINT PIECES
        for (Piece piece : pieceList) {
            piece.paint(g2d);
        }*/
    }


}
