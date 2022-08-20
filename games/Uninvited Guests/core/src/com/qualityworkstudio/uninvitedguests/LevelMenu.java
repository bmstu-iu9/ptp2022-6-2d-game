package com.qualityworkstudio.uninvitedguests;

import com.badlogic.gdx.graphics.Texture;

public interface LevelMenu {
    void show();

    void hide();

    void addLevel(int map, String name, Texture levelTexture);
}
