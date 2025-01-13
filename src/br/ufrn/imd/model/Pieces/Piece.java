    package br.ufrn.imd.model.Pieces;

    import br.ufrn.imd.model.Matrices.Position2D;
    import br.ufrn.imd.model.Rules.Side;
    /**
     * Classe abstrata de uma peça generica 
     * @author Joao Lucas
     *
     */
    public abstract class Piece implements Cloneable {

        protected Position2D current_position = new Position2D(0,0);

        protected Side          side  = null;
        protected int          value  = 0;
        
        /**
         * Método para classificar uma peça como Branca
         * @return true
         */
        public boolean  isWhite() {
            return side.isWhite();
        }

        /**
         * Método para classificar uma peça como Preta
         * @return true
         */
        public boolean  isBlack() {
            return side.isBlack();
        }


        /**
         * Método para verificação de a peça pode se mover
         * @param candidate_position
         * @return true or false
         */
        public abstract boolean movable(Position2D candidate_position);

        /**
         * Método Getter de current_position
         * @return Position2D
         */
        public Position2D getCurrent_position() { return current_position; }

        /**
         * Método Getter de Valeu
         * @return
         */
        public int getValue() { return value; }

        /**
         * Método Getter de Side
         * @return
         */
        public Side getSide() { return side; }

        /**
         * Método Setter de current_position
         * @param current_position
         */
        public void setCurrent_position(Position2D current_position) { this.current_position = current_position; }

        /**
         * Método Setter de Side
         * @param side
         */
        public void setSide(Side side) { this.side = side; }

        /**
         * Método Setter de Valeu
         * @param value
         */
        public void setValue(int value) { this.value = value; }

        /**
         * Método para criar novas peças
         */
        @Override
        public abstract Piece clone();

    }
