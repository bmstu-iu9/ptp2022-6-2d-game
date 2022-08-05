package com.qualityworkstudio.uninvitedguests;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

public class BasicContactListener implements ContactListener {

    @Override
    public void beginContact(Contact contact) {
        Fixture a = contact.getFixtureA();
        Fixture b = contact.getFixtureB();
        System.out.println(a.getFilterData().groupIndex + " " + b.getFilterData().groupIndex);

        if (a.getFilterData().groupIndex == GroupIndices.PLAYER && b.getFilterData().groupIndex == GroupIndices.DOOR) {
            Door door = (Door)b.getBody().getUserData();
            door.open();
        }

        if (a.getFilterData().groupIndex == GroupIndices.DOOR && b.getFilterData().groupIndex == GroupIndices.PLAYER) {
            Door door = (Door)a.getBody().getUserData();
            door.open();
        }
    }

    @Override
    public void endContact(Contact contact) {
        Fixture a = contact.getFixtureA();
        Fixture b = contact.getFixtureB();

        if (a.getFilterData().groupIndex == GroupIndices.PLAYER && b.getFilterData().groupIndex == GroupIndices.DOOR) {
            Door door = (Door)b.getBody().getUserData();
            door.close();
        }

        if (a.getFilterData().groupIndex == GroupIndices.DOOR && b.getFilterData().groupIndex == GroupIndices.PLAYER) {
            Door door = (Door)a.getBody().getUserData();
            door.close();
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
