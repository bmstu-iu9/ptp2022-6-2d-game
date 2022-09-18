package com.qualityworkstudio.uninvitedguests.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.qualityworkstudio.uninvitedguests.BasicClickListener;
import com.qualityworkstudio.uninvitedguests.BasicRaImage;
import com.qualityworkstudio.uninvitedguests.Game;
import com.qualityworkstudio.uninvitedguests.GameSettings;
import com.qualityworkstudio.uninvitedguests.MobileRaImage;
import com.qualityworkstudio.uninvitedguests.RaImage;

/**
 * @author Andrey Karanik
 */

public class RatioAdjustmentScreen extends ScreenAdapter {

    private GameSettings settings;
    private AssetManager assetManager;

    private Viewport viewport;
    private Stage stage;
    private RaImage raImage;
    private Image continueButton;

    private boolean ok;

    public RatioAdjustmentScreen(final Game game, final int screen) {
        settings = game.getSettings();
        assetManager = game.getAssetManager();

        viewport = new FitViewport(settings.getViewportSize(), settings.getViewportSize() * (
                (float) Gdx.graphics.getHeight() / Gdx.graphics.getWidth()));
        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);

        if (settings.isMobileMode()) {
            raImage = new MobileRaImage(stage, assetManager);
        } else {
            raImage = new BasicRaImage(stage, assetManager);
        }

        Drawable drawable = new TextureRegionDrawable(assetManager.<Texture>get("continue_button.png"));
        continueButton = new Image(drawable);
        continueButton.setSize(stage.getWidth() / 1.5f, stage.getWidth() / 3f);
        continueButton.setOrigin(Align.center);
        continueButton.setPosition(stage.getWidth() / 2f, stage.getHeight() / 2f, Align.center);
        continueButton.setScale(0f);
        continueButton.addListener(new BasicClickListener(continueButton));
        continueButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                game.setScreen(screen);
            }
        });
        stage.addActor(continueButton);
    }

    @Override
    public void show() {
        raImage.show();
    }

    @Override
    public void render(float delta) {
        stage.act(delta);

        if (Gdx.graphics.getWidth() - Gdx.graphics.getHeight() >= 0 && !ok) {
            continueButton.addAction(Actions.sequence(Actions.scaleBy(0f, 0f, 1f), Actions.scaleTo(1f, 1f, 0.5f, Interpolation.swingOut)));
            raImage.hide();
            ok = true;
        } else if (Gdx.graphics.getWidth() - Gdx.graphics.getHeight() < 0 && ok) {
            continueButton.clearActions();
            continueButton.setScale(0f);
            raImage.show();
            ok = false;
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
