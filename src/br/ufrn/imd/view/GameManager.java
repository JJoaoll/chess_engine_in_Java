package br.ufrn.imd.view;

import br.ufrn.imd.control.Input;
import br.ufrn.imd.model.Board.Board;
import br.ufrn.imd.model.Game;
import br.ufrn.imd.model.Pieces.Piece;
import br.ufrn.imd.model.Rules.ClassicalRules;
import br.ufrn.imd.model.Rules.Move;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Optional;

// TODO: Consertar a parte singleton
public class GameManager extends JPanel {

    private Optional<Piece> selected_piece = Optional.empty();

    private static GameManager instance;

    private GameManager() {
        Input input = Input.getInstance();
        Board board = Game.getBoard();
        this.setPreferredSize(new Dimension(board.getWidth() * board.tileSize, board.getHeight() * board.tileSize));

        this.addMouseListener       (input);
        this.addMouseMotionListener (input);
        //this.setBackground(Color.GREEN);
    }

    public static GameManager getInstance() {
        if (Objects.isNull(instance)) {
            synchronized (GameManager.class) {
                if (Objects.isNull(instance)) {
                    instance = new GameManager();
                }
            }
        }
        return instance;
    }


    /// ///////////////////////////////////////////////////////////
    // TODO: Simplificar a logica
    public void makeMove (Move move) {
        if (move.getBoardBeforeMove().equals(Game.getBoard())) {
            if (true) {
                Game.makeMove (move);
                // TODO: DA OVERLEAD LOGO, POR FAVOR!!
                System.out.println(Game.getBoard().getPiece(move.getInitialPosition().getX(), move.getInitialPosition().getY()));
            }
        }
    }


    public static Optional<Piece> getPiece (int col, int row) {
        Game game = Game.getInstance();
        return game.getPiece(col, row);
    }


    public static void selectPiece (Optional<Piece> opt_piece) {
        GameManager referee = GameManager.getInstance();
        referee.setSelectedPiece(opt_piece);

    }

    private void setSelectedPiece(Optional<Piece> opt_piece) {
        selected_piece = opt_piece;
    }


    public Optional<Piece> getSelectedPiece () {
        return selected_piece;
    }


    public void paintComponent (Graphics g) {
        super.paintComponent (g); // apaga as coisas
        Game.getInstance ();
        Board board = Game.getBoard();
        Graphics2D g2d = (Graphics2D) g;

        paintBoard (g);
        paintPieces (g);
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
        // TODO: Modularize e confira os c's e os r's
        for (int r = 0; r < board.getTiles().getCols(); r++) {
            for (int c = 0; c < board.getTiles().getRows(); c++) {
                // TODO: Generalize colors by using the settings checkup!!
                g2d.setColor((c+r) % 2 == 0 ? new Color(227, 198, 181) : new Color(157, 105, 53));
                g2d.fillRect(c * board.tileSize, r * board.tileSize, board.tileSize, board.tileSize);

            }
        }
    }


    private void paintPieces (Graphics g) {
        Board board = Game.getBoard();
        Graphics2D g2d = (Graphics2D) g;

        // TODO: resolver chamada de metodos aninhadas
        // Salvando possiveis erros remanescentes
        LinkedList<Piece> pieces = board.getPieces();
        for (Piece piece : pieces) {
            //System.out.println(piece.getCurrent_position().getX() + "|" + piece.getCurrent_position().getY() + " : " + piece.getClass());
            PieceView.paintPiece(g2d, piece);
        }
    }


}
