package com.qualityworkstudio.uninvitedguests;

/**
 * @author Andrey Karanik
 */

public class DoorInteraction implements Interaction {

    private BasicDoor door;

    public DoorInteraction(BasicDoor door) {
        this.door = door;
    }

    @Override
    public void interactIn(int groupIndex) {
        if (groupIndex != GroupIndices.PLAYER || door.getType() != BasicDoor.Type.GREEN) {
            return;
        }

        door.open();
    }

    @Override
    public void interactOut(int groupIndex) {
        if (groupIndex != GroupIndices.PLAYER || door.getType() != BasicDoor.Type.GREEN) {
            return;
        }

        door.close();
    }
}
