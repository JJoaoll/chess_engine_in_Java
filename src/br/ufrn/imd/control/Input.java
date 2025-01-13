package br.ufrn.imd.control;

import br.ufrn.imd.model.Board.Board;
import br.ufrn.imd.model.Game;
import br.ufrn.imd.model.Matrices.Position2D;
import br.ufrn.imd.model.Pieces.Piece;
import br.ufrn.imd.model.Rules.Move;
import br.ufrn.imd.view.GameManager;

import java.awt.event.*;
import java.util.Objects;
import java.util.Optional;


/**
 * Classe que gerencia o input do mouse
 * @author Joao Lucas
 * @author Felipe Augusto
 */
public class Input extends MouseAdapter {

    private int draggedX = -1;
    private int draggedY = -1;

    private static Input instance;

    private Input () {}

    public static Input getInstance() {
        if (Objects.isNull(instance)) {
            synchronized (Input.class) {
                if (Objects.isNull(instance)) {
                    instance = new Input();
                }
            }
        }
        return instance;
    }

    /**
     * Método que identifica a libera do clique do mouse
     */
    @Override
    public void mousePressed(MouseEvent e) {
        Board board  = GameManager.getInstance().getBoard();

        int col = e.getX() / board.tileSize;
        int row = e.getY() / board.tileSize;

        Optional<Piece> opt_piece = GameManager.getPiece(col, row);
        GameManager.selectPiece(opt_piece);
    }

    /**
     * Método que identifica a libera do clique do mouse
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        GameManager gm = GameManager.getInstance();
        Board board    = gm.getBoard();

        int col = e.getX() / board.tileSize;
        int row = e.getY() / board.tileSize;

        Optional<Piece> opt_piece = gm.getSelectedPiece();
        opt_piece.ifPresent(piece -> {
            Move move = new Move(board, piece.getCurrent_position(), new Position2D(col, row));
            gm.makeMove(move);
        });

        GameManager.selectPiece(Optional.empty());
        gm.repaint();



        draggedX = -1;
        draggedY = -1;
    }

    /**
     * Método que movimenta as peças baseado usando o mouse
     */
    @Override
    public void mouseDragged(MouseEvent e) {
        GameManager gm       = GameManager.getInstance();
        Optional<Piece> opt_piece = gm.getSelectedPiece();

        opt_piece.ifPresent(piece -> {
            draggedX = e.getX() - (gm.getBoard().getTileSize() / 2);
            draggedY = e.getY() - (gm.getBoard().getTileSize() / 2);

            gm.repaint();
        });
    }

    /**
     * Método getter de DraggedX
     * @return
     */
    public int getDraggedX() {
        return draggedX;
    }

    /**
     * Método getter de Draggedy
     * @return
     */
    public int getDraggedY() {
        return draggedY;
    }
}
