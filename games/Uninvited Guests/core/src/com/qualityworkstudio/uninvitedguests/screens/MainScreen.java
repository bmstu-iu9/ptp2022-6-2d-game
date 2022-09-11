package com.qualityworkstudio.uninvitedguests.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.qualityworkstudio.uninvitedguests.BasicContactListener;
import com.qualityworkstudio.uninvitedguests.BasicDoor;
import com.qualityworkstudio.uninvitedguests.BasicPlayerController;
import com.qualityworkstudio.uninvitedguests.BasicPlayerInterface;
import com.qualityworkstudio.uninvitedguests.Game;
import com.qualityworkstudio.uninvitedguests.GameSettings;
import com.qualityworkstudio.uninvitedguests.BasicLevelMenu;
import com.qualityworkstudio.uninvitedguests.Gun;
import com.qualityworkstudio.uninvitedguests.InteractionArea;
import com.qualityworkstudio.uninvitedguests.LevelMenuInteraction;
import com.qualityworkstudio.uninvitedguests.Map;
import com.qualityworkstudio.uninvitedguests.MapData;
import com.qualityworkstudio.uninvitedguests.MobilePlayerController;
import com.qualityworkstudio.uninvitedguests.MobilePlayerInterface;
import com.qualityworkstudio.uninvitedguests.Player;
import com.qualityworkstudio.uninvitedguests.ReloadWidget;
import com.qualityworkstudio.uninvitedguests.ScreenTransition;
import com.qualityworkstudio.uninvitedguests.Timer;

/**
 * The main screen of the game.
 *
 * @author Andrey Karanik, Bogdan Teryukhov
 */

public class MainScreen extends ScreenAdapter {

    private Game game;
    private GameSettings settings;
    private AssetManager assetManager;

    private SpriteBatch batch;
    private World world;
    private Viewport viewport;
    private Stage stage;
    private BasicLevelMenu levelMenu;
    private ScreenTransition screenTransition;
    private Timer gameTimer;
    private Label timerLabel;
    private Label helpLabel;

    private Player player;
    private Map map;
    private BasicDoor door;
    private InteractionArea levelMenuArea;

    private Box2DDebugRenderer debugRenderer;


    public MainScreen(Game game) {
        this.game = game;
        settings = game.getSettings();
        assetManager = game.getAssetManager();
        gameTimer = game.getGameTimer();

        viewport = new FitViewport(settings.getViewportSize(), settings.getViewportSize() * (
                (float)Gdx.graphics.getHeight() / Gdx.graphics.getWidth()));
        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);

        Label.LabelStyle labelStyle = new Label.LabelStyle(assetManager.<BitmapFont>get("level_complete_font.fnt"), Color.WHITE);
        timerLabel = new Label("", labelStyle);
        timerLabel.setAlignment(Align.center);
        timerLabel.setPosition(stage.getWidth() / 2f, stage.getHeight() - 64f, Align.center);
        stage.addActor(timerLabel);

        Label.LabelStyle style = new Label.LabelStyle(assetManager.<BitmapFont>get("font.fnt"), Color.WHITE);
        helpLabel = new Label("Complete all levels before the time runs out!", style);
        helpLabel.setAlignment(Align.center);
        helpLabel.setPosition(stage.getWidth() / 2f, 64f, Align.center);
        stage.addActor(helpLabel);

        screenTransition = new ScreenTransition(stage, assetManager);

        batch = new SpriteBatch();
        world = new World(new Vector2(0, 0), false);
        world.setContactListener(new BasicContactListener());
        map = new Map(new MapData("maps/main_map.json"), assetManager, world);
        door = new BasicDoor(world, assetManager);
        door.setPosition(0f, 16f);
        door.setType(BasicDoor.Type.GREEN);
        player = new Player(world, assetManager, settings.getCameraSize());
        if (settings.isMobileMode()) {
            MobilePlayerInterface playerInterface = new MobilePlayerInterface(stage, assetManager);
            player.setController(new MobilePlayerController(player, playerInterface.getMovementJoystick(), playerInterface.getRotationJoystick()));
            player.setPlayerInterface(playerInterface);
        } else {
            player.setController(new BasicPlayerController(player));
            player.setPlayerInterface(new BasicPlayerInterface());
        }
        player.setFixedCamera(true);
        player.setWeapon(new Gun(world, assetManager));
        player.setReloadWidget(new ReloadWidget(stage, assetManager));

        levelMenu = new BasicLevelMenu(stage, game);
        for (int i = 1; i < 4; i++) {
            if (game.isLevelComplete(i-1)) {
                levelMenu.addLevel("Completed", assetManager.<Texture>get("level" + i + "_image.png"));
            } else {
                levelMenu.addLevel("Level " + i, assetManager.<Texture>get("level" + i + "_image.png"));
            }
        }

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
        debugRenderer = new Box2DDebugRenderer();
    }

    @Override
    public void show() {
        player.getPlayerInterface().show();
        screenTransition.out(1f);
    }

    @Override
    public void render(float delta) {
        world.step(1 / 60f, 6, 2);
        player.update(delta);
        door.update(delta);
        stage.act(delta);
        gameTimer.update(delta);
        timerLabel.setText(String.valueOf((int)gameTimer.getCurrentTime()));
        if (game.isOver() || player.isDied()) {
            game.setScreen(new DefeatScreen(game));
        }
        if (game.isVictory()) {
            game.setScreen(new VictoryScreen(game));
        }

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
