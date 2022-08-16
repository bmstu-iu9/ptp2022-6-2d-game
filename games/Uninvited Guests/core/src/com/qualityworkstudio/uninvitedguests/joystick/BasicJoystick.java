package com.qualityworkstudio.uninvitedguests.joystick;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;

import java.awt.Graphics;

/**
 * @author Bogdan Teryukhov
 */

public class BasicJoystick implements Joystick{
    private Image background;
    private Image stick;
    private Vector2 joystickPosition;
    private Vector2 direction;
    private float radius;

    public BasicJoystick(final Stage stage, AssetManager assetManager){
        Drawable backgroundDrawable = new TextureRegionDrawable(assetManager.<Texture>get("joystick_bg.png"));
        Drawable stickDrawable = new TextureRegionDrawable(assetManager.<Texture>get("joystick_stick.png"));
        background = new Image(backgroundDrawable);
        background.setScale(0);

        stick = new Image(stickDrawable);
        stick.setScale(0);
        stick.setTouchable(Touchable.disabled);

        stage.addActor(background);
        stage.addActor(stick);


        joystickPosition = new Vector2(background.getWidth()/2, background.getHeight()/2);
        stick.setPosition(joystickPosition.x,joystickPosition.y,Align.center);
        direction = new Vector2();

        background.addListener(new ClickListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                stick.addAction(Actions.moveTo(joystickPosition.x - stick.getWidth()/2f,
                        joystickPosition.y - stick.getHeight()/2f, 1f, Interpolation.swingOut));
            }

            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                super.touchDragged(event, x, y, pointer);

                float dx = x - joystickPosition.x;
                float dy = y - joystickPosition.y;
                float dist = (float) Math.sqrt(dx*dx + dy*dy);

                direction.set(dx / dist, dy / dist);

                if(dist > radius){
                    stick.setPosition(direction.x*radius + joystickPosition.x, direction.y * radius + joystickPosition.y,Align.center);
                }else{
                    stick.setPosition(x,y,Align.center);
                }
            }
        });
        radius = 220f;
    }

    @Override
    public void show() {
        background.setScale(1);
        stick.setScale(1);
    }

    @Override
    public void hide() {
        background.setScale(0);
        stick.setScale(0);
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
        background.setPosition(position.x,position.y, Align.center);
        stick.setPosition(position.x,position.y,Align.center);
    }

    @Override
    public Vector2 getPosition() {
        return joystickPosition;
    }

    @Override
    public void setRadius(float radius) {
        this.radius = radius;
    }
}

