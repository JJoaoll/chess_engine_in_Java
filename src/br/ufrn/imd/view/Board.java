package br.ufrn.imd.view;

import br.ufrn.imd.model.Grid;

import java.util.ArrayList;

//TODO: tratamento de erros
public class Board {
    private int width = 8, height = 8;
    private Grid<Tile> tiles;

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


}
