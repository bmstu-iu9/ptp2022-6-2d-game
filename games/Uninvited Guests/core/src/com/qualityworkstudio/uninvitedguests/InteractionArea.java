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
    private Fixture fixture;
    private boolean interacted;

    public InteractionArea(World world, Vector2 size, Object data, int index) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        body = world.createBody(bodyDef);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(size.x, size.y);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.isSensor = true;
        fixture = body.createFixture(fixtureDef);
        Filter filter = new Filter();
        filter.groupIndex = (short)index;
        fixture.setFilterData(filter);
        shape.dispose();
        fixture.setUserData(data);
        body.setUserData(this);
    }

    public InteractionArea(World world, float radius, Object data, int index) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        body = world.createBody(bodyDef);
        CircleShape shape = new CircleShape();
        shape.setRadius(radius);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.isSensor = true;
        fixture = body.createFixture(fixtureDef);
        Filter filter = new Filter();
        filter.groupIndex = (short)index;
        fixture.setFilterData(filter);
        shape.dispose();
        fixture.setUserData(data);
        body.setUserData(this);
    }

    public void setUserData(Object data) {
        fixture.setUserData(data);
    }

    public void setGroupIndex(int index) {
        fixture.getFilterData().groupIndex = (short)index;
    }

    public void setPosition(Vector2 position) {
        body.setTransform(position, 0f);
    }

    public Vector2 getPosition() {
        return body.getPosition();
    }

    public void setRotation(float radians) {
        body.setTransform(body.getPosition(), radians);
    }

    public float getRotation() {
        return body.getAngle();
    }

    public void setInteracted(boolean interacted) {
        this.interacted = interacted;
    }

    public boolean isInteracted() {
        return interacted;
    }
}
