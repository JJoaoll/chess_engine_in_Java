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


// TODO: Fix DRY!
// TODO: KeyListener next!
public class Input extends MouseAdapter{

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

    @Override
    public void mousePressed(MouseEvent e) {
        Board board  = Game.getBoard();

        int col = e.getX() / board.tileSize;
        int row = e.getY() / board.tileSize;

        Optional<Piece> opt_piece = GameManager.getPiece(col, row);
        GameManager.selectPiece(opt_piece);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        GameManager gm = GameManager.getInstance();
        Board board = Game.getBoard();

        int col = e.getX() / board.tileSize;
        int row = e.getY() / board.tileSize;

        // TODO: REMOVE THESE SHITTY TRY CATCHS!!!!
        // TODO: DO!
        Optional<Piece> opt_piece = gm.getSelectedPiece();
        opt_piece.ifPresent(piece -> {
            Move move = new Move(board, piece.getCurrent_position(), new Position2D(col, row));
            // isValidMove
            gm.makeMove(move);
        });

        // TODO: IT SHOULD BE STATIC?
        GameManager.selectPiece(Optional.empty());
        gm.repaint();



        draggedX = -1;
        draggedY = -1;
    }


    @Override
    public void mouseDragged(MouseEvent e) {
        GameManager referee       = GameManager.getInstance();
        Optional<Piece> opt_piece = referee.getSelectedPiece();

        opt_piece.ifPresent(piece -> {
            draggedX = e.getX() - (Game.getBoard().getTileSize() / 2);
            draggedY = e.getY() - (Game.getBoard().getTileSize() / 2);

            // Solicitar redesenho do tabuleiro
            referee.repaint();
        });
    }


    public int getDraggedX() {
        return draggedX;
    }

    public int getDraggedY() {
        return draggedY;
    }
}
