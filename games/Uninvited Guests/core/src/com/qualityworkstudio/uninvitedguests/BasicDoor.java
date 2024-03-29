package com.qualityworkstudio.uninvitedguests;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

/**
 * @author Andrey Karanik
 */

public class BasicDoor extends Door {

    private int type;

    private InteractionArea interactionArea;

    private Texture greenDoorTexture;
    private Texture yellowDoorTexture;
    private Texture redDoorTexture;

    /**
     * Constructs a basic door.
     *
     * @param world a world object.
     * @param assetManager the asset manager
     */
    public BasicDoor(World world, AssetManager assetManager) {
        super(world, new Vector2(3f, 1.687f), new Vector2(6f, 4f), assetManager.<Texture>get("green_door_part.png"));

        greenDoorTexture = assetManager.<Texture>get("green_door_part.png");
        yellowDoorTexture = assetManager.<Texture>get("yellow_door_part.png");
        redDoorTexture = assetManager.<Texture>get("red_door_part.png");

        interactionArea = new InteractionArea(world, new Vector2(6f, 6f), new DoorInteraction(this));

        type = Type.GREEN;
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        interactionArea.setPosition(getPosition());
        interactionArea.setRotation(getRotation());
    }

    @Override
    public void open() {
        setState(IS_OPENING);
    }

    @Override
    public void close() {
        setState(IS_CLOSING);
    }

    public void setType(int type) {
        switch (type) {
            case Type.GREEN:
                setTexture(greenDoorTexture);
                break;
            case Type.YELLOW:
                setTexture(yellowDoorTexture);
                break;
            case Type.RED:
                setTexture(redDoorTexture);
                break;
        }

        this.type = type;
    }

    public int getType() {
        return type;
    }

    public static class Type {
        public static final int GREEN = 1;
        public static final int YELLOW = 2;
        public static final int RED = 4;
    }
}
