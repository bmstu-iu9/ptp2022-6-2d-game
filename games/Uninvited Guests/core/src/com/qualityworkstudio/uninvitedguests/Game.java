package com.qualityworkstudio.uninvitedguests;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.qualityworkstudio.uninvitedguests.screens.Level1;
import com.qualityworkstudio.uninvitedguests.screens.Level2;
import com.qualityworkstudio.uninvitedguests.screens.Level3;
import com.qualityworkstudio.uninvitedguests.screens.MainScreen;
import com.qualityworkstudio.uninvitedguests.screens.MapEditorScreen;
import com.qualityworkstudio.uninvitedguests.screens.RatioAdjustmentScreen;
import com.qualityworkstudio.uninvitedguests.screens.Screens;
import com.qualityworkstudio.uninvitedguests.screens.SettingsScreen;

/**
 * The main class.
 *
 * @author Andrey Karanik
 */

public class Game implements ApplicationListener {

	private GameSettings settings;
	private AssetManager assetManager;
	private Loader loader;
	private Screen screen;

	private Timer gameTimer;
	private boolean isOver;
	private float playSeconds = 120f;
	private boolean[] completeLevels;

	public Game(GameSettings settings) {
		this.settings = settings;
	}

	/**
	 * Called when the game is first created.
	 */
	@Override
	public void create () {
		settings.setDeveloperMode(true);

		assetManager = new AssetManager();
		assetManager.load("loading_image1.png", Texture.class);
		assetManager.load("loading_image2.png", Texture.class);
		assetManager.load("mobile_mode_button.png", Texture.class);
		assetManager.load("selected_mobile_mode_button.png", Texture.class);
		assetManager.load("continue_button.png", Texture.class);
		assetManager.load("loading_image2.png", Texture.class);
		assetManager.load("ra_h_window_image.png", Texture.class);
		assetManager.load("ra_v_window_image.png", Texture.class);
		assetManager.load("ra_mobile_image.png", Texture.class);
		assetManager.load("ra_image.png", Texture.class);
		assetManager.load("font.png", Texture.class);
		assetManager.load("font.fnt", BitmapFont.class);
		assetManager.finishLoading();

		loader = new Loader(assetManager);

		start();
	}

	/**
	 * Starts the game. (restarts)
	 */
	public void start() {
		isOver = false;
		gameTimer = new Timer(new Timer.Task() {
			@Override
			public void doTask() {
				isOver = true;
			}
		});
		gameTimer.start(playSeconds);
		completeLevels = new boolean[3];
		setScreen(new SettingsScreen(this, Screens.MAIN_SCREEN));
	}

	/**
	 * Sets the level is complete.
	 *
	 * @param level the level.
	 * @param complete whether the level is complete.
	 */
	public void setLevelComplete(int level, boolean complete) {
		completeLevels[level] = complete;
	}

	/**
	 * Returns true when the level is complete.
	 *
	 * @param level the level.
	 * @return whether the level is complete.
	 */
	public boolean isLevelComplete(int level) {
		return completeLevels[level];
	}

	/**
	 * Returns true when the game is over.
	 *
	 * @return whether the game is over.
	 */
	public boolean isOver() {
		return isOver;
	}

	public boolean isVictory() {
		for (boolean completeLevel : completeLevels) {
			if (!completeLevel) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Returns the game timer.
	 *
	 * @return the timer.
	 */
	public Timer getGameTimer() {
		return gameTimer;
	}

	/**
	 * Gets the loader.
	 *
	 * @return the loader.
	 */
	public Loader getLoader() {
		return loader;
	}

	/**
	 * Gets the asset manager.
	 *
	 * @return the asset manager.
	 */
	public AssetManager getAssetManager() {
		return assetManager;
	}

	/**
	 * Sets the game settings.
	 *
	 * @param settings new game settings.
	 */
	public void setSettings(GameSettings settings) {
		this.settings = settings;
	}

	/**
	 * Gets the game settings.
	 *
	 * @return the game settings.
	 */
	public GameSettings getSettings() {
		return settings;
	}

	/**
	 * Disposes the current screen.
	 */
	@Override
	public void dispose() {
		if (screen == null) {
			return;
		}
		screen.hide();
	}

	/**
	 * Pauses the current screen.
	 */
	@Override
	public void pause() {
		if (screen == null) {
			return;
		}
		screen.pause();
	}

	/**
	 * Resumes the current screen.
	 */
	@Override
	public void resume() {
		if (screen == null) {
			return;
		}
		screen.resume();
	}

	/**
	 * Renders the current screen.
	 */
	@Override
	public void render() {
		if (screen == null) {
			return;
		}
		screen.render(Gdx.graphics.getDeltaTime());
	}

	/**
	 * Resizes the current screen.
	 */
	@Override
	public void resize(int width, int height) {
		if (screen == null) {
			return;
		}
		screen.resize(width, height);
	}

	/**
	 * Sets the current screen.
	 *
	 * @param screen a new screen.
	 */
	public void setScreen(Screen screen) {
		if (this.screen != null) {
			this.screen.hide();
		}

		this.screen = screen;

		if (this.screen != null) {
			this.screen.show();
			this.screen.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		}
	}

	/**
	 * Sets the current screen by ID.
	 *
	 * @param screenId the screen ID.
	 */
	public void setScreen(int screenId) {
		if (Gdx.graphics.getWidth() - Gdx.graphics.getHeight() < 0) {
			setScreen(new RatioAdjustmentScreen(this, screenId));
		} else {
			switch (screenId) {
				case Screens.MAIN_SCREEN:
					setScreen(new MainScreen(this));
					break;
				case Screens.LEVEL1:
					setScreen(new Level1(this));
					break;
				case Screens.LEVEL2:
					setScreen(new Level2(this));
					break;
				case Screens.LEVEL3:
					setScreen(new Level3(this));
					break;
			}
		}
	}

	/**
	 * Gets the current screen.
	 *
	 * @return the current screen.
	 */
	public Screen getScreen() {
		return screen;
	}
}