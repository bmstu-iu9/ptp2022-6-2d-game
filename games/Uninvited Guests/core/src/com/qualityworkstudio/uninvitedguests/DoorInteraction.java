package com.qualityworkstudio.uninvitedguests;

/**
 * @author Andrey Karanik
 */

public class DoorInteraction implements Interaction {

    private Door door;

    public DoorInteraction(Door door) {
        this.door = door;
    }

    @Override
    public void interactIn() {
        door.open();
    }

    @Override
    public void interactOut() {
        door.close();
    }
}
