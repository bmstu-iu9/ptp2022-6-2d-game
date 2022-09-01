package com.qualityworkstudio.uninvitedguests.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.qualityworkstudio.uninvitedguests.BasicClickListener;
import com.qualityworkstudio.uninvitedguests.Game;
import com.qualityworkstudio.uninvitedguests.GameSettings;
import com.qualityworkstudio.uninvitedguests.LoadingData;

public class SettingsScreen extends ScreenAdapter {

    private GameSettings settings;
    private AssetManager assetManager;

    private Viewport viewport;
    private Stage stage;
    private Image continueButton;
    private Image mobileModeToggle;

    public SettingsScreen(final Game game, final int screen) {
        settings = game.getSettings();
        assetManager = game.getAssetManager();

        if (Gdx.graphics.getWidth() - Gdx.graphics.getHeight() < 0) {
            viewport = new FitViewport(settings.getViewportSize() * (
                    (float) Gdx.graphics.getWidth() / Gdx.graphics.getHeight()), settings.getViewportSize());
        } else {
            viewport = new FitViewport(settings.getViewportSize(), settings.getViewportSize() * (
                    (float) Gdx.graphics.getHeight() / Gdx.graphics.getWidth()));
        }
        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);

        final Drawable image1 = new TextureRegionDrawable(assetManager.<Texture>get("mobile_mode_button.png"));
        final Drawable image2 = new TextureRegionDrawable(assetManager.<Texture>get("selected_mobile_mode_button.png"));
        mobileModeToggle = new Image(settings.isMobileMode() ? image2 : image1);
        mobileModeToggle.setSize(400f, 200f);
        mobileModeToggle.setOrigin(Align.center);
        mobileModeToggle.setPosition(stage.getWidth() / 2f, stage.getHeight() / 2f, Align.center);
        mobileModeToggle.addListener(new BasicClickListener(mobileModeToggle));
        mobileModeToggle.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                if (settings.isMobileMode()) {
                    mobileModeToggle.setDrawable(image1);
                } else {
                    mobileModeToggle.setDrawable(image2);
                }
                settings.setMobileMode(!settings.isMobileMode());
            }
        });
        stage.addActor(mobileModeToggle);

        Drawable drawable = new TextureRegionDrawable(game.getAssetManager().<Texture>get("continue_button.png"));
        continueButton = new Image(drawable);
        continueButton.setSize(400f, 200f);
        continueButton.setOrigin(Align.center);
        continueButton.setPosition(stage.getWidth() / 2f, stage.getHeight() / 2f - 150, Align.center);
        continueButton.addListener(new BasicClickListener(continueButton));
        continueButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                game.setScreen(new LoadingScreen( game, screen, "json/firstload.json"));
            }
        });
        stage.addActor(continueButton);
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
