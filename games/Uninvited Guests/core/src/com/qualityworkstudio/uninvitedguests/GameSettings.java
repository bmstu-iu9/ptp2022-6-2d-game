package com.qualityworkstudio.uninvitedguests;

/**
 * Describes game settings.
 * @author Andrey Karanik
 */

public class GameSettings {

    /**
     * This constant is the <em>standard</em> camera width.
     */
    public static final float STANDARD_CAMERA_SIZE = 128f;

    /**
     * This constant is the <em>standard</em> viewport width.
     */
    public static final float STANDARD_VIEWPORT_SIZE = 1920f;

    /**
     * The float value indicates camera width.
     */
    private float cameraSize;

    /**
     * The float value indicates viewport width.
     */
    private float viewportSize;

    /**
     * The boolean value indicates whether mobile mode is enabled. It's used to enable
     * additional user interface.
     */
    private boolean mobileMode;

    public GameSettings() {
        cameraSize = STANDARD_CAMERA_SIZE;
        viewportSize = STANDARD_VIEWPORT_SIZE;
    }

    public GameSettings(float cameraSize, float viewportSize, boolean mobileMode) {
        this.cameraSize = cameraSize;
        this.viewportSize = viewportSize;
        this.mobileMode = mobileMode;
    }

    public float getCameraSize() {
        return cameraSize;
    }

    public void setCameraSize(float cameraSize) {
        this.cameraSize = cameraSize;
    }

    public float getViewportSize() {
        return viewportSize;
    }

    public void setViewportSize(float viewportSize) {
        this.viewportSize = viewportSize;
    }

    public boolean isMobileMode() {
        return mobileMode;
    }

    public void setMobileMode(boolean mobileMode) {
        this.mobileMode = mobileMode;
    }
}
