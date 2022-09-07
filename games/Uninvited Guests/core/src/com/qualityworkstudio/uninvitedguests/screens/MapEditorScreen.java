package com.qualityworkstudio.uninvitedguests.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.qualityworkstudio.uninvitedguests.Game;
import com.qualityworkstudio.uninvitedguests.GameSettings;
import com.qualityworkstudio.uninvitedguests.Map;
import com.qualityworkstudio.uninvitedguests.MapData;
import com.qualityworkstudio.uninvitedguests.WallData;

import java.util.List;

public class MapEditorScreen extends ScreenAdapter {

    private GameSettings settings;
    private AssetManager assetManager;

    private SpriteBatch batch;
    private Viewport viewport;
    private Stage stage;

    private Image cursor;
    private Label label;
    private OrthographicCamera camera;
    private float cameraStep = 1f;

    private ShapeRenderer shapeRenderer;
    private int currentWallIndex;

    private Map map;
    private MapData mapData;
    private String fileName;

    public MapEditorScreen(Game game, String fileName) {
        settings = game.getSettings();
        assetManager = game.getAssetManager();
        this.fileName = fileName;

        viewport = new FitViewport(settings.getViewportSize(), settings.getViewportSize() * (
                (float)Gdx.graphics.getHeight() / Gdx.graphics.getWidth()));
        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);

        mapData = new MapData(fileName);
        for (String layer : mapData.getLayers()) {
            assetManager.load(layer, Texture.class);
        }
        assetManager.load("white_square.png", Texture.class);
        assetManager.finishLoading();

        Label.LabelStyle labelStyle = new Label.LabelStyle(assetManager.<BitmapFont>get("font.fnt"), Color.WHITE);
        label = new Label("WASD - movement\nE - add vertex\nQ - remove last vertex\n" +
                            "SHIFT + E - add wall\nSHIFT + Q - remove last wall\nSPACE - switch wall\n" +
                            "ENTER - save map data", labelStyle);
        label.setAlignment(Align.topLeft);
        label.setPosition(0f, stage.getHeight(), Align.topLeft);

        stage.addActor(label);

        cursor = new Image(new TextureRegionDrawable(assetManager.<Texture>get("white_square.png")));
        cursor.setSize(8f, 8f);
        cursor.setOrigin(Align.center);
        cursor.setPosition(stage.getWidth() / 2f, stage.getHeight() / 2f, Align.center);
        stage.addActor(cursor);

        batch = new SpriteBatch();
        camera = new OrthographicCamera(settings.getCameraSize(), settings.getCameraSize() *
                ((float) Gdx.graphics.getHeight() / Gdx.graphics.getWidth()));
        map = new Map(mapData, assetManager);

        shapeRenderer = new ShapeRenderer();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        stage.act(delta);
        camera.update();

        if (Gdx.input.isKeyJustPressed(Input.Keys.CAPS_LOCK)) {
            cameraStep = cameraStep == 1 ? 8f : 1f;
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.W)) {
            camera.position.set(camera.position.x, camera.position.y + cameraStep, 0f);
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.A)) {
            camera.position.set(camera.position.x - cameraStep, camera.position.y, 0f);
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.S)) {
            camera.position.set(camera.position.x, camera.position.y - cameraStep, 0f);
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.D)) {
            camera.position.set(camera.position.x + cameraStep, camera.position.y, 0f);
        }

        List<Vector2> vertices = mapData.getWallDataList().get(currentWallIndex).getVertices();

        if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT) && Gdx.input.isKeyJustPressed(Input.Keys.E)) {
            mapData.getWallDataList().add(new WallData(mapData.getWallDataList().size()));
            currentWallIndex = mapData.getWallDataList().size() - 1;
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.E)) {
            vertices.add(new Vector2(camera.position.x, camera.position.y));
        }

        if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT) && Gdx.input.isKeyJustPressed(Input.Keys.Q)) {
            if (mapData.getWallDataList().size() > 1) {
                mapData.getWallDataList().remove(mapData.getWallDataList().size() - 1);
                currentWallIndex = mapData.getWallDataList().size() - 1;
            }
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.Q)) {
            if (!vertices.isEmpty()) {
                vertices.remove(vertices.size() - 1);
            }
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            currentWallIndex = currentWallIndex < mapData.getWallDataList().size() - 1 ? currentWallIndex + 1 : 0;
        }


        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            FileHandle file = Gdx.files.local("assets/" + fileName);
            Json json = new Json();
            json.setOutputType(JsonWriter.OutputType.json);
            file.writeString(json.prettyPrint(mapData), false);
        }

        ScreenUtils.clear(Color.BLACK);

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        map.draw(batch);
        map.draw(batch);
        batch.end();

        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        if (!vertices.isEmpty()) {
            shapeRenderer.setColor(Color.GREEN);
            shapeRenderer.circle(vertices.get(0).x, vertices.get(0).y, 1f, 16);
            shapeRenderer.setColor(Color.YELLOW);
            shapeRenderer.circle(vertices.get(vertices.size() - 1).x, vertices.get(vertices.size() - 1).y, 1f, 16);
            shapeRenderer.setColor(Color.WHITE);
        }
        for (int i = 0; i < vertices.size() - 1; i++) {
            shapeRenderer.line(vertices.get(i).x, vertices.get(i).y, vertices.get(i + 1).x, vertices.get(i + 1).y);
        }
        shapeRenderer.end();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        for (int i = 0; i < vertices.size(); i++) {
            shapeRenderer.setColor(Color.BLUE);
            shapeRenderer.circle(vertices.get(i).x, vertices.get(i).y, 0.5f, 16);
        }
        shapeRenderer.end();


        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        camera.viewportWidth = settings.getCameraSize();
        camera.viewportHeight = settings.getCameraSize() * viewport.getScreenHeight() / viewport.getScreenWidth();
        camera.update();
    }

    @Override
    public void dispose() {
        batch.dispose();
        stage.dispose();
    }
}

