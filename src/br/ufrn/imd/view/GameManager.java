package br.ufrn.imd.view;

import br.ufrn.imd.model.Game;

import java.util.Objects;

public class GameManager {

    private Game            game;
    private BoardView board_view;

    private static GameManager instance;

    private GameManager() {}

    // Fiz um double lock ;)
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


}
