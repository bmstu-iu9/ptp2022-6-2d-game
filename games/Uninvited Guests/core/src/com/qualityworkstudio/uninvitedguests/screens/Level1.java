package com.qualityworkstudio.uninvitedguests.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.qualityworkstudio.uninvitedguests.BasicDoor;
import com.qualityworkstudio.uninvitedguests.Game;
import com.qualityworkstudio.uninvitedguests.GroupIndices;
import com.qualityworkstudio.uninvitedguests.Interaction;
import com.qualityworkstudio.uninvitedguests.InteractionArea;
import com.qualityworkstudio.uninvitedguests.Map;

import java.util.ArrayList;

public class Level1 extends LevelScreen {

    private Map map;
    private ArrayList<BasicDoor> doors;

    private Body body;
    private Sprite sprite;

    private InteractionArea interactionArea;

    private Box2DDebugRenderer renderer;

    public Level1(Game game) {
        super(game);

        map = new Map(128, assetManager.<Texture>get("maps/lvl1_map_layer1.png"), assetManager.<Texture>get("maps/lvl1_map_layer2.png"));
        doors = new ArrayList<>();
        BasicDoor door1 = new BasicDoor(world, assetManager);
        door1.setType(BasicDoor.Type.RED);
        door1.setPosition(0f, -56f);
        doors.add(door1);
        BasicDoor door2 = new BasicDoor(world, assetManager);
        door2.setType(BasicDoor.Type.GREEN);
        door2.setPosition(0f, -16f);
        doors.add(door2);
        final BasicDoor door3 = new BasicDoor(world, assetManager);
        door3.setType(BasicDoor.Type.YELLOW);
        door3.setPosition(0f, 32f);
        doors.add(door3);
        player.setPosition(0f, -36f);

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.linearDamping = 15;
        bodyDef.angularDamping = 15;
        body = world.createBody(bodyDef);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(2.5f, 2.5f);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 0.5f;
        fixtureDef.friction = 0.5f;
        Fixture fixture = body.createFixture(fixtureDef);
        Filter filter = new Filter();
        filter.groupIndex = GroupIndices.BOX;
        fixture.setFilterData(filter);
        shape.dispose();

        sprite = new Sprite(assetManager.<Texture>get("box_1.png"));
        sprite.setSize(6f, 6f);
        sprite.setOriginCenter();

        interactionArea = new InteractionArea(world, new Vector2(1f, 1f), new Interaction() {
            @Override
            public void interactIn(int groupIndex) {
                if (groupIndex != GroupIndices.BOX) {
                    return;
                }

                door3.open();
            }

            @Override
            public void interactOut(int groupIndex) {
                if (groupIndex != GroupIndices.BOX) {
                    return;
                }

                door3.close();
            }
        });
        interactionArea.setPosition(new Vector2(0f, 12f));

        renderer = new Box2DDebugRenderer();
    }

    @Override
    public void update(float delta) {
        player.update(delta);
        sprite.setPosition(body.getPosition().x - sprite.getWidth() / 2f, body.getPosition().y - sprite.getHeight() / 2f);
        sprite.setRotation((float)Math.toDegrees(body.getAngle()));
        for (BasicDoor door : doors) {
            door.update(delta);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            game.setScreen(new MainScreen(game));
        }
    }

    @Override
    public void draw() {
        map.draw(batch);
        for (BasicDoor door : doors) {
            door.draw(batch);
        }
        map.draw(batch);
        sprite.draw(batch);
        player.draw(batch);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        //renderer.render(world, player.getCamera().combined);
    }
}
