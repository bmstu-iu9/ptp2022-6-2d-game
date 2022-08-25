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

public class MobileRaImage implements RaImage {

    private Stage stage;
    private Image image;
    private Image raImage;

    public MobileRaImage(Stage stage, AssetManager assetManager) {
        this.stage = stage;
        Drawable drawable1 = new TextureRegionDrawable(assetManager.<Texture>get("ra_mobile_image.png"));
        Drawable drawable2 = new TextureRegionDrawable(assetManager.<Texture>get("ra_image.png"));
        image = new Image(drawable1);
        image.setOrigin(Align.center);
        image.setPosition(stage.getWidth() / 2f, stage.getHeight() / 2f, Align.center);
        image.setScale(0f);
        stage.addActor(image);
        raImage = new Image(drawable2);
        raImage.setSize(768f, 768f);
        raImage.setOrigin(Align.center);
        raImage.setPosition(stage.getWidth() / 2f, stage.getHeight() / 2f, Align.center);
        raImage.setScale(0f);
        stage.addActor(raImage);
    }

    @Override
    public void show() {
        image.clearActions();
        raImage.clearActions();
        image.setPosition(stage.getWidth() / 2f, stage.getHeight() / 2f, Align.center);
        image.setScale(0f);
        raImage.setPosition(stage.getWidth() / 2f, stage.getHeight() / 2f, Align.center);
        raImage.setScale(0f);
        image.addAction(Actions.forever(Actions.sequence(Actions.scaleTo(1.2f, 1.2f, 1f, Interpolation.swingOut),
                Actions.scaleTo(1f, 1f, 1f, Interpolation.swingIn),
                Actions.rotateTo(90f, 1f, Interpolation.pow2),
                Actions.scaleTo(1f, 1f, 1f),
                Actions.scaleTo(0f, 0f, 1f, Interpolation.swingIn),
                Actions.rotateTo(0f))));
        raImage.addAction(Actions.forever(Actions.sequence(Actions.scaleTo(1.2f, 1.2f, 1f, Interpolation.swingOut),
                Actions.parallel(Actions.scaleTo(1f, 1f, 1f, Interpolation.swingIn),
                Actions.alpha(0f, 1f, Interpolation.pow2)),
                Actions.rotateTo(0f, 1f),
                Actions.alpha(1f, 1f, Interpolation.pow2),
                Actions.scaleTo(0f, 0f, 1f, Interpolation.swingIn))));
    }

    @Override
    public void hide() {
        image.clearActions();
        raImage.clearActions();
        image.addAction(Actions.scaleTo(0f, 0f, 1f, Interpolation.swingIn));
        raImage.addAction(Actions.scaleTo(0f, 0f, 1f, Interpolation.swingIn));
    }
}
