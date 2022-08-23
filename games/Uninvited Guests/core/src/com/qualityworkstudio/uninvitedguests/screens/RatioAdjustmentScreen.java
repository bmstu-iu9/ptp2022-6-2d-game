package com.qualityworkstudio.uninvitedguests.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.qualityworkstudio.uninvitedguests.GameSettings;
import com.qualityworkstudio.uninvitedguests.Timer;

public class RatioAdjustmentScreen extends ScreenAdapter {
    private Game game;
    private AssetManager assetManager;
    private GameSettings settings;
    private int screen;

    private Viewport viewport;
    private Stage stage;

    private Image image;
    private Timer timer;

    public RatioAdjustmentScreen(final int screen, final Game game, final AssetManager assetManager, final GameSettings settings) {
        this.game = game;
        this.assetManager = assetManager;
        this.settings = settings;
        this.screen = screen;

        viewport = new FitViewport(settings.getViewportSize(), settings.getViewportSize() * (
                (float) Gdx.graphics.getHeight() / Gdx.graphics.getWidth()));
        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);

        Drawable drawable = new TextureRegionDrawable(assetManager.<Texture>get("ratio_adjustment_image.png"));
        image = new Image(drawable);
        image.setOrigin(Align.center);
        image.setPosition(stage.getWidth() / 2f, stage.getHeight() / 2f, Align.center);
        image.addAction(Actions.forever(Actions.sequence(Actions.scaleTo(1.2f, 1.2f, 1f, Interpolation.swingOut),
                Actions.scaleTo(1f, 1f, 1f, Interpolation.swingIn))));
        stage.addActor(image);

        timer = new Timer(new Timer.Task() {
            @Override
            public void doTask() {
                game.setScreen(Screens.getScreen(screen, game, assetManager, settings));
            }
        });
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        stage.act(delta);
        timer.update(delta);

        if (Gdx.graphics.getWidth() - Gdx.graphics.getHeight() >= 0 && !timer.isActive()) {
            timer.start(2f);
            image.addAction(Actions.scaleTo(0f, 0f, 1f, Interpolation.swingIn));
        }

        ScreenUtils.clear(Color.BLACK);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
