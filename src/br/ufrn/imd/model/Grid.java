package br.ufrn.imd.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// TODO: tratamento de erros!
public class Grid<T> {
    private final List<List<T>> grid = new ArrayList<>();
    private final int cols, rows;

    // Um vetor[coluna][fileira]
    public Grid(int cols, int rows) {
        this.cols = cols;
        this.rows = rows;

        for (int i = 0; i < cols; i++) {
            grid.add(new ArrayList<>(Collections.nCopies(rows, null)));
        }
    }

    // col_index [0, cols-1]
    public void setRow(int col_index, List<T> row) {
        if (col_index > 0 && col_index <= cols - 1) {
            grid.set(col_index, row);
        }
    }

    public T getValue(int col_index, int row_index) {
        return grid.get(col_index).get(row_index);
    }

    public void setValue(int col_index, int row_index, T value) {
        grid.get(col_index).set(row_index, value);
    }


    // GETTER'S

    public List<List<T>> getGrid() {
        return grid;
    }

    public int getCols() {
        return cols;
    }

    public int getRows() {
        return rows;
    }






}
