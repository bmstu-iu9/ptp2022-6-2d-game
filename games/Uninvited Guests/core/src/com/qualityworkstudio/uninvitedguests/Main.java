package com.qualityworkstudio.uninvitedguests;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.qualityworkstudio.uninvitedguests.screens.MainScreen;

/**
 * The main class for launching the game.
 *
 * @author Andrey Karanik
 */

public class Main extends Game {

	@Override
	public void create () {
		GameSettings settings = new GameSettings();
		settings.setDeveloperMode(true);
		AssetManager assetManager = new AssetManager();
		setScreen(new MainScreen(this, assetManager, settings));
	}
}