package com.qualityworkstudio.uninvitedguests.joystick;

/**
 * @author Bogdan Teryukhov
 */
import com.badlogic.gdx.math.Vector2;

public interface Joystick {
    void show();
    void hide();
    Vector2 getDirection();
    double getAngle();
    void setPosition(Vector2 position);
    Vector2 getPosition();
    void setRadius(float radius);
    boolean isTouched();
}
