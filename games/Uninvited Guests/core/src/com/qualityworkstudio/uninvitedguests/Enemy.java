package com.qualityworkstudio.uninvitedguests;

import com.badlogic.gdx.assets.AssetManager;
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

public class Enemy extends GameObject implements HealthSystem {

    private Body body;
    private Sprite sprite;
    private InteractionArea fieldView;
    private InteractionArea hitArea;
    private Vector2 homePosition;
    private float movementSpeed = 16f;
    private float areaX = 0.25f;
    private float areaY = 0.25f;
    private float hitAreaRadius;
    private boolean destroyed;
    private float health = 40f;
    private Texture texture1;
    private Texture texture2;

    private Player player;

    public Enemy(World world, Texture texture1, Texture texture2, float bodyRadius, Vector2 spriteSize, float fieldViewRadius) {
        super(0, GroupIndices.ENEMY);
        this.texture1 = texture1;
        this.texture2 = texture2;

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.fixedRotation = true;
        bodyDef.linearDamping = 45;
        bodyDef.angularDamping = 45;
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
        body.setUserData(this);
        shape.dispose();

        sprite = new Sprite(texture1);
        sprite.setSize(spriteSize.x, spriteSize.y);
        sprite.setOriginCenter();

        homePosition = new Vector2();
        hitAreaRadius = bodyRadius + 1f;

        fieldView = new InteractionArea(world, fieldViewRadius, new Interaction() {
            @Override
            public void interactIn(GameObject object) {
                if (object.getGroupIndex() != GroupIndices.PLAYER) {
                    return;
                }

                player = (Player) object;
            }

            @Override
            public void interactOut(GameObject object) {
                if (object.getGroupIndex() != GroupIndices.PLAYER) {
                    return;
                }

                player = null;
            }
        });

        hitArea = new InteractionArea(world, hitAreaRadius, new Interaction() {
            @Override
            public void interactIn(GameObject object) {
                if (object.getGroupIndex() != GroupIndices.PLAYER || destroyed) {
                    return;
                }

                Player p = (Player) object;
                p.setHealth(0);
            }

            @Override
            public void interactOut(GameObject object) {
                if (object.getGroupIndex() != GroupIndices.PLAYER) {
                    return;
                }
            }
        });
    }

    public Enemy(World world, AssetManager assetManager, float bodyRadius, Vector2 spriteSize, float fieldViewRadius) {
        this(world, assetManager.<Texture>get("enemy.png"), assetManager.<Texture>get("enemy_dead.png"), bodyRadius, spriteSize, fieldViewRadius);
    }

    @Override
    public void update(float deltaTime) {
        sprite.setPosition(body.getPosition().x - sprite.getWidth() / 2f, body.getPosition().y - sprite.getHeight() / 2f);
        sprite.setRotation((float)Math.toDegrees(body.getAngle()));

        if (destroyed) {
            return;
        }
        Vector2 position = player != null ? player.getPosition() : getHomePosition();

        float dx = position.x - getPosition().x;
        float dy = position.y - getPosition().y;

        if (Math.abs(dx) >= areaX || (float)Math.abs(dy) >= areaY) {
            float angle = (float)Math.atan2(dy, dx);
            body.setLinearVelocity(movementSpeed * (float) Math.cos(angle), movementSpeed * (float) Math.sin(angle));
            body.setTransform(body.getPosition().x, body.getPosition().y, angle + (float) Math.PI / 2f);
        }

        fieldView.setPosition(getPosition());
        hitArea.setPosition(getPosition());
    }

    @Override
    public void draw(SpriteBatch batch) {
        sprite.draw(batch);
    }

    public void setTexture(Texture texture) {
        sprite.setTexture(texture);
    }

    public boolean isDestroyed() {
        return destroyed;
    }

    public void setAreaX(float areaX) {
        this.areaX = areaX;
    }

    public float getAreaX() {
        return areaX;
    }

    public void setAreaY(float areaY) {
        this.areaY = areaY;
    }

    public float getAreaY() {
        return areaY;
    }

    public void setMovementSpeed(float movementSpeed) {
        this.movementSpeed = movementSpeed;
    }

    public float getMovementSpeed() {
        return movementSpeed;
    }

    public void setHomePosition(Vector2 homePosition) {
        this.homePosition = homePosition;
    }

    public Vector2 getHomePosition() {
        return homePosition;
    }

    /**
     * Sets the enemy position.
     *
     * @param x a new x position.
     * @param y a new y position.
     */
    public void setPosition(float x, float y) {
        body.setTransform(x, y, body.getAngle());
    }

    /**
     * Gets the enemy position.
     *
     * @return the enemy position.
     */
    public Vector2 getPosition() {
        return body.getPosition();
    }

    @Override
    public void setHealth(float health) {
        if (health <= 0 && !destroyed) {
            setTexture(texture2);
            destroyed = true;
        } else if (health > 0 && destroyed) {
            setTexture(texture1);
            destroyed = false;
        }

        this.health = health;
    }

    @Override
    public float getHealth() {
        return health;
    }
}
