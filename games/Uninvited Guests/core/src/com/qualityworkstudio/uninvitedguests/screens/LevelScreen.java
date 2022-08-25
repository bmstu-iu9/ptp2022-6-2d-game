package com.qualityworkstudio.uninvitedguests.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.qualityworkstudio.uninvitedguests.BasicContactListener;
import com.qualityworkstudio.uninvitedguests.BasicPlayerController;
import com.qualityworkstudio.uninvitedguests.BasicPlayerInterface;
import com.qualityworkstudio.uninvitedguests.Game;
import com.qualityworkstudio.uninvitedguests.GameSettings;
import com.qualityworkstudio.uninvitedguests.MobilePlayerController;
import com.qualityworkstudio.uninvitedguests.MobilePlayerInterface;
import com.qualityworkstudio.uninvitedguests.Player;

public class LevelScreen extends ScreenAdapter {

    Game game;
    AssetManager assetManager;
    GameSettings settings;

    SpriteBatch batch;
    World world;
    Viewport viewport;
    Stage stage;

    Player player;

    public LevelScreen(Game game) {
        this.game = game;
        this.assetManager = game.getAssetManager();
        this.settings = game.getSettings();

        viewport = new FitViewport(settings.getViewportSize(), settings.getViewportSize() * (
                (float) Gdx.graphics.getHeight() / Gdx.graphics.getWidth()));
        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);

        batch = new SpriteBatch();
        world = new World(new Vector2(0, 0), false);
        world.setContactListener(new BasicContactListener());
        player = new Player(world, assetManager.<Texture>get("character.png"), settings.getCameraSize());
        if (settings.isMobileMode()) {
            MobilePlayerInterface playerInterface = new MobilePlayerInterface(stage, assetManager);
            player.setController(new MobilePlayerController(player, playerInterface.getMovementJoystick(), playerInterface.getRotationJoystick()));
            player.setPlayerInterface(playerInterface);
        } else {
            player.setController(new BasicPlayerController(player));
            player.setPlayerInterface(new BasicPlayerInterface());
        }
    }

    @Override
    public void show() {
        player.getPlayerInterface().show();
    }

    @Override
    public void render(float delta) {
        world.step(1 / 60f, 6, 2);
        stage.act(delta);
        update(delta);

        ScreenUtils.clear(Color.BLACK);

        batch.setProjectionMatrix(player.getCamera().combined);
        batch.begin();
        draw();
        batch.end();

        stage.draw();
    }

    public void update(float delta) {

    }

    public void draw() {
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        player.getCamera().viewportWidth = settings.getCameraSize();
        player.getCamera().viewportHeight = settings.getCameraSize() * viewport.getScreenHeight() / viewport.getScreenWidth();
        player.getCamera().update();
    }

    @Override
    public void dispose() {
        batch.dispose();
        world.dispose();
        stage.dispose();
    }
}
