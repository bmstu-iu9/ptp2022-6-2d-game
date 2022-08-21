package com.qualityworkstudio.uninvitedguests;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

/**
 * The class contains methods to control the player.
 *
 * @author Andrey Karanik
 * @see PlayerController
 */

public class BasicPlayerController implements PlayerController {

    private Player player;

    public BasicPlayerController(Player player) {
        this.player = player;
    }

    @Override
    public void move() {
        Body body = player.getBody();
        float movementSpeed = player.getMovementSpeed();

        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            body.applyLinearImpulse(0, movementSpeed, body.getPosition().x, body.getPosition().y, true);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            body.applyLinearImpulse(-movementSpeed, 0, body.getPosition().x, body.getPosition().y, true);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            body.applyLinearImpulse(0, -movementSpeed, body.getPosition().x, body.getPosition().y, true);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            body.applyLinearImpulse(movementSpeed, 0, body.getPosition().x, body.getPosition().y, true);
        }
    }

    @Override
    public void look() {
        Body body = player.getBody();
        OrthographicCamera camera = player.getCamera();

        Vector2 playerPosOnScreen = new Vector2((body.getPosition().x * (Gdx.graphics.getWidth() / camera.viewportWidth) + Gdx.graphics.getWidth() / 2f),
                Gdx.graphics.getHeight() - (body.getPosition().y * (Gdx.graphics.getHeight() / camera.viewportHeight) + Gdx.graphics.getHeight() / 2f));
        body.setTransform(body.getPosition(), -(float)(Math.atan2(Gdx.input.getY() - playerPosOnScreen.y, Gdx.input.getX() - playerPosOnScreen.x)) - (float)Math.PI / 2f);
    }
}
