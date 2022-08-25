package com.qualityworkstudio.uninvitedguests.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.qualityworkstudio.uninvitedguests.Game;

/**
 * @author Andrey Karanik
 */

public class Screens {

    public static final int MAIN_SCREEN = 0;

    public static final int LEVEL1 = 100;

    @Deprecated
    public static Screen getScreen(Game game, int screen) {

        if (Gdx.graphics.getWidth() - Gdx.graphics.getHeight() < 0) {
            return new RatioAdjustmentScreen(game, screen);
        }

        switch (screen) {
            case MAIN_SCREEN:
                return new MainScreen(game);
            case LEVEL1:
                return new Level1(game);
        }

        return new MainScreen(game);
    }
}
