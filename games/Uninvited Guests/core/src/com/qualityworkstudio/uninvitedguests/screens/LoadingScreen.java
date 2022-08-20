package com.qualityworkstudio.uninvitedguests.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
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
import com.qualityworkstudio.uninvitedguests.GameSettings;
import com.qualityworkstudio.uninvitedguests.LoadingData;
import com.qualityworkstudio.uninvitedguests.LoadingImage;

public class LoadingScreen extends ScreenAdapter {

    private Game game;
    private AssetManager assetManager;
    private GameSettings settings;

    private Viewport viewport;
    private Stage stage;
    private LoadingImage loadingImage;
    private Container<Label> labelContainer;
    private Label label;

    private float minDelay;
    private float currentTime;
    private int step;

    public LoadingScreen(Game game, AssetManager assetManager, GameSettings settings, LoadingData loadingData) {
        this.game = game;
        this.assetManager = assetManager;
        this.settings = settings;

        viewport = new FitViewport(settings.getViewportSize(), settings.getViewportSize() * (
                (float) Gdx.graphics.getHeight() / Gdx.graphics.getWidth()));
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

        for (String fileName : loadingData.getUnloadFiles()) {
            assetManager.unload(fileName);
        }

        for (String fileName : loadingData.getTextureFiles()) {
            assetManager.load(fileName, Texture.class);
        }

        for (String fileName : loadingData.getBitmapFontFiles()) {
            assetManager.load(fileName, BitmapFont.class);
        }

        for (String fileName : loadingData.getSoundFiles()) {
            assetManager.load(fileName, Sound.class);
        }

        for (String fileName : loadingData.getMusicFiles()) {
            assetManager.load(fileName, Sound.class);
        }

        minDelay = 3f;
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
                game.setScreen(new MainScreen(game, assetManager, settings));
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