package br.ufrn.imd.model.Board;

import br.ufrn.imd.model.Pieces.Piece;

import java.util.Optional;

public class Tile {
    // Optional pode ser uma opcao valida?
    private Optional<Piece> piece       = Optional.empty();
    private String coordinate           = ""  ;
    //private Color color                 = null;
    //  g2d.setColor((c+r) % 2 == 0 ?
    //  new Color(227, 198, 181) : new Color(157, 105, 53));


    // Dois construtores diferentes bem faceis de usar :P
    public Tile(String coordinate) {
        this.coordinate = coordinate;
    }

    public Tile(String coordinate, Piece piece) {
        this.coordinate = coordinate;
        this.piece      = Optional.of(piece);
    }

    // GETTER's

    public Optional<Piece> getPiece() {
        return piece;
    }

    public String getCoordinate() {
        return coordinate;
    }

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

                /*System.out.println("piece1: " + piece1.getCurrent_position().toChessNotation());
                System.out.println("piece1: " + piece1.getClass().getSimpleName());

                System.out.println("piece2: " + piece2.getCurrent_position().toChessNotation());
                System.out.println("piece2: " + piece2.getClass().getSimpleName());*/

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

   /* public Color getColor() {
        return color;
    }*/

    // SETTER's

    public void setPiece(Optional<Piece> optional_piece) {
        this.piece = optional_piece;
    }

    public void setCoordinate(String coordinate) {
        this.coordinate = coordinate;
    }

   /* public void setColor(Color color) {
        this.color = color;
    }*/

}
