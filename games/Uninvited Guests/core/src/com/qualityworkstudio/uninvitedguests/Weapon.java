package com.qualityworkstudio.uninvitedguests;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public interface Weapon {
    void shoot();
    void reload();
    void update(float deltaTime);
    void draw(SpriteBatch batch);
    void setPosition(Vector2 position);
    Vector2 getPosition();
    void setRotation(float angle);
    float getRotation();
}
