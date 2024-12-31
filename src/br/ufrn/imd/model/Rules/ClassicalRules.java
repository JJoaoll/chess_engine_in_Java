package br.ufrn.imd.model.Rules;

import br.ufrn.imd.control.PieceNotFound;
import br.ufrn.imd.model.Board.Board;
import br.ufrn.imd.model.Board.Tile;
import br.ufrn.imd.model.Game;
import br.ufrn.imd.model.Matrices.Grid;
import br.ufrn.imd.model.Matrices.Position2D;
import br.ufrn.imd.model.Pieces.*;
import br.ufrn.imd.view.GameManager;
import com.sun.jdi.connect.spi.TransportService;

import javax.swing.text.Position;
import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static br.ufrn.imd.model.Matrices.Position2D.lowestXPosition;
// TODO: REMOVER ACOPLAMENTO COM O GAME ao maximo!!!


// TODO: DO!
public class ClassicalRules implements RuleSet {

    LinkedList<Position2D> board_positions = new LinkedList<>();

    {
        for (char col = 'a'; col <= 'h'; col++) {
            for (int row = 1; row <= 8; row++) {
                String notation = String.valueOf(col) + row;
                board_positions.add(Position2D.fromChessNotation(notation));
            }
        }
    }

    public boolean theKingIsInCheck (Board board, Side side_of_the_king) {
        King king = board.getKingFrom (side_of_the_king);
        if (king != null) {
            Side oponent_side = side_of_the_king.OponentSide();

            LinkedList<Piece> pieces_on_board = board.getPieces();
            for (Piece piece : pieces_on_board) {
                if (isAValidPieceMove (new Move (board, piece.getCurrent_position(), king.getCurrent_position()), oponent_side)
                && (piece.getSide() != king.getSide()))
                    return true;
            }
        }
        /*if (king == null)
            System.out.println("The king was not in the board, how?!!");*/

        return false;
    }


    // CONTEM MUITOS SIDE-EFFECTS!!!!!!!
    @Override
    public void makeItSpecial (Game game, Move move) throws IllegalArgumentException {
        Board board = game.getBoardRf(); // BUSCANDO SIDE EFFECTS!!!! //TODO: tem refatoracão de base aqui!!!!

        int x0 = move.getInitialPosition().getX();
        int y0 = move.getInitialPosition().getY();

        Optional<Piece> opt_piece = board.getPiece (x0, y0);
        if (opt_piece.isEmpty ())
            throw new IllegalArgumentException ("Piece not found");

        // Nao vamos reverificar nada aqui!
        try {
            Piece piece = opt_piece.get();

            // Como nenhum dos casos conflitam:
            if (piece instanceof Pawn pawn) {
                if(isAPromotingPawn (pawn, move)) {

                }

                else if (isAnEnPassant (game, pawn, move)) {

                }
            }

            else if (piece instanceof King king) {
                // Eh mais comum que o rei roque curto :)
                if (isCastlingShort (king, move, Game.getTurn())) { // TODO: FIXAR ESSE METODO ESTATICO PRA NAO SER!

                    String rook_side = king.isWhite() ? "h1" : "h8";
                    Position2D rook_position = Position2D.fromChessNotation(rook_side);
                    Position2D king_position = king.getCurrent_position();

                    /*//
                    Rook rook = (Rook) board.getPiece(rook_position.getX(), rook_position.getY()).get();*/

                    // deletando:
                    board.replacePiece (king_position.getX(), king_position.getY(), Optional.empty());
                    board.replacePiece (rook_position.getX(), rook_position.getY(), Optional.empty());

                    String rook_spot = king.isWhite() ? "f1" : "f8";
                    String king_spot = king.isWhite() ? "g1" : "g8";

                    Position2D new_rook_position = Position2D.fromChessNotation(rook_spot);
                    Position2D new_king_position = Position2D.fromChessNotation(king_spot);

                    // Recolocando:
                    board.replacePiece (new_king_position.getX(), new_king_position.getY(), Optional.of(king));
                    board.replacePiece (new_rook_position.getX(), new_rook_position.getY(), Optional.of(new Rook(new_rook_position, king.getSide())));

                }

                else if (isCastlingLong   (king, move, Game.getTurn())) {
                    // Eh mais comum que o rei roque curto :)
                    if (isCastlingLong (king, move, Game.getTurn())) { // TODO: FIXAR ESSE METODO ESTATICO PRA NAO SER!

                        String rook_side = king.isWhite() ? "a1" : "a8";
                        Position2D rook_position = Position2D.fromChessNotation(rook_side);
                        Position2D king_position = king.getCurrent_position();

                    /*//
                    Rook rook = (Rook) board.getPiece(rook_position.getX(), rook_position.getY()).get();*/

                        // deletando:
                        board.replacePiece(king_position.getX(), king_position.getY(), Optional.empty());
                        board.replacePiece(rook_position.getX(), rook_position.getY(), Optional.empty());

                        String rook_spot = king.isWhite() ? "d1" : "d8";
                        String king_spot = king.isWhite() ? "c1" : "c8";

                        Position2D new_rook_position = Position2D.fromChessNotation(rook_spot);
                        Position2D new_king_position = Position2D.fromChessNotation(king_spot);

                        // Recolocando:
                        board.replacePiece(new_king_position.getX(), new_king_position.getY(), Optional.of(king));
                        board.replacePiece(new_rook_position.getX(), new_rook_position.getY(), Optional.of(new Rook(new_rook_position, king.getSide())));
                    }

                }
            }

            else {
                throw new IllegalArgumentException ("Not a special piece");
            }

            game.swapTurn();

        }

        catch (Exception e) {
            e.getMessage();
            throw e;
        }

    }

