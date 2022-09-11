package com.qualityworkstudio.uninvitedguests;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/**
 * @author Andrey Karanik
 */

public interface Weapon {
    boolean shoot();
    boolean reload();
    void update(float deltaTime);
    void draw(SpriteBatch batch);
    void setPosition(Vector2 position);
    Vector2 getPosition();
    void setRotation(float angle);
    float getRotation();
    boolean isEmpty();
    boolean isReloading();
    void setShootingAvailable(boolean shootingAvailable);
    boolean isShootingAvailable();
    float getAmmoInClip();
    float getMaxAmmoInClip();
    float getCurrentReloadTime();
    void setReloadTime(float reloadTime);
    float getReloadTime();
    void setDamage(float damage);
    float getDamage();
}
