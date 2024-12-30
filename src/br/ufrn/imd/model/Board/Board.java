package br.ufrn.imd.model.Board;

import br.ufrn.imd.model.Matrices.Grid;
import br.ufrn.imd.model.Matrices.Position2D;
import br.ufrn.imd.model.Pieces.King;
import br.ufrn.imd.model.Pieces.Piece;
import br.ufrn.imd.model.Rules.Side;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

//TODO: tratamento de erros
public class Board {
    private int width = 8, height = 8;
    private Grid<Tile> tiles;
    // TODO: It's ok with that beeing public and final?
    public final int tileSize = 85;

    public Board () {
        Grid<Tile> grid = new Grid<>(width, height);
    }

    // TODO: o getValue pode dar nulo, faz um try catch.
    public Board(Board board_to_copy) {
        this.width = board_to_copy.width;
        this.height = board_to_copy.height;

        // Criação de um novo Grid para os tiles
        Grid<Tile> new_tiles = new Grid<>(width, height);

        // Iteração sobre o tabuleiro original
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                // Tornando x e y efetivamente finais

                Tile original_tile        = board_to_copy.tiles.getValue(x, y);
                Optional<Piece> opt_piece = original_tile.getPiece();

                Tile new_tile = opt_piece.isPresent()
                        ? new Tile(
                        new Position2D(x, y).toChessNotation(),
                        opt_piece.get().clone())
                        : new Tile(
                        new Position2D(x, y).toChessNotation());

                new_tiles.setValue(x, y, new_tile);
            }

            this.tiles = new_tiles;
        }

        // Atualiza os tiles do novo tabuleiro
        this.tiles = new_tiles;
    }


    // Assumindo que esta tudo bem aqui!!
    public void setBoardGrid (Grid<Tile> grid) {
        for (int i = 0; i < width; i++)
            for (int j = 0; j < height; j++) {
                this.tiles.setValue(i, j, grid.getValue(i, j));
            }
    }

    public LinkedList<Piece> getPieces() {
        LinkedList<Piece> pieces = new LinkedList<Piece>();

        for (int c = 0; c < width; c++)
            for (int r = 0; r < height; r++) {
                Tile tile = tiles.getValue(c, r);
                if (tile != null) {
                    tile.getPiece().ifPresent(p -> pieces.add(p));
                    // versao com lambda:
                    // tile.getPiece().ifPresent(pieces::add);
                }
            }

        return pieces;
    }

    public Optional<Piece> getPiece (int col, int row) {
        try {
            Tile t = tiles.getValue(col, row);
            return t.getPiece();
        }

        catch (Exception e) {
            //System.out.println(e.getMessage());
            // Por hora ,esta bom!
            return Optional.empty();
        }
    }

    // Pega o rei de um certo lado/time/side
    // Assumption: so ha um rei de cada lado e sempre ha um.
    public King getKingFrom (Side side) {
        LinkedList<Piece> pieces = getPieces();

        for (Piece piece : pieces) {
            if (piece instanceof King k) {
                if (k.getSide() == side) {
                    return k;
                }
            }
        }

        return null;
    }

    // TODO: TRATAMENTO DE ERROS!!!
    public void replacePiece (int col, int row, Optional<Piece> opt_piece) {
        opt_piece.ifPresent(p -> {p.setCurrent_position(new Position2D(col, row));});
        System.out.println("Antes: " + tiles.getValue(col, row).getPiece());
        tiles.getValue(col, row).setPiece(opt_piece);
        System.out.println("Depois: " + tiles.getValue(col, row).getPiece());
    }

    // GETTER's

    public int getWidth() {
        return width;
    }

    public Grid<Tile> getTiles() {
        return tiles;
    }

    public int getTileSize() {
        return tileSize;
    }

    public int getHeight() {
        return height;
    }


    // SETTER's

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setTiles(Grid<Tile> tiles) {
        this.tiles = tiles;
    }
}