    @Override
    public boolean isSpecialMove (Game game, Move move) {
        Board board = move.getBoardBeforeMove();

        int x0 = move.getInitialPosition().getX();
        int y0 = move.getInitialPosition().getY();

        Optional<Piece> opt_piece = board.getPiece (x0, y0);

        return opt_piece.map(piece -> {

            if (piece instanceof Pawn p)        {
                return isAPromotingPawn (p, move)
                    || isAnEnPassant    (game, p, move);
            }

            else if (piece instanceof King k) {
                return isCastlingShort  (k, move, Game.getTurn()) // TODO: FIXAR ESSA ESTATICIDADE!!!
                    || isCastlingLong   (k, move, Game.getTurn());
            }

            else {
                return false;
            }

        }).orElse(false);
    }

    private boolean isCastlingShort (King king, Move move, Side turn) {
        Board board = move.getBoardBeforeMove();

        String expected_rook_coordinate = king.isWhite() ? "h1" : "h8";
        Position2D rook_position = Position2D.fromChessNotation(expected_rook_coordinate);
        Optional<Piece> opt_piece = board.getPiece(rook_position.getX(), rook_position.getY());

        if(opt_piece.isPresent()) {

            try {
                Rook rook          = (Rook) opt_piece.get();
                boolean twin_souls =  king.isTheFirstMove()
                                  &&  rook.isTheFirstMove();
                System.out.println("twin souls: " + twin_souls);

                // NOMES RUINS ATRAPALHARAM! TODO: melhorar nomes se sobrar tempo.
                String empty_tile1 = king.isWhite() ? "g1" : "g8";
                String empty_tile2 = king.isWhite() ? "f1" : "f8";

                Position2D empty1  = Position2D.fromChessNotation (empty_tile1);
                Position2D empty2  = Position2D.fromChessNotation (empty_tile2);

                boolean free_space = board.getPiece(empty1.getX(), empty1.getY()).isEmpty()
                                  && board.getPiece(empty2.getX(), empty2.getY()).isEmpty();

                System.out.println("free space: " + free_space);

                boolean is_in_check  = theKingIsInCheck(board, king.getSide());
                System.out.println("is in check: " + is_in_check);

                // Break for efficiency
                if (is_in_check || !free_space || !twin_souls)
                    return false;

                Board board_after_first_step  = new Board(board);
                Board board_after_second_step = new Board(board);

                int initial_x = king.getCurrent_position().getX();
                int initial_y = king.getCurrent_position().getY();

                board_after_first_step.replacePiece(initial_x, initial_y,Optional.empty());
                board_after_first_step.replacePiece(empty1.getX(), empty1.getY(),
                        Optional.of(new King (king.getCurrent_position(), king.getSide())));

                board_after_second_step.replacePiece(initial_x, initial_y,Optional.empty());
                board_after_second_step.replacePiece(empty2.getX(), empty2.getY(),
                        Optional.of(new King (king.getCurrent_position(), king.getSide())));

                return move.getFinalPosition().equals(empty1)
                    && !theKingIsInCheck(board_after_first_step  , king.getSide())
                    && !theKingIsInCheck(board_after_second_step , king.getSide())
                    && isTheCorrectTurn(Optional.of(king), turn);
            }

            catch (ClassCastException e) {
                System.out.println(e.getMessage());
                return false;
            }
        }

        return false;
    }

