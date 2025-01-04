package br.ufrn.imd.control;
/**
 * Classe de tratamento de erros
 * @author Joao Lucas
 *
 */
public class PieceNotFound extends RuntimeException {
	/**
	 * Método para o tratamento de erro de uma peça que não foi achada
	 * @param message
	 */
    public PieceNotFound(String message) {
        super(message);
    }
}
