package com.qualityworkstudio.uninvitedguests;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

/**
 * @author Andrey Karanik
 */

public class BasicContactListener implements ContactListener {

    @Override
    public void beginContact(Contact contact) {
        Fixture a = contact.getFixtureA();
        Fixture b = contact.getFixtureB();

        if (b.getFilterData().groupIndex == GroupIndices.INTERACTION_AREA) {
            InteractionArea area = (InteractionArea)b.getBody().getUserData();
            area.getInteraction().interactIn(a.getFilterData().groupIndex);
            return;
        }

        if (a.getFilterData().groupIndex == GroupIndices.INTERACTION_AREA) {
            InteractionArea area = (InteractionArea)a.getBody().getUserData();
            area.getInteraction().interactIn(b.getFilterData().groupIndex);
            return;
        }
    }

    @Override
    public void endContact(Contact contact) {
        Fixture a = contact.getFixtureA();
        Fixture b = contact.getFixtureB();

        if (b.getFilterData().groupIndex == GroupIndices.INTERACTION_AREA) {
            InteractionArea area = (InteractionArea)b.getBody().getUserData();
            area.getInteraction().interactOut(a.getFilterData().groupIndex);
            return;
        }

        if (a.getFilterData().groupIndex == GroupIndices.INTERACTION_AREA) {
            InteractionArea area = (InteractionArea)a.getBody().getUserData();
            area.getInteraction().interactOut(b.getFilterData().groupIndex);
            return;
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
