package br.ufrn.imd.model.Matrices;

//TODO: Lidar com valores nulos nessa clase:

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

public class Position2D {
    private int x = 0;
    private int y = 0;

    public Position2D(int new_x, int new_y) {
        this.x = new_x;
        this.y = new_y;
    }

    public Position2D(Position2D pos) {
        this.x = pos.getX();
        this.y = pos.getY();
    }

    public Position2D () {}

    public boolean isInTheSameColumnOf (Position2D another_position) {
        return this.x == another_position.getX();
    }

    public boolean isInTheSameRowOf (Position2D another_position) {
        return this.y == another_position.getY();
    }

    public boolean isAlignedWith (Position2D another_position) {
        return this.isInTheSameColumnOf(another_position) ||
               this.isInTheSameRowOf(another_position);
    }

    public boolean isInTheSameRightDiagonalOf (Position2D another_position) {
        return (this.x - this.y) == (another_position.getX() - another_position.getY());
    }

    public boolean isInTheSameLeftDiagonalOf (Position2D another_position) {
        return (this.x + this.y) == (another_position.getX() + another_position.getY());
    }


    public boolean isInTheSameDiagonalOf (Position2D another_position) {
        return this.isInTheSameRightDiagonalOf (another_position) ||
               this.isInTheSameLeftDiagonalOf  (another_position);
    }

    public boolean isInAKnightsAwayOf (Position2D another_position) {
        return Math.abs(this.x - another_position.getX()) * Math.abs(this.y - another_position.getY()) == 2;
    }

    // Inclui a propria posicao
    public boolean isSurroundedBy (Position2D another_position) {
        return Math.abs(this.x - another_position.getX()) <= 1
            && Math.abs(this.y - another_position.getY()) <= 1;
    }

    public boolean isXAboveOf (Position2D another_position) {
        return this.x > another_position.getX();
    }

    public boolean isXBehindOf (Position2D another_position) {
        return this.x < another_position.getX();
    }

    public boolean isYAboveOf (Position2D another_position) {
        return this.y > another_position.getY();
    }

    public boolean isYBehindOf (Position2D another_position) {
        return this.y < another_position.getY();
    }

    public boolean isInRightUpDiagonalOf (Position2D another_position) {
        return this.x > another_position.getX()
            && this.y > another_position.getY();
    }

    public boolean isInLeftUpDiagonalOf (Position2D another_position) {
        return this.x < another_position.getX()
            && this.y > another_position.getY();
    }

    public boolean isInRightDownDiagonalOf (Position2D another_position) {
        return this.x > another_position.getX()
            && this.y < another_position.getY();
    }

    public boolean isInLeftDownDiagonalOf (Position2D another_position) {
        return this.x < another_position.getX()
            && this.y < another_position.getY();
    }

    // TODO: contratos!

    // Assumindo que todos estao na mesma diagonal
    public static Position2D highestInTheLeftDiagonal (LinkedList<Position2D> positions) {
        return lowestXPosition(positions); // o mais a esquerda
    }

    // Assumindo que todos estao na mesma diagonal
    public static Position2D lowestInTheLeftDiagonal (LinkedList<Position2D> positions) {
        return highestXPosition(positions); // o mais a direita
    }

    // Assumindo que todos estao na mesma diagonal
    public static Position2D highestInTheRightDiagonal (LinkedList<Position2D> positions) {
        return highestXPosition(positions); // o mais a direita
    }

    // Assumindo que todos estao na mesma diagonal
    public static Position2D lowestInTheRightDiagonal (LinkedList<Position2D> positions) {
        return lowestXPosition(positions); // o mais a esquerda
    }

    // Aqui nao precisa usar getters e nem setters

    // Reducao sugerida:

    // TODO: Tratar do throws illegalArguments!!?
    public static Position2D highestXPosition(LinkedList<Position2D> positions) {
        return positions.stream().max(Comparator.comparingInt(Position2D::getX)).orElse(null);
    }

    public static Position2D lowestXPosition(LinkedList<Position2D> positions) {
        return positions.stream().min(Comparator.comparingInt(Position2D::getX)).orElse(null);
    }

    public static Position2D highestYPosition(LinkedList<Position2D> positions) {
        return positions.stream().max(Comparator.comparingInt(Position2D::getY)).orElse(null);
    }

    public static Position2D lowestYPosition(LinkedList<Position2D> positions) {
        return positions.stream().min(Comparator.comparingInt(Position2D::getY)).orElse(null);
    }

    public boolean isOneYBehind (Position2D another_position) {
        return this.y + 1 == another_position.getY();
    }

    //TODO: Melhorar as exceptions
    public static Position2D fromChessNotation(String coordinate) throws IllegalArgumentException {
        if (coordinate == null || coordinate.length() != 2) {
            throw new IllegalArgumentException("Coordenada inválida: " + coordinate);
        }

        // nomes sugeridos
        char file = coordinate.charAt(0);
        char rank = coordinate.charAt(1);

        if (file < 'a' || file > 'h' || rank < '1' || rank > '8') {
            throw new IllegalArgumentException("Coordenada fora do tabuleiro: " + coordinate);
        }

        // assim fica mais legivel:
        int x = file - 'a';
        int y = '8' - rank;

        return new Position2D(x, y);
    }

    //TODO: criar um throw unico, novo e adequado que herde deste
    // O nome "chessNotation ficou bem melhor"
    public String toChessNotation() throws IllegalArgumentException {

        if (x < 0 || x > 7 || y < 0 || y > 7) {
            throw new IllegalArgumentException("Posição fora do tabuleiro: (" + x + ", " + y + ")");
        }

        char file = (char) ('a' + x);
        char rank = (char) Math.abs('8' - y);

        return String.valueOf(file) + rank;
    }

    // !!!!!!!!!1
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Position2D another_position) {
            return this.x == another_position.getX()
                && this.y == another_position.getY();
        }
        return false;
    }

    public int getX () { return x; }

    public int getY () { return y; }

    public void setY (int y) { this.y = y; }

    public void setX (int x) { this.x = x; }

}
