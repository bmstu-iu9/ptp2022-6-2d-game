package com.qualityworkstudio.uninvitedguests;

/**
 * @author Andrey Karanik
 */

public class LevelMenuInteraction implements Interaction {

    private LevelMenu levelMenu;
    private Player player;

    public LevelMenuInteraction(LevelMenu levelMenu, Player player) {
        this.levelMenu = levelMenu;
        this.player = player;
    }

    @Override
    public void interactIn() {
        levelMenu.show();
        player.setFixed(true);
        player.getPlayerInterface().hide();
    }

    @Override
    public void interactOut() {

    }
}
