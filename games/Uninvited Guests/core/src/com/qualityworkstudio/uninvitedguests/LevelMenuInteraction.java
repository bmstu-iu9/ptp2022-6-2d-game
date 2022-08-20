package com.qualityworkstudio.uninvitedguests;

public class LevelMenuInteraction implements Interaction {

    private LevelMenu levelMenu;

    public LevelMenuInteraction(LevelMenu levelMenu) {
        this.levelMenu = levelMenu;
    }

    @Override
    public void interactIn() {
        levelMenu.show();
    }

    @Override
    public void interactOut() {
        levelMenu.hide();
    }
}
