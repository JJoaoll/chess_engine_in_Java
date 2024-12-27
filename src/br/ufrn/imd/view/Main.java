package br.ufrn.imd.view;

import br.ufrn.imd.model.Board.Board;
import br.ufrn.imd.model.Game;
import br.ufrn.imd.model.Matrices.Position2D;
import br.ufrn.imd.model.Rules.ClassicalRules;
import br.ufrn.imd.model.Rules.RuleSet;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {

        /*Grid<Double> grid = new Grid<>(8, 8);
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                grid.setValue(i, j, ((double) i + ((double) j /10)));
            }
        }

        Double d1 = grid.getValue(0, 0);
        Double d2 = grid.getValue(5, 5);
        Double d3 = grid.getValue(7, 7);
        System.out.printf(d1 + "," + d2 + "," + d3 + "\n");*/

        /*GameManager game = new GameManager();

        JFrame frame = new JFrame();
        frame.getContentPane().setBackground(Color.BLACK);

        frame.setLayout(new GridBagLayout());
        frame.setMinimumSize(new Dimension(1000, 1000));
        frame.setLocationRelativeTo(null);

        frame.add(game);

        frame.setVisible(true);*/
        Position2D position7 = new Position2D(4, 3);
        Position2D position6 = new Position2D(7, 5);
        Position2D position5 = new Position2D(2, 4);
        Position2D position4 = new Position2D(3, 3);
        Position2D position3 = new Position2D(5, 5);
        Position2D position2 = new Position2D(0, 7);
        Position2D position1 = new Position2D(1, 0);

        System.out.println(position1.toChessNotation());
        System.out.println(position2.toChessNotation());
        System.out.println(position3.toChessNotation());
        System.out.println(position4.toChessNotation());
        System.out.println(position5.toChessNotation());
        System.out.println(position5.toChessNotation());
        System.out.println(position7.toChessNotation());


        System.out.println(Position2D.fromChessNotation(position1.toChessNotation()).toChessNotation());
        System.out.println(Position2D.fromChessNotation(position2.toChessNotation()).toChessNotation());
        System.out.println(Position2D.fromChessNotation(position3.toChessNotation()).toChessNotation());
        System.out.println(Position2D.fromChessNotation(position4.toChessNotation()).toChessNotation());
        System.out.println(position5.toChessNotation());
        System.out.println(position5.toChessNotation());
        System.out.println(position7.toChessNotation());


        Game.getInstance();

        JFrame frame = new JFrame("Chess Board");
        frame.getContentPane().setBackground(Color.BLACK);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        GameManager gameManager = GameManager.getInstance();
        frame.add(gameManager);

        frame.setLayout(new GridBagLayout());
        frame.setMinimumSize(new Dimension(1000, 1000));
        frame.setLocationRelativeTo(null);
        frame.pack();

        frame.setVisible(true);
    }
}