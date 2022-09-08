package com.qualityworkstudio.uninvitedguests.screens;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.qualityworkstudio.uninvitedguests.BasicDoor;
import com.qualityworkstudio.uninvitedguests.Box;
import com.qualityworkstudio.uninvitedguests.Game;
import com.qualityworkstudio.uninvitedguests.GameObject;
import com.qualityworkstudio.uninvitedguests.GroupIndices;
import com.qualityworkstudio.uninvitedguests.Interaction;
import com.qualityworkstudio.uninvitedguests.InteractionArea;
import com.qualityworkstudio.uninvitedguests.Map;
import com.qualityworkstudio.uninvitedguests.MapData;
import com.qualityworkstudio.uninvitedguests.Timer;

import java.util.ArrayList;

/**
 * @author Andrey Karanik
 */

public class Level1 extends LevelScreen {

    private Map map;
    private ArrayList<BasicDoor> doors;

    private Box box;

    private InteractionArea interactionArea;

    public Level1(Game gm) {
        super(gm);

        map = new Map(new MapData("maps/lvl1_map.json"), assetManager, world);
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
        box.setPosition(12f, 0f);

        interactionArea = new InteractionArea(world, new Vector2(1f, 1f), new Interaction() {
            @Override
            public void interactIn(GameObject object) {
                if (object.getGroupIndex() != GroupIndices.BOX) {
                    return;
                }

                door3.open();
            }

            @Override
            public void interactOut(GameObject object) {
                if (object.getGroupIndex() != GroupIndices.BOX) {
                    return;
                }

                door3.close();
            }
        });
        interactionArea.setPosition(new Vector2(0f, 12f));

        completeTimer.setTask(new Timer.Task() {
            @Override
            public void doTask() {
                game.setScreen(new LoadingScreen(game, Screens.MAIN_SCREEN, "loads/main.json"));
                game.setLevelComplete(0, true);
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
        box.update(delta);
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
}
