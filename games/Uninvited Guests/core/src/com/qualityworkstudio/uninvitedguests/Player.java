package com.qualityworkstudio.uninvitedguests;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

/**
 * @author Andrey Karanik
 */

public class Player extends GameObject {

    private Body body;
    private Sprite sprite;
    private Texture texture1;
    private Texture texture2;
    private PlayerController controller;
    private PlayerInterface playerInterface;
    private ReloadWidget reloadWidget;

    private float spriteSize;
    private float bodyRadius;
    private float spriteOffsetX;
    private float spriteOffsetY;
    private float cameraOffset;
    private float weaponOffset;
    private float shootingTime;
    private boolean shooting;
    private boolean autoReloading;
    private Timer shootingTimer;
    private float extraSpriteAngle;
    private float movementSpeed;
    private boolean moveToFixed;
    private Vector2 moveToPosition;

    private OrthographicCamera camera;
    private boolean fixedCamera;
    private boolean fixed;

    private Weapon weapon;

    /**
     * Constructs a new player.
     *
     * @param world the world object.
     * @param assetManager the asset manager.
     * @param cameraSize the camera size.
     */
    public Player(World world, AssetManager assetManager, float cameraSize) {
        super(0, GroupIndices.PLAYER);
        texture1 = assetManager.get("character.png");
        texture2 = assetManager.get("character_shooting.png");

        spriteSize = cameraSize / 8f;
        bodyRadius = spriteSize / 8f;
        spriteOffsetX = 0f;
        spriteOffsetY = 1.5f;
        cameraOffset = 1.5f;
        weaponOffset = bodyRadius + 1f;
        shootingTime = 0.05f;
        autoReloading = true;
        extraSpriteAngle = 90f;
        movementSpeed = bodyRadius * cameraSize * 0.125f;

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.linearDamping = 30;
        body = world.createBody(bodyDef);
        CircleShape shape = new CircleShape();
        shape.setRadius(bodyRadius);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 0.5f;
        fixtureDef.friction = 0.5f;
        Fixture fixture = body.createFixture(fixtureDef);
        Filter filter = new Filter();
        filter.groupIndex = (short)getGroupIndex();
        fixture.setFilterData(filter);
        body.setFixedRotation(true);
        body.setUserData(this);
        shape.dispose();

        sprite = new Sprite(texture1);
        sprite.setSize(spriteSize, spriteSize);
        sprite.setOrigin(spriteSize / 2f - spriteOffsetX, spriteSize / 2f - spriteOffsetY);
        camera = new OrthographicCamera(cameraSize, cameraSize *
                ((float) Gdx.graphics.getHeight() / Gdx.graphics.getWidth()));
        shootingTimer = new Timer(new Timer.Task() {
            @Override
            public void doTask() {
                shooting = false;
                sprite.setTexture(texture1);
            }
        });
    }

    /**
     * The method updates the state of the player.
     *
     * @param deltaTime the time span between the last frame and the current frame in seconds.
     */
    public void update(float deltaTime) {
        if (!fixedCamera) {
            camera.position.set(camera.position.x + (getPosition().x + (float)Math.cos(body.getAngle()) * cameraOffset - camera.position.x) * 0.1f,
                    camera.position.y + (getPosition().y + (float)Math.sin(body.getAngle()) * cameraOffset - camera.position.y) * 0.1f, 0);
        }
        camera.update();

        if (weapon != null) {
            weapon.setPosition(new Vector2(body.getPosition().x + (float)Math.cos(body.getAngle()) * weaponOffset, body.getPosition().y + (float)Math.sin(body.getAngle()) * weaponOffset));
            weapon.setRotation((float) Math.toDegrees(body.getAngle()));
            weapon.update(deltaTime);
        }

        shootingTimer.update(deltaTime);

        if (!moveToFixed && !fixed && controller != null) {
            controller.move();
            controller.look();
            controller.shootAndReload();
        } else if (moveToFixed) {
            float dx = moveToPosition.x - getPosition().x;
            float dy = moveToPosition.y - getPosition().y;
            float dist = (float)Math.sqrt(dx*dx + dy*dy);
            body.setLinearVelocity(movementSpeed * dx / dist,
                    movementSpeed * dy / dist);
            body.setTransform(getPosition(), (float)(Math.atan2(dy, dx)));
            if (Math.abs(dx) < 0.25 && Math.abs(dy) < 0.25) {
                moveToFixed = false;
            }
        }

        sprite.setPosition(getPosition().x - spriteSize / 2f + spriteOffsetX, getPosition().y - spriteSize / 2f + spriteOffsetY);
        sprite.setRotation((float)Math.toDegrees(body.getAngle() - (float)Math.toRadians(extraSpriteAngle)));
    }

    /**
     * This method draws the player.
     *
     * @param batch a sprite batch.
     */
    public void draw(SpriteBatch batch) {
        weapon.draw(batch);
        sprite.draw(batch);
    }

    public void shoot() {
        if (weapon.shoot()) {
            if (!shooting) {
                shooting = true;
                sprite.setTexture(texture2);
                shootingTimer.start(shootingTime);
            }
        } else if (autoReloading && weapon.isEmpty() && !weapon.isReloading()) {
            reload();
        }
    }

    public void setReloadWidget(ReloadWidget reloadWidget) {
        this.reloadWidget = reloadWidget;
    }

    public ReloadWidget getReloadWidget() {
        return reloadWidget;
    }

