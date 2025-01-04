package br.ufrn.imd.model.Matrices;

//TODO: Lidar com valores nulos nessa clase:

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
/**
 * Classe para coordenadas das peças
 * @author Joao Lucas
 *
 */
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

    /**
     * Método para saber se uma posição está na mesma coluna que outra posição
     * @param another_position
     * @return
     */
    public boolean isInTheSameColumnOf (Position2D another_position) {
        return this.x == another_position.getX();
    }

    /**
     * Método para saber se uma posição está na mesma linha que outra posição
     * @param another_position
     * @return
     */
    public boolean isInTheSameRowOf (Position2D another_position) {
        return this.y == another_position.getY();
    }

    /**
     * Método para saber se uma posição alinhada com outra posição
     * @param another_position
     * @return
     */
    public boolean isAlignedWith (Position2D another_position) {
        return this.isInTheSameColumnOf(another_position) ||
               this.isInTheSameRowOf(another_position);
    }

    /**
     * Método para saber se uma posição está na mesma diagonal direita que outra posição
     * @param another_position
     * @return
     */
    public boolean isInTheSameRightDiagonalOf (Position2D another_position) {
        return (this.x - this.y) == (another_position.getX() - another_position.getY());
    }

    /**
     * Método para saber se uma posição está na mesma diagonal esquerta que outra posição
     * @param another_position
     * @return
     */
    public boolean isInTheSameLeftDiagonalOf (Position2D another_position) {
        return (this.x + this.y) == (another_position.getX() + another_position.getY());
    }


    /**
     * Método para saber se uma posição está nas mesmas coordenadas que outra posição
     * @param another_position
     * @return
     */
    public boolean isInTheSameDiagonalOf (Position2D another_position) {
        return this.isInTheSameRightDiagonalOf (another_position) ||
               this.isInTheSameLeftDiagonalOf  (another_position);
    }

    /**
     * Método para saber se uma posição está no alcance de um cavalo
     * @param another_position
     * @return
     */
    public boolean isInAKnightsAwayOf (Position2D another_position) {
        return Math.abs(this.x - another_position.getX()) * Math.abs(this.y - another_position.getY()) == 2;
    }

    // Inclui a propria posicao
    /**
     * Método para saber de uma posição esta sendo cercada
     * @param another_position
     * @return
     */
    public boolean isSurroundedBy (Position2D another_position) {
        return Math.abs(this.x - another_position.getX()) <= 1
            && Math.abs(this.y - another_position.getY()) <= 1;
    }

    /**
     * Método para saber se uma posição está mais alta
     * @param another_position
     * @return
     */
    public boolean isXAboveOf (Position2D another_position) {
        return this.x > another_position.getX();
    }

    /**
     * Método para saber se uma posição está mais baixa
     * @param another_position
     * @return
     */
    public boolean isXBehindOf (Position2D another_position) {
        return this.x < another_position.getX();
    }

    /**
     * Método para saber se uma posição está mais a direita
     * @param another_position
     * @return
     */
    public boolean isYAboveOf (Position2D another_position) {
        return this.y > another_position.getY();
    }

    /**
     * Método para saber se uma posição está mais a esquerda
     * @param another_position
     * @return
     */
    public boolean isYBehindOf (Position2D another_position) {
        return this.y < another_position.getY();
    }

