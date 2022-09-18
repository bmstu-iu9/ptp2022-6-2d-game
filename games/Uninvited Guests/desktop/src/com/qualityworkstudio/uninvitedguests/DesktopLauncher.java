package com.qualityworkstudio.uninvitedguests;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(60);
		config.setTitle("Uninvited Guests");
		config.setWindowedMode(1280, 720);
		GameSettings settings = new GameSettings();
		new Lwjgl3Application(new Game(settings), config);
	}
}