    public void reload() {
        if (weapon.reload() && reloadWidget != null) {
            reloadWidget.show(weapon);
        }
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    public Weapon getWeapon() {
        return weapon;
    }

    /**
     * Sets the player controller.
     *
     * @param controller a new player controller.
     */
    public void setController(PlayerController controller) {
        // Following the dependency inversion principle (DIP from SOLID)
        this.controller = controller;
    }

    /**
     * Gets the player controller.
     *
     * @return the player controller.
     */
    public PlayerController getController() {
        return controller;
    }

    /**
     * Gets the player interface.
     *
     * @return the player interface.
     */
    public PlayerInterface getPlayerInterface() {
        return playerInterface;
    }

    /**
     * Sets the player interface.
     *
     * @param playerInterface a new player interface.
     */
    public void setPlayerInterface(PlayerInterface playerInterface) {
        this.playerInterface = playerInterface;
    }

    /**
     * Gets the player body.
     *
     * @return the player body.
     */
    public Body getBody() {
        return body;
    }

    /**
     * Sets the player sprite.
     *
     * @param sprite a new sprite.
     */
    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    /**
     * Gets the player sprite.
     *
     * @return the player sprite.
     */
    public Sprite getSprite() {
        return sprite;
    }

    /**
     * Gets the player movement speed.
     *
     * @return the player movement speed.
     */
    public float getMovementSpeed() {
        return movementSpeed;
    }

    /**
     * Sets the player movement speed.
     *
     * @param movementSpeed a new player movement speed.
     */
    public void setMovementSpeed(float movementSpeed) {
        this.movementSpeed = movementSpeed;
    }

    /**
     * Sets the camera.
     *
     * @param camera a new camera.
     */
    public void setCamera(OrthographicCamera camera) {
        this.camera = camera;
    }

    /**
     * Gets the player camera.
     *
     * @return the player camera.
     */
    public OrthographicCamera getCamera() {
        return camera;
    }

    /**
     * Returns true when the player camera is fixed.
     *
     * @return whether the player camera is fixed.
     */
    public boolean isFixedCamera() {
        return fixedCamera;
    }

    /**
     * Sets whether the player camera is fixed. Default is false.
     *
     * @param fixedCamera whether the player camera is fixed.
     */
    public void setFixedCamera(boolean fixedCamera) {
        this.fixedCamera = fixedCamera;
    }

    /**
     * Returns true when the player is fixed.
     *
     * @return whether the player is fixed.
     */
    public boolean isFixed() {
        return fixed;
    }

    /**
     * Sets whether the player is fixed. Default is false.
     *
     * @param fixed whether the player is fixed.
     */
    public void setFixed(boolean fixed) {
        this.fixed = fixed;
    }

    /**
     * Sets the player position.
     *
     * @param x a new x position.
     * @param y a new y position.
     */
    public void setPosition(float x, float y) {
        body.setTransform(x, y, body.getAngle());
    }

    /**
     * Sets the player position.
     *
     * @param position a new position.
     */
    public void setPosition(Vector2 position) {
        body.setTransform(position.x, position.y, body.getAngle());
    }

    /**
     * Gets the player position.
     *
     * @return the player position.
     */
    public Vector2 getPosition() {
        return body.getPosition();
    }

    /**
     * Sets the player rotation.
     *
     * @param degrees a new angle in degrees.
     */
    public void setRotation(float degrees) {
        body.setTransform(body.getPosition().x, body.getPosition().y, (float)Math.toRadians(degrees));
    }

    /**
     * Gets the player rotation.
     *
     * @return the player rotation in radians.
     */
    public float getRotation() {
        return body.getAngle();
    }

    /**
     * Sets the sprite offset x.
     *
     * @param spriteOffsetX a new sprite offset y.
     */
    public void setSpriteOffsetX(float spriteOffsetX) {
        this.spriteOffsetX = spriteOffsetX;
    }

    /**
     * Gets the sprite offset x.
     *
     * @return the sprite offset x.
     */
    public float getSpriteOffsetX() {
        return spriteOffsetX;
    }

    /**
     * Sets the sprite offset y.
     *
     * @param spriteOffsetY a new sprite offset y.
     */
    public void setSpriteOffsetY(float spriteOffsetY) {
        this.spriteOffsetY = spriteOffsetY;
    }

    /**
     * Gets the sprite offset y.
     *
     * @return the sprite offset y.
     */
    public float getSpriteOffsetY() {
        return spriteOffsetY;
    }

    /**
     * Sets the camera offset.
     *
     * @param cameraOffset a new camera offset.
     */
    public void setCameraOffset(float cameraOffset) {
        this.cameraOffset = cameraOffset;
    }

    /**
     * Gets the camera offset.
     *
     * @return the camera offset.
     */
    public float getCameraOffset() {
        return cameraOffset;
    }

    /**
     * Sets the extra sprite angle.
     *
     * @param degrees a new angle in degrees.
     */
    public void setExtraSpriteAngle(float degrees) {
        extraSpriteAngle = degrees;
    }

    /**
     * Gets the extra sprite angle.
     *
     * @return the extra sprite angle.
     */
    public float getExtraSpriteAngle() {
        return extraSpriteAngle;
    }

    /**
     * Directs the player to the specified position.
     *
     * @param position the position.
     */
    public void moveTo(Vector2 position) {
        moveToPosition = position;
        moveToFixed = true;
    }
}
