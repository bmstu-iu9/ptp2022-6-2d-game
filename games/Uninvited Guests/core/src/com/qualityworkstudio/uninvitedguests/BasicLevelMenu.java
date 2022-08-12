package com.qualityworkstudio.uninvitedguests;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;

import java.util.ArrayList;
import java.util.List;

public class BasicLevelMenu implements LevelMenu {

    private int selectedLevelIndex;
    private List<LevelButton> buttonList;
    private ImageButton startButton;

    private Drawable levelButtonImage;
    private Drawable selectedLevelButtonImage;
    private float levelImagePadTop;

    private HorizontalGroup horizontalGroup;

    public BasicLevelMenu(Stage stage, AssetManager assetManager, Game game) {
        TextureRegionDrawable levelMenuImage = new TextureRegionDrawable(assetManager.<Texture>get("levelmenu_bg.png"));
        TextureRegionDrawable startButtonImage = new TextureRegionDrawable(assetManager.<Texture>get("level_start_button.png"));

        levelButtonImage = new TextureRegionDrawable(assetManager.<Texture>get("level_button.png"));
        selectedLevelButtonImage = new TextureRegionDrawable(assetManager.<Texture>get("selected_level_button.png"));
        levelImagePadTop = 100;

        buttonList = new ArrayList<>();

        Table table = new Table();
        table.setBackground(levelMenuImage);
        table.setSize(levelMenuImage.getMinWidth(), levelMenuImage.getMinHeight());
        table.setPosition(stage.getWidth() / 2f, stage.getHeight() / 2f, Align.center);

        Table holdTable = new Table();
        holdTable.setSize(1016, 448);
        holdTable.setPosition(672, 396, Align.center);

        horizontalGroup = new HorizontalGroup();
        ScrollPane scrollPane = new ScrollPane(horizontalGroup);
        holdTable.add(scrollPane);
        holdTable.row();

        startButton = new ImageButton(startButtonImage);
        startButton.setPosition(672, 96, Align.center);
        startButton.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                startButton.getImage().setColor(0.75f, 0.75f, 0.75f, 1f);
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                startButton.getImage().setColor(1f, 1f, 1f, 1f);
            }
        });

        table.addActor(holdTable);
        table.addActor(startButton);

        stage.addActor(table);
    }

    @Override
    public void open() {

    }

    @Override
    public void close() {

    }

    public void addLevel(int map, Texture levelTexture) {
        TextureRegionDrawable levelImage = new TextureRegionDrawable(levelTexture);

        LevelButton.LevelButtonStyle style = new LevelButton.LevelButtonStyle();
        style.levelImage = levelImage;
        style.levelButtonImage = levelButtonImage;
        style.selectedLevelButtonImage = selectedLevelButtonImage;
        style.levelImagePadTop = levelImagePadTop;

        LevelButton button = new LevelButton(style,this, buttonList.size());

        horizontalGroup.addActor(button);
        horizontalGroup.rowRight();

        buttonList.add(button);
    }

    public void setSelectedLevelIndex(int index) {
        if (selectedLevelIndex != index) {
            buttonList.get(selectedLevelIndex).setSelected(false);
        }

        selectedLevelIndex = index;
    }

    public int getSelectedLevelIndex() {
        return selectedLevelIndex;
    }
}
