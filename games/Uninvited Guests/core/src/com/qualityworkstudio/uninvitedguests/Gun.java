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

/**
 * @author Andrey Karanik
 */

public class Gun extends GameObject implements Weapon {

    private AssetManager assetManager;

    private World world;
    private Vector2 position;
    private float rotation;
    private float currentFraction;
    private Fixture currentFixture;
    private Vector2 shotPoint;
    private float weaponLength = 1.5f;
    private float maxShotLength = 100f;
    private float shotTime = 0.05f;
    private float delay = 0.2f;
    private Timer delayTimer;
    private boolean shootingAvailable;

    private Queue<Shot> shotQueue;
    private Sprite sprite;

    private int ammoInClip = 20;
    private int maxAmmoInClip = 20;
    private float reloadTime = 2f;
    private Timer reloadTimer;
    private boolean reloading;
    private Vector2 startPoint;
    private Vector2 endPoint;
    private boolean infinity;
    private float damage = 10f;

    /**
     * Constructs a gun.
     *
     * @param world the world object.
     * @param assetManager the asset manager.
     */
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
        shotPoint = new Vector2();
        shootingAvailable = true;
        delayTimer = new Timer(new Timer.Task() {
            @Override
            public void doTask() {
                shootingAvailable = true;
            }
        });
        reloadTimer = new Timer(new Timer.Task() {
            @Override
            public void doTask() {
                reloading = false;
                ammoInClip = maxAmmoInClip;
            }
        });
    }

    /**
     * Shoots.
     *
     * @return true if the gun fired.
     */
    @Override
    public boolean shoot() {
        if (ammoInClip == 0 || !shootingAvailable || reloading) {
            return false;
        }

        delayTimer.start(delay);
        shootingAvailable = false;

        if (!infinity) {
            ammoInClip--;
        }

        RayCastCallback callback = new RayCastCallback() {
            @Override
            public float reportRayFixture(Fixture fixture, Vector2 point, Vector2 normal, float fraction) {
                if (fixture.getFilterData().groupIndex == GroupIndices.INTERACTION_AREA || fixture.getFilterData().groupIndex == GroupIndices.UNKNOWN) {
                    return 1;
                }
                if (currentFraction > fraction) {
                    currentFixture = fixture;
                    currentFraction = fraction;
                    shotPoint.set(point.x, point.y);
                }
                return fraction;
            }
        };
        currentFraction = 1;
        world.rayCast(callback, startPoint, endPoint);
        if (currentFixture.getFilterData().groupIndex == GroupIndices.PLAYER || currentFixture.getFilterData().groupIndex == GroupIndices.ENEMY) {
            HealthSystem obj = (HealthSystem) currentFixture.getBody().getUserData();
            obj.setHealth(obj.getHealth() - damage);
        }
        if (currentFraction == 1) {
            shotPoint.set(endPoint.x, endPoint.y);
        }
        sprite.setCenter(shotPoint.x, shotPoint.y);
        float dx = shotPoint.x - startPoint.x;
        float dy = shotPoint.y - startPoint.y;
        float length = (float)Math.sqrt(dx * dx + dy * dy);
        Vector2 pos = new Vector2(startPoint.x + dx / 2f, startPoint.y + dy / 2f);
        Vector2 size = new Vector2(length, 0.5f);
        shotQueue.add(new Shot(assetManager.<Texture>get("shot.png"), pos, (float)Math.toDegrees(rotation), size, shotTime));

        return true;
    }

    /**
     * Reloads the gun.
     *
     * @return true if the gun can be reloaded.
     */
    @Override
    public boolean reload() {
        if (reloading || ammoInClip == maxAmmoInClip) {
            return false;
        }
        reloading = true;
        reloadTimer.start(reloadTime);
        return true;
    }

    @Override
    public void update(float deltaTime) {
        startPoint.set(position.x + (float)Math.cos(rotation) * weaponLength, position.y + (float)Math.sin(rotation) * weaponLength);
        endPoint.set(position.x + (float)Math.cos(rotation) * (weaponLength + maxShotLength), position.y + (float)Math.sin(rotation) * (weaponLength + maxShotLength));

        delayTimer.update(deltaTime);
        reloadTimer.update(deltaTime);

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
    }

    /**
     * Sets the gun position.
     *
     * @param position a new position.
     */
    @Override
    public void setPosition(Vector2 position) {
        this.position.set(position);
    }

    /**
     * Gets the gun position.
     *
     * @return the gun position.
     */
    @Override
    public Vector2 getPosition() {
        return position.cpy();
    }

    /**
     * Sets the gun rotation.
     *
     * @param degrees a new angle in degrees.
     */
    @Override
    public void setRotation(float degrees) {
        rotation = (float)Math.toRadians(degrees);
    }

    /**
     * Gets the gun rotation.
     *
     * @return the gun rotation in radians.
     */
    @Override
    public float getRotation() {
        return rotation;
    }

    /**
     * Returns true when the ammo in clip is empty.
     *
     * @return whether the ammo in clip is empty.
     */
    @Override
    public boolean isEmpty() {
        return ammoInClip == 0;
    }

    /**
     * Returns true when the gun is reloading.
     *
     * @return whether the gun is reloading.
     */
    @Override
    public boolean isReloading() {
        return reloading;
    }

    /**
     * Sets the shooting is available.
     *
     * @param shootingAvailable whether the shooting is available.
     */
    @Override
    public void setShootingAvailable(boolean shootingAvailable) {
        this.shootingAvailable = shootingAvailable;
    }

    /**
     * Returns true when the shooting is available.
     *
     * @return whether the shooting is available.
     */
    @Override
    public boolean isShootingAvailable() {
        return shootingAvailable;
    }

    /**
     * Sets the ammo in clip.
     *
     * @param ammoInClip the ammo in clip.
     */
    public void setAmmoInClip(int ammoInClip) {
        this.ammoInClip = ammoInClip;
    }

    /**
     * Gets the ammo in clip.
     *
     * @return the ammo in clip.
     */
    @Override
    public float getAmmoInClip() {
        return ammoInClip;
    }

    /**
     * Sets the max ammo in clip.
     *
     * @param maxAmmoInClip the max ammo in clip.
     */
    public void setMaxAmmoInClip(int maxAmmoInClip) {
        this.maxAmmoInClip = maxAmmoInClip;
    }

    /**
     * Gets the max ammo in clip.
     *
     * @return the max ammo in clip.
     */
    @Override
    public float getMaxAmmoInClip() {
        return maxAmmoInClip;
    }

    /**
     * Gets the current reload time.
     *
     * @return the current reload time.
     */
    @Override
    public float getCurrentReloadTime() {
        return reloadTimer.getCurrentTime();
    }

    /**
     * Sets the reload time.
     *
     * @param reloadTime the reload time.
     */
    @Override
    public void setReloadTime(float reloadTime) {
        this.reloadTime = reloadTime;
    }

    /**
     * Sets the reload time.
     *
     * @return the reload time.
     */
    @Override
    public float getReloadTime() {
        return reloadTime;
    }

    @Override
    public void setDamage(float damage) {
        this.damage = damage;
    }

    @Override
    public float getDamage() {
        return damage;
    }

    /**
     * Sets the infinity.
     *
     * @param infinity the infinity.
     */
    public void setInfinity(boolean infinity) {
        this.infinity = infinity;
    }

    /**
     * Returns true when the ammo in clip is endless.
     *
     * @return whether the ammo in clip is endless.
     */
    public boolean isInfinity() {
        return infinity;
    }

    /**
     * Sets the shot delay.
     *
     * @param delay the shot delay.
     */
    public void setShotDelay(float delay) {
        this.delay = delay;
    }

    /**
     * Gets the shot delay.
     *
     * @return the shot delay.
     */
    public float getShotDelay() {
        return delay;
    }

    /**
     * Sets the gun length.
     *
     * @param weaponLength the gun length.
     */
    public void setWeaponLength(float weaponLength) {
        this.weaponLength = weaponLength;
    }

    /**
     * Gets the gun length.
     *
     * @return the gun length.
     */
    public float getWeaponLength() {
        return weaponLength;
    }

}
