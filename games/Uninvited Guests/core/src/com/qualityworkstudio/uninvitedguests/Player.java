package com.qualityworkstudio.uninvitedguests;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;

/**
 *
 * @author Andrey Karanik
 */

public class Player {

    private Body body;
    private Sprite sprite;

    private float movementSpeed;

    private OrthographicCamera camera;
    private boolean fixedCamera;

    public Player(Texture texture, GameSettings settings) {

        sprite = new Sprite(texture);
        sprite.setOriginCenter();
        sprite.setSize(8f, 8f);
        sprite.setCenter(0, 0);
        movementSpeed = 8f;
        camera = new OrthographicCamera(settings.getCameraSize(), settings.getCameraSize() *
                ((float) Gdx.graphics.getHeight() / Gdx.graphics.getWidth()));
    }

    public void update(float deltaTime) {
        camera.update();

        if (!fixedCamera) {
            camera.position.set(sprite.getX(), sprite.getY(), 0);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            sprite.translate(0, movementSpeed * deltaTime);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            sprite.translate(-movementSpeed * deltaTime, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            sprite.translate(0, -movementSpeed * deltaTime);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            sprite.translate(movementSpeed * deltaTime, 0);
        }
    }

    public void draw(SpriteBatch batch) {
        sprite.draw(batch);
    }

    public OrthographicCamera getCamera() {
        return camera;
    }

    public boolean isFixedCamera() {
        return fixedCamera;
    }

    public void setFixedCamera(boolean fixedCamera) {
        this.fixedCamera = fixedCamera;
    }
}
