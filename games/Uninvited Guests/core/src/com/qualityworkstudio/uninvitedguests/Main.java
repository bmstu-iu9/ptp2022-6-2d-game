package com.qualityworkstudio.uninvitedguests;

import com.badlogic.gdx.Game;
<<<<<<< HEAD
import com.qualityworkstudio.uninvitedguests.screens.MainScreen;

public class Main extends Game {
	
	@Override
	public void create () {
		GameSettings settings = new GameSettings();
		setScreen(new MainScreen(this, settings));
=======
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class Main extends Game {
	SpriteBatch batch;
	Texture img;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
	}

	@Override
	public void render () {
		ScreenUtils.clear(0.1f, 0, 0.2f, 1);
		batch.begin();
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
>>>>>>> 0435e4276e66d0fca083e3050c5e54ece9ad38fe
	}
}
