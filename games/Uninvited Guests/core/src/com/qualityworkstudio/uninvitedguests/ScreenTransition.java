package com.qualityworkstudio.uninvitedguests;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * @author Andrey Karanik
 */

public class ScreenTransition {

    private Image image;

    public ScreenTransition(Stage stage, AssetManager assetManager) {
        image = new Image(new TextureRegionDrawable(assetManager.<Texture>get("white_square.png")));
        image.setSize(stage.getWidth(), stage.getHeight());
        image.setColor(0f, 0f, 0f, 1f);
        image.setTouchable(Touchable.disabled);
        stage.addActor(image);
    }

    public void in(float duration) {
        image.addAction(Actions.fadeIn(duration));
    }

    public void out(float duration) {
        image.addAction(Actions.fadeOut(duration));
    }
}