    private boolean isCastlingLong(King king, Move move, Side turn) {
        Board board = move.getBoardBeforeMove();

        String expected_rook_coordinate = king.isWhite() ? "a1" : "a8";
        Position2D rook_position = Position2D.fromChessNotation(expected_rook_coordinate);
        Optional<Piece> opt_piece = board.getPiece(rook_position.getX(), rook_position.getY());

        if(opt_piece.isPresent()) {

            try {
                Rook rook          = (Rook) opt_piece.get();
                boolean twin_souls =  king.isTheFirstMove()
                        &&  rook.isTheFirstMove();
                System.out.println("twin souls: " + twin_souls);

                // NOMES RUINS ATRAPALHARAM! TODO: melhorar nomes se sobrar tempo.
                String empty_tile1 = king.isWhite() ? "c1" : "c8";
                String empty_tile2 = king.isWhite() ? "d1" : "d8";

                Position2D empty1  = Position2D.fromChessNotation (empty_tile1);
                Position2D empty2  = Position2D.fromChessNotation (empty_tile2);

                boolean free_space = board.getPiece(empty1.getX(), empty1.getY()).isEmpty()
                        && board.getPiece(empty2.getX(), empty2.getY()).isEmpty();

                System.out.println("free space: " + free_space);

                boolean is_in_check  = theKingIsInCheck(board, king.getSide());
                System.out.println("is in check: " + is_in_check);

                // Break for efficiency
                if (is_in_check || !free_space || !twin_souls)
                    return false;

                Board board_after_first_step  = new Board(board);
                Board board_after_second_step = new Board(board);

                int initial_x = king.getCurrent_position().getX();
                int initial_y = king.getCurrent_position().getY();

                board_after_first_step.replacePiece(initial_x, initial_y,Optional.empty());
                board_after_first_step.replacePiece(empty1.getX(), empty1.getY(),
                        Optional.of(new King (king.getCurrent_position(), king.getSide())));

                board_after_second_step.replacePiece(initial_x, initial_y,Optional.empty());
                board_after_second_step.replacePiece(empty2.getX(), empty2.getY(),
                        Optional.of(new King (king.getCurrent_position(), king.getSide())));

                return move.getFinalPosition().equals(empty1)
                        && !theKingIsInCheck(board_after_first_step  , king.getSide())
                        && !theKingIsInCheck(board_after_second_step , king.getSide())
                        && isTheCorrectTurn(Optional.of(king), turn);
            }

            catch (ClassCastException e) {
                System.out.println(e.getMessage());
                return false;
            }
        }

        return false;
    }

    private boolean isAnEnPassant(Game game, Pawn p, Move move) {
        return false;
    }

    private boolean isAPromotingPawn(Pawn p, Move move) {
       return false;
    }

    private boolean isAValidPieceMove (Move move, Side turn) {
        int x = move.getInitialPosition().getX();
        int y = move.getInitialPosition().getY();

        Optional<Piece> opt_piece = move.getBoardBeforeMove().getPiece(x, y);

        return opt_piece.map(piece -> {

            if (piece instanceof Pawn p)        {
                return isAValidPawnMove (p, move);
            }

            else if (piece instanceof Rook r)   {
                return isAValidRookMove (r, move);

            }

            else if (piece instanceof Knight n) {
                return isAValidKnightMove (n, move);
            }

            else if (piece instanceof Bishop b) {
                return isAValidBishopMove (b, move);
            }

            else if (piece instanceof Queen q)  {
                return isAValidQueenMove (q, move);
            }

            else if (piece instanceof King k)   {
                return isAValidKingMove (k, move);
            }

            else {
                throw new PieceNotFound("Unexpected piece: " + piece.getClass().getSimpleName());
            }
        }).orElse(false);
    }

    private boolean isTheCorrectTurn (Optional<Piece> opt_piece, Side turn) {
        return opt_piece.map (piece -> {return piece.getSide() == turn;}).orElse(false);
    }

    @Override
    public boolean isValidMove(Move move, Side turn) {

        //System.out.println("initial Position: " + move.getInitialPosition().getX() + " " + move.getInitialPosition().getY());
        //System.out.println("Final Position: "   + move.getFinalPosition().getX() + " " + move.getFinalPosition().getY());

        int x = move.getInitialPosition().getX();
        int y = move.getInitialPosition().getY();

        Optional<Piece> opt_piece = move.getBoardBeforeMove().getPiece(x, y);
        boolean correct_turn = isTheCorrectTurn (opt_piece, turn);

        boolean valid_piece_movement = isAValidPieceMove (move, turn);

        if (!valid_piece_movement || !correct_turn)
            return false;

        Piece piece = opt_piece.get().clone();

        Board board_after_move = new Board (move.getBoardBeforeMove());

        int initial_x = piece.getCurrent_position().getX();
        int initial_y = piece.getCurrent_position().getY();

        int final_x   = move.getFinalPosition().getX();
        int final_y   = move.getFinalPosition().getY();

        board_after_move.replacePiece (initial_x, initial_y, Optional.empty());
        board_after_move.replacePiece (  final_x,   final_y,          Optional.of(piece));

        //System.out.println ("the " + turn.toString() + " king is in check: (" + theKingIsInCheck(board_after_move, turn) + ")");
        return !theKingIsInCheck (board_after_move, turn);
    }

