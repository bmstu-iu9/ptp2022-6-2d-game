package com.qualityworkstudio.uninvitedguests;

/**
 * @author Andrey Karanik
 */

public interface Interaction {

    /**
     * Called when the game object starts interacting.
     */
    void interactIn(GameObject object);

    /**
     * Called when the game object finishes interacting.
     */
    void interactOut(GameObject object);
}
