/*
package tests;

import br.ufrn.imd.view.PieceSelectionDialog;
import br.ufrn.imd.view.PieceView;
import br.ufrn.imd.model.Pieces.*;

import java.util.LinkedList;
import java.util.Scanner;

public class Tests {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        // Criar uma LinkedList de PieceView, representando diferentes peças disponíveis
        LinkedList<PieceView> pieceViews = new LinkedList<>();

        // Inicializar peças específicas para o teste, utilizando o Singleton da PieceView
        PieceView pieceView = PieceView.getInstance();

        pieceViews.add(pieceView); // Adiciona a instância de PieceView para simulação

        // Criar um diálogo de seleção de peças com a lista
        PieceSelectionDialog dialog = new PieceSelectionDialog(pieceViews);

        // Simulação de seleção
        System.out.println("Digite a peça que deseja selecionar: (exemplo: Queen, Rook)");
        String selectedPiece = scanner.nextLine();

        try {
            // Simular o funcionamento da seleção na interface
            boolean isValid = pieceViews.stream().anyMatch(view -> {
                // Aqui você pode implementar uma validação realista de peça
                return view != null; // Este é um placeholder, ajuste conforme necessário
            });

            if (isValid) {
                System.out.println("Peça selecionada com sucesso: " + selectedPiece);
            } else {
                System.out.println("Peça inválida ou não encontrada.");
            }
        } catch (Exception e) {
            System.out.println("Erro ao processar a seleção de peça: " + e.getMessage());
        }

        scanner.close();
    }
}*/
