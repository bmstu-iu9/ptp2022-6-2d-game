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

        if (a.getFilterData().groupIndex == GroupIndices.PLAYER && b.getFilterData().groupIndex == GroupIndices.DOOR) {
            InteractionArea area = (InteractionArea)b.getBody().getUserData();
            area.setInteracted(true);
            Door door = (Door)area.getData();
            door.open();
            return;
        }

        if (a.getFilterData().groupIndex == GroupIndices.DOOR && b.getFilterData().groupIndex == GroupIndices.PLAYER) {
            InteractionArea area = (InteractionArea)a.getBody().getUserData();
            area.setInteracted(true);
            Door door = (Door)area.getData();
            door.open();
            return;
        }

        if (a.getFilterData().groupIndex == GroupIndices.PLAYER && b.getFilterData().groupIndex == GroupIndices.LEVEL_MENU_AREA) {
            InteractionArea area = (InteractionArea)b.getBody().getUserData();
            area.setInteracted(true);
            LevelMenu menu = (LevelMenu)area.getData();
            menu.show();
            return;
        }

        if (a.getFilterData().groupIndex == GroupIndices.LEVEL_MENU_AREA && b.getFilterData().groupIndex == GroupIndices.PLAYER) {
            InteractionArea area = (InteractionArea)a.getBody().getUserData();
            area.setInteracted(true);
            LevelMenu menu = (LevelMenu)area.getData();
            menu.show();
        }
    }

    @Override
    public void endContact(Contact contact) {
        Fixture a = contact.getFixtureA();
        Fixture b = contact.getFixtureB();

        if (a.getFilterData().groupIndex == GroupIndices.PLAYER && b.getFilterData().groupIndex == GroupIndices.DOOR) {
            InteractionArea area = (InteractionArea)b.getBody().getUserData();
            area.setInteracted(false);
            Door door = (Door)area.getData();
            door.close();
        }

        if (a.getFilterData().groupIndex == GroupIndices.DOOR && b.getFilterData().groupIndex == GroupIndices.PLAYER) {
            InteractionArea area = (InteractionArea)a.getBody().getUserData();
            area.setInteracted(false);
            Door door = (Door)area.getData();
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
