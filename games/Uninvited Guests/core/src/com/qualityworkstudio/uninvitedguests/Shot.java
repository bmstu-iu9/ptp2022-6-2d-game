package com.qualityworkstudio.uninvitedguests;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/**
 * @author Andrey Karanik
 */

public class Shot extends GameObject {
    private Timer shotTimer;
    private boolean destroyed;
    private Sprite shotSprite;

    public Shot(Texture texture, Vector2 position, float angle, Vector2 size, float shotTime) {
        shotSprite = new Sprite(texture);
        shotSprite.setSize(size.x, size.y);
        shotSprite.setOriginCenter();
        shotSprite.setCenter(position.x, position.y);
        shotSprite.setRotation(angle);
        shotTimer = new Timer(new Timer.Task() {
            @Override
            public void doTask() {
                destroyed = true;
            }
        });
        shotTimer.start(shotTime);
    }

    @Override
    public void update(float deltaTime) {
        shotTimer.update(deltaTime);
    }

    @Override
    public void draw(SpriteBatch batch) {
        shotSprite.draw(batch);
    }

    public boolean isDestroyed() {
        return destroyed;
    }
}
