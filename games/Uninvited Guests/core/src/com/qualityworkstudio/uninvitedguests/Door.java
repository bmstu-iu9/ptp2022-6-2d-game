package com.qualityworkstudio.uninvitedguests;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

/**
 * The class represents a door.
 *
 * @author Andrey Karanik
 */

public class Door extends GameObject {

    // Door states
    public static final int CLOSED = 1;
    public static final int OPEN = 2;
    public static final int IS_CLOSING = 4;
    public static final int IS_OPENING = 8;

    private World world;

    private Body leftPartBody;
    private Body rightPartBody;

    private Sprite leftPartSprite;
    private Sprite rightPartSprite;

    private int state;

    private float radius;
    private float opennessDegree;
    private float maxOpennessDegree;

    private Vector2 position;
    private float rotation;

    private boolean fixed;

    /**
     * Constructs a door.
     *
     * @param world a world object.
     * @param shapeSize a shape size.
     * @param spriteSize a sprite size.
     * @param texture a texture.
     */
    public Door(World world, Vector2 shapeSize, Vector2 spriteSize, Texture texture) {
        super(0, GroupIndices.DOOR);
        this.world = world;
        state = CLOSED;

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        leftPartBody = world.createBody(bodyDef);
        rightPartBody = world.createBody(bodyDef);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(shapeSize.x, shapeSize.y);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        Fixture fixture1 = leftPartBody.createFixture(fixtureDef);
        Fixture fixture2 = rightPartBody.createFixture(fixtureDef);
        Filter filter = new Filter();
        filter.groupIndex = (short)getGroupIndex();
        fixture1.setFilterData(filter);
        fixture2.setFilterData(filter);
        leftPartBody.setUserData(this);
        rightPartBody.setUserData(this);
        shape.dispose();

        leftPartSprite = new Sprite(texture);
        leftPartSprite.setSize(spriteSize.x, spriteSize.y);
        leftPartSprite.setOriginCenter();
        rightPartSprite = new Sprite(texture);
        rightPartSprite.setSize(spriteSize.x, spriteSize.y);
        rightPartSprite.setOriginCenter();

        this.radius = shapeSize.x;
        maxOpennessDegree = 1.5f * radius;
        position = new Vector2();

        leftPartBody.setTransform(position.x - radius, 0f, rotation);
        rightPartBody.setTransform(position.y + radius, 0f, rotation + (float)Math.PI);
    }

    /**
     * The method updates the state of the door.
     *
     * @param deltaTime the time span between the last frame and the current frame in seconds.
     */
    public void update(float deltaTime) {
        if (!fixed) {
            float distance = radius + opennessDegree;
            float k = 0.1f;
            switch (state) {
                case IS_OPENING:
                    if (opennessDegree >= maxOpennessDegree - 0.01f) {
                        opennessDegree = maxOpennessDegree;
                        state = OPEN;
                    }
                    opennessDegree = opennessDegree + (maxOpennessDegree - opennessDegree) * k;
                    break;
                case IS_CLOSING:
                    if (opennessDegree <= 0 + 0.01f) {
                        opennessDegree = 0;
                        state = CLOSED;
                    }
                    opennessDegree = opennessDegree - (opennessDegree) * k;
                    break;
            }
            leftPartBody.setTransform(position.x - distance * (float)Math.cos(rotation),
                    position.y - distance * (float)Math.sin(rotation), rotation);
            rightPartBody.setTransform(position.x + distance * (float)Math.cos(rotation),
                    position.y + distance * (float)Math.sin(rotation), rotation + (float)Math.PI);
        }

        leftPartSprite.setPosition(leftPartBody.getPosition().x - leftPartSprite.getWidth() / 2f,
                leftPartBody.getPosition().y - leftPartSprite.getHeight() / 2f);
        rightPartSprite.setPosition(rightPartBody.getPosition().x - rightPartSprite.getWidth() / 2f,
                rightPartBody.getPosition().y - rightPartSprite.getHeight() / 2f);
        leftPartSprite.setRotation((float)Math.toDegrees(leftPartBody.getAngle()));
        rightPartSprite.setRotation((float)Math.toDegrees(rightPartBody.getAngle()));
    }

    /**
     * The method draws the door.
     *
     * @param batch a sprite batch.
     */
    public void draw(SpriteBatch batch) {
        leftPartSprite.draw(batch);
        rightPartSprite.draw(batch);
    }

    /**
     * This method opens the door.
     */
    public void open() {
        if ((state & (OPEN | IS_CLOSING | IS_OPENING)) != 0) {
            return;
        }

        opennessDegree = 0;
        state = IS_OPENING;
    }

    /**
     * This method closes the door.
     */
    public void close() {
        if ((state & (CLOSED | IS_CLOSING | IS_OPENING)) != 0) {
            return;
        }

        opennessDegree = maxOpennessDegree;
        state = IS_CLOSING;
    }

    /**
     * Sets the door position.
     *
     * @param position a new position.
     */
    public void setPosition(Vector2 position) {
        this.position.set(position);
    }

    /**
     * Sets the door position.
     *
     * @param x a new x position.
     * @param y a new y position.
     */
    public void setPosition(float x, float y) {
        position.set(x, y);
    }

    /**
     * Gets the door position.
     *
     * @return the door position.
     */
    public Vector2 getPosition() {
        return position;
    }

    /**
     * Sets the door rotation.
     *
     * @param radians a new angle in radians.
     */
    public void setRotation(float radians) {
        rotation = radians;
    }

    /**
     * Gets the door rotation.
     *
     * @return the door rotation in radians.
     */
    public float getRotation() {
        return rotation;
    }

    /**
     * Sets whether the door is fixed.
     *
     * @param flag whether the door is fixed.
     */
    public void setFixed(boolean flag) {
        fixed = flag;
    }

    /**
     * Returns true when the door is fixed.
     *
     * @return whether the door is fixed.
     */
    public boolean isFixed() {
        return fixed;
    }

    /**
     * Sets the door texture.
     *
     * @param texture a new texture.
     */
    public void setTexture(Texture texture) {
        leftPartSprite.setTexture(texture);
        rightPartSprite.setTexture(texture);
    }

    /**
     * Sets the radius.
     *
     * @param radius a new radius.
     */
    public void setRadius(float radius) {
        this.radius = radius;
    }

    /**
     * Gets the radius.
     *
     * @return the radius.
     */
    public float getRadius() {
        return radius;
    }

    /**
     * Sets the max openness degree.
     *
     * @param value a new max openness degree.
     */
    public void setMaxOpennessDegree(float value) {
        maxOpennessDegree = value;
    }

    /**
     * Gets the max openness degree.
     *
     * @return the max openness degree.
     */
    public float getMaxOpennessDegree() {
        return maxOpennessDegree;
    }

    /**
     * Gets the door state.
     *
     * @return the door state.
     */
    public int getState() {
        return state;
    }

    /**
     * Sets the door state.
     *
     * @param state a new door state.
     */
    public void setState(int state) {
        this.state = state;
    }
}
