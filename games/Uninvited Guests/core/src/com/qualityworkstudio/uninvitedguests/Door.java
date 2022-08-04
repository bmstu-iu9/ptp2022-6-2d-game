package com.qualityworkstudio.uninvitedguests;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Door {
    private Body leftPartBody;
    private Body rightPartBody;

    private Sprite leftPartSprite;
    private Sprite rightPartSprite;

    private Texture[] textures;

    private float radius;
    private float opennessDegree;
    private Vector2 position;
    private float rotation;

    private boolean fixed;

    public Door(World world, Texture ... textures) {
        this.textures = textures;
        float width = 6f;
        float height = 4f;

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        leftPartBody = world.createBody(bodyDef);
        rightPartBody = world.createBody(bodyDef);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / 2f, 1.687f);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        leftPartBody.createFixture(fixtureDef);
        rightPartBody.createFixture(fixtureDef);
        shape.dispose();

        leftPartSprite = new Sprite(textures[0]);
        leftPartSprite.setSize(width, height);
        leftPartSprite.setOriginCenter();
        rightPartSprite = new Sprite(textures[0]);
        rightPartSprite.setSize(width, height);
        rightPartSprite.setOriginCenter();

        radius = 3f;
        position = new Vector2();

        leftPartBody.setTransform(position.x - radius, 0f, 0f);
        rightPartBody.setTransform(position.y + radius, 0f, (float)Math.PI);
    }

    public void update(float deltaTime) {
        if (!fixed) {
            float distance = radius + opennessDegree;
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

    public void draw(SpriteBatch batch) {
        leftPartSprite.draw(batch);
        rightPartSprite.draw(batch);
    }

    public void setPosition(Vector2 position) {
        this.position.set(position);
    }

    public void setPosition(float x, float y) {
        position.set(x, y);
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setRotation(float radians) {
        rotation = radians;
    }

    public float getRotation() {
        return rotation;
    }

    public void setFixed(boolean flag) {
        fixed = flag;
    }

    public boolean isFixed() {
        return fixed;
    }
}
