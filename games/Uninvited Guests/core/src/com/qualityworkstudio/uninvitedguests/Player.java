package com.qualityworkstudio.uninvitedguests;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

/**
 *
 * @author Andrey Karanik
 */

public class Player {

    private Body body;
    private Sprite sprite;
    private PlayerController controller;

    private float size;
    private float movementSpeed;

    private OrthographicCamera camera;
    private boolean fixedCamera;

    /**
     * @param world a world object.
     * @param texture a texture.
     * @param settings game settings.
     */
    public Player(World world, Texture texture, GameSettings settings) {
        size = 16f;
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.linearDamping = 30;
        body = world.createBody(bodyDef);
        CircleShape shape = new CircleShape();
        shape.setRadius(size / 8f);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 0.5f;
        fixtureDef.friction = 0.5f;
        body.createFixture(fixtureDef);
        body.setFixedRotation(true);
        shape.dispose();

        sprite = new Sprite(texture);
        sprite.setSize(size, size);
        sprite.setOriginCenter();
        movementSpeed = 64f;
        camera = new OrthographicCamera(settings.getCameraSize(), settings.getCameraSize() *
                ((float) Gdx.graphics.getHeight() / Gdx.graphics.getWidth()));
    }

    /**
     * The method updates the state of the player. Before using this method, make sure you have set the player controller.
     * @param deltaTime the time span between the last frame and the current frame in seconds.
     */
    public void update(float deltaTime) {
        camera.update();

        if (!fixedCamera) {
            camera.position.set(sprite.getX(), sprite.getY(), 0);
        }

        controller.move();
        controller.look();

        sprite.setPosition(body.getPosition().x - size / 2f, body.getPosition().y - size / 2f);
        sprite.setRotation((float)Math.toDegrees(body.getAngle()));
    }

    /**
     * This method draws the player.
     *
     * @param batch a sprite batch.
     */
    public void draw(SpriteBatch batch) {
        sprite.draw(batch);
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
     * Sets the player controller.
     *
     * @param controller a new player controller.
     */
    public void setController(PlayerController controller) {
        // Following the dependency inversion principle (DIP from SOLID)
        this.controller = controller;
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

    public float getSize() {
        return size;
    }
}