    private boolean isAValidKingMove(King king, Move move) {
        Board board = move.getBoardBeforeMove();

        if (!king.getCurrent_position().equals(move.getInitialPosition()))
            throw new IllegalArgumentException("As posicoes inicial do movimento e a da torre nao batem");

        if (move.getFinalPosition().equals(move.getInitialPosition()))
            return false;

        LinkedList<Position2D> valid_positions    = new LinkedList<>(List.copyOf(board_positions))
                .stream().filter(king::movable)
                .collect(Collectors
                .toCollection(LinkedList::new));

        boolean same_team =
                board
                .getPiece(move.getFinalPosition().getX(), move.getFinalPosition().getY())
                .map(piece -> piece.getSide().equals(king.getSide()))
                .orElse(false);

        return valid_positions.contains(move.getFinalPosition())
                && !same_team;
    }

    private boolean isAValidQueenMove(Queen queen, Move move) {
        if (!queen.getCurrent_position().equals(move.getInitialPosition()))
            throw new IllegalArgumentException ("As posicoes inicial do movimento e a da torre nao batem");

        Position2D initial_position = queen.getCurrent_position();
        Position2D final_position = move.getFinalPosition();

        int col = initial_position.getX();
        int row = initial_position.getY();

        Bishop b = new Bishop (initial_position, queen.getSide());
        Rook   r = new Rook   (initial_position, queen.getSide());

        Board b_board = new Board (move.getBoardBeforeMove());
        Board r_board = new Board (move.getBoardBeforeMove());

        Tile b_tile = new Tile (initial_position.toChessNotation(), b);
        Tile r_tile = new Tile (initial_position.toChessNotation(), r);

        b_board.getTiles().setValue(col, row, b_tile);
        Move b_move = new Move (b_board, initial_position, final_position);

        r_board.getTiles().setValue(col, row, r_tile);
        Move r_move = new Move (r_board, initial_position, final_position);

        return isAValidRookMove   (r, r_move) ||
               isAValidBishopMove (b, b_move);


    }

