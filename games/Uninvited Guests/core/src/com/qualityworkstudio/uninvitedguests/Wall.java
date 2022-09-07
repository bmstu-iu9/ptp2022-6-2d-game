package com.qualityworkstudio.uninvitedguests;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

/**
 * @author Andrey Karanik
 */

public class Wall extends GameObject {

    private Body body;

    /**
     * Constructs a wall.
     *
     * @param world the world object.
     * @param vertices the wall vertices.
     * @param id the wall id.
     */
    public Wall(World world, int id, Vector2[] vertices) {
        super(id, GroupIndices.WALL);

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        body = world.createBody(bodyDef);
        ChainShape shape = new ChainShape();
        shape.createChain(vertices);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        Fixture fixture = body.createFixture(fixtureDef);
        Filter filter = new Filter();
        filter.groupIndex = GroupIndices.WALL;
        fixture.setFilterData(filter);
        shape.dispose();
    }

    /**
     * Constructs a wall from the wall data.
     *
     * @param data the wall data.
     * @param world the world object.
     */
    public Wall(WallData data, World world) {
        this(world, data.getId(), data.getVertices().toArray(new Vector2[0]));
    }

    @Override
    public void update(float deltaTime) {

    }

    @Override
    public void draw(SpriteBatch batch) {

    }

    /**
     * Gets the wall body.
     *
     * @return the wall body.
     */
    public Body getBody() {
        return body;
    }
}
