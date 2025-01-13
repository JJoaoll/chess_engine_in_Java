package br.ufrn.imd.model.Board;

import br.ufrn.imd.model.Pieces.Piece;

import java.util.Optional;
/**
 * Classe que gerencia as posições
 * @author Joao Lucas
 *
 */
public class Tile {
    private Optional<Piece> piece       = Optional.empty();
    private String coordinate           = ""  ;


    public Tile(String coordinate) {
        this.coordinate = coordinate;
    }

    public Tile(String coordinate, Piece piece) {
        this.coordinate = coordinate;
        this.piece      = Optional.of(piece);
    }


    /**
     * Método getter de Piece
     * @return
     */
    public Optional<Piece> getPiece() {
        return piece;
    }

    /**
     * Método getter de Coordinate
     * @return
     */
    public String getCoordinate() {
        return coordinate;
    }

    /**
     * Método para verificação se dois ladrinhos esão se sobrepondo 
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Tile another_tile) {

            if (this.piece.isPresent() != another_tile.piece.isPresent()) {
                System.out.println("saida 0");
                return false;
            }

            if (this.piece.isPresent()) {
                Piece piece1 = this         .piece.get();
                Piece piece2 = another_tile .piece.get();


                if (!piece1.getClass().getSimpleName().equals(piece2.getClass().getSimpleName())) {
                    System.out.println("saida 1");
                    return false;
                }

                if (!piece1.getSide().equals(piece2.getSide())) {
                    System.out.println("saida 2");
                    return false;
                }

            }

            return this.coordinate.equals(another_tile.coordinate);

        }
        return false;
    }


    /**
     * Método setter de Piece
     * @param optional_piece
     */
    public void setPiece(Optional<Piece> optional_piece) {
        this.piece = optional_piece;
    }

    /**
     * Método setter de Coordinate
     * @param coordinate
     */
    public void setCoordinate(String coordinate) {
        this.coordinate = coordinate;
    }


}
