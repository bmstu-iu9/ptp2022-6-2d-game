package com.qualityworkstudio.uninvitedguests;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Andrey Karanik
 */

public class BasicLevelMenu implements LevelMenu {

    private Game game;
    private AssetManager assetManager;
    private GameSettings settings;

    private Table table;
    private int selectedLevelIndex;
    private List<LevelButton> buttonList;
    private ImageButton startButton;
    private ImageButton closeButton;

    private Drawable levelButtonImage;
    private Drawable selectedLevelButtonImage;
    private BitmapFont levelButtonFont;
    private float levelImagePadTop;
    private float labelPadTop;

    private HorizontalGroup horizontalGroup;

    public BasicLevelMenu(Stage stage, AssetManager manager, Game gm, GameSettings gameSettings) {
        game = gm;
        assetManager = manager;
        settings = gameSettings;
        TextureRegionDrawable levelMenuImage = new TextureRegionDrawable(assetManager.<Texture>get("levelmenu_bg.png"));
        TextureRegionDrawable startButtonImage = new TextureRegionDrawable(assetManager.<Texture>get("level_start_button.png"));
        TextureRegionDrawable closeButtonImage = new TextureRegionDrawable(assetManager.<Texture>get("level_close_button.png"));

        levelButtonImage = new TextureRegionDrawable(assetManager.<Texture>get("level_button.png"));
        selectedLevelButtonImage = new TextureRegionDrawable(assetManager.<Texture>get("selected_level_button.png"));
        levelButtonFont = assetManager.get("font.fnt", BitmapFont.class);
        levelImagePadTop = 10;
        labelPadTop = 50;

        buttonList = new ArrayList<>();

        table = new Table();
        table.setBackground(levelMenuImage);
        table.setSize(levelMenuImage.getMinWidth(), levelMenuImage.getMinHeight());
        table.setPosition(stage.getWidth() / 2f, stage.getHeight() / 2f, Align.center);
        table.setOrigin(table.getWidth() / 2f, table.getHeight() / 2f);
        table.setTransform(true);
        table.setScale(0f);

        Table holdTable = new Table();
        holdTable.setSize(1016, 448);

        horizontalGroup = new HorizontalGroup();
        ScrollPane scrollPane = new ScrollPane(horizontalGroup);
        holdTable.add(scrollPane);
        holdTable.row();

        startButton = new ImageButton(startButtonImage);
        startButton.setOrigin(startButton.getWidth() / 2f, startButton.getHeight() / 2f);
        startButton.setTransform(true);
        startButton.setScale(1f, 0f);
        startButton.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                startButton.addAction(Actions.scaleTo(0.9f, 0.9f, 0.1f, Interpolation.swingIn));
                startButton.getImage().setColor(0.75f, 0.75f, 0.75f, 1f);
                return super.touchDown(event, x, y, pointer, button);
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                startButton.addAction(Actions.scaleTo(1f, 1f, 0.1f, Interpolation.swingOut));
                startButton.getImage().setColor(1f, 1f, 1f, 1f);
            }

            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                game.setScreen(100 + selectedLevelIndex);
            }
        });

        closeButton = new ImageButton(closeButtonImage);
        closeButton.setOrigin(closeButton.getWidth() / 2f, closeButton.getHeight() / 2f);
        closeButton.setTransform(true);
        closeButton.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                closeButton.addAction(Actions.scaleTo(0.9f, 0.9f, 0.1f, Interpolation.swingIn));
                closeButton.getImage().setColor(0.75f, 0.75f, 0.75f, 1f);
                return super.touchDown(event, x, y, pointer, button);
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                closeButton.addAction(Actions.scaleTo(1f, 1f, 0.1f, Interpolation.swingOut));
                closeButton.getImage().setColor(1f, 1f, 1f, 1f);
            }
        });


        holdTable.setPosition(table.getWidth() / 2f, table.getHeight() / 2f + 12f, Align.center);
        table.addActor(holdTable);
        startButton.setPosition(table.getWidth() / 2f, 96f, Align.center);
        table.addActor(startButton);
        closeButton.setPosition(table.getWidth() - 128f, table.getHeight() - 110f, Align.center);
        table.addActor(closeButton);

        stage.addActor(table);
        selectedLevelIndex = -1;
    }

    @Override
    public void show() {
        table.addAction(Actions.scaleTo(1f, 1f, 0.5f, Interpolation.swingOut));
    }

    @Override
    public void hide() {
        table.addAction(Actions.scaleTo(0f, 0f, 0.5f, Interpolation.swingIn));
    }

    @Override
    public void addLevel(String name, Texture levelTexture) {
        TextureRegionDrawable levelImage = new TextureRegionDrawable(levelTexture);

        LevelButton.LevelButtonStyle style = new LevelButton.LevelButtonStyle();
        style.levelImage = levelImage;
        style.levelButtonImage = levelButtonImage;
        style.selectedLevelButtonImage = selectedLevelButtonImage;
        style.labelStyle = new Label.LabelStyle(levelButtonFont, Color.WHITE);
        style.levelImagePadTop = levelImagePadTop;
        style.labelPadTop = labelPadTop;

        LevelButton button = new LevelButton(style,this, name, buttonList.size());

        horizontalGroup.addActor(button);
        horizontalGroup.rowRight();

        buttonList.add(button);
    }

    public void setSelectedLevelIndex(int index) {
        if (selectedLevelIndex != index) {
            if (selectedLevelIndex != -1) {
                buttonList.get(selectedLevelIndex).setSelected(false);
            }
            if (index != -1) {
                startButton.addAction(Actions.scaleTo(1f, 1f, 0.25f, Interpolation.swingOut));
            } else {
                startButton.addAction(Actions.scaleTo(1f, 0f, 0.25f, Interpolation.swingIn));
            }
            selectedLevelIndex = index;
        }
    }

    public int getSelectedLevelIndex() {
        return selectedLevelIndex;
    }

    public ImageButton getCloseButton() {
        return closeButton;
    }
}