    private boolean isAValidBishopMove(Bishop bishop, Move move) {
        Board board = move.getBoardBeforeMove();

        if (!bishop.getCurrent_position().equals(move.getInitialPosition())) {
            throw new IllegalArgumentException("As posicoes inicial do movimento e a do bispo nao batem");
        }

        LinkedList<Position2D> valid_positions =
                new LinkedList<>(List.copyOf(board_positions))
                .stream().filter(bishop::movable)
                .collect(Collectors
                .toCollection(LinkedList::new));

        LinkedList<Piece> pieces_in_the_way = new LinkedList<>();
        for (Position2D position : valid_positions) {
            board.getPiece(position.getX(), position.getY())
                    .ifPresent(piece -> pieces_in_the_way.add(piece));
        }

        Piece blocker_upR    = null;
        Piece blocker_upL    = null;
        Piece blocker_downR  = null;
        Piece blocker_downL  = null;

        Position2D bishop_position = bishop.getCurrent_position();

        // Setta os blocker's
        for (Piece piece : pieces_in_the_way) {
            Position2D piece_position = piece.getCurrent_position();

            // upR
            if (piece_position.isInRightUpDiagonalOf(bishop_position)
                    && (blocker_upR == null || blocker_upR.getCurrent_position().isInRightUpDiagonalOf(piece_position)))
            {
                blocker_upR = piece;
            }

            // upL
            else if (piece_position.isInLeftUpDiagonalOf(bishop.getCurrent_position())
                    && (blocker_upL == null || blocker_upL.getCurrent_position().isInLeftUpDiagonalOf(piece_position))) {
                blocker_upL = piece;
            }

            // downR
            else if (piece_position.isInRightDownDiagonalOf(bishop.getCurrent_position())
                    && (blocker_downR == null || blocker_downR.getCurrent_position().isInRightDownDiagonalOf(piece_position))) {
                blocker_downR = piece;
            }

            // downL
            else if (piece_position.isInLeftDownDiagonalOf(bishop.getCurrent_position())
                    && (blocker_downL == null || blocker_downL.getCurrent_position().isInLeftDownDiagonalOf(piece_position))) {
                blocker_downL = piece;
            }
        }

        Position2D final_position = move.getFinalPosition();

        // EFICIENCIA:

        // upR
        if (final_position.isInRightUpDiagonalOf(bishop_position)) {
            return valid_positions.contains(move.getFinalPosition())
                    && (blocker_upR == null || blocker_upR.getCurrent_position().isInRightUpDiagonalOf(final_position)
                    || (!blocker_upR.getSide().equals(bishop.getSide())
                    && blocker_upR.getCurrent_position().equals(final_position)));
        }

        // upL
        else if (final_position.isInLeftUpDiagonalOf(bishop_position)) {
            return valid_positions.contains(move.getFinalPosition())
                    && (blocker_upL == null || blocker_upL.getCurrent_position().isInLeftUpDiagonalOf(final_position)
                    || (!blocker_upL.getSide().equals(bishop.getSide())
                    && blocker_upL.getCurrent_position().equals(final_position)));
        }

        // downR
        else if (final_position.isInRightDownDiagonalOf(bishop_position)) {
            return valid_positions.contains(move.getFinalPosition())
                    && (blocker_downR == null || blocker_downR.getCurrent_position().isInRightDownDiagonalOf(final_position)
                    || (!blocker_downR.getSide().equals(bishop.getSide())
                    && blocker_downR.getCurrent_position().equals(final_position)));
        }

        // downL
        else if (final_position.isInLeftDownDiagonalOf(bishop_position)) {
            return valid_positions.contains(move.getFinalPosition())
                    && (blocker_downL == null || blocker_downL.getCurrent_position().isInLeftDownDiagonalOf(final_position)
                    || (!blocker_downL.getSide().equals(bishop.getSide())
                    && blocker_downL.getCurrent_position().equals(final_position)));
        }

        return false;
    }

    private boolean isAValidKnightMove(Knight knight, Move move) {
        Board board = move.getBoardBeforeMove();

        if (!knight.getCurrent_position().equals(move.getInitialPosition()))
            throw new IllegalArgumentException("As posicoes inicial do movimento e a da torre nao batem");


        boolean same_team = board
            .getPiece(move.getFinalPosition().getX(), move.getFinalPosition().getY())
            .map(piece -> piece.getSide().equals(knight.getSide()))
            .orElse(false);


        LinkedList<Position2D> valid_positions    = new LinkedList<>(List.copyOf(board_positions))
                .stream().filter(knight::movable)
                .collect(Collectors
                .toCollection(LinkedList::new));

        if (!valid_positions.contains(move.getFinalPosition()))
            return false;

        return !same_team;
    }

    private boolean isAValidPawnMove(Pawn pawn, Move move) {

        if (!pawn.getCurrent_position().equals(move.getInitialPosition()))
            throw new IllegalArgumentException("As posicoes inicial do movimento e a da torre nao batem");

        LinkedList<Position2D> valid_positions   = new LinkedList<>(List.copyOf(board_positions))
                .stream().filter(pawn::movable)
                .collect(Collectors
                .toCollection(LinkedList::new));

        Position2D final_position = move.getFinalPosition();
        int final_x = final_position.getX();
        int final_y = final_position.getY();

        Board board = move.getBoardBeforeMove();

        boolean one_step_available = board.getPiece(final_x, final_y).isEmpty();

        if (one_step_available && valid_positions.contains(final_position))
            return true;

        int side_direction = pawn.isWhite() ? 1 : -1;
        int adjusted_y     = final_y + side_direction;
        Position2D adjusted_position = new Position2D(final_x, adjusted_y);

        if (one_step_available
            && valid_positions.contains(adjusted_position)
            && board.getPiece(final_x, adjusted_y).isEmpty()
            && pawn.isTheFirstMove()) {
            return true;
        }

        // TODO: Captura
        int initial_x     = move.getInitialPosition().getX();
        adjusted_y        = pawn.getCurrent_position().getY() - side_direction;

        boolean capture_left  = final_position.equals(new Position2D (initial_x + 1, adjusted_y))
                && board.getPiece(initial_x + 1, adjusted_y).map(piece -> { return piece.isWhite() != pawn.isWhite();}).orElse(false);
        boolean capture_right = final_position.equals(new Position2D (initial_x - 1, adjusted_y))
                && board.getPiece(initial_x - 1, adjusted_y).map(piece -> { return piece.isWhite() != pawn.isWhite();}).orElse(false);

        return capture_left
            || capture_right;
    }

