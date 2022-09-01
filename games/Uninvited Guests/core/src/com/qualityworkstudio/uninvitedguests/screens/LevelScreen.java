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
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.qualityworkstudio.uninvitedguests.BasicContactListener;
import com.qualityworkstudio.uninvitedguests.BasicPlayerController;
import com.qualityworkstudio.uninvitedguests.BasicPlayerInterface;
import com.qualityworkstudio.uninvitedguests.Game;
import com.qualityworkstudio.uninvitedguests.GameSettings;
import com.qualityworkstudio.uninvitedguests.GroupIndices;
import com.qualityworkstudio.uninvitedguests.Interaction;
import com.qualityworkstudio.uninvitedguests.InteractionArea;
import com.qualityworkstudio.uninvitedguests.MobilePlayerController;
import com.qualityworkstudio.uninvitedguests.MobilePlayerInterface;
import com.qualityworkstudio.uninvitedguests.Player;
import com.qualityworkstudio.uninvitedguests.ScreenTransition;
import com.qualityworkstudio.uninvitedguests.Timer;

public class LevelScreen extends ScreenAdapter {

    protected Game game;
    protected AssetManager assetManager;
    protected GameSettings settings;

    protected SpriteBatch batch;
    protected World world;
    protected Viewport viewport;
    protected Stage stage;
    protected Label levelLabel;
    protected Container<Label> levelLabelContainer;
    protected ScreenTransition screenTransition;

    protected Player player;
    protected InteractionArea finishArea;
    protected Timer completeTimer;

    public LevelScreen(Game game) {
        this.game = game;
        this.assetManager = game.getAssetManager();
        this.settings = game.getSettings();

        viewport = new FitViewport(settings.getViewportSize(), settings.getViewportSize() * (
                (float) Gdx.graphics.getHeight() / Gdx.graphics.getWidth()));
        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);

        screenTransition = new ScreenTransition(stage, assetManager);

        Label.LabelStyle labelStyle = new Label.LabelStyle(assetManager.<BitmapFont>get("level_complete_font.fnt"), Color.WHITE);
        levelLabel = new Label("Level Complete", labelStyle);
        levelLabel.setAlignment(Align.center);

        levelLabelContainer = new Container<>(levelLabel);
        levelLabelContainer.setTransform(true);
        levelLabelContainer.size(256, 256);
        levelLabelContainer.setOrigin(levelLabelContainer.getWidth() / 2, levelLabelContainer.getHeight() / 2);
        levelLabelContainer.setPosition(stage.getWidth() / 2f, stage.getHeight() / 2f, Align.center);
        levelLabelContainer.setScale(0f);

        stage.addActor(levelLabelContainer);

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

        completeTimer = new Timer();

        finishArea = new InteractionArea(world, new Vector2(6f, 1f), new Interaction() {
            @Override
            public void interactIn(int groupIndex) {
                if (groupIndex != GroupIndices.PLAYER) {
                    return;
                }
                player.setFixed(true);
                player.getPlayerInterface().hide();
                screenTransition.in(1f);
                levelLabelContainer.addAction(Actions.sequence(Actions.scaleTo(1f, 1f, 0.5f, Interpolation.swingOut),
                        Actions.delay(1f), Actions.scaleTo(0f, 0f, 0.5f, Interpolation.swingIn)));
                completeTimer.start(2f);
            }

            @Override
            public void interactOut(int groupIndex) {
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
