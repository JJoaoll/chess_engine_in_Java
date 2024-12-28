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


import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

// TODO: poderia deixar de ser singleton e apenas ser um atributo do gameManager. (sem metodos estaticos!!)
// TODO: poderia trocar tudo para pieceManager ou algo similar pra simualar (controle x visao)
// static class (singleton por eficiencia)
public class PieceView {

    private static final Map<String, Image> spriteCache = new HashMap<>();
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
    private static PieceView instance;

    // TODO: Aprender a lidar com classes estaticas!
    private PieceView () {}

    public static PieceView getInstance() {
        if (Objects.isNull(instance)) {
            synchronized (PieceView.class) {
                if (Objects.isNull(instance)) {
                    instance = new PieceView();
                }
            }
        }
        return instance;
    }

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
    private Image getSprite(Piece p) throws PieceNotFound {
        String key = p.getClass().getSimpleName() + (p.isWhite() ? "_white" : "_black");
        return spriteCache.computeIfAbsent(key, k -> {
            int tileSize = Game.getBoard().getTileSize();
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
                    .getScaledInstance(tileSize, tileSize, BufferedImage.SCALE_SMOOTH);
        });
    }

    // TODO: resolver a chamada de metodos aninhados!
    //TODO: singleton things here
    public static void paintPiece (Graphics2D g2d, Piece piece) {
        PieceView pv = PieceView.getInstance();
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

    // TODO: singleton things here
    public static void paintPieceAt (Graphics2D g2d, Piece piece, int PosX, int PosY) {
        PieceView pv = PieceView.getInstance();

        try {
            Image sprite = pv.getSprite(piece);
            g2d.drawImage(sprite, PosX, PosY, null);
        }

        catch (Exception e) {
            // Sugestao de texto gerada pelo meu editor lindao <3
            System.err.printf("Erro ao desenhar peça na posição (%d, %d): %s\n", PosX, PosY, e.getMessage());
            e.printStackTrace();
        }
    }



}
