package br.ufrn.imd.view;

import br.ufrn.imd.control.PieceNotFound;
import br.ufrn.imd.model.Pieces.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;


import java.util.HashMap;
import java.util.Map;

// TODO: poderia deixar de ser singleton e apenas ser um atributo do gameManager. (sem metodos estaticos!!)
// TODO: poderia trocar tudo para pieceManager ou algo similar pra simualar (controle x visao)
// static class (singleton por eficiencia)

/**
 * Classe gerenciadora das peças
 * @author Joao Lucas
 *
 */

public class PieceManager {

    private static final Map<String, Image> spriteCache = new HashMap<>();
    private BufferedImage sheet;
    {
        try{
            // TODO: Unify things with a "resources" package.
            sheet = ImageIO.read(new FileInputStream("resources/pieces.png"));
            //sheet = ImageIO.read(ClassLoader.getSystemResourceAsStream("../resources/piece.png"));
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    private final int sheetScale = sheet.getWidth() / 6;

    // TODO: Aprender a lidar com classes estaticas!
    public PieceManager() {}


    // FICOU FEIO PORQUE O JAVA 17 NAO SUPORTA O BASICO DE PATTERN MATCHING!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    // TODO: Lidar com IOException
    // TODO: ENTENDER PQ TROCAR O p.isWhite por p.isBlack deu certo!!!!
   /* private Image getSprite (Piece p) throws PieceNotFound, IOException, Exception {
        // TODO: Modularize isso (talvez global!??)
        int tileSize = Game.getBoard().getTileSize();
        try {
            if (p instanceof Pawn) {
                return sheet.getSubimage(5 * this.sheetScale,
                       p.isBlack() ? 0 : sheetScale, sheetScale, sheetScale)
                       .getScaledInstance(tileSize, tileSize, BufferedImage.SCALE_SMOOTH);
            }

            else if (p instanceof Rook) {
                return sheet.getSubimage(4 * this.sheetScale,
                       p.isBlack() ? 0 : sheetScale, sheetScale, sheetScale)
                       .getScaledInstance(tileSize, tileSize, BufferedImage.SCALE_SMOOTH);
            }

            else if (p instanceof Knight) {
                return sheet.getSubimage(3 * this.sheetScale,
                       p.isBlack() ? 0 : sheetScale, sheetScale, sheetScale)
                       .getScaledInstance(tileSize, tileSize, BufferedImage.SCALE_SMOOTH);
            }

            else if (p instanceof Bishop) {
                return sheet.getSubimage(2 * this.sheetScale,
                       p.isBlack() ? 0 : sheetScale, sheetScale, sheetScale)
                       .getScaledInstance(tileSize, tileSize, BufferedImage.SCALE_SMOOTH);
            }

            else if (p instanceof Queen) {
                return sheet.getSubimage(1 * this.sheetScale,
                       p.isBlack() ? 0 : sheetScale, sheetScale, sheetScale)
                       .getScaledInstance(tileSize, tileSize, BufferedImage.SCALE_SMOOTH);
            }

            else if (p instanceof King) {
                return sheet.getSubimage(0 * this.sheetScale,
                       p.isBlack() ? 0 : sheetScale, sheetScale, sheetScale)
                       .getScaledInstance(tileSize, tileSize, BufferedImage.SCALE_SMOOTH);
            }

            else {
                // Texto gerado apertando tab no intellij :O
                throw new PieceNotFound("Unsupported piece type: " + p.getClass().getSimpleName());
            }

        }

        // TODO: entender o printStackTrace
        catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }*/

    // metodo gerado de uma simplificacao por eficiencia.
    /**
     * Método para achar o sprite correte de uma peça
     * @param p
     * @param tile_size
     * @return Image
     * @throws PieceNotFound
     */
    public Image getSprite(Piece p, int tile_size) throws PieceNotFound {
        String key = p.getClass().getSimpleName() + (p.isWhite() ? "_white" : "_black");
        return spriteCache.computeIfAbsent(key, k -> {
            int spriteX = switch (p.getClass().getSimpleName()) {
                case "Pawn"   -> 5;
                case "Rook"   -> 4;
                case "Knight" -> 3;
                case "Bishop" -> 2;
                case "Queen"  -> 1;
                case "King"   -> 0;
                default -> throw new IllegalStateException("Unexpected value: " + p.getClass().getSimpleName());
            };
            int spriteY = p.isWhite() ? 0 : sheetScale;
            return sheet.getSubimage(spriteX * sheetScale, spriteY, sheetScale, sheetScale)
                    .getScaledInstance(tile_size, tile_size, BufferedImage.SCALE_SMOOTH);
        });
    }

    // TODO: resolver a chamada de metodos aninhados!
    //TODO: singleton things here
    /**
     * Método para inserir o sprite de uma peça
     * @param g2d
     * @param piece
     * @param tile_size
     */
    public void paintPiece (Graphics2D g2d, Piece piece, int tile_size) {
        int xPos     = piece.getCurrent_position().getX() * tile_size;
        int yPos     = piece.getCurrent_position().getY() * tile_size;

        try {
            g2d.drawImage(this.getSprite(piece, tile_size), xPos, yPos, null);
        }

        catch (Exception e) {
            //throw new RuntimeException(e);
            System.out.printf("Error: %s\n", e.getMessage());
            e.printStackTrace();
            return;
        }
    }

    // TODO: singleton things here
    /**
     * Método para inserir o sprite de uma peça no tabuleiro
     * @param g2d
     * @param piece
     * @param PosX
     * @param PosY
     * @param tile_size
     */
    public void paintPieceAt (Graphics2D g2d, Piece piece, int PosX, int PosY, int tile_size) {

        try {
            Image sprite = this.getSprite(piece, tile_size);
            g2d.drawImage(sprite, PosX, PosY, null);
        }

        catch (Exception e) {
            // Sugestao de texto gerada pelo meu editor lindao <3
            System.err.printf("Erro ao desenhar peça na posição (%d, %d): %s\n", PosX, PosY, e.getMessage());
            e.printStackTrace();
        }
    }



}
