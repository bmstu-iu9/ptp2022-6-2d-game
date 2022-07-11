package com.qualityworkstudio.uninvitedguests.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.qualityworkstudio.uninvitedguests.GameSettings;

/**
 * The main screen of the game.
 *
 * @author Andrey Karanik
 */

public class MainScreen implements Screen {

    private Game game;
    private GameSettings settings;

    private SpriteBatch batch;
    private OrthographicCamera camera;

    public MainScreen(Game game, GameSettings settings) {
        this.game = game;
        this.settings = settings;
        batch = new SpriteBatch();
        camera = new OrthographicCamera(settings.getCameraSize(), settings.getCameraSize() *
                ((float) Gdx.graphics.getHeight() / Gdx.graphics.getWidth()));
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        camera.update();
        ScreenUtils.clear(Color.GOLD);
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        camera.viewportWidth = settings.getCameraSize();
        camera.viewportHeight = settings.getCameraSize() * ((float) height / width);
        camera.update();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
