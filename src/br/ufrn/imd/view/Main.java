package br.ufrn.imd.view;

import br.ufrn.imd.model.Game;
import br.ufrn.imd.model.Rules.ClassicalChessRules;

import javax.swing.*;
import java.awt.*;
/**
 * 
 * @author Joao Lucas
 *
 */

public class Main {
    public static void main(String[] args) {



        Game game = new Game (new ClassicalChessRules());

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