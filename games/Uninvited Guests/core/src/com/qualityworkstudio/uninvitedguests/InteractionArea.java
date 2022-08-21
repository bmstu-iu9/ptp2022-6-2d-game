package com.qualityworkstudio.uninvitedguests;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class InteractionArea {

    private Body body;
    private Interaction interaction;

    /**
     * Constructs an interaction area.
     *
     * @param world a world object.
     * @param size a size of polygon shape.
     * @param interaction an interaction.
     */
    public InteractionArea(World world, Vector2 size, Interaction interaction) {
        this.interaction = interaction;
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        body = world.createBody(bodyDef);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(size.x, size.y);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.isSensor = true;
        Fixture fixture = body.createFixture(fixtureDef);
        Filter filter = new Filter();
        filter.groupIndex = GroupIndices.INTERACTION_AREA;
        fixture.setFilterData(filter);
        shape.dispose();
        body.setUserData(this);
    }

    /**
     * Constructs an interaction area.
     *
     * @param world a world object.
     * @param radius a radius of circle shape.
     * @param interaction an interaction.
     */
    public InteractionArea(World world, float radius, Interaction interaction) {
        this.interaction = interaction;
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        body = world.createBody(bodyDef);
        CircleShape shape = new CircleShape();
        shape.setRadius(radius);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.isSensor = true;
        Fixture fixture = body.createFixture(fixtureDef);
        Filter filter = new Filter();
        filter.groupIndex = GroupIndices.INTERACTION_AREA;
        fixture.setFilterData(filter);
        shape.dispose();
        body.setUserData(this);
    }

    /**
     * Sets the position.
     *
     * @param position a new position.
     */
    public void setPosition(Vector2 position) {
        body.setTransform(position, 0f);
    }

    /**
     * Gets the position.
     *
     * @return the position.
     */
    public Vector2 getPosition() {
        return body.getPosition();
    }

    /**
     * Sets the rotation
     *
     * @param radians a new angle in radians.
     */
    public void setRotation(float radians) {
        body.setTransform(body.getPosition(), radians);
    }

    /**
     * Gets the rotation.
     *
     * @return the rotation.
     */
    public float getRotation() {
        return body.getAngle();
    }

    /**
     * Sets the interaction.
     *
     * @param interaction a new interaction.
     */
    public void setInteraction(Interaction interaction) {
        this.interaction = interaction;
    }

    /**
     * Gets the interaction.
     *
     * @return the interaction.
     */
    public Interaction getInteraction() {
        return interaction;
    }
}
