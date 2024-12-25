package br.ufrn.imd.model.Rules;

// Another name could be "team"
public enum Side {
    WhiteSide, BlackSide;

    // Booleanismo
    public boolean isWhite() {
        return this == WhiteSide;
    }

    public boolean isBlack() {
        return this == BlackSide;
    }

    // For legibility
    public boolean isInTheSameSideOf(Side otherSide) {
        return this == otherSide;
    }


}