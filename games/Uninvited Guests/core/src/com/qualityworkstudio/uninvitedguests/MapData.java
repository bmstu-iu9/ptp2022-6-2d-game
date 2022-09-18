package com.qualityworkstudio.uninvitedguests;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Andrey Karanik
 */

public class MapData {
    private String name;
    private int size;
    private String[] layers;
    private List<WallData> wallDataList;

    /**
     * Constructs an object of empty map data.
     */
    public MapData() {
        wallDataList = new ArrayList<>();
    }

    /**
     * Constructs an object of map data from a json file.
     *
     * @param fileName the json file name.
     */
    public MapData(String fileName) {
        FileHandle file = Gdx.files.internal(fileName);
        wallDataList = new ArrayList<>();
        if (file.exists()) {
            JsonReader jsonReader = new JsonReader();
            JsonValue value = jsonReader.parse(file);
            setName(value.get("name").asString());
            setSize(value.get("size").asInt());
            setLayers(value.get("layers").asStringArray());
            JsonValue currentValue = value.get("wallDataList").get(0);
            for (int i = 0; i < value.get("wallDataList").size; i++, currentValue = value.get("wallDataList").get(i)) {
                WallData wallData = new WallData(currentValue.get("id").asInt());
                for (JsonValue jsonValue : currentValue.get("vertices")) {
                    wallData.getVertices().add(new Vector2(jsonValue.get("x").asFloat(), jsonValue.get("y").asFloat()));
                }
                wallDataList.add(wallData);
            }
        }
    }

    /**
     * Sets the map name.
     *
     * @param name a new map name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the map name.
     *
     * @return the map name.
     */
    public String getName() {
        return name;
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
    public int getSize() {
        return size;
    }

    /**
     * Sets the map layers.
     *
     * @param layers map layers.
     */
    public void setLayers(String[] layers) {
        this.layers = layers;
    }

    /**
     * Gets the map layers.
     *
     * @return the map layers.
     */
    public String[] getLayers() {
        return layers;
    }

    /**
     * Sets the wall data list.
     *
     * @param wallDataList a new wall data list.
     */
    public void setWallDataList(List<WallData> wallDataList) {
        this.wallDataList = wallDataList;
    }

    /**
     * Gets the wall data list.
     *
     * @return the wall data list.
     */
    public List<WallData> getWallDataList() {
        return wallDataList;
    }
}
