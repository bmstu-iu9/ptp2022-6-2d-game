package com.qualityworkstudio.uninvitedguests.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
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

/**
 * @author Andrey Karanik
 */

public class DefeatScreen extends ScreenAdapter {

    private Game game;
    private GameSettings settings;
    private AssetManager assetManager;
    private Label label;

    private Viewport viewport;
    private Stage stage;
    private Image continueButton;

    public DefeatScreen(Game gm) {
        this.game = gm;
        settings = game.getSettings();
        assetManager = game.getAssetManager();

        viewport = new FitViewport(settings.getViewportSize(), settings.getViewportSize() * (
                (float)Gdx.graphics.getHeight() / Gdx.graphics.getWidth()));
        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);

        Label.LabelStyle labelStyle = new Label.LabelStyle(assetManager.<BitmapFont>get("level_complete_font.fnt"), Color.WHITE);
        label = new Label("You lost! Try again.", labelStyle);
        label.setAlignment(Align.center);
        label.setPosition(stage.getWidth() / 2f, stage.getHeight() / 2f, Align.center);
        stage.addActor(label);

        Drawable drawable = new TextureRegionDrawable(assetManager.<Texture>get("continue_button.png"));
        continueButton = new Image(drawable);
        continueButton.setSize(512f, 256f);
        continueButton.setOrigin(Align.center);
        continueButton.setPosition(stage.getWidth() / 2f, stage.getHeight() / 2f - 128f, Align.center);
        continueButton.setScale(0f);
        continueButton.addListener(new BasicClickListener(continueButton));
        continueButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                game.start();
            }
        });
        stage.addActor(continueButton);
    }

    @Override
    public void show() {
        continueButton.addAction(Actions.scaleTo(1f, 1f, 0.25f, Interpolation.swingOut));
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

