package com.qualityworkstudio.uninvitedguests.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.qualityworkstudio.uninvitedguests.GameSettings;
import com.qualityworkstudio.uninvitedguests.Player;

/**
 * The main screen of the game.
 *
 * @author Andrey Karanik
 */

public class MainScreen extends ScreenAdapter {

    private Game game;
    private GameSettings settings;

    private SpriteBatch batch;
    private World world;
    private Viewport viewport;
    private Stage stage;

    private Player player;

    public MainScreen(Game game, GameSettings settings) {
        this.game = game;
        this.settings = settings;
        batch = new SpriteBatch();
        world = new World(new Vector2(0, 0), false);
        player = new Player(new Texture(Gdx.files.internal("white_square.png")), settings);
        player.setFixedCamera(true);
        viewport = new FitViewport(1920f, 1920f * (
                (float)Gdx.graphics.getHeight() / Gdx.graphics.getWidth()));
        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        world.step(1 / 60f, 6, 2);
        player.update(delta);

        ScreenUtils.clear(Color.GOLD);

        batch.setProjectionMatrix(player.getCamera().combined);
        batch.begin();
        player.draw(batch);
        batch.end();

        world = new World(new Vector2(0, 0), false);

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        player.getCamera().viewportWidth = settings.getCameraSize();
        player.getCamera().viewportHeight = settings.getCameraSize() * ((float) height / width);
        player.getCamera().update();
    }

    @Override
    public void dispose() {
        batch.dispose();
        stage.dispose();
    }
}
