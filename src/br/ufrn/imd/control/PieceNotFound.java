package br.ufrn.imd.control;

public class PieceNotFound extends RuntimeException {
    public PieceNotFound(String message) {
        super(message);
    }
}
