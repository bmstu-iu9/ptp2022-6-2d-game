package com.qualityworkstudio.uninvitedguests.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.qualityworkstudio.uninvitedguests.BasicDoor;
import com.qualityworkstudio.uninvitedguests.Game;
import com.qualityworkstudio.uninvitedguests.GameSettings;
import com.qualityworkstudio.uninvitedguests.Map;

import java.util.ArrayList;

public class Level1 extends LevelScreen {

    private Map map;
    private ArrayList<BasicDoor> doors;

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
        BasicDoor door3 = new BasicDoor(world, assetManager);
        door3.setType(BasicDoor.Type.YELLOW);
        door3.setPosition(0f, 32f);
        doors.add(door3);
        player.setPosition(0f, -36f);
    }

    @Override
    public void update(float delta) {
        player.update(delta);
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
        player.draw(batch);
    }
}
