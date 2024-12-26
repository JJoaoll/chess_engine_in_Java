package br.ufrn.imd.view;

import br.ufrn.imd.control.PieceNotFound;
import br.ufrn.imd.model.Board.Board;
import br.ufrn.imd.model.Game;
import br.ufrn.imd.model.Pieces.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;


// static class
public class PieceView {

    // TODO: Aprender a lidar com classes estaticas!
    private PieceView () {}

    private BufferedImage sheet;
    {
        try{
            // TODO: Unify things with a "resources" package.
            sheet = ImageIO.read(new FileInputStream("resources/pieces.png"));
            //sheet = ImageIO.read(ClassLoader.getSystemResourceAsStream("../resources/piece.png"));
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    private final int sheetScale = sheet.getWidth() / 6;

    // FICOU FEIO PORQUE O JAVA 17 NAO SUPORTA O BASICO DE PATTERN MATCHING!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    // TODO: Lidar com IOException
    // TODO: ENTENDER PQ TROCAR O p.isWhite por p.isBlack deu certo!!!!
    private Image getSprite (Piece p) throws PieceNotFound, IOException, Exception {
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
    }

    // TODO: resolver a chamada de metodos aninhados!
    public static void paintPiece(Graphics2D g2d, Piece piece) {
        PieceView pv = new PieceView();
        int tileSize = Game.getBoard().getTileSize();
        int xPos     = piece.getCurrent_position().getX() * tileSize;
        int yPos     = piece.getCurrent_position().getY() * tileSize;

        try {
            g2d.drawImage(pv.getSprite(piece), xPos, yPos, null);
        }

        catch (Exception e) {
            //throw new RuntimeException(e);
            System.out.printf("Error: %s\n", e.getMessage());
            e.printStackTrace();
            return;
        }
    }
}
