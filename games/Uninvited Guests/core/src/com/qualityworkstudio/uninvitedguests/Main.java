package com.qualityworkstudio.uninvitedguests;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.qualityworkstudio.uninvitedguests.screens.MainScreen;

/**
 * The main class.
 *
 * @author Andrey Karanik
 */

public class Main extends Game {

	private GameSettings settings;

	public Main(GameSettings settings) {
		this.settings = settings;
	}

	@Override
	public void create () {
		AssetManager assetManager = new AssetManager();
		settings.setDeveloperMode(true);
		setScreen(new MainScreen(this, assetManager, settings));
	}
}