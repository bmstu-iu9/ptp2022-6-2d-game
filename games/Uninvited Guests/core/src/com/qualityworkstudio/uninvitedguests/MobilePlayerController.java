package com.qualityworkstudio.uninvitedguests;

/**
 * @author Bogdan Teryukhov, Andrey Karanik
 */

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Body;
import com.qualityworkstudio.uninvitedguests.joystick.Joystick;

public class MobilePlayerController implements PlayerController {
    private Player player;
    private Joystick movementJoystick;
    private Joystick rotationJoystick;

    private float switchCurrentTime;
    private float switchDelay;

    public MobilePlayerController(Player player, Joystick movementJoystick, Joystick rotationJoystick) {
        this.player = player;
        this.movementJoystick = movementJoystick;
        this.rotationJoystick = rotationJoystick;
        switchDelay = 1f;
    }

    @Override
    public void move() {
        Body body = player.getBody();
        float movementSpeed = player.getMovementSpeed();
        body.applyLinearImpulse(movementSpeed*movementJoystick.getDirection().x,
                movementSpeed*movementJoystick.getDirection().y,
                body.getPosition().x, body.getPosition().y, true);
    }

    @Override
    public void look() {
        Body body = player.getBody();
        if (rotationJoystick.isTouched()) {
            switchCurrentTime = switchDelay;
            body.setTransform(body.getPosition(), (float) Math.toRadians(rotationJoystick.getAngle() - 90f));
        } else {
            if (movementJoystick.isTouched()) {
                if (switchCurrentTime <= 0) {
                    body.setTransform(body.getPosition(), (float) Math.toRadians(movementJoystick.getAngle() - 90f));
                } else {
                    switchCurrentTime -= Gdx.graphics.getDeltaTime();
                }
            }
        }
    }
}
