package br.ufrn.imd.view;

import br.ufrn.imd.model.Pieces.Piece;

import java.awt.*;
import java.util.Optional;

public class Tile {
    // Optional pode ser uma opcao valida?
    private Optional<Piece> piece       = Optional.empty();
    private String coordinate           = ""  ;
    private Color color                 = null;
    //  g2d.setColor((c+r) % 2 == 0 ?
    //  new Color(227, 198, 181) : new Color(157, 105, 53));

    public Tile(String coordinate, Color color) {
        this.coordinate = coordinate;
        this.color      = color;
    }

    public Tile(String coordinate, Color color, Piece piece) {
        this.coordinate = coordinate;
        this.color      = color;
        this.piece      = Optional.of(piece);
    }

    // GETTER's

    public Optional<Piece> getPiece() {
        return piece;
    }

    public String getCoordinate() {
        return coordinate;
    }

    public Color getColor() {
        return color;
    }

    // SETTER's

    public void setPiece(Optional<Piece> optional_piece) {
        this.piece = optional_piece;
    }

    public void setCoordinate(String coordinate) {
        this.coordinate = coordinate;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
