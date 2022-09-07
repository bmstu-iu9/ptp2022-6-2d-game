package com.qualityworkstudio.uninvitedguests;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.RayCastCallback;
import com.badlogic.gdx.physics.box2d.World;

import java.util.ArrayDeque;
import java.util.Queue;

public class Gun extends GameObject implements Weapon {

    private AssetManager assetManager;

    private World world;
    private Vector2 position;
    private float rotation;
    private float weaponLength = 1.5f;
    private float maxShotLength = 100f;
    private float shotTime = 0.1f;

    private Queue<Shot> shotQueue;
    private Sprite sprite;

    private int ammoInClip = 20;
    private int maxAmmoInClip = 20;
    private float reloadTime = 1f;
    private Vector2 startPoint;
    private Vector2 endPoint;
    private boolean infinity = true;

    public Gun(World world, AssetManager assetManager) {
        this.world = world;
        this.assetManager = assetManager;
        position = new Vector2();
        startPoint = new Vector2();
        endPoint = new Vector2();
        sprite = new Sprite(assetManager.<Texture>get("white_square.png"));
        sprite.setSize(0.5f, 0.5f);
        sprite.setOriginCenter();
        sprite.setColor(Color.RED);
        shotQueue = new ArrayDeque<>();
    }

    @Override
    public void shoot() {
        if (ammoInClip == 0) {
            return;
        }

        if (!infinity) {
            ammoInClip--;
        }
        RayCastCallback callback = new RayCastCallback() {
            @Override
            public float reportRayFixture(Fixture fixture, Vector2 point, Vector2 normal, float fraction) {
                if (fixture.getFilterData().groupIndex == GroupIndices.INTERACTION_AREA || fixture.getFilterData().groupIndex == GroupIndices.UNKNOWN) {
                    return 1;
                }
                System.out.println(fixture.getFilterData().groupIndex);
                float dx = point.x - startPoint.x;
                float dy = point.y - startPoint.y;
                float length = (float)Math.sqrt(dx * dx + dy * dy);
                Vector2 pos = new Vector2(startPoint.x + dx / 2f, startPoint.y + dy / 2f);
                Vector2 size = new Vector2(length, 0.05f);
                shotQueue.add(new Shot(assetManager.<Texture>get("white_square.png"), pos, (float)Math.toDegrees(rotation), size, shotTime));
                sprite.setCenter(point.x, point.y);
                return fraction;
            }
        };
        world.rayCast(callback, startPoint, endPoint);
    }

    @Override
    public void reload() {
        ammoInClip = maxAmmoInClip;
    }

    @Override
    public void update(float deltaTime) {
        startPoint.set(position.x + (float)Math.cos(rotation) * weaponLength, position.y + (float)Math.sin(rotation) * weaponLength);
        endPoint.set(position.x + (float)Math.cos(rotation) * (weaponLength + maxShotLength), position.y + (float)Math.sin(rotation) * (weaponLength + maxShotLength));

        while (!shotQueue.isEmpty()) {
            if (!shotQueue.peek().isDestroyed()) {
                break;
            }
            shotQueue.poll();
        }

        for (Shot shot : shotQueue) {
            shot.update(deltaTime);
        }
    }

    @Override
    public void draw(SpriteBatch batch) {
        for (Shot shot : shotQueue) {
            shot.draw(batch);
        }
        sprite.draw(batch);
    }

    public Vector2 getStartPoint() {
        return startPoint;
    }

    /**
     * Sets the gun position.
     *
     * @param position a new position.
     */
    public void setPosition(Vector2 position) {
        this.position.set(position);
    }

    /**
     * Gets the gun position.
     *
     * @return the gun position.
     */
    public Vector2 getPosition() {
        return position.cpy();
    }

    /**
     * Sets the gun rotation.
     *
     * @param degrees a new angle in degrees.
     */
    public void setRotation(float degrees) {
        rotation = (float)Math.toRadians(degrees);
    }

    /**
     * Gets the gun rotation.
     *
     * @return the gun rotation in radians.
     */
    public float getRotation() {
        return rotation;
    }
}
