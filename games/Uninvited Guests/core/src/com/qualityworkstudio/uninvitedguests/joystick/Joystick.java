package com.qualityworkstudio.uninvitedguests.joystick;

/**
 * @author Bogdan Teryukhov, Andrey Karanik
 */
import com.badlogic.gdx.math.Vector2;

public interface Joystick {
    void show();
    void hide();
    Vector2 getDirection();
    double getAngle();
    void setPosition(Vector2 position);
    Vector2 getPosition();
    void setSize(Vector2 size);
    Vector2 getSize();
    void setRadius(float radius);
    boolean isTouched();
}
