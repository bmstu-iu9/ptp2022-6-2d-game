package com.qualityworkstudio.uninvitedguests.screens;

import com.badlogic.gdx.Game;
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
import com.qualityworkstudio.uninvitedguests.GameSettings;

public class RatioAdjustmentScreen extends ScreenAdapter {

    private Viewport viewport;
    private Stage stage;
    private RaImage raImage;
    private Image continueButton;

    private boolean ok;

    public RatioAdjustmentScreen(final int screen, final Game game, final AssetManager assetManager, final GameSettings settings) {

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
        continueButton.setOrigin(Align.center);
        continueButton.setPosition(stage.getWidth() / 2f, stage.getHeight() / 2f, Align.center);
        continueButton.setScale(0f);
        continueButton.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                continueButton.addAction(Actions.scaleTo(0.9f, 0.9f, 0.1f, Interpolation.swingIn));
                continueButton.setColor(0.75f, 0.75f, 0.75f, 1f);
                return super.touchDown(event, x, y, pointer, button);
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                continueButton.addAction(Actions.scaleTo(1f, 1f, 0.1f, Interpolation.swingOut));
                continueButton.setColor(1f, 1f, 1f, 1f);
            }
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                game.setScreen(Screens.getScreen(screen, game, assetManager, settings));

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
