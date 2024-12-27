package br.ufrn.imd.model.Rules;

import br.ufrn.imd.control.PieceNotFound;
import br.ufrn.imd.model.Board.Board;
import br.ufrn.imd.model.Board.Tile;
import br.ufrn.imd.model.Matrices.Grid;
import br.ufrn.imd.model.Matrices.Position2D;
import br.ufrn.imd.model.Pieces.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

// TODO: DO!
public class ClassicalRules implements RuleSet {

    LinkedList<Position2D> board_positions = new LinkedList<>();

    //SMART WAY WHEN??
    {
        board_positions.add(Position2D.fromChessNotation("a1"));
        board_positions.add(Position2D.fromChessNotation("a2"));
        board_positions.add(Position2D.fromChessNotation("a3"));
        board_positions.add(Position2D.fromChessNotation("a4"));
        board_positions.add(Position2D.fromChessNotation("a5"));
        board_positions.add(Position2D.fromChessNotation("a6"));
        board_positions.add(Position2D.fromChessNotation("a7"));
        board_positions.add(Position2D.fromChessNotation("a8"));

        board_positions.add(Position2D.fromChessNotation("b1"));
        board_positions.add(Position2D.fromChessNotation("b2"));
        board_positions.add(Position2D.fromChessNotation("b3"));
        board_positions.add(Position2D.fromChessNotation("b4"));
        board_positions.add(Position2D.fromChessNotation("b5"));
        board_positions.add(Position2D.fromChessNotation("b6"));
        board_positions.add(Position2D.fromChessNotation("b7"));
        board_positions.add(Position2D.fromChessNotation("b8"));

        board_positions.add(Position2D.fromChessNotation("c1"));
        board_positions.add(Position2D.fromChessNotation("c2"));
        board_positions.add(Position2D.fromChessNotation("c3"));
        board_positions.add(Position2D.fromChessNotation("c4"));
        board_positions.add(Position2D.fromChessNotation("c5"));
        board_positions.add(Position2D.fromChessNotation("c6"));
        board_positions.add(Position2D.fromChessNotation("c7"));
        board_positions.add(Position2D.fromChessNotation("c8"));

        board_positions.add(Position2D.fromChessNotation("d1"));
        board_positions.add(Position2D.fromChessNotation("d2"));
        board_positions.add(Position2D.fromChessNotation("d3"));
        board_positions.add(Position2D.fromChessNotation("d4"));
        board_positions.add(Position2D.fromChessNotation("d5"));
        board_positions.add(Position2D.fromChessNotation("d6"));
        board_positions.add(Position2D.fromChessNotation("d7"));
        board_positions.add(Position2D.fromChessNotation("d8"));

        board_positions.add(Position2D.fromChessNotation("e1"));
        board_positions.add(Position2D.fromChessNotation("e2"));
        board_positions.add(Position2D.fromChessNotation("e3"));
        board_positions.add(Position2D.fromChessNotation("e4"));
        board_positions.add(Position2D.fromChessNotation("e5"));
        board_positions.add(Position2D.fromChessNotation("e6"));
        board_positions.add(Position2D.fromChessNotation("e7"));
        board_positions.add(Position2D.fromChessNotation("e8"));

        board_positions.add(Position2D.fromChessNotation("f1"));
        board_positions.add(Position2D.fromChessNotation("f2"));
        board_positions.add(Position2D.fromChessNotation("f3"));
        board_positions.add(Position2D.fromChessNotation("f4"));
        board_positions.add(Position2D.fromChessNotation("f5"));
        board_positions.add(Position2D.fromChessNotation("f6"));
        board_positions.add(Position2D.fromChessNotation("f7"));
        board_positions.add(Position2D.fromChessNotation("f8"));

        board_positions.add(Position2D.fromChessNotation("g1"));
        board_positions.add(Position2D.fromChessNotation("g2"));
        board_positions.add(Position2D.fromChessNotation("g3"));
        board_positions.add(Position2D.fromChessNotation("g4"));
        board_positions.add(Position2D.fromChessNotation("g5"));
        board_positions.add(Position2D.fromChessNotation("g6"));
        board_positions.add(Position2D.fromChessNotation("g7"));
        board_positions.add(Position2D.fromChessNotation("g8"));

        board_positions.add(Position2D.fromChessNotation("h1"));
        board_positions.add(Position2D.fromChessNotation("h2"));
        board_positions.add(Position2D.fromChessNotation("h3"));
        board_positions.add(Position2D.fromChessNotation("h4"));
        board_positions.add(Position2D.fromChessNotation("h5"));
        board_positions.add(Position2D.fromChessNotation("h6"));
        board_positions.add(Position2D.fromChessNotation("h7"));
        board_positions.add(Position2D.fromChessNotation("h8"));
    }

    @Override
    public boolean isValidMove(Move move) {

        int x = move.getInitialPosition().getX();
        int y = move.getInitialPosition().getY();

        Optional<Piece> opt_piece = move.getBoardBeforeMove().getPiece(x, y);

        // EXPRESSAO HORRIVEL DE FEIA PQ O JAVA NAO TEM O BASICO!!!
        return opt_piece.map(piece -> {

            if (piece instanceof Pawn p)        {
                return false;
            }

            else if (piece instanceof Rook r)   {
                return validRookMoves (r, move.getBoardBeforeMove())
                            .contains      (move.getFinalPosition());
            }

            else if (piece instanceof Knight k) {
                return false;
            }

            else if (piece instanceof Bishop b) {
                return true;
            }

            else if (piece instanceof Queen q)  {
                return true;
            }

            else if (piece instanceof King k)   {
                return false;
            }

            else {
                throw new PieceNotFound("Unexpected piece: " + piece.getClass().getSimpleName());
            }
        }).orElse(false);

    }

    private LinkedList<Position2D> validRookMoves (Rook rook, Board board) {
        // Correcao automatica
        LinkedList<Position2D> valid_positions = new LinkedList<>(List.copyOf(board_positions));

        System.out.println("Initial valid_positions: " + valid_positions.stream()
                .map(Position2D::toChessNotation).collect(Collectors.joining(",")));

        LinkedList<Position2D> one_step_after_valid_positions = valid_positions.stream()
                .filter(rook::movable)
                .collect(Collectors
                .toCollection(LinkedList::new));

        System.out.println("AFTER ONE STEP valid_positions: " + one_step_after_valid_positions.stream()
                .map(Position2D::toChessNotation).collect(Collectors.joining(",")));


        return one_step_after_valid_positions;

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
