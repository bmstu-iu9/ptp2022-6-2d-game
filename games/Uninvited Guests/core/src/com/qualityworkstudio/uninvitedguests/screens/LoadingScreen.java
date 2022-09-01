package com.qualityworkstudio.uninvitedguests.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.qualityworkstudio.uninvitedguests.Game;
import com.qualityworkstudio.uninvitedguests.GameSettings;
import com.qualityworkstudio.uninvitedguests.Loader;
import com.qualityworkstudio.uninvitedguests.LoadingImage;

/**
 * @author Andrey Karanik
 */

public class LoadingScreen extends ScreenAdapter {

    private Game game;
    private GameSettings settings;
    private AssetManager assetManager;
    private int screen;

    private Viewport viewport;
    private Stage stage;
    private LoadingImage loadingImage;
    private Container<Label> labelContainer;
    private Label label;

    private float minDelay = 3f;
    private float currentTime;
    private int step;

    public LoadingScreen(Game game, int screen, String fileName) {
        this.game = game;
        this.assetManager = game.getAssetManager();
        this.settings = game.getSettings();
        this.screen = screen;

        if (Gdx.graphics.getWidth() - Gdx.graphics.getHeight() < 0) {
            viewport = new FitViewport(settings.getViewportSize() * (
                    (float) Gdx.graphics.getWidth() / Gdx.graphics.getHeight()), settings.getViewportSize());
        } else {
            viewport = new FitViewport(settings.getViewportSize(), settings.getViewportSize() * (
                    (float) Gdx.graphics.getHeight() / Gdx.graphics.getWidth()));
        }
        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);

        loadingImage = new LoadingImage(stage, assetManager);

        Label.LabelStyle labelStyle = new Label.LabelStyle(assetManager.<BitmapFont>get("font.fnt"), Color.WHITE);
        label = new Label("Loading...", labelStyle);
        label.setAlignment(Align.center);

        labelContainer = new Container<>(label);
        labelContainer.setTransform(true);
        labelContainer.size(256, 256);
        labelContainer.setOrigin(labelContainer.getWidth() / 2, labelContainer.getHeight() / 2);
        labelContainer.setPosition(stage.getWidth() / 2f, stage.getHeight() / 2f - 160f, Align.center);
        labelContainer.setScale(0f);

        stage.addActor(labelContainer);

        Loader loader = game.getLoader();
        if (fileName != null) {
            loader.add(fileName);
        }
        loader.unload();
        assetManager.finishLoading();
        loader.load();
    }

    @Override
    public void show() {
        loadingImage.show();
        labelContainer.addAction(Actions.scaleTo(1f, 1f, 1f, Interpolation.swingOut));
    }

    @Override
    public void render(float delta) {
        stage.act(delta);

        switch (step) {
            case 0:
                calculateTime(delta);
                break;
            case 1:
                if (assetManager.update()) {
                    loadingImage.hide();
                    labelContainer.addAction(Actions.scaleTo(0f, 0f, 0.25f, Interpolation.swingIn));
                    minDelay = 0.5f;
                    currentTime = 0f;
                    step++;
                }
                break;
            case 2:
                calculateTime(delta);
                break;
            case 3:
                game.setScreen(screen);
                step++;
                break;
        }

        ScreenUtils.clear(Color.BLACK);
        stage.draw();
    }

    private void calculateTime(float delta) {
        currentTime += delta;
        if (currentTime >= minDelay) {
            step++;
        }
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
