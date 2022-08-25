package com.qualityworkstudio.uninvitedguests;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;

/**
 * @author Andrey Karanik
 * @see RaImage
 */

public class BasicRaImage implements RaImage {

    private Stage stage;
    private Image vImage;
    private Image hImage;

    public BasicRaImage(Stage stage, AssetManager assetManager) {
        this.stage = stage;

        Drawable drawable1 = new TextureRegionDrawable(assetManager.<Texture>get("ra_v_window_image.png"));
        Drawable drawable2 = new TextureRegionDrawable(assetManager.<Texture>get("ra_h_window_image.png"));

        vImage = new Image(drawable1);
        vImage.setSize(768f, 768f);
        vImage.setOrigin(Align.center);
        vImage.setPosition(stage.getWidth() / 2f, stage.getHeight() / 2f, Align.center);
        vImage.setScale(0f);
        stage.addActor(vImage);

        hImage = new Image(drawable2);
        hImage.setSize(768f, 768f);
        hImage.setOrigin(Align.center);
        hImage.setPosition(stage.getWidth() / 2f, stage.getHeight() / 2f, Align.center);
        hImage.setScale(0f);
        stage.addActor(hImage);
    }

    @Override
    public void show() {
        vImage.clearActions();
        hImage.clearActions();

        vImage.setPosition(stage.getWidth() / 2f, stage.getHeight() / 2f, Align.center);
        vImage.setScale(0f);
        hImage.setPosition(stage.getWidth() / 2f, stage.getHeight() / 2f, Align.center);
        hImage.setScale(0f);

        vImage.addAction(Actions.forever(Actions.sequence(Actions.scaleTo(1.1f, 1.1f, 1f, Interpolation.swingOut),
                Actions.scaleTo(1f, 1f, 1f, Interpolation.swingIn),
                Actions.moveBy(-580f, 0f, 1f, Interpolation.swingOut),
                Actions.moveBy(0f, 0f, 1f),
                Actions.scaleTo(0f, 0f, 1f, Interpolation.swingIn),
                Actions.moveBy(580f, 0f, 2f))));
        hImage.addAction(Actions.forever(Actions.sequence(Actions.moveBy(380f, 0f, 3f),
                Actions.scaleTo(1.1f, 1.1f, 1f, Interpolation.swingOut),
                Actions.moveBy(0f, 0f, 1f),
                Actions.moveBy(-380f, 0f, 1f, Interpolation.swingOut),
                Actions.scaleTo(0f, 0f, 1f, Interpolation.swingIn))));
    }

    @Override
    public void hide() {
        vImage.clearActions();
        hImage.clearActions();

        vImage.addAction(Actions.scaleTo(0f, 0f, 1f, Interpolation.swingIn));
        hImage.addAction(Actions.scaleTo(0f, 0f, 1f, Interpolation.swingIn));
    }
}
