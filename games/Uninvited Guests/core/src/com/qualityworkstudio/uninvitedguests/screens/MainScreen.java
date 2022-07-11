package com.qualityworkstudio.uninvitedguests.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.qualityworkstudio.uninvitedguests.GameSettings;
import com.qualityworkstudio.uninvitedguests.Player;

/**
 * The main screen of the game.
 *
 * @author Andrey Karanik
 */

public class MainScreen implements Screen {

    private Game game;
    private GameSettings settings;

    private SpriteBatch batch;

    private Player player;

    public MainScreen(Game game, GameSettings settings) {
        this.game = game;
        this.settings = settings;
        batch = new SpriteBatch();
        player = new Player(new Texture(Gdx.files.internal("white_square.png")), settings);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        player.update(Gdx.graphics.getDeltaTime());

        ScreenUtils.clear(Color.GOLD);
        batch.setProjectionMatrix(player.getCamera().combined);
        batch.begin();
        player.draw(batch);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        player.getCamera().viewportWidth = settings.getCameraSize();
        player.getCamera().viewportHeight = settings.getCameraSize() * ((float) height / width);
        player.getCamera().update();
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
