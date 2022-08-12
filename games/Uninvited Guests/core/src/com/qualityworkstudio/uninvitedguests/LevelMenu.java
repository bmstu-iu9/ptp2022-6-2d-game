package com.qualityworkstudio.uninvitedguests;

import com.badlogic.gdx.graphics.Texture;

public interface LevelMenu {
    void open();

    void close();

    void addLevel(int map, Texture levelTexture);
}
