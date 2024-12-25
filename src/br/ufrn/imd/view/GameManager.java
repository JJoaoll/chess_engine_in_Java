package br.ufrn.imd.view;

import br.ufrn.imd.control.Input;
import br.ufrn.imd.model.Board.Board;
import br.ufrn.imd.model.Game;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class GameManager extends JPanel {

    public GameManager() {
        Input input = Input.getInstance();
        Board board = Game.getBoard();
        this.setPreferredSize(new Dimension(board.getWidth() * board.tileSize, board.getHeight() * board.tileSize));

        this.addMouseListener       (input);
        this.addMouseMotionListener (input);
        //this.setBackground(Color.GREEN);
    }


    public void paintComponent (Graphics g) {
        super.paintComponent (g); // apaga as coisas
        Game.getInstance ();
        Board board = Game.getBoard();
        Graphics2D g2d = (Graphics2D) g;

        paintBoard (g);
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

    private void paintBoard (Graphics g) {
        Board board = Game.getBoard();
        Graphics2D g2d = (Graphics2D) g;
        // TODO: Modularize
        for (int r = 0; r < board.getTiles().getCols(); r++) {
            for (int c = 0; c < board.getTiles().getRows(); c++) {
                // Inline if else (haskell's better)
                g2d.setColor((c+r) % 2 == 0 ? new Color(227, 198, 181) : new Color(157, 105, 53));
                g2d.fillRect(c * board.tileSize, r * board.tileSize, board.tileSize, board.tileSize);
            }
        }
    }


}