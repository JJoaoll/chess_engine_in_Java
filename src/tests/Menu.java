/*import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu {

    public static void main(String[] args) {
        JFrame frame = new JFrame("Menu Principal");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 1000);
        frame.setLayout(new GridBagLayout());

        JButton botao1 = new JButton("Jogar");
        JButton botao2 = new JButton("Configurações");
        JButton botao3 = new JButton("Sair");

        botao1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	mostrarJogo("Menu 1");
            }
        });

        botao2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarMenuConfig("Menu 2");
            }
        });

        botao3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        
        JPanel painel = new JPanel();
        painel.setLayout(new GridLayout(3, 1, 200, 75)); 
        painel.add(botao1);
        painel.add(botao2);
        painel.add(botao3);

        
        frame.add(painel, new GridBagConstraints());

        
        frame.setVisible(true);
    }

    private static void mostrarJogo(String titulo) {
        
    }
    
    private static void mostrarMenuConfig(String titulo) {
        JFrame frame = new JFrame(titulo);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(300, 150);
        frame.setLayout(new FlowLayout());

        JLabel label = new JLabel("Configurações");
        JButton voltarButton = new JButton("Voltar");

        voltarButton.addActionListener(e -> frame.dispose());

        frame.add(label);
        frame.add(voltarButton);

        frame.setVisible(true);
    }
}
*/