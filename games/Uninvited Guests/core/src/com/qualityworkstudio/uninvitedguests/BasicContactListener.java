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

        if (a.getFilterData().groupIndex != GroupIndices.UNKNOWN && b.getFilterData().groupIndex == GroupIndices.INTERACTION_AREA) {
            InteractionArea area = (InteractionArea)b.getBody().getUserData();
            area.getInteraction().interactIn((GameObject)a.getBody().getUserData());
            return;
        }

        if (a.getFilterData().groupIndex == GroupIndices.INTERACTION_AREA && b.getFilterData().groupIndex != GroupIndices.UNKNOWN) {
            InteractionArea area = (InteractionArea)a.getBody().getUserData();
            area.getInteraction().interactIn((GameObject)b.getBody().getUserData());
            return;
        }
    }

    @Override
    public void endContact(Contact contact) {
        Fixture a = contact.getFixtureA();
        Fixture b = contact.getFixtureB();

        if (a.getFilterData().groupIndex != GroupIndices.UNKNOWN && b.getFilterData().groupIndex == GroupIndices.INTERACTION_AREA) {
            InteractionArea area = (InteractionArea)b.getBody().getUserData();
            area.getInteraction().interactOut((GameObject)a.getBody().getUserData());
            return;
        }

        if (a.getFilterData().groupIndex == GroupIndices.INTERACTION_AREA && b.getFilterData().groupIndex != GroupIndices.UNKNOWN) {
            InteractionArea area = (InteractionArea)a.getBody().getUserData();
            area.getInteraction().interactOut((GameObject)b.getBody().getUserData());
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
