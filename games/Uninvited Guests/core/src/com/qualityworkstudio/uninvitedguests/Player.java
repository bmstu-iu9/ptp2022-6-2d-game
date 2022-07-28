package com.qualityworkstudio.uninvitedguests;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

/**
 *
 * @author Andrey Karanik
 */

public class Player {

    private Body body;
    private Sprite sprite;

    private float movementSpeed;

    private OrthographicCamera camera;
    private boolean fixedCamera;

    public Player(World world, Texture texture, GameSettings settings) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.linearDamping = 30;
        body = world.createBody(bodyDef);
        CircleShape shape = new CircleShape();
        shape.setRadius(2f);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 0.5f;
        fixtureDef.friction = 0.5f;
        body.createFixture(fixtureDef);
        shape.dispose();

        sprite = new Sprite(texture);
        sprite.setSize(16f, 16f);
        sprite.setOriginCenter();
        movementSpeed = 64f;
        camera = new OrthographicCamera(settings.getCameraSize(), settings.getCameraSize() *
                ((float) Gdx.graphics.getHeight() / Gdx.graphics.getWidth()));
    }

    public void update(float deltaTime) {
        camera.update();

        if (!fixedCamera) {
            camera.position.set(sprite.getX(), sprite.getY(), 0);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            body.applyLinearImpulse(0, movementSpeed, body.getPosition().x, body.getPosition().y, true);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            body.applyLinearImpulse(-movementSpeed, 0, body.getPosition().x, body.getPosition().y, true);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            body.applyLinearImpulse(0, -movementSpeed, body.getPosition().x, body.getPosition().y, true);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            body.applyLinearImpulse(movementSpeed, 0, body.getPosition().x, body.getPosition().y, true);
        }

        sprite.setPosition(body.getPosition().x - sprite.getWidth() / 2f, body.getPosition().y - sprite.getHeight() / 2f);

        Vector2 playerPosOnScreen = new Vector2(((sprite.getX() + sprite.getWidth() / 2f) * (Gdx.graphics.getWidth() / camera.viewportWidth) + Gdx.graphics.getWidth() / 2f),
                Gdx.graphics.getHeight() - ((sprite.getY() + sprite.getHeight() / 2f) * (Gdx.graphics.getHeight() / camera.viewportHeight) + Gdx.graphics.getHeight() / 2f));
        sprite.setRotation(-(float)Math.toDegrees(Math.atan2(Gdx.input.getY() - playerPosOnScreen.y, Gdx.input.getX() - playerPosOnScreen.x)) - 90f);
    }

    public void draw(SpriteBatch batch) {
        sprite.draw(batch);
    }

    /**
     * Gets the player camera.
     *
     * @return the player camera.
     */
    public OrthographicCamera getCamera() {
        return camera;
    }

    public boolean isFixedCamera() {
        return fixedCamera;
    }

    public void setFixedCamera(boolean fixedCamera) {
        this.fixedCamera = fixedCamera;
    }
}
