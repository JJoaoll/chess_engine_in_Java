package br.ufrn.imd.model.Rules;

import br.ufrn.imd.model.Board.Board;
import br.ufrn.imd.model.Board.Tile;
import br.ufrn.imd.model.Matrices.Grid;
import br.ufrn.imd.model.Matrices.Position2D;
import br.ufrn.imd.model.Pieces.*;

import java.util.LinkedList;
import java.util.Optional;

// TODO: DO!
public class ClassicalRules implements RuleSet {

    @Override
    public boolean isValidMove(Move move) {
        int x = move.getInitialPosition().getX();
        int y = move.getInitialPosition().getY();

        Optional<Piece> opt_piece = move.getBoardBeforeMove().getPiece(x, y);


        return false;
    }

    // TODO: Deleat it
    public LinkedList<Move> getAllValidMoves(Board board, Piece piece) {
        return null;
    }

    @Override
    public Board initializeBoard() {
        Board      new_board = new Board();
        Grid<Tile> new_tiles = new Grid<>(new_board.getWidth(), new_board.getHeight());

        // TODO: Maneira mais inteligente de fazer isso?
        new_tiles.setValue(0, 0, new Tile("a1", new Rook   (new Position2D(0, 0), Side.WhiteSide)));
        new_tiles.setValue(0, 1, new Tile("a2", new Pawn   (new Position2D(0, 1), Side.WhiteSide)));
        new_tiles.setValue(0, 2, new Tile("a3"));
        new_tiles.setValue(0, 3, new Tile("a4"));
        new_tiles.setValue(0, 4, new Tile("a5"));
        new_tiles.setValue(0, 5, new Tile("a6"));
        new_tiles.setValue(0, 6, new Tile("a7", new Pawn   (new Position2D(0, 6), Side.BlackSide)));
        new_tiles.setValue(0, 7, new Tile("a8", new Rook   (new Position2D(0, 7), Side.BlackSide)));

        new_tiles.setValue(1, 0, new Tile("b1", new Knight (new Position2D(1, 0), Side.WhiteSide) ));
        new_tiles.setValue(1, 1, new Tile("b2", new Pawn   (new Position2D(1, 1), Side.WhiteSide)));
        new_tiles.setValue(1, 2, new Tile("b3"));
        new_tiles.setValue(1, 3, new Tile("b4"));
        new_tiles.setValue(1, 4, new Tile("b5"));
        new_tiles.setValue(1, 5, new Tile("b6"));
        new_tiles.setValue(1, 6, new Tile("b7", new Pawn   (new Position2D(1, 6), Side.BlackSide)));
        new_tiles.setValue(1, 7, new Tile("b8", new Knight (new Position2D(1, 7), Side.BlackSide)));

        new_tiles.setValue(2, 0, new Tile("c1", new Bishop (new Position2D(2, 0), Side.WhiteSide) ));
        new_tiles.setValue(2, 1, new Tile("c2", new Pawn   (new Position2D(2, 1), Side.WhiteSide)));
        new_tiles.setValue(2, 2, new Tile("c3"));
        new_tiles.setValue(2, 3, new Tile("c4"));
        new_tiles.setValue(2, 4, new Tile("c5"));
        new_tiles.setValue(2, 5, new Tile("c6"));
        new_tiles.setValue(2, 6, new Tile("c7", new Pawn   (new Position2D(2, 6), Side.BlackSide)));
        new_tiles.setValue(2, 7, new Tile("c8", new Bishop (new Position2D(2, 7), Side.BlackSide)));

        new_tiles.setValue(3, 0, new Tile("d1", new Queen  (new Position2D(3, 0), Side.WhiteSide)));
        new_tiles.setValue(3, 1, new Tile("d2", new Pawn   (new Position2D(3, 1), Side.WhiteSide)));
        new_tiles.setValue(3, 2, new Tile("d3"));
        new_tiles.setValue(3, 3, new Tile("d4"));
        new_tiles.setValue(3, 4, new Tile("d5"));
        new_tiles.setValue(3, 5, new Tile("d6"));
        new_tiles.setValue(3, 6, new Tile("d7", new Pawn  (new Position2D(3, 6), Side.BlackSide)));
        new_tiles.setValue(3, 7, new Tile("d8", new Queen (new Position2D(3, 7), Side.BlackSide)));

        new_tiles.setValue(4, 0, new Tile("e1", new King  (new Position2D(4, 0), Side.WhiteSide)));
        new_tiles.setValue(4, 1, new Tile("e2", new Pawn  (new Position2D(4, 1), Side.WhiteSide)));
        new_tiles.setValue(4, 2, new Tile("e3"));
        new_tiles.setValue(4, 3, new Tile("e4"));
        new_tiles.setValue(4, 4, new Tile("e5"));
        new_tiles.setValue(4, 5, new Tile("e6"));
        new_tiles.setValue(4, 6, new Tile("e7", new Pawn  (new Position2D(4, 6), Side.BlackSide)));
        new_tiles.setValue(4, 7, new Tile("e8", new King  (new Position2D(4, 7), Side.BlackSide)));

        new_tiles.setValue(5, 0, new Tile("f1", new Bishop (new Position2D(5, 0), Side.WhiteSide) ));
        new_tiles.setValue(5, 1, new Tile("f2", new Pawn   (new Position2D(5, 1), Side.WhiteSide)));
        new_tiles.setValue(5, 2, new Tile("f3"));
        new_tiles.setValue(5, 3, new Tile("f4"));
        new_tiles.setValue(5, 4, new Tile("f5"));
        new_tiles.setValue(5, 5, new Tile("f6"));
        new_tiles.setValue(5, 6, new Tile("f7", new Pawn   (new Position2D(5, 6), Side.BlackSide)));
        new_tiles.setValue(5, 7, new Tile("f8", new Bishop (new Position2D(5, 7), Side.BlackSide)));

        new_tiles.setValue(6, 0, new Tile("g1", new Knight (new Position2D(6, 0), Side.WhiteSide)));
        new_tiles.setValue(6, 1, new Tile("g2", new Pawn   (new Position2D(6, 1), Side.WhiteSide)));
        new_tiles.setValue(6, 2, new Tile("g3"));
        new_tiles.setValue(6, 3, new Tile("g4"));
        new_tiles.setValue(6, 4, new Tile("g5"));
        new_tiles.setValue(6, 5, new Tile("g6"));
        new_tiles.setValue(6, 6, new Tile("g7", new Pawn   (new Position2D(6, 6), Side.BlackSide)));
        new_tiles.setValue(6, 7, new Tile("g8", new Knight (new Position2D(6, 7), Side.BlackSide)));

        new_tiles.setValue(7, 0, new Tile("h1", new Rook   (new Position2D(7, 0), Side.WhiteSide)));
        new_tiles.setValue(7, 1, new Tile("h2", new Pawn   (new Position2D(7, 1), Side.WhiteSide)));
        new_tiles.setValue(7, 2, new Tile("h3"));
        new_tiles.setValue(7, 3, new Tile("h4"));
        new_tiles.setValue(7, 4, new Tile("h5"));
        new_tiles.setValue(7, 5, new Tile("h6"));
        new_tiles.setValue(7, 6, new Tile("h7", new Pawn   (new Position2D(7, 6), Side.BlackSide)));
        new_tiles.setValue(7, 7, new Tile("h8", new Rook   (new Position2D(7, 7), Side.BlackSide)));

        new_board.setTiles(new_tiles);
        return new_board;
    }


}
