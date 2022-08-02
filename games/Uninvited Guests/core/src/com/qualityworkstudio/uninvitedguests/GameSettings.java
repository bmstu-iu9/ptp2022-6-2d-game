package com.qualityworkstudio.uninvitedguests;

/**
 * Describes game settings.
 *
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
     * The float value specifies the camera width.
     */
    private float cameraSize;

    /**
     * The float value specifies the viewport width.
     */
    private float viewportSize;

    /**
     * The boolean value indicates whether the mobile mode is enabled.
     */
    private boolean mobileMode;

    /**
     * The boolean value indicates whether the developer mode is enabled.
     */
    private boolean developerMode;

    public GameSettings() {
        cameraSize = STANDARD_CAMERA_SIZE;
        viewportSize = STANDARD_VIEWPORT_SIZE;
    }

    public GameSettings(float cameraSize, float viewportSize, boolean mobileMode) {
        this.cameraSize = cameraSize;
        this.viewportSize = viewportSize;
        this.mobileMode = mobileMode;
    }

    /**
     * Returns the camera size.
     *
     * @return the camera size.
     */
    public float getCameraSize() {
        return cameraSize;
    }

    /**
     * Sets the camera size.
     *
     * @param cameraSize a new camera size.
     */
    public void setCameraSize(float cameraSize) {
        this.cameraSize = cameraSize;
    }

    /**
     * Returns the viewport size.
     *
     * @return the viewport size.
     */
    public float getViewportSize() {
        return viewportSize;
    }

    /**
     * Sets the viewport size.
     *
     * @param viewportSize a new viewport size.
     */
    public void setViewportSize(float viewportSize) {
        this.viewportSize = viewportSize;
    }

    /**
     * Returns true when the mobile mode is enabled.
     *
     * @return whether the mobile mode is enabled.
     */
    public boolean isMobileMode() {
        return mobileMode;
    }

    /**
     * Sets the mobile mode. Default is false.
     *
     * @param mobileMode a value of {@code true} for the mobileMode key indicates the mobile mode will be enabled,
     *                   and a value of {@code false} indicates the mobile mode will not be enabled.
     */
    public void setMobileMode(boolean mobileMode) {
        this.mobileMode = mobileMode;
    }

    /**
     * Returns true when the developer mode is enabled.
     *
     * @return whether the developer mode is enabled.
     */
    public boolean isDeveloperMode() {
        return developerMode;
    }

    /**
     * Sets the developer mode. Default is false.
     *
     * @param developerMode whether the developer mode will be enabled.
     */
    public void setDeveloperMode(boolean developerMode) {
        this.developerMode = developerMode;
    }
}
