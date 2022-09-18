package com.qualityworkstudio.uninvitedguests;

import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Andrey Karanik
 */

public class WallData {

    private int id;
    private List<Vector2> vertices;

    /**
     * Constructs an object of wall data.
     *
     * @param id the wall id.
     */
    public WallData(int id) {
        this.id = id;
        vertices = new ArrayList<>();
    }

    /**
     * Constructs an object of wall data.
     *
     * @param id the wall id.
     * @param vertices the wall vertices.
     */
    public WallData(int id, Vector2[] vertices) {
        this.id = id;
        this.vertices = Arrays.asList(vertices);
    }

    /**
     * Sets the wall id.
     *
     * @param id the wall id.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the wall id.
     *
     * @return the wall id.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the wall vertices.
     *
     * @param vertices the wall vertices.
     */
    public void setVertices(List<Vector2> vertices) {
        this.vertices = vertices;
    }

    /**
     * Gets the wall vertices.
     *
     * @return the wall vertices.
     */
    public List<Vector2> getVertices() {
        return vertices;
    }
}