/**
 * Método para saber se uma posição está na diagonal superior direita
 * @param another_position
 * @return
 */
    public boolean isInRightUpDiagonalOf (Position2D another_position) {
        return this.x > another_position.getX()
            && this.y > another_position.getY();
    }

    /**
     * Método para saber se uma posição está na diagonal superior esquerda
     * @param another_position
     * @return
     */
    public boolean isInLeftUpDiagonalOf (Position2D another_position) {
        return this.x < another_position.getX()
            && this.y > another_position.getY();
    }

    /**
     * Método para saber se uma posição está na diagonal inferior direita
     * @param another_position
     * @return
     */
    public boolean isInRightDownDiagonalOf (Position2D another_position) {
        return this.x > another_position.getX()
            && this.y < another_position.getY();
    }

    /**
     * Método para saber se uma posição está na diagonal inferior esquerda
     * @param another_position
     * @return
     */
    public boolean isInLeftDownDiagonalOf (Position2D another_position) {
        return this.x < another_position.getX()
            && this.y < another_position.getY();
    }

    // TODO: contratos!

    // Assumindo que todos estao na mesma diagonal
    /**
     * Método para achar a ultima coordenada da diagonal superior esquerda
     * @param positions
     * @return
     */
    public static Position2D highestInTheLeftDiagonal (LinkedList<Position2D> positions) {
        return lowestXPosition(positions); // o mais a esquerda
    }

    // Assumindo que todos estao na mesma diagonal
    /**
     * Método para achar a ultima coordenada da diagonal inferior esquerda
     * @param positions
     * @return
     */
    public static Position2D lowestInTheLeftDiagonal (LinkedList<Position2D> positions) {
        return highestXPosition(positions); // o mais a direita
    }

    // Assumindo que todos estao na mesma diagonal
    /**
     * Método para achar a ultima coordenada da diagonal superior direita
     * @param positions
     * @return
     */
    public static Position2D highestInTheRightDiagonal (LinkedList<Position2D> positions) {
        return highestXPosition(positions); // o mais a direita
    }

    // Assumindo que todos estao na mesma diagonal
    /**
     * Método para achar a ultima coordenada da diagonal inferior esquerda
     * @param positions
     * @return
     */
    public static Position2D lowestInTheRightDiagonal (LinkedList<Position2D> positions) {
        return lowestXPosition(positions); // o mais a esquerda
    }

    // Aqui nao precisa usar getters e nem setters

    // Reducao sugerida:

    // TODO: Tratar do throws illegalArguments!!?
    /**
     * Método que retorna a maior posição X
     * @param positions
     * @return
     */
    public static Position2D highestXPosition(LinkedList<Position2D> positions) {
        return positions.stream().max(Comparator.comparingInt(Position2D::getX)).orElse(null);
    }

    /**
     * Método que retorna a menor posição X
     * @param positions
     * @return
     */
    public static Position2D lowestXPosition(LinkedList<Position2D> positions) {
        return positions.stream().min(Comparator.comparingInt(Position2D::getX)).orElse(null);
    }

    /**
     * Método que retorna a maior posição Y
     * @param positions
     * @return
     */
    public static Position2D highestYPosition(LinkedList<Position2D> positions) {
        return positions.stream().max(Comparator.comparingInt(Position2D::getY)).orElse(null);
    }

    /**
     * Método que retorna a menor posição Y
     * @param positions
     * @return
     */
    public static Position2D lowestYPosition(LinkedList<Position2D> positions) {
        return positions.stream().min(Comparator.comparingInt(Position2D::getY)).orElse(null);
    }

    /**
     * Método que verifica se uma peça está uma unidade de Y atrás
     * @param another_position
     * @return
     */
    public boolean isOneYBehind (Position2D another_position) {
        return this.y + 1 == another_position.getY();
    }

    //TODO: Melhorar as exceptions
    /**
     * Método de tratamento de erro da peça estar fora do tabuleiro na hora da criação 
     * @param coordinate
     * @return
     * @throws IllegalArgumentException
     */
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
    /**
     * Método de tratamento de erro da peça estar fora do tabuleiro
     * @return
     * @throws IllegalArgumentException
     */
    public String toChessNotation() throws IllegalArgumentException {

        if (x < 0 || x > 7 || y < 0 || y > 7) {
            throw new IllegalArgumentException("Posição fora do tabuleiro: (" + x + ", " + y + ")");
        }

        char file = (char) ('a' + x);
        char rank = (char) Math.abs('8' - y);

        return String.valueOf(file) + rank;
    }

    // !!!!!!!!!1
    /**
     * Método de verificação se duas peças estão no mesmo lugar
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Position2D another_position) {
            return this.x == another_position.getX()
                && this.y == another_position.getY();
        }
        return false;
    }

    /**
     * Método getter X
     * @return x
     */
    public int getX () { return x; }

    /**
     * Método getter Y
     * @return y
     */
    public int getY () { return y; }

    /**
     * Método setter Y
     * @param y
     */
    public void setY (int y) { this.y = y; }

    /**
     * Método setter X
     * @param x
     */
    public void setX (int x) { this.x = x; }

}
