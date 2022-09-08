package com.qualityworkstudio.uninvitedguests;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;

/**
 * @author Andrey Karanik
 */

public class ReloadWidget {

    private Label label;
    private Container<Label> labelContainer;
    private Image image;
    private float maxImageScale = 20f;

    public ReloadWidget(Stage stage, AssetManager assetManager) {
        image = new Image(new TextureRegionDrawable(assetManager.<Texture>get("white_square.png")));
        image.setSize(10f, 10f);
        image.setOrigin(Align.center);
        image.setScale(0f, 1f);
        image.setPosition(stage.getWidth() / 2f, 224f, Align.center);

        Label.LabelStyle labelStyle = new Label.LabelStyle(assetManager.<BitmapFont>get("font.fnt"), Color.WHITE);
        label = new Label("Reloading...", labelStyle);
        label.setAlignment(Align.center);

        labelContainer = new Container<>(label);
        labelContainer.setTransform(true);
        labelContainer.size(256, 256);
        labelContainer.setOrigin(labelContainer.getWidth() / 2, labelContainer.getHeight() / 2);
        labelContainer.setPosition(stage.getWidth() / 2f, 256f, Align.center);
        labelContainer.setScale(0f);

        stage.addActor(image);
        stage.addActor(labelContainer);
    }

    public void show(Weapon weapon) {
        image.setScale(0f, 1f);
        labelContainer.setScale(1f);
        image.addAction(Actions.sequence(Actions.scaleTo(maxImageScale, 1f, weapon.getReloadTime()),
                Actions.scaleTo(0f, 0f, 0.2f, Interpolation.swingIn)));
        labelContainer.addAction(Actions.sequence(Actions.delay(weapon.getReloadTime()), Actions.scaleTo(0f, 0f, 0.2f, Interpolation.swingIn)));
    }
}
