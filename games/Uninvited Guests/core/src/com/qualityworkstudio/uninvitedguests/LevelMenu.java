package com.qualityworkstudio.uninvitedguests;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;

public class LevelMenu {

    public LevelMenu(Stage stage, AssetManager assetManager) {
        TextureRegionDrawable levelMenuTexture = new TextureRegionDrawable(assetManager.<Texture>get("levelmenu_bg.png"));
        TextureRegionDrawable levelButtonTexture = new TextureRegionDrawable(assetManager.<Texture>get("level_button.png"));
        TextureRegionDrawable startButtonTexture = new TextureRegionDrawable(assetManager.<Texture>get("level_start_button.png"));

        Group group = new Group();
        group.setSize(1344, 768);
        group.setPosition(960, 540, Align.center);

        Image background = new Image(levelMenuTexture);

        ImageButton button1 = new ImageButton(levelButtonTexture);

        Table holdTable = new Table();
        holdTable.setSize(1016, 448);
        holdTable.setPosition(672, 396, Align.center);

        HorizontalGroup horizontalGroup = new HorizontalGroup();
        ScrollPane scrollPane = new ScrollPane(horizontalGroup);
        horizontalGroup.addActor(button1);
        horizontalGroup.rowRight();
        holdTable.add(scrollPane);
        holdTable.row();

        final ImageButton startButton = new ImageButton(startButtonTexture);
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

        group.addActor(background);
        group.addActor(holdTable);
        group.addActor(startButton);

        stage.addActor(group);
    }
}
