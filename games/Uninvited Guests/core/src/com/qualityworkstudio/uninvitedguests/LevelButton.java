package com.qualityworkstudio.uninvitedguests;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Align;

public class LevelButton extends Table {

    private LevelButtonStyle style;
    private LevelMenu levelMenu;
    private boolean selected;
    private int index;

    public LevelButton(LevelButtonStyle style, LevelMenu levelMenu, int index) {
        super();
        this.style = style;
        this.levelMenu = levelMenu;
        this.index = index;

        setBackground(style.levelButtonImage);
        align(Align.top);
        Image image = new Image(style.levelImage);
        add(image);
        padTop(style.levelImagePadTop);

        setTouchable(Touchable.enabled);
        addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                setSelected(!selected);
            }
        });
    }

    public void setSelected(boolean selected) {
        if (selected) {
            setBackground(style.selectedLevelButtonImage);
            levelMenu.setSelectedLevelIndex(index);
        } else {
            setBackground(style.levelButtonImage);
            if (levelMenu.getSelectedLevelIndex() == index) {
                levelMenu.setSelectedLevelIndex(-1);
            }
        }

        this.selected = selected;
    }

    public int getIndex() {
        return index;
    }

    public static class LevelButtonStyle {
        public Drawable levelImage;
        public Drawable levelButtonImage;
        public Drawable selectedLevelButtonImage;
        public float levelImagePadTop;

        public LevelButtonStyle() {
        }

        public LevelButtonStyle(Drawable levelImage, Drawable levelButtonImage, Drawable selectedLevelButtonImage, float levelImagePadTop) {
            this.levelImage = levelImage;
            this.levelButtonImage = levelButtonImage;
            this.selectedLevelButtonImage = selectedLevelButtonImage;
            this.levelImagePadTop = levelImagePadTop;
        }
    }
}
