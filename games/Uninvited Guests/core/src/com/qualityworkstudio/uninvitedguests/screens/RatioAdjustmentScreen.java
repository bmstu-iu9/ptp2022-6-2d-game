package com.qualityworkstudio.uninvitedguests.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.qualityworkstudio.uninvitedguests.GameSettings;

public class RatioAdjustmentScreen extends ScreenAdapter {
    private Game game;
    private AssetManager assetManager;
    private GameSettings settings;

    private Viewport viewport;
    private Stage stage;

    public RatioAdjustmentScreen(Game game, AssetManager assetManager, GameSettings settings) {
        this.game = game;
        this.assetManager = assetManager;
        this.settings = settings;

        viewport = new FitViewport(settings.getViewportSize(), settings.getViewportSize() * (
                (float) Gdx.graphics.getHeight() / Gdx.graphics.getWidth()));
        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        stage.act(delta);

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
