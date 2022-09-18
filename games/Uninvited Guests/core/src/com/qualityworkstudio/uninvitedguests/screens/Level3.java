package com.qualityworkstudio.uninvitedguests.screens;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.qualityworkstudio.uninvitedguests.BasicDoor;
import com.qualityworkstudio.uninvitedguests.Box;
import com.qualityworkstudio.uninvitedguests.Door;
import com.qualityworkstudio.uninvitedguests.Enemy;
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

/**
 * @author Andrey Karanik
 */

public class Level3 extends LevelScreen {

    private Map map;
    private ArrayList<BasicDoor> doors;
    private ArrayList<Box> boxes;
    private BasicDoor door3;

    private boolean flag1;
    private boolean flag2;

    private Enemy enemy1;
    private Enemy enemy2;
    private Enemy enemy3;

    public Level3(Game gm) {
        super(gm);

        map = new Map(new MapData("maps/lvl3_map.json"), assetManager, world);
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

        Box box1 = new Box(world, assetManager.<Texture>get("box_lifo.png"), new Vector2(6f, 6f), new Vector2(2.5f, 2.5f));
        box1.setId(1);
        box1.setPosition(16f, 8f);
        boxes.add(box1);
        Box box2 = new Box(world, assetManager.<Texture>get("box_fifo.png"), new Vector2(6f, 6f), new Vector2(2.5f, 2.5f));
        box2.setId(2);
        box2.setPosition(-12f, 22f);
        boxes.add(box2);
        Box box3 = new Box(world, assetManager.<Texture>get("box_jdk.png"), new Vector2(6f, 6f), new Vector2(2.5f, 2.5f));
        box3.setId(3);
        box3.setPosition(22f, 24f);
        boxes.add(box3);
        Box box4 = new Box(world, assetManager.<Texture>get("box_oop.png"), new Vector2(6f, 6f), new Vector2(2.5f, 2.5f));
        box4.setId(4);
        box4.setPosition(-22f, 24f);
        boxes.add(box4);

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
        interactionArea1.setPosition(new Vector2(-16f, 16f));
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
        interactionArea2.setPosition(new Vector2(16f, 16f));

        enemy1 = new Enemy(world, assetManager, 2f, new Vector2(16f, 16f), 24f);
        enemy1.setPosition(-10f, 0f);
        enemy1.setHomePosition(new Vector2(-10f, 0f));
        enemy2 = new Enemy(world, assetManager, 2f, new Vector2(16f, 16f), 24f);
        enemy2.setPosition(0f, 0f);
        enemy2.setHomePosition(new Vector2(0f, 0f));
        enemy3 = new Enemy(world, assetManager, 2f, new Vector2(16f, 16f), 24f);
        enemy3.setPosition(10f, 0f);
        enemy3.setHomePosition(new Vector2(10f, 0f));

        completeTimer.setTask(new Timer.Task() {
            @Override
            public void doTask() {
                game.setScreen(new LoadingScreen(game, Screens.MAIN_SCREEN, "loads/main.json"));
                game.setLevelComplete(2, true);
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
        enemy1.update(delta);
        enemy2.update(delta);
        enemy3.update(delta);

        for (BasicDoor door : doors) {
            door.update(delta);
        }
        for (Box box : boxes) {
            box.update(delta);
        }

        if (flag1 && flag2) {
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
        enemy1.draw(batch);
        enemy2.draw(batch);
        enemy3.draw(batch);
        player.draw(batch);
    }
}