    // Todas as direcoes/orientacoes sao pra matriz e nao pra posicao!
    private boolean isAValidRookMove (Rook rook, Move move) {
        Board board = move.getBoardBeforeMove();

        if (!rook.getCurrent_position().equals(move.getInitialPosition())) {
           throw new IllegalArgumentException("As posicoes inicial do movimento e a da torre nao batem!!");
        }


        LinkedList<Position2D> valid_positions =
                new LinkedList<>(List.copyOf(board_positions))
                .stream()
                .filter(rook::movable)
                .collect(Collectors
                .toCollection(LinkedList::new));

        LinkedList<Piece> pieces_in_the_way = new LinkedList<>();
        for (Position2D position : valid_positions) {
            board.getPiece(position.getX(), position.getY())
                    .ifPresent(piece -> pieces_in_the_way.add(piece));
        }

        Piece blocker_up    = null;
        Piece blocker_right = null;
        Piece blocker_left  = null;
        Piece blocker_down  = null;

        Position2D rook_position = rook.getCurrent_position();

        // Setta os blocker's
        for (Piece piece : pieces_in_the_way) {
            Position2D piece_position = piece.getCurrent_position();
            if (piece_position.isYAboveOf(rook_position)
                    && (blocker_up == null || blocker_up.getCurrent_position().isYAboveOf(piece_position)))
            {
                blocker_up = piece;
            }

            else if (piece_position.isXAboveOf(rook.getCurrent_position())
                    && (blocker_right == null || blocker_right.getCurrent_position().isXAboveOf(piece_position))) {
                blocker_right = piece;
            }

            else if (piece_position.isXBehindOf(rook.getCurrent_position())
                    && (blocker_left == null || blocker_left.getCurrent_position().isXBehindOf(piece_position))) {
                blocker_left = piece;
            }

            else if (piece_position.isYBehindOf(rook.getCurrent_position())
            && (blocker_down == null || blocker_down.getCurrent_position().isYBehindOf(piece_position))) {
                blocker_down = piece;
            }
        }

        Position2D final_position = move.getFinalPosition();

        // EFICIENCIA:

        // up
        if (final_position.isYAboveOf(rook_position)) {
            return valid_positions.contains(move.getFinalPosition())
                   && (blocker_up == null || blocker_up.getCurrent_position().isYAboveOf(final_position)
                   || (!blocker_up.getSide().equals(rook.getSide())
                    && blocker_up.getCurrent_position().equals(final_position)));
        }

        // right
        else if (final_position.isXAboveOf(rook_position)) {
            return valid_positions.contains(move.getFinalPosition())
                    && (blocker_right == null || blocker_right.getCurrent_position().isXAboveOf(final_position)
                    || (!blocker_right.getSide().equals(rook.getSide())
                    && blocker_right.getCurrent_position().equals(final_position)));
        }

        // left
        else if (final_position.isXBehindOf(rook_position)) {
            return valid_positions.contains(move.getFinalPosition())
                    && (blocker_left == null || blocker_left.getCurrent_position().isXBehindOf(final_position)
                    || (!blocker_left.getSide().equals(rook.getSide())
                    && blocker_left.getCurrent_position().equals(final_position)));
        }

        // down
        else if (final_position.isYBehindOf(rook_position)) {
            return valid_positions.contains(move.getFinalPosition())
                    && (blocker_down == null || blocker_down.getCurrent_position().isYBehindOf(final_position)
                    || (!blocker_down.getSide().equals(rook.getSide())
                    && blocker_down.getCurrent_position().equals(final_position)));
        }

        return false;
    }

