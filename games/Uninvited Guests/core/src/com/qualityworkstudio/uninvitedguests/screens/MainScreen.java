package com.qualityworkstudio.uninvitedguests.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.BitmapFontLoader;
import com.badlogic.gdx.files.FileHandle;
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
import com.qualityworkstudio.uninvitedguests.BasicContactListener;
import com.qualityworkstudio.uninvitedguests.BasicDoor;
import com.qualityworkstudio.uninvitedguests.BasicPlayerController;
import com.qualityworkstudio.uninvitedguests.GameSettings;
import com.qualityworkstudio.uninvitedguests.BasicLevelMenu;
import com.qualityworkstudio.uninvitedguests.LevelMenu;
import com.qualityworkstudio.uninvitedguests.Map;
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
    private Map map;
    private BasicDoor door;

    private Box2DDebugRenderer debugRenderer;

    public MainScreen(Game game, AssetManager assetManager, GameSettings settings) {
        this.game = game;
        this.settings = settings;

        assetManager.load("character.png", Texture.class);
        assetManager.load("maps/main_map_layer1.png", Texture.class);
        assetManager.load("maps/main_map_layer2.png", Texture.class);
        assetManager.load("green_door_part.png", Texture.class);
        assetManager.load("yellow_door_part.png", Texture.class);
        assetManager.load("red_door_part.png", Texture.class);
        assetManager.load("level_button.png", Texture.class);
        assetManager.load("selected_level_button.png", Texture.class);
        assetManager.load("levelmenu_bg.png", Texture.class);
        assetManager.load("level1_image.png", Texture.class);
        assetManager.load("level_start_button.png", Texture.class);
        assetManager.load("font.fnt", BitmapFont.class);
        assetManager.finishLoading();

        batch = new SpriteBatch();
        world = new World(new Vector2(0, 0), false);
        world.setContactListener(new BasicContactListener());
        map = new Map(128, assetManager.<Texture>get("maps/main_map_layer1.png"), assetManager.<Texture>get("maps/main_map_layer2.png"));
        door = new BasicDoor(world, assetManager);
        door.setPosition(0f, 16f);
        door.setType(BasicDoor.Type.GREEN);
        player = new Player(world, assetManager.<Texture>get("character.png"), settings);
        player.setController(new BasicPlayerController(player));
        player.setFixedCamera(true);

        viewport = new FitViewport(settings.getViewportSize(), settings.getViewportSize() * (
                (float)Gdx.graphics.getHeight() / Gdx.graphics.getWidth()));
        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);

        LevelMenu levelMenu = new BasicLevelMenu(stage, assetManager, game);
        levelMenu.addLevel(1, assetManager.<Texture>get("level1_image.png"));
        levelMenu.addLevel(1, assetManager.<Texture>get("level1_image.png"));
        levelMenu.addLevel(1, assetManager.<Texture>get("level1_image.png"));
        levelMenu.addLevel(1, assetManager.<Texture>get("level1_image.png"));
        levelMenu.addLevel(1, assetManager.<Texture>get("level1_image.png"));
        levelMenu.addLevel(1, assetManager.<Texture>get("level1_image.png"));
    }

    @Override
    public void render(float delta) {
        world.step(1 / 60f, 6, 2);
        player.update(delta);
        door.update(delta);

        ScreenUtils.clear(Color.BLACK);

        batch.setProjectionMatrix(player.getCamera().combined);
        batch.begin();
        map.draw(batch);
        door.draw(batch);
        map.draw(batch);
        player.draw(batch);
        batch.end();

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
        world.dispose();
        stage.dispose();
    }
}
