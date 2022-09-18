package com.qualityworkstudio.uninvitedguests;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;

/**
 * @author Andrey Karanik
 */

public class LoadingImage {

    private Image loadingImage1;
    private Image loadingImage2;

    public LoadingImage(Stage stage, AssetManager assetManager) {
        Drawable drawable1 = new TextureRegionDrawable(assetManager.<Texture>get("loading_image1.png"));
        Drawable drawable2 = new TextureRegionDrawable(assetManager.<Texture>get("loading_image2.png"));
        loadingImage1 = new Image(drawable1);
        loadingImage1.setOrigin(loadingImage1.getWidth() / 2f, loadingImage1.getHeight() / 2f);
        loadingImage1.setScale(0);
        loadingImage2 = new Image(drawable2);
        loadingImage2.setOrigin(loadingImage2.getWidth() / 2f, loadingImage2.getHeight() / 2f);
        loadingImage2.setScale(0);
        setPosition(stage.getWidth() / 2f, stage.getHeight() / 2f);

        stage.addActor(loadingImage1);
        stage.addActor(loadingImage2);
    }

    public void show() {
        loadingImage1.addAction(Actions.sequence(Actions.scaleTo(1f, 1f, 0.25f, Interpolation.swingOut),
                Actions.forever(Actions.rotateBy(360f, 1f, Interpolation.swingOut))));
        loadingImage2.addAction(Actions.sequence(Actions.scaleTo(1f, 1f, 0.25f, Interpolation.swingOut),
                Actions.forever(Actions.rotateBy(-360f, 2f, Interpolation.swingOut))));
    }

    public void hide() {
        loadingImage1.clearActions();
        loadingImage2.clearActions();
        loadingImage1.addAction(Actions.scaleTo(0f, 0f, 0.25f, Interpolation.swingIn));
        loadingImage2.addAction(Actions.scaleTo(0f, 0f, 0.25f, Interpolation.swingIn));
    }

    public void setPosition(float x, float y) {
        loadingImage1.setPosition(x, y, Align.center);
        loadingImage2.setPosition(x, y, Align.center);
    }

    public void setPosition(Vector2 position) {
        loadingImage1.setPosition(position.x, position.y, Align.center);
        loadingImage2.setPosition(position.x, position.y, Align.center);
    }

    public float getX() {
        return loadingImage1.getX();
    }

    public float getY() {
        return loadingImage1.getY();
    }
}
