package com.qualityworkstudio.uninvitedguests;

public class LevelMenuInteraction implements Interaction {

    private LevelMenu levelMenu;
    private Player player;
    private MobileInterface mobileInterface;
    private GameSettings settings;

    public LevelMenuInteraction(LevelMenu levelMenu, Player player, MobileInterface mobileInterface, GameSettings settings) {
        this.levelMenu = levelMenu;
        this.player = player;
        this.mobileInterface = mobileInterface;
        this.settings = settings;
    }

    @Override
    public void interactIn() {
        levelMenu.show();
        player.setFixed(true);
        if (settings.isMobileMode()) {
            mobileInterface.hide();
        }
    }

    @Override
    public void interactOut() {

    }
}
