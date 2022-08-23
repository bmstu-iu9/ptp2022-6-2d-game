package com.qualityworkstudio.uninvitedguests.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.qualityworkstudio.uninvitedguests.GameSettings;

public class Screens {

    public static final int MAIN_SCREEN = 0;
    public static final int LEVEL1 = 1;


    public static Screen getScreen(int screen, Game game, AssetManager assetManager, GameSettings settings) {

        if (Gdx.graphics.getWidth() - Gdx.graphics.getHeight() < 0) {
            return new RatioAdjustmentScreen(screen, game, assetManager, settings);
        }

        switch (screen) {
            case MAIN_SCREEN:
                return new MainScreen(game, assetManager, settings);
            case LEVEL1:
                return new Level1(game, assetManager, settings);
        }

        return new MainScreen(game, assetManager, settings);
    }
}