   /* private boolean isAValidRookMove (Rook rook, Move move) {

        if (rook.getCurrent_position() != move.getInitialPosition())
            throw new IllegalArgumentException("Bogou");

        // Correcao automatica
        LinkedList<Position2D> valid_positions    = new LinkedList<>(List.copyOf(board_positions))
                .stream().filter(rook::movable)
                .collect(Collectors
                .toCollection(LinkedList::new));

        Position2D initial_position = rook.getCurrent_position();
        Position2D final_position   = move.getFinalPosition();

        BiPredicate<Position2D, Position2D> predicate;
        Function<LinkedList<Position2D>, Position2D> extremeFunction = null;

        //System.out.println(final_position == initial_position);

        // RIGHT
        if (final_position.isXAboveOf(move.getInitialPosition())) {
            predicate      = Position2D::isXAboveOf;

            extremeFunction = Position2D::highestXPosition;
        }

        // LEFT
        else if (final_position.isXBehindOf(move.getInitialPosition())) {
            predicate = Position2D::isXBehindOf;

            extremeFunction = Position2D::lowestXPosition;
        }

        // UP
        else if (final_position.isYAboveOf(move.getInitialPosition())) {
            predicate = Position2D::isYAboveOf;

            extremeFunction = Position2D::highestYPosition;
        }

        // DOWN
        else if (final_position.isYBehindOf(move.getInitialPosition())) {
            predicate = Position2D::isYBehindOf;

            extremeFunction = Position2D::lowestYPosition;
        }

        else {
            predicate = null;
            //System.out.println(String.valueOf(final_position == initial_position));
            // Nao deve dar throw aqui!
            //throw new IllegalArgumentException("Posicao invalida");
        }

        if (predicate == null)
            return false;

        // TODO: um nome melhor seria "directioned positions"
        LinkedList<Position2D> directioned_moves
                = new LinkedList<>(valid_positions.stream()
                .filter(position -> predicate.test(initial_position, position))
                .toList());

        // umas versoes iterativas de codigo
        LinkedList<Piece> blocking_pieces = new LinkedList<>();
        for (Position2D position : directioned_moves) {
            GameManager.getPiece(position.getX(), position.getY())
                       .ifPresent(blocking_pieces::add);
        }

        if (blocking_pieces.isEmpty())
            return valid_positions.contains(final_position);

        // ---- Parte final: (Hallelluia)

        LinkedList<Position2D> blocking_positions = blocking_pieces.stream()
                .map(Piece::getCurrent_position)
                .collect(Collectors.toCollection(LinkedList::new));

        Position2D block_position = extremeFunction.apply(blocking_positions);
        System.out.printf(block_position.getX() + " " + block_position.getY() + ", ");

        // TODO: FIXE ESSE TRUST!!!!
        Piece block_piece = Game.getBoard().getPiece(block_position.getX(), block_position.getY()).get();





        // Solucao iterativa pra poupar a minha sanidade mental :P
        for (Position2D position : directioned_moves) {
            if (predicate.test(block_position, position)) {
                //System.out.println(position.getX() + " " + position.getY());
                return true;
            }
        }

        // TODO: possiveis problemas com eficiencia
        if (block_piece.isWhite() != rook.isWhite())
            return final_position == block_position;

        return false;
    }
*/

    // TODO: Deleat it
    public LinkedList<Move> getAllValidMoves(Board board, Piece piece) {
        return null;
    }

