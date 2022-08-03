package com.qualityworkstudio.uninvitedguests;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Door {
    private Body leftPartBody;
    private Body rightPartBody;
    private Sprite leftPartSprite;
    private Sprite rightPartSprite;
    public Door(World world, Texture ... textures) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.linearDamping = 1;
        leftPartBody= world.createBody(bodyDef);
        rightPartBody= world.createBody(bodyDef);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(6f, 6f);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        leftPartBody.createFixture(fixtureDef);
        rightPartBody.createFixture(fixtureDef);
        shape.dispose();

        leftPartSprite = new Sprite(textures[0]);
        leftPartSprite.setSize(16f, 8f);
        leftPartSprite.setOriginCenter();
        rightPartSprite = new Sprite(textures[1]);
        rightPartSprite.setSize(16f, 8f);
        rightPartSprite.setOriginCenter();
    }

    public void update(float deltaTime) {
        leftPartSprite.setPosition(leftPartBody.getPosition().x - leftPartSprite.getWidth() / 2f,
                leftPartBody.getPosition().y - leftPartSprite.getHeight() / 2f);
        rightPartSprite.setPosition(rightPartBody.getPosition().x - rightPartSprite.getWidth() / 2f,
                rightPartBody.getPosition().y - rightPartSprite.getHeight() / 2f);
    }

    public void draw(SpriteBatch batch) {
        leftPartSprite.draw(batch);
        rightPartSprite.draw(batch);
    }
}
