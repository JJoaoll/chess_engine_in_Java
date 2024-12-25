package br.ufrn.imd.model.Board;

import br.ufrn.imd.model.Matrices.Grid;

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
