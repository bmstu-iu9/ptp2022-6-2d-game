package com.qualityworkstudio.uninvitedguests;

/**
 * @author Andrey Karanik
 */

public interface Interaction {

    /**
     * Called when something starts interacting.
     */
    void interactIn();

    /**
     * Called when something finishes interacting.
     */
    void interactOut();
}
