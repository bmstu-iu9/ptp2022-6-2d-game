package com.qualityworkstudio.uninvitedguests;

import com.badlogic.gdx.graphics.Texture;

/**
 * @author Andrey Karanik
 */

public interface LevelMenu {

    /**
     * Shows the level menu.
     */
    void show();

    /**
     * Hides the level menu.
     */
    void hide();

    /**
     * Adds a new level button.
     * @param name the level name.
     * @param levelTexture the level preview.
     */
    void addLevel(String name, Texture levelTexture);
}
