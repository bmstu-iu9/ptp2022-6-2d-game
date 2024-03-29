package com.qualityworkstudio.uninvitedguests;

/**
 * @author Andrey Karanik
 * @see BasicPlayerController
 * @see MobilePlayerController
 */

public interface PlayerController {

    void shootAndReload();
    /**
     * Moves the {@link Player}.
     */
    void move();

    /**
     * Rotates the {@link Player}.
     */
    void look();
}
