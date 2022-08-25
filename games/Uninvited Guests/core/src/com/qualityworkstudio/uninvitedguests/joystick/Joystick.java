package com.qualityworkstudio.uninvitedguests.joystick;

import com.badlogic.gdx.math.Vector2;

/**
 * @author Bogdan Teryukhov, Andrey Karanik
 * @see BasicJoystick
 */

public interface Joystick {

    /**
     * Shows the joystick.
     */
    void show();

    /**
     * Hides the joystick.
     */
    void hide();

    /**
     * Gets the direction.
     *
     * @return the direction.
     */
    Vector2 getDirection();

    /**
     * Gets the angle of direction.
     *
     * @return the angle in degrees.
     */
    double getAngle();

    /**
     * Sets the joystick position.
     *
     * @param position a new joystick position.
     */
    void setPosition(Vector2 position);

    /**
     * Gets the joystick position.
     *
     * @return the joystick position.
     */
    Vector2 getPosition();

    /**
     * Sets the joystick size.
     *
     * @param size a new joystick size.
     */
    void setSize(Vector2 size);

    /**
     * Gets the joystick size.
     *
     * @return the joystick size.
     */
    Vector2 getSize();

    /**
     * Sets the joystick radius.
     *
     * @param radius a new joystick radius.
     */
    void setRadius(float radius);

    /**
     * Gets the joystick radius.
     *
     * @return the joystick radius.
     */
    float getRadius();

    /**
     * Returns true when the joystick is touched.
     *
     * @return whether the joystick is touched.
     */
    boolean isTouched();
}
