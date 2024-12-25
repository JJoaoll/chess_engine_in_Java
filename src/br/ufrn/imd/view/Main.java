package br.ufrn.imd.view;

import br.ufrn.imd.model.Board.Board;
import br.ufrn.imd.model.Game;
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

        Game.getInstance();

        JFrame frame = new JFrame("Chess Board");
        frame.getContentPane().setBackground(Color.BLACK);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        GameManager gameManager = new GameManager();
        frame.add(gameManager);

        frame.setLayout(new GridBagLayout());
        frame.setMinimumSize(new Dimension(1000, 1000));
        frame.setLocationRelativeTo(null);
        frame.pack();

        frame.setVisible(true);
    }
}