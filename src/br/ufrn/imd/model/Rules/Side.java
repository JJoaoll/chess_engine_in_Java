package br.ufrn.imd.model.Rules;

// Another name could be "team"
/**
 * Classe para verificação dos times
 * @author Joao Lucas
 *
 */
public enum Side {
    WhiteSide, BlackSide;

    @Override
    public String toString() {
        if (this == WhiteSide)
            return "White";

        return "Black";
    }

    // Booleanismo
    public boolean isWhite() {
        return this == WhiteSide;
    }
    
    public boolean isBlack() {
        return this == BlackSide;
    }

    // For legibility
    /**
     * Método para verificação se é do mesmop time
     * @param otherSide
     * @return otherSide
     */
    public boolean isInTheSameSideOf(Side otherSide) {
        return this == otherSide;
    }

    /**
     * Método para verificação se é do time rival
     * @return BlackSide
     */
    public Side OponentSide() {
        if (this == WhiteSide)
            return BlackSide;

        return WhiteSide;
    }


}