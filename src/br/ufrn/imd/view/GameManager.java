package br.ufrn.imd.view;

import br.ufrn.imd.control.Input;
import br.ufrn.imd.model.Board.Board;
import br.ufrn.imd.model.Game;
import br.ufrn.imd.model.Matrices.Position2D;
import br.ufrn.imd.model.Pieces.Piece;
import br.ufrn.imd.model.Rules.ClassicalChessRules;
import br.ufrn.imd.model.Rules.GameState;
import br.ufrn.imd.model.Rules.Move;
import br.ufrn.imd.model.Rules.RuleSet;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Optional;

// TODO: Consertar a parte singleton
/**
 * Classe gerenciadora de estado do jogo
 * @author Joao Lucas
 *
 */

public class GameManager extends JPanel {

    // TODO: um setter pra generalizar!
    private Game game                      = new Game (new ClassicalChessRules());

    private Optional<Piece> selected_piece = Optional.empty();

    private PieceManager piece_manager     = new PieceManager();

    private static GameManager instance;
    
    private GameManager() {
        Input input = Input.getInstance();
        Board board = game.getBoard();
        this.setPreferredSize(new Dimension(board.getWidth() * board.tileSize, board.getHeight() * board.tileSize));

        this.addMouseListener       (input);
        this.addMouseMotionListener (input);
        //this.setBackground(Color.GREEN);
    }
    /**
     * Retorna a instancia de GameManager de acordo com o Simpleton
     * @return instance
     */
    public static GameManager getInstance() {
        if (Objects.isNull(instance)) {
            synchronized (GameManager.class) {
                if (Objects.isNull(instance)) {
                    instance = new GameManager();
                }
            }
        }
        return instance;
    }
    /**
     * Atualiza o estado do jogo
     */
    private void updateGameState () {
        game.updateGameState();
        mostrarResultados();
    }
    /**
     * Getter para Board
     * @return Board
     */
    public Board getBoard() {
        GameManager gm = GameManager.getInstance();
        return gm.game.getBoard();
    }

    /// ///////////////////////////////////////////////////////////
    // TODO: Simplificar a logica
    /**
     * Método para realizar movimento
     * @param move
     */
    public void makeMove (Move move) {
        if (move.getBoardBeforeMove().equals(game.getBoard())) {
            RuleSet referee = game.getRules();

            // Em troca de eficiencia, o projeto se torna mais estavel (Por causa da promocao do peao).
            // TRUST (Nessa ordem nunca haverao conflitos.)
            if (referee.isSpecialMove(game, move)) {

                try {
                    referee.makeItSpecial(game, move);
                }

                catch (Exception e) {
                    e.printStackTrace();
                }
            }

            else if (referee.isValidMove(move, game.getTurn())) {
                game.makeMove (move);
                // TODO: DA OVERLEAD LOGO, POR FAVOR!!
                System.out.println(game.getBoard().getPiece(move.getInitialPosition().getX(), move.getInitialPosition().getY()));
            }
        }

        updateGameState();
        System.out.println("\n\nGame STATE: " + game.getGameState());
    }


    // Acoplamento pra resolver
    // TODO: devia ser um funcao da classe "Game"
    
    /**
     * Método para achar qualquer uma das peças do tabuleiro
     * @param col
     * @param row
     * @return
     */
    public static Optional<Piece> getPiece(int col, int row) {
        GameManager gameManager = GameManager.getInstance();
        Game game = gameManager.game;
        Board board = game.getBoard();

        return game.getPiece(col, row);
    }


    /**
     * Método para seleção de peça
     * @param opt_piece
     */
    public static void selectPiece (Optional<Piece> opt_piece) {
        GameManager referee = GameManager.getInstance();
        referee.setSelectedPiece(opt_piece);
    }

    /**
     * Método para atualização da peça selecionada
     * @param opt_piece
     */
    private void setSelectedPiece(Optional<Piece> opt_piece) {
        selected_piece = opt_piece;
    }

    /**
     * 
     * @return selected_piece
     */
    public Optional<Piece> getSelectedPiece () {
        return selected_piece;
    }

    
    public void paintComponent (Graphics g) {
        super.paintComponent (g); // apaga as coisas
        Board board    = game.getBoard();
        Graphics2D g2d = (Graphics2D) g;


        paintBoard      (g2d, board);
        paintPieces     (g2d, board);
        paintHighlights (g2d, board);

    }
    /**
     * Método para inserção do tabuleiro
     * @param g2d
     * @param board
     */
    private void paintBoard (Graphics2D g2d, Board board) {

        // TODO: Modularize e confira os c's e os r's
        for (int c = 0; c < board.getTiles().getCols(); c++) {
            for (int r = 0; r < board.getTiles().getRows(); r++) {
                // TODO: Generalize colors by using the settings checkup!!
                g2d.setColor((c+r) % 2 == 0 ? new Color(227, 198, 181) : new Color(157, 105, 53));
                g2d.fillRect(c * board.tileSize, r * board.tileSize, board.tileSize, board.tileSize);

            }
        }
    }

    /**
     * Método para inserção do sprite de todas as peças no tabuleiro
     * @param g2d
     * @param board
     */
    private void paintPieces (Graphics2D g2d, Board board) {

        // TODO: resolver chamada de metodos aninhadas
        // Salvando possiveis erros remanescentes
        LinkedList<Piece> pieces = board.getPieces();
        selected_piece.ifPresent(piece -> {
            pieces.remove(piece);

            Input input  = Input.getInstance();

            int draggedX = input.getDraggedX();
            int draggedY = input.getDraggedY();

            piece_manager.paintPieceAt (g2d, piece, draggedX, draggedY, board.tileSize);
        });

        for (Piece piece : pieces) {
            //System.out.println(piece.getCurrent_position().getX() + "|" + piece.getCurrent_position().getY() + " : " + piece.getClass());
            piece_manager.paintPiece(g2d, piece, board.tileSize);
        }
    }
    /**
     * Método para realçar os possiveis movimentos da peça selecionada
     * @param g2d
     * @param board
     */
    private void paintHighlights (Graphics2D g2d, Board board) {

        selected_piece.ifPresent(piece -> {

            int tileSize    = board.tileSize;

            g2d.setColor(new Color(68, 180, 57, 190));
            Move move;

            // Possible Moves Highlights
            for (int c = 0; c < board.getWidth(); c++)
                for (int r = 0; r < board.getHeight(); r++) {

                    RuleSet referee = game.getRules();
                    move            = new Move(board, piece.
                            getCurrent_position(), new Position2D(c, r));

                    // TODO: DESTAQUE NOS MOVIMENTOS ESPECIAIS!!!!
                    if(referee.isValidMove(move, game.getTurn())
                        || referee.isSpecialMove(game, move)) {
                        int ovalSize = tileSize / 3;

                        int x = c * board.tileSize + (tileSize - ovalSize) / 2;
                        int y = r * board.tileSize + (tileSize - ovalSize) / 2;

                        g2d.fillOval(x, y, ovalSize, ovalSize);
                    }
                }

            // selected piece highlight:

            // Chamada de metodos aninhados!
            int x = piece.getCurrent_position().getX();
            int y = piece.getCurrent_position().getY();

            g2d.fillRect(x * board.tileSize, y * tileSize, tileSize, tileSize);
        });


    }

    
    /**
     * Método para visualização dos resultados
     */
    private void mostrarResultados() {
    	if (game.getGameState() == GameState.WhiteWon ) {
            JFrame frameResultado = new JFrame("Resultados");
            frameResultado.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frameResultado.setSize(300, 150);
            frameResultado.setLayout(new FlowLayout());
            JFrame framePrincipal = (JFrame) SwingUtilities.getWindowAncestor(this);


            JLabel label = new JLabel("Brancas Ganharam");
            JButton voltarButton = new JButton("Começar outro jogo");

            voltarButton.addActionListener(e -> {
            	frameResultado.dispose();
            	
            	if (framePrincipal != null ) {
            		framePrincipal.getContentPane().removeAll();
                    GameManager.instance = null;
                    GameManager novoGameManager = GameManager.getInstance();
                    framePrincipal.getContentPane().add(novoGameManager);
                    framePrincipal.revalidate();
                    framePrincipal.repaint();
            	}
            	
            });
            frameResultado.add(label);
            frameResultado.add(voltarButton);
            
            if (framePrincipal != null) {
                frameResultado.setLocationRelativeTo(framePrincipal);
            }
            
            frameResultado.setVisible(true);
    	}
    	if (game.getGameState() == GameState.BlackWon ) {
            JFrame frameResultado = new JFrame("Resultados");
            frameResultado.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frameResultado.setSize(300, 150);
            frameResultado.setLayout(new FlowLayout());
            JFrame framePrincipal = (JFrame) SwingUtilities.getWindowAncestor(this);

            JLabel label = new JLabel("Pretas Ganharam");
            JButton voltarButton = new JButton("Começar outro jogo");

            voltarButton.addActionListener(e -> {
            	frameResultado.dispose();
            	
            	if (framePrincipal != null ) {
            		framePrincipal.getContentPane().removeAll();
                    GameManager.instance = null;
                    GameManager novoGameManager = GameManager.getInstance();
                    framePrincipal.getContentPane().add(novoGameManager);
                    framePrincipal.revalidate();
                    framePrincipal.repaint();
            	}
            	
            });
            frameResultado.add(label);
            frameResultado.add(voltarButton);

            if (framePrincipal != null) {
                frameResultado.setLocationRelativeTo(framePrincipal);
            }
            
            frameResultado.setVisible(true);
    	}
    	if (game.getGameState() == GameState.Draw ) {
            JFrame frameResultado = new JFrame("Resultados");
            frameResultado.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frameResultado.setSize(300, 150);
            frameResultado.setLayout(new FlowLayout());
            JFrame framePrincipal = (JFrame) SwingUtilities.getWindowAncestor(this);

            JLabel label = new JLabel("Empate");
            JButton voltarButton = new JButton("Começar outro jogo");

        	
            voltarButton.addActionListener(e -> {
            	frameResultado.dispose();

            	if (framePrincipal != null ) {
            		framePrincipal.getContentPane().removeAll();
                    GameManager.instance = null;
                    GameManager novoGameManager = GameManager.getInstance();
                    framePrincipal.getContentPane().add(novoGameManager);
                    framePrincipal.revalidate();
                    framePrincipal.repaint();
            	}
            	
            });
            
            frameResultado.add(label);
            frameResultado.add(voltarButton);
            
            if (framePrincipal != null) {
                frameResultado.setLocationRelativeTo(framePrincipal);
            }
            
            frameResultado.setVisible(true);
    	}
    }

}
