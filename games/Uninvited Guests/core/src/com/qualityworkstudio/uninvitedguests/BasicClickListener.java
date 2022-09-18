package com.qualityworkstudio.uninvitedguests;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class BasicClickListener extends ClickListener {

    private Actor actor;

    public BasicClickListener(Actor actor) {
        this.actor = actor;
    }

    @Override
    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        actor.addAction(Actions.scaleTo(0.9f, 0.9f, 0.1f, Interpolation.swingIn));
        actor.setColor(0.75f, 0.75f, 0.75f, 1f);
        return super.touchDown(event, x, y, pointer, button);
    }

    @Override
    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
        super.touchUp(event, x, y, pointer, button);
        actor.addAction(Actions.scaleTo(1f, 1f, 0.1f, Interpolation.swingOut));
        actor.setColor(1f, 1f, 1f, 1f);
    }
}
