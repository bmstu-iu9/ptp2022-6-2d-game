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
    public void shootAndReload() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.R)) {
            player.reload();
        }

        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
            player.shoot();
        }
    }

    @Override
    public void move() {
        Body body = player.getBody();
        float movementSpeed = player.getMovementSpeed();

        Vector2 direction = new Vector2();

        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            direction.add(0f, 1f);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            direction.add(-1f, 0f);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            direction.add(0f, -1f);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            direction.add(1f, 0f);
        }


        if (direction.x != 0 || direction.y != 0) {
            float dist = (float)Math.sqrt(direction.x * direction.x + direction.y * direction.y);
            direction.set(direction.x / dist, direction.y / dist);
        }

        body.setLinearVelocity(direction.x * movementSpeed, direction.y * movementSpeed);
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
