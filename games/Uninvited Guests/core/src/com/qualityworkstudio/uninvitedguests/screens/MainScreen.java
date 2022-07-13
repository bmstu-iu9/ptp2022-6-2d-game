package com.qualityworkstudio.uninvitedguests.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
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
    private BitmapFont font;

    private Player player;

    //For developers
    private Box2DDebugRenderer debugRenderer;

    public MainScreen(Game game, GameSettings settings) {
        this.game = game;
        this.settings = settings;
        batch = new SpriteBatch();
        world = new World(new Vector2(0, 0), false);
        player = new Player(world, new Texture(Gdx.files.internal("white_square.png")), settings);
        player.setFixedCamera(true);
        viewport = new FitViewport(1920f, 1920f * (
                (float)Gdx.graphics.getHeight() / Gdx.graphics.getWidth()));
        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);

        Texture texture = new Texture(Gdx.files.internal("font.png"));
        texture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        font = new BitmapFont(Gdx.files.internal("font.fnt"), new TextureRegion(texture), false);

        Label.LabelStyle style = new Label.LabelStyle(font, Color.WHITE);
        Label label = new Label("QualityWork Studio", style);
        stage.addActor(label);

        debugRenderer = new Box2DDebugRenderer();
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

        if (settings.isDeveloperMode()) {
            debugRenderer.render(world, player.getCamera().combined);
        }

        stage.act(delta);
        stage.draw();
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
        stage.dispose();
    }
}
