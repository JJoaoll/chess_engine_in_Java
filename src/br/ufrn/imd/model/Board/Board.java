package br.ufrn.imd.model.Board;

import br.ufrn.imd.model.Matrices.Grid;
import br.ufrn.imd.model.Matrices.Position2D;
import br.ufrn.imd.model.Pieces.Piece;

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
        Tile t = tiles.getValue(col, row);
        return t.getPiece();
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
