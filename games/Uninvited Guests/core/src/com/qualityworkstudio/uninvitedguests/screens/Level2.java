package com.qualityworkstudio.uninvitedguests.screens;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.qualityworkstudio.uninvitedguests.BasicDoor;
import com.qualityworkstudio.uninvitedguests.Box;
import com.qualityworkstudio.uninvitedguests.Door;
import com.qualityworkstudio.uninvitedguests.Game;
import com.qualityworkstudio.uninvitedguests.GameObject;
import com.qualityworkstudio.uninvitedguests.GroupIndices;
import com.qualityworkstudio.uninvitedguests.Interaction;
import com.qualityworkstudio.uninvitedguests.InteractionArea;
import com.qualityworkstudio.uninvitedguests.Map;
import com.qualityworkstudio.uninvitedguests.MapData;
import com.qualityworkstudio.uninvitedguests.Timer;

import java.awt.image.AreaAveragingScaleFilter;
import java.util.ArrayList;

public class Level2 extends LevelScreen {

    private Map map;
    private ArrayList<BasicDoor> doors;
    private ArrayList<Box> boxes;
    private BasicDoor door3;

    private boolean flag1;
    private boolean flag2;
    private boolean flag3;

    public Level2(Game gm) {
        super(gm);

        map = new Map(new MapData("maps/lvl2_map.json"), assetManager, world);
        doors = new ArrayList<>();
        boxes = new ArrayList<>();

        BasicDoor door1 = new BasicDoor(world, assetManager);
        door1.setType(BasicDoor.Type.RED);
        door1.setPosition(0f, -56f);
        doors.add(door1);
        BasicDoor door2 = new BasicDoor(world, assetManager);
        door2.setType(BasicDoor.Type.GREEN);
        door2.setPosition(0f, -16f);
        doors.add(door2);
        door3 = new BasicDoor(world, assetManager);
        door3.setType(BasicDoor.Type.YELLOW);
        door3.setPosition(0f, 32f);
        doors.add(door3);
        player.setPosition(0f, -36f);

        Box box1 = new Box(world, assetManager.<Texture>get("box_x.png"), new Vector2(6f, 6f), new Vector2(2.5f, 2.5f));
        box1.setId(1);
        box1.setPosition(-16f, 8f);
        boxes.add(box1);
        Box box2 = new Box(world, assetManager.<Texture>get("box_x2.png"), new Vector2(6f, 6f), new Vector2(2.5f, 2.5f));
        box2.setId(2);
        box2.setPosition(-12f, 22f);
        boxes.add(box2);
        Box box3 = new Box(world, assetManager.<Texture>get("box_1x.png"), new Vector2(6f, 6f), new Vector2(2.5f, 2.5f));
        box3.setId(3);
        box3.setPosition(22f, 24f);
        boxes.add(box3);
        Box box4 = new Box(world, assetManager.<Texture>get("box.png"), new Vector2(6f, 6f), new Vector2(2.5f, 2.5f));
        Box box5 = new Box(world, assetManager.<Texture>get("box.png"), new Vector2(6f, 6f), new Vector2(2.5f, 2.5f));
        box4.setPosition(16f, 20f);
        box5.setPosition(20f, 14f);
        boxes.add(box4);
        boxes.add(box5);

        InteractionArea interactionArea1 = new InteractionArea(world, new Vector2(1f, 1f), new Interaction() {
            @Override
            public void interactIn(GameObject object) {
                if (object.getGroupIndex() != GroupIndices.BOX || object.getId() != 1) {
                    return;
                }

                flag1 = true;
            }

            @Override
            public void interactOut(GameObject object) {
                if (object.getGroupIndex() != GroupIndices.BOX || object.getId() != 1) {
                    return;
                }

                flag1 = false;
            }
        });
        interactionArea1.setPosition(new Vector2(0f, 12f));
        InteractionArea interactionArea2 = new InteractionArea(world, new Vector2(1f, 1f), new Interaction() {
            @Override
            public void interactIn(GameObject object) {
                if (object.getGroupIndex() != GroupIndices.BOX || object.getId() != 2) {
                    return;
                }

                flag2 = true;
            }

            @Override
            public void interactOut(GameObject object) {
                if (object.getGroupIndex() != GroupIndices.BOX || object.getId() != 2) {
                    return;
                }

                flag2 = false;
            }
        });
        interactionArea2.setPosition(new Vector2(-16f, -6f));
        InteractionArea interactionArea3 = new InteractionArea(world, new Vector2(1f, 1f), new Interaction() {
            @Override
            public void interactIn(GameObject object) {
                if (object.getGroupIndex() != GroupIndices.BOX || object.getId() != 3) {
                    return;
                }

                flag3 = true;
            }

            @Override
            public void interactOut(GameObject object) {
                if (object.getGroupIndex() != GroupIndices.BOX || object.getId() != 3) {
                    return;
                }

                flag3 = false;
            }
        });
        interactionArea3.setPosition(new Vector2(16f, -6f));

        completeTimer.setTask(new Timer.Task() {
            @Override
            public void doTask() {
                game.setScreen(new LoadingScreen(game, Screens.LEVEL1, "loads/level1.json"));
            }
        });
        finishArea.setPosition(new Vector2(0f, 32f));
    }

    @Override
    public void show() {
        super.show();
    }

    @Override
    public void update(float delta) {
        player.update(delta);

        for (BasicDoor door : doors) {
            door.update(delta);
        }
        for (Box box : boxes) {
            box.update(delta);
        }

        if (flag1 && flag2 && flag3) {
            door3.open();
        } else {
            door3.close();
        }
    }

    @Override
    public void draw() {
        map.draw(batch);
        for (BasicDoor door : doors) {
            door.draw(batch);
        }
        map.draw(batch);
        for (Box box : boxes) {
            box.draw(batch);
        }
        player.draw(batch);
    }
}
