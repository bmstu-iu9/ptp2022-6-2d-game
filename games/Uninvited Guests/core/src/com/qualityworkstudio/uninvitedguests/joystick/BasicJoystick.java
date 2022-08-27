package com.qualityworkstudio.uninvitedguests.joystick;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;

/**
 * @author Bogdan Teryukhov, Andrey Karanik
 */

public class BasicJoystick implements Joystick {

    private Image background;
    private Image stick;

    private Vector2 joystickPosition;
    private Vector2 direction;
    private float radius;
    private boolean isTouched;

    public BasicJoystick(Stage stage, AssetManager assetManager, String bgTextureName) {
        radius = 220f;

        Drawable backgroundDrawable = new TextureRegionDrawable(assetManager.<Texture>get(bgTextureName));
        Drawable stickDrawable = new TextureRegionDrawable(assetManager.<Texture>get("joystick_stick.png"));
        background = new Image(backgroundDrawable);
        background.setOrigin(background.getWidth() / 2f, background.getHeight() / 2f);
        background.setScale(0);

        stick = new Image(stickDrawable);
        stick.setOrigin(stick.getWidth() / 2f, stick.getHeight() / 2f);
        stick.setScale(0);
        stick.setTouchable(Touchable.disabled);

        stage.addActor(background);
        stage.addActor(stick);

        joystickPosition = new Vector2(background.getWidth() / 2f, background.getHeight() / 2f);
        stick.setPosition(joystickPosition.x, joystickPosition.y, Align.center);
        direction = new Vector2();

        background.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return super.touchDown(event, x, y, pointer, button);
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                isTouched = false;
                direction.set(0,0);
                stick.addAction(Actions.moveTo(joystickPosition.x - stick.getWidth() / 2f,
                        joystickPosition.y - stick.getHeight() / 2f, 0.25f, Interpolation.swingOut));
            }

            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                super.touchDragged(event, x, y, pointer);

                isTouched = true;
                float dx = x - background.getWidth() / 2f;
                float dy = y - background.getHeight() / 2f;
                float dist = (float) Math.sqrt(dx*dx + dy*dy);

                direction.set(dx / dist, dy / dist);

                if(dist > radius){
                    stick.setPosition(direction.x*radius + joystickPosition.x, direction.y * radius + joystickPosition.y, Align.center);
                } else {
                    stick.setPosition(dx + joystickPosition.x, dy + joystickPosition.y, Align.center);
                }
            }
        });
    }

    @Override
    public void show() {
        background.addAction(Actions.scaleTo(1f, 1f, 0.25f, Interpolation.swingOut));
        stick.addAction(Actions.scaleTo(1f, 1f, 0.25f, Interpolation.swingOut));
    }

    @Override
    public void hide() {
        background.addAction(Actions.scaleTo(0f, 0f, 0.25f, Interpolation.swingIn));
        stick.addAction(Actions.scaleTo(0f, 0f, 0.25f, Interpolation.swingIn));
    }

    @Override
    public Vector2 getDirection() {
        return direction;
    }

    @Override
    public double getAngle() {
        return (float)(Math.toDegrees(Math.atan2(direction.y, direction.x)));
    }

    @Override
    public void setPosition(Vector2 position) {
        joystickPosition.set(position);
        background.setPosition(position.x, position.y, Align.center);
        stick.setPosition(position.x, position.y, Align.center);
    }

    @Override
    public Vector2 getPosition() {
        return joystickPosition;
    }

    @Override
    public void setSize(Vector2 size) {
        background.setSize(size.x, size.y);
        stick.setSize(size.x, size.y);
    }

    @Override
    public Vector2 getSize() {
        return new Vector2(background.getWidth(), background.getHeight());
    }

    @Override
    public void setRadius(float radius) {
        this.radius = radius;
    }

    @Override
    public float getRadius() {
        return radius;
    }

    @Override
    public boolean isTouched() {
        return isTouched;
    }
}

