package com.qualityworkstudio.uninvitedguests.client;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import com.qualityworkstudio.uninvitedguests.Game;
import com.qualityworkstudio.uninvitedguests.GameSettings;

public class HtmlLauncher extends GwtApplication {

        @Override
        public GwtApplicationConfiguration getConfig () {
                return new GwtApplicationConfiguration(true);
        }

        @Override
        public ApplicationListener createApplicationListener () {
                GameSettings settings = new GameSettings();
                return new Game(settings);
        }
}