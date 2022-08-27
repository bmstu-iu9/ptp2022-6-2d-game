package com.qualityworkstudio.uninvitedguests;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.qualityworkstudio.uninvitedguests.joystick.BasicJoystick;
import com.qualityworkstudio.uninvitedguests.joystick.Joystick;

/**
 * @author Andrey Karanik
 * @see PlayerInterface
 */

public class MobilePlayerInterface implements PlayerInterface {

    private Joystick movementJoystick;
    private Joystick rotationJoystick;

    public MobilePlayerInterface(Stage stage, AssetManager assetManager) {
        movementJoystick = new BasicJoystick(stage, assetManager, "movement_joystick_bg.png");
        rotationJoystick = new BasicJoystick(stage, assetManager, "rotation_joystick_bg.png");
        movementJoystick.setSize(new Vector2(420, 420));
        rotationJoystick.setSize(new Vector2(420, 420));
        movementJoystick.setRadius(140);
        rotationJoystick.setRadius(140);
        movementJoystick.setPosition(new Vector2(235, 235));
        rotationJoystick.setPosition(new Vector2(stage.getWidth() - 235, 235));
    }

    public void show() {
        movementJoystick.show();
        rotationJoystick.show();
    }

    public void hide() {
        movementJoystick.hide();
        rotationJoystick.hide();
    }

    public Joystick getMovementJoystick() {
        return movementJoystick;
    }

    public Joystick getRotationJoystick() {
        return rotationJoystick;
    }
}
