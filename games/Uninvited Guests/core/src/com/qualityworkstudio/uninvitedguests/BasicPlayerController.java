package com.qualityworkstudio.uninvitedguests;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
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
        OrthographicCamera camera = player.getCamera();

        Vector2 playerPosOnScreen = new Vector2(
                (player.getPosition().x - camera.position.x) * Gdx.graphics.getWidth() / camera.viewportWidth + Gdx.graphics.getWidth() / 2f,
                (player.getPosition().y - camera.position.y) * Gdx.graphics.getWidth() / camera.viewportWidth + Gdx.graphics.getHeight() / 2f);
        player.getBody().setTransform(player.getPosition(), (float)(Math.atan2((Gdx.graphics.getHeight() - Gdx.input.getY()) - playerPosOnScreen.y, Gdx.input.getX() - playerPosOnScreen.x)));
    }
}
