    package br.ufrn.imd.model.Pieces;

    import br.ufrn.imd.model.Matrices.Position2D;
    import br.ufrn.imd.model.Rules.Side;

    public abstract class Piece {

        protected Position2D current_position = new Position2D(0,0);

        protected Side          side  = null;
        protected int          value  = 0;

        public boolean  isWhite() {
            return side.isWhite();
        }

        public boolean  isBlack() {
            return side.isBlack();
        }


        // Algo alem das pecas vai se mover?
        public abstract boolean movable(Position2D candidate_position);

        // GETTER'S

        public Position2D getCurrent_position() { return current_position; }

        public int getValue() { return value; }

        public Side getSide() { return side; }

        // SETTER'S

        public void setCurrent_position(Position2D current_position) { this.current_position = current_position; }

        public void setSide(Side side) { this.side = side; }

        public void setValue(int value) { this.value = value; }

    }
