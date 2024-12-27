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
        GameManager referee = GameManager.getInstance();
        Board board = Game.getBoard();

        int col = e.getX() / board.tileSize;
        int row = e.getY() / board.tileSize;

        // TODO: REMOVE THESE SHITTY TRY CATCHS!!!!
        // TODO: DO!
        Optional<Piece> opt_piece = referee.getSelectedPiece();
        opt_piece.ifPresent(piece -> {
            Move move = new Move(board, piece.getCurrent_position(), new Position2D(col, row));
            // isValidMove
            referee.makeMove(move);
        });

        // TODO: IT SHOULD BE STATIC?
        GameManager.selectPiece(Optional.empty());
        referee.repaint();

        /*if(board.selectedPiece != null) {
            Move move = new Move(board, board.selectedPiece, col, row);

            // TODO: Inlinize it
            try {
                if(board.isValidMove(move)){
                    board.makeMove(move);
                }

                else {
                    board.selectedPiece.setxPos(
                            board.selectedPiece.getCol() * board.tileSize);

                    board.selectedPiece.setyPos(
                            board.selectedPiece.getRow() * board.tileSize);
                }
            }

            catch (Exception ex) {
                throw new RuntimeException(ex);
            }

            board.selectedPiece = null;
            board.repaint();
        }*/

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

/*@Override
    public void mouseDragged(MouseEvent e) {
        GameManager referee = GameManager.getInstance();
        Board board = Game.getBoard();
        Optional<Piece> opt_piece = referee.getSelectedPiece();

        opt_piece.ifPresent(piece -> {
            // Atualize as coordenadas da peça com base no movimento do mouse
            int newX = e.getX() - (board.tileSize / 2);
            int newY = e.getY() - (board.tileSize / 2);

            Position2D position = new Position2D(newX, newY);

            if (true) {
                piece.setCurrent_position(position);
            }

            referee.repaint();
        });
    }*/

    public int getDraggedX() {
        return draggedX;
    }

    public int getDraggedY() {
        return draggedY;
    }
}
