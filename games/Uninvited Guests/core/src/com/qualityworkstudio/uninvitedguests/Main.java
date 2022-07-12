package com.qualityworkstudio.uninvitedguests;

import com.badlogic.gdx.Game;
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
		setScreen(new MainScreen(this, settings));
	}
}