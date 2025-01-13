package br.ufrn.imd.model.Pieces;
/**
 * Interface para registrar se as peças fizeram o primeiro movimento para os movimentos especiais
 * @author Joao Lucas
 *
 */
public interface BeginnersLucky {
	/**
	 * Método para garantir que é o primeiro movimento da peça
	 * @return true
	 */
    boolean isTheFirstMove  ();
    
    /**
     * Método para registrar o primeiro movimento
     */
    void registerMoveAsMade ();
}
