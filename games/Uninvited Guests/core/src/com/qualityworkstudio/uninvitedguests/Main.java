package com.qualityworkstudio.uninvitedguests;

import com.badlogic.gdx.Game;
import com.qualityworkstudio.uninvitedguests.screens.MainScreen;

public class Main extends Game {

	@Override
	public void create () {
		GameSettings settings = new GameSettings();
		setScreen(new MainScreen(this, settings));
	}
}