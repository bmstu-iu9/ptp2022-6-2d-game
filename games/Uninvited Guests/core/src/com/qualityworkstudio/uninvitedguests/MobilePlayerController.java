package com.qualityworkstudio.uninvitedguests;

/**
 * @author Bogdan Teryukhov
 */

import com.badlogic.gdx.physics.box2d.Body;
import com.qualityworkstudio.uninvitedguests.joystick.Joystick;

public class MobilePlayerController implements PlayerController{
    private Player player;
    private Joystick movementJoystick;
    private Joystick rotationJoystick;

    public MobilePlayerController(Player player, Joystick movementJoystick, Joystick rotationJoystick) {
        this.player = player;
        this.movementJoystick = movementJoystick;
        this.rotationJoystick = rotationJoystick;
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
        if (rotationJoystick.isTouched()){
            body.setTransform(body.getPosition(), (float) Math.toRadians(rotationJoystick.getAngle() - 90f));
        }else{
            body.setTransform(body.getPosition(), (float) Math.toRadians(movementJoystick.getAngle() - 90f));
        }

    }
}
