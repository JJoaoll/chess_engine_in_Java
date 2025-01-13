package br.ufrn.imd.model.Matrices;

import br.ufrn.imd.model.Board.Tile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Classe que gerencia o grid
 * @author Joao Lucas
 *
 */
public class Grid<T> {
    private final List<List<T>> grid = new ArrayList<>();
    private final int cols, rows;

    public Grid(int cols, int rows) {
        this.cols = cols;
        this.rows = rows;

        for (int i = 0; i < cols; i++) {
            grid.add(new ArrayList<>(Collections.nCopies(rows, null)));
        }
    }

    public Grid(Grid<T> grid_to_copy) {
        
        this.cols = grid_to_copy.getCols();
        this.rows = grid_to_copy.getRows();
        
        for (int i = 0; i < this.cols; i++) {
            List<T> new_col = new ArrayList<>(this.rows);
            for (int j = 0; j < this.rows; j++) {
                new_col.add(grid_to_copy.getValue(i, j));
            }           
            this.grid.add(new_col);
        }
    }

    /**
     * Método para verificação de igualdade entre posições
     */
    @Override
    public boolean equals(Object obj) {
        System.out.println("\ntestou os grids");
        if (obj instanceof Grid another_grid) {
            for (int i = 0; i < this.cols; i++)
                for (int j = 0; j < this.rows; j++) {
                    Tile tile1 = (Tile) this        .getValue(i, j);
                    Tile tile2 = (Tile) another_grid.getValue(i, j);

                    if (!tile1.equals(tile2)) {
                        System.out.println ("as posicoes" + i + "x" + j + ": nao concordam" );
                        return false;
                    }
                }

            System.out.println("retornou true pros grids!!");
            return true;

        }
        return false;
    }
    
    /**
     * método settter de row
     * @param col_index
     * @param row
     */
    public void setRow(int col_index, List<T> row) {
        if (col_index > 0 && col_index <= cols - 1) {
            grid.set(col_index, row);
        }
    }

    /**
     * método getter value
     * @param col_index
     * @param row_index
     * @return
     */
    public T getValue(int col_index, int row_index) {
        return grid.get(col_index).get(row_index);
    }

    /**
     * Método setter de Value
     * @param col_index
     * @param row_index
     * @param value
     */
    public void setValue(int col_index, int row_index, T value) {
        grid.get(col_index).set(row_index, value);
    }



    /**
     * Método getter de Grid
     * @return
     */
    public List<List<T>> getGrid() {
        return grid;
    }

    /**
     * Método getter de Cols
     * @return
     */
    public int getCols() {
        return cols;
    }

    /**
     * Método getter de Rows
     * @return
     */
    public int getRows() {
        return rows;
    }






}
