package com.qualityworkstudio.uninvitedguests;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

/**
 * @author Andrey Karanik
 */

public class Map {

    private float size;
    private Texture[] layers;
    private Wall[] walls;

    private int currentIndex;

    /**
     * Constructs a map.
     *
     * @param size the map size.
     * @param layers the layers.
     * @param walls the walls.
     */
    public Map(float size, Texture[] layers, Wall[] walls) {
        this.size = size;
        this.layers = layers;
        this.walls = walls;
    }

    /**
     * Constructs a map from the map data.
     *
     * @param data the map data.
     * @param assetManager the asset manager.
     */
    public Map(MapData data, AssetManager assetManager) {
        this.size = data.getSize();
        layers = new Texture[data.getLayers().length];
        for (int i = 0; i < layers.length; i++) {
            layers[i] = assetManager.get(data.getLayers()[i]);
        }
    }

    /**
     * Constructs a map from the map data.
     *
     * @param data the map data.
     * @param assetManager the asset manager.
     * @param world the world object.
     */
    public Map(MapData data, AssetManager assetManager, World world) {
        this(data, assetManager);
        walls = new Wall[data.getWallDataList().size()];
        for (int i = 0; i < walls.length; i++) {
            walls[i] = new Wall(data.getWallDataList().get(i), world);
        }
    }

    /**
     * Draws the layer at the index.
     *
     * @param batch the sprite batch.
     * @param index the index of layer.
     */
    public void drawLayer(SpriteBatch batch, int index) {
        batch.draw(layers[index], -size / 2, -size / 2, size, size);
    }

    /**
     * Draws the layer.
     *
     * @param batch the sprite batch.
     */
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

    /**
     * Gets the map size.
     *
     * @return the map size.
     */
    public float getSize() {
        return size;
    }

    /**
     * Gets the map layers.
     *
     * @return the map layers.
     */
    public Texture[] getLayers() {
        return layers;
    }

    /**
     * Gets the map walls.
     *
     * @return the map walls.
     */
    public Wall[] getWalls() {
        return walls;
    }
}