    @Override
    public Board initializeBoard() {
        Board      new_board = new Board();
        Grid<Tile> new_tiles = new Grid<>(new_board.getWidth(), new_board.getHeight());

        // TODO: Maneira mais inteligente de fazer isso?
        new_tiles.setValue(0, 7, new Tile("a1", new Rook   (new Position2D(0, 7), Side.WhiteSide)));
        new_tiles.setValue(0, 6, new Tile("a2", new Pawn   (new Position2D(0, 6), Side.WhiteSide)));
        new_tiles.setValue(0, 5, new Tile("a3"));
        new_tiles.setValue(0, 4, new Tile("a4"));
        new_tiles.setValue(0, 3, new Tile("a5"));
        new_tiles.setValue(0, 2, new Tile("a6"));
        new_tiles.setValue(0, 1, new Tile("a7", new Pawn   (new Position2D(0, 1), Side.BlackSide)));
        new_tiles.setValue(0, 0, new Tile("a8", new Rook   (new Position2D(0, 0), Side.BlackSide)));

        new_tiles.setValue(1, 7, new Tile("b1", new Knight (new Position2D(1, 7), Side.WhiteSide) ));
        new_tiles.setValue(1, 6, new Tile("b2", new Pawn   (new Position2D(1, 6), Side.WhiteSide)));
        new_tiles.setValue(1, 5, new Tile("b3"));
        new_tiles.setValue(1, 4, new Tile("b4"));
        new_tiles.setValue(1, 3, new Tile("b5"));
        new_tiles.setValue(1, 2, new Tile("b6"));
        new_tiles.setValue(1, 1, new Tile("b7", new Pawn   (new Position2D(1, 1), Side.BlackSide)));
        new_tiles.setValue(1, 0, new Tile("b8", new Knight (new Position2D(1, 0), Side.BlackSide)));

        new_tiles.setValue(2, 7, new Tile("c1", new Bishop (new Position2D(2, 7), Side.WhiteSide) ));
        new_tiles.setValue(2, 6, new Tile("c2", new Pawn   (new Position2D(2, 6), Side.WhiteSide)));
        new_tiles.setValue(2, 5, new Tile("c3"));
        new_tiles.setValue(2, 4, new Tile("c4"));
        new_tiles.setValue(2, 3, new Tile("c5"));
        new_tiles.setValue(2, 2, new Tile("c6"));
        new_tiles.setValue(2, 1, new Tile("c7", new Pawn   (new Position2D(2, 1), Side.BlackSide)));
        new_tiles.setValue(2, 0, new Tile("c8", new Bishop (new Position2D(2, 0), Side.BlackSide)));

        new_tiles.setValue(3, 7, new Tile("d1", new Queen  (new Position2D(3, 7), Side.WhiteSide)));
        new_tiles.setValue(3, 6, new Tile("d2", new Pawn   (new Position2D(3, 6), Side.WhiteSide)));
        new_tiles.setValue(3, 5, new Tile("d3"));
        new_tiles.setValue(3, 4, new Tile("d4"));
        new_tiles.setValue(3, 3, new Tile("d5"));
        new_tiles.setValue(3, 2, new Tile("d6"));
        new_tiles.setValue(3, 1, new Tile("d7", new Pawn  (new Position2D(3, 1), Side.BlackSide)));
        new_tiles.setValue(3, 0, new Tile("d8", new Queen (new Position2D(3, 0), Side.BlackSide)));

        new_tiles.setValue(4, 7, new Tile("e1", new King  (new Position2D(4, 7), Side.WhiteSide)));
        new_tiles.setValue(4, 6, new Tile("e2", new Pawn  (new Position2D(4, 6), Side.WhiteSide)));
        new_tiles.setValue(4, 5, new Tile("e3"));
        new_tiles.setValue(4, 4, new Tile("e4"));
        new_tiles.setValue(4, 3, new Tile("e5"));
        new_tiles.setValue(4, 2, new Tile("e6"));
        new_tiles.setValue(4, 1, new Tile("e7", new Pawn  (new Position2D(4, 1), Side.BlackSide)));
        new_tiles.setValue(4, 0, new Tile("e8", new King  (new Position2D(4, 0), Side.BlackSide)));

        new_tiles.setValue(5, 7, new Tile("f1", new Bishop (new Position2D(5, 7), Side.WhiteSide) ));
        new_tiles.setValue(5, 6, new Tile("f2", new Pawn   (new Position2D(5, 6), Side.WhiteSide)));
        new_tiles.setValue(5, 5, new Tile("f3"));
        new_tiles.setValue(5, 4, new Tile("f4"));
        new_tiles.setValue(5, 3, new Tile("f5"));
        new_tiles.setValue(5, 2, new Tile("f6"));
        new_tiles.setValue(5, 1, new Tile("f7", new Pawn   (new Position2D(5, 1), Side.BlackSide)));
        new_tiles.setValue(5, 0, new Tile("f8", new Bishop (new Position2D(5, 0), Side.BlackSide)));

        new_tiles.setValue(6, 7, new Tile("g1", new Knight (new Position2D(6, 7), Side.WhiteSide)));
        new_tiles.setValue(6, 6, new Tile("g2", new Pawn   (new Position2D(6, 6), Side.WhiteSide)));
        new_tiles.setValue(6, 5, new Tile("g3"));
        new_tiles.setValue(6, 4, new Tile("g4"));
        new_tiles.setValue(6, 3, new Tile("g5"));
        new_tiles.setValue(6, 2, new Tile("g6"));
        new_tiles.setValue(6, 1, new Tile("g7", new Pawn   (new Position2D(6, 1), Side.BlackSide)));
        new_tiles.setValue(6, 0, new Tile("g8", new Knight (new Position2D(6, 0), Side.BlackSide)));

        new_tiles.setValue(7, 7, new Tile("h1", new Rook   (new Position2D(7, 7), Side.WhiteSide)));
        new_tiles.setValue(7, 6, new Tile("h2", new Pawn   (new Position2D(7, 6), Side.WhiteSide)));
        new_tiles.setValue(7, 5, new Tile("h3"));
        new_tiles.setValue(7, 4, new Tile("h4"));
        new_tiles.setValue(7, 3, new Tile("h5"));
        new_tiles.setValue(7, 2, new Tile("h6"));
        new_tiles.setValue(7, 1, new Tile("h7", new Pawn   (new Position2D(7, 1), Side.BlackSide)));
        new_tiles.setValue(7, 0, new Tile("h8", new Rook   (new Position2D(7, 0), Side.BlackSide)));

        new_board.setTiles(new_tiles);
        return new_board;
    }


}
