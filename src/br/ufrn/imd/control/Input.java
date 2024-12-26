package br.ufrn.imd.control;

import br.ufrn.imd.model.Board.Board;
import br.ufrn.imd.model.Game;
import br.ufrn.imd.model.Pieces.Piece;

import java.awt.event.*;
import java.util.Objects;
import java.util.Optional;


// TODO: Fix DRY!
// TODO: KeyListener next!
public class Input extends MouseAdapter{

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
        Game game    = Game.getInstance();
        Board board  = Game.getBoard();

        int col = e.getX() / board.tileSize;
        int row = e.getY() / board.tileSize;


        Optional<Piece> opt_piece = board.getPiece(col, row);
        game.selectPiece(opt_piece);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        Board board = Game.getBoard();

        int col = e.getX() / board.tileSize;
        int row = e.getY() / board.tileSize;

        // TODO: REMOVE THESE SHITTY TRY CATCHS!!!!
        // TODO: DO!
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
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        // TODO: DO!
       /* if(board.selectedPiece != null) {
            board.selectedPiece.setxPos(
                    e.getX() - (board.tileSize / 2));
            board.selectedPiece.setyPos(
                    e.getY() - (board.tileSize / 2));

            board.repaint();
        }*/
    }

}
