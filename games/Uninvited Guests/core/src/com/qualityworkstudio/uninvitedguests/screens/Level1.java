package com.qualityworkstudio.uninvitedguests.screens;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
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

    private Box box;

    private InteractionArea interactionArea;

    private Box2DDebugRenderer renderer;

    public Level1(final Game game) {
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

        box = new Box(world, assetManager.<Texture>get("box_1.png"), new Vector2(6f, 6f), new Vector2(2.5f, 2.5f));

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

        new InteractionArea(world, new Vector2(6f, 1f), new Interaction() {
            @Override
            public void interactIn(int groupIndex) {
                if (groupIndex != GroupIndices.PLAYER) {
                    return;
                }
                game.setScreen(new LoadingScreen(game, Screens.LEVEL2, "json/level1.json"));
            }

            @Override
            public void interactOut(int groupIndex) {
            }

        }).setPosition(new Vector2(0f, 32f));

        renderer = new Box2DDebugRenderer();
    }

    @Override
    public void show() {
        super.show();
    }

    @Override
    public void update(float delta) {
        player.update(delta);
        box.update();
        for (BasicDoor door : doors) {
            door.update(delta);
        }
    }

    @Override
    public void draw() {
        map.draw(batch);
        for (BasicDoor door : doors) {
            door.draw(batch);
        }
        map.draw(batch);
        box.draw(batch);
        player.draw(batch);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
    }
}
