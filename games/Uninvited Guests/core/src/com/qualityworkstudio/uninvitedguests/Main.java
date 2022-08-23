package com.qualityworkstudio.uninvitedguests;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.qualityworkstudio.uninvitedguests.screens.LoadingScreen;
import com.qualityworkstudio.uninvitedguests.screens.MainScreen;
import com.qualityworkstudio.uninvitedguests.screens.RatioAdjustmentScreen;
import com.qualityworkstudio.uninvitedguests.screens.Screens;

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
		settings.setDeveloperMode(true);
		settings.setMobileMode(true);

		AssetManager assetManager = new AssetManager();
		assetManager.load("loading_image1.png", Texture.class);
		assetManager.load("loading_image2.png", Texture.class);
		assetManager.load("font.fnt", BitmapFont.class);
		assetManager.finishLoading();

		LoadingData loadingData = new LoadingData("json/firstload.json");
		setScreen(new LoadingScreen(Screens.MAIN_SCREEN, this, assetManager, settings, loadingData));
	}
}