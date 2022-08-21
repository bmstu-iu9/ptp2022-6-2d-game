package com.qualityworkstudio.uninvitedguests.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.qualityworkstudio.uninvitedguests.BasicContactListener;
import com.qualityworkstudio.uninvitedguests.BasicDoor;
import com.qualityworkstudio.uninvitedguests.BasicPlayerController;
import com.qualityworkstudio.uninvitedguests.GameSettings;
import com.qualityworkstudio.uninvitedguests.BasicLevelMenu;
import com.qualityworkstudio.uninvitedguests.InteractionArea;
import com.qualityworkstudio.uninvitedguests.LevelMenuInteraction;
import com.qualityworkstudio.uninvitedguests.Map;
import com.qualityworkstudio.uninvitedguests.MobilePlayerController;
import com.qualityworkstudio.uninvitedguests.Player;

/**
 * The main screen of the game.
 *
 * @author Andrey Karanik, Bogdan Teryukhov
 */

public class MainScreen extends ScreenAdapter {

    private Game game;
    private GameSettings settings;

    private SpriteBatch batch;
    private World world;
    private Viewport viewport;
    private Stage stage;
    private BasicLevelMenu levelMenu;

    private Player player;
    private Map map;
    private BasicDoor door;
    private InteractionArea levelMenuArea;

    private Box2DDebugRenderer debugRenderer;


    public MainScreen(Game game, AssetManager assetManager, GameSettings gameSettings) {
        this.game = game;
        this.settings = gameSettings;

        viewport = new FitViewport(settings.getViewportSize(), settings.getViewportSize() * (
                (float)Gdx.graphics.getHeight() / Gdx.graphics.getWidth()));
        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);

        levelMenu = new BasicLevelMenu(stage, assetManager, game);
        levelMenu.addLevel(1, "Level 1", assetManager.<Texture>get("level1_image.png"));
        levelMenu.addLevel(2, "Level 2", assetManager.<Texture>get("level1_image.png"));
        levelMenu.addLevel(3, "Level 3", assetManager.<Texture>get("level1_image.png"));
        levelMenu.addLevel(4, "Level 4", assetManager.<Texture>get("level1_image.png"));
        levelMenu.addLevel(5, "Level 5", assetManager.<Texture>get("level1_image.png"));
        levelMenu.addLevel(6, "Level 6", assetManager.<Texture>get("level1_image.png"));

        batch = new SpriteBatch();
        world = new World(new Vector2(0, 0), false);
        world.setContactListener(new BasicContactListener());
        map = new Map(128, assetManager.<Texture>get("maps/main_map_layer1.png"), assetManager.<Texture>get("maps/main_map_layer2.png"));
        door = new BasicDoor(world, assetManager);
        door.setPosition(0f, 16f);
        door.setType(BasicDoor.Type.GREEN);
        player = new Player(world, assetManager.<Texture>get("character.png"), settings);
        if (settings.isMobileMode()) {
            MobilePlayerInterface playerInterface = new MobilePlayerInterface(stage, assetManager);
            player.setController(new MobilePlayerController(player, playerInterface.getMovementJoystick(), playerInterface.getRotationJoystick()));
            player.setPlayerInterface(playerInterface);
        } else {
            player.setController(new BasicPlayerController(player));
            player.setPlayerInterface(new BasicPlayerInterface());
        }
        player.setFixedCamera(true);
        levelMenuArea = new InteractionArea(world, new Vector2(6f, 1f), new LevelMenuInteraction(levelMenu, player));
        levelMenu.getCloseButton().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                levelMenu.hide();
                player.setFixed(false);
                player.moveTo(new Vector2(0, 0));
                if (settings.isMobileMode()) {
                    player.getPlayerInterface().show();
                }
            }
        });
        levelMenuArea.setPosition(door.getPosition());
    }

    @Override
    public void show() {
        player.getPlayerInterface().show();
    }

    @Override
    public void render(float delta) {
        world.step(1 / 60f, 6, 2);
        player.update(delta);
        door.update(delta);
        stage.act(delta);

        ScreenUtils.clear(Color.BLACK);

        batch.setProjectionMatrix(player.getCamera().combined);
        batch.begin();
        map.draw(batch);
        door.draw(batch);
        map.draw(batch);
        player.draw(batch);
        batch.end();
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
