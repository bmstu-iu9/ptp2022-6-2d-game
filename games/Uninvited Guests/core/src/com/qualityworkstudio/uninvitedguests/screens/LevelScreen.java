package com.qualityworkstudio.uninvitedguests.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.qualityworkstudio.uninvitedguests.BasicContactListener;
import com.qualityworkstudio.uninvitedguests.BasicPlayerController;
import com.qualityworkstudio.uninvitedguests.BasicPlayerInterface;
import com.qualityworkstudio.uninvitedguests.Game;
import com.qualityworkstudio.uninvitedguests.GameObject;
import com.qualityworkstudio.uninvitedguests.GameSettings;
import com.qualityworkstudio.uninvitedguests.GroupIndices;
import com.qualityworkstudio.uninvitedguests.Gun;
import com.qualityworkstudio.uninvitedguests.Interaction;
import com.qualityworkstudio.uninvitedguests.InteractionArea;
import com.qualityworkstudio.uninvitedguests.MobilePlayerController;
import com.qualityworkstudio.uninvitedguests.MobilePlayerInterface;
import com.qualityworkstudio.uninvitedguests.Player;
import com.qualityworkstudio.uninvitedguests.ReloadWidget;
import com.qualityworkstudio.uninvitedguests.ScreenTransition;
import com.qualityworkstudio.uninvitedguests.Timer;

import java.util.ArrayList;

/**
 * @author Andrey Karanik
 */

public class LevelScreen extends ScreenAdapter {

    protected Game game;
    protected AssetManager assetManager;
    protected GameSettings settings;

    protected SpriteBatch batch;
    protected World world;
    protected Viewport viewport;
    protected Stage stage;
    protected Table completeTable;
    protected ScreenTransition screenTransition;
    protected Timer gameTimer;
    protected Label timerLabel;

    protected Player player;
    protected InteractionArea finishArea;
    protected Timer completeTimer;

    public LevelScreen(Game game) {
        this.game = game;
        this.assetManager = game.getAssetManager();
        this.settings = game.getSettings();
        gameTimer = game.getGameTimer();

        viewport = new FitViewport(settings.getViewportSize(), settings.getViewportSize() * (
                (float) Gdx.graphics.getHeight() / Gdx.graphics.getWidth()));
        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);

        Label.LabelStyle style = new Label.LabelStyle(assetManager.<BitmapFont>get("level_complete_font.fnt"), Color.WHITE);
        timerLabel = new Label("", style);
        timerLabel.setAlignment(Align.center);
        timerLabel.setPosition(stage.getWidth() / 2f, stage.getHeight() - 64f, Align.center);
        stage.addActor(timerLabel);

        screenTransition = new ScreenTransition(stage, assetManager);

        completeTable = new Table();
        completeTable.setSize(stage.getWidth(), stage.getHeight());
        completeTable.setOrigin(Align.center);
        completeTable.setPosition(stage.getWidth() / 2f, stage.getHeight() / 2f, Align.center);
        completeTable.setTransform(true);
        completeTable.setScale(0f, 1f);

        Image image1 = new Image(new TextureRegionDrawable(assetManager.<Texture>get("white_square.png")));
        image1.setSize(stage.getWidth(), 256f);
        image1.setOrigin(Align.center);
        image1.setPosition(stage.getWidth() / 2f, stage.getHeight() / 2f, Align.center);
        image1.setColor(new Color(0f, 1f, 200f / 255f, 0.1f));
        Image image2 = new Image(new TextureRegionDrawable(assetManager.<Texture>get("white_square.png")));
        image2.setSize(stage.getWidth(), 192f);
        image2.setOrigin(Align.center);
        image2.setPosition(stage.getWidth() / 2f, stage.getHeight() / 2f, Align.center);
        image2.setColor(new Color(0f, 1f, 200f / 255f, 0.1f));

        completeTable.addActor(image1);
        completeTable.addActor(image2);

        Label.LabelStyle labelStyle = new Label.LabelStyle(assetManager.<BitmapFont>get("level_complete_font.fnt"), Color.WHITE);
        Label levelLabel = new Label("Level Complete", labelStyle);
        levelLabel.setAlignment(Align.center);

        Container<Label> levelLabelContainer = new Container<>(levelLabel);
        levelLabelContainer.setTransform(true);
        levelLabelContainer.size(256, 256);
        levelLabelContainer.setOrigin(levelLabelContainer.getWidth() / 2, levelLabelContainer.getHeight() / 2);
        levelLabelContainer.setPosition(stage.getWidth() / 2f, stage.getHeight() / 2f, Align.center);

        completeTable.addActor(levelLabelContainer);
        stage.addActor(completeTable);

        batch = new SpriteBatch();
        world = new World(new Vector2(0, 0), false);
        world.setContactListener(new BasicContactListener());
        player = new Player(world, assetManager, settings.getCameraSize());
        if (settings.isMobileMode()) {
            MobilePlayerInterface playerInterface = new MobilePlayerInterface(stage, assetManager);
            player.setController(new MobilePlayerController(player, playerInterface.getMovementJoystick(), playerInterface.getRotationJoystick()));
            player.setPlayerInterface(playerInterface);
        } else {
            player.setController(new BasicPlayerController(player));
            player.setPlayerInterface(new BasicPlayerInterface());
        }
        player.setWeapon(new Gun(world, assetManager));
        player.setReloadWidget(new ReloadWidget(stage, assetManager));

        completeTimer = new Timer();

        finishArea = new InteractionArea(world, new Vector2(6f, 1f), new Interaction() {
            @Override
            public void interactIn(GameObject object) {
                if (object.getGroupIndex() != GroupIndices.PLAYER) {
                    return;
                }
                player.setFixed(true);
                player.getPlayerInterface().hide();
                screenTransition.in(1f);
                completeTable.addAction(Actions.sequence(Actions.scaleTo(1f, 1f, 0.5f, Interpolation.swingOut),
                        Actions.delay(1f), Actions.scaleTo(1f, 0f, 0.5f, Interpolation.swingIn)));
                completeTimer.start(2f);
            }

            @Override
            public void interactOut(GameObject object) {
            }

        });
    }

    @Override
    public void show() {
        player.getPlayerInterface().show();
        screenTransition.out(1f);
    }

    @Override
    public void render(float delta) {
        world.step(1 / 60f, 6, 2);
        stage.act(delta);
        completeTimer.update(delta);
        gameTimer.update(delta);
        timerLabel.setText(String.valueOf((int)gameTimer.getCurrentTime()));
        if (game.isOver()) {
            game.setScreen(new DefeatScreen(game));
        }
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
