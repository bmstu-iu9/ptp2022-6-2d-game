package com.qualityworkstudio.uninvitedguests;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * @author Andrey Karanik
 */

public abstract class GameObject {

    private int id;
    private int groupIndex;

    public GameObject() {
    }

    public GameObject(int id, int groupIndex) {
        this.id = id;
        this.groupIndex = groupIndex;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setGroupIndex(int groupIndex) {
        this.groupIndex = groupIndex;
    }

    public int getGroupIndex() {
        return groupIndex;
    }

    public abstract void update(float deltaTime);

    public abstract void draw(SpriteBatch batch);

    @Override
    public String toString() {
        return "GameObject{" +
                "id=" + id +
                ", groupIndex=" + groupIndex +
                '}';
    }
}
