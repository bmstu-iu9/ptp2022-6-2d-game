package com.qualityworkstudio.uninvitedguests;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * @author Andrey Karanik
 */

public class Map {

    private float size;
    private Texture[] layers;

    private int currentIndex;

    public Map(float size, Texture ... layers) {
        this.size = size;
        this.layers = layers;
    }

    public void drawLayer(SpriteBatch batch, int index) {
        batch.draw(layers[index], -size / 2, -size / 2, size, size);
    }

    public void draw(SpriteBatch batch) {
        batch.draw(layers[currentIndex++], -size / 2, -size / 2, size, size);
        currentIndex %= layers.length;
    }

    /**
     * Sets the map size.
     *
     * @param size a new map size.
     */
    public void setSize(int size) {
        this.size = size;
    }
}
