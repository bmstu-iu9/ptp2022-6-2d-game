package com.qualityworkstudio.uninvitedguests;

import java.util.ArrayList;

/**
 * @author Andrey Karanik
 */

public class DoorInteraction implements Interaction {

    private ArrayList<GameObject> objects;
    private BasicDoor door;

    public DoorInteraction(BasicDoor door) {
        this.door = door;
        objects = new ArrayList<>();
    }

    @Override
    public void interactIn(GameObject object) {
        if ((object.getGroupIndex() != GroupIndices.PLAYER && object.getGroupIndex() != GroupIndices.ENEMY) || door.getType() != BasicDoor.Type.GREEN) {
            return;
        }

        objects.add(object);
        door.open();
    }

    @Override
    public void interactOut(GameObject object) {
        if ((object.getGroupIndex() != GroupIndices.PLAYER && object.getGroupIndex() != GroupIndices.ENEMY) || door.getType() != BasicDoor.Type.GREEN) {
            return;
        }

        objects.remove(object);

        if (objects.isEmpty()) {
            door.close();
        }
    }
}
