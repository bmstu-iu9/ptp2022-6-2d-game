package com.qualityworkstudio.uninvitedguests;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Align;

public class LevelButton extends Table {

    private LevelButtonStyle style;
    private BasicLevelMenu levelMenu;
    private boolean selected;
    private int index;

    public LevelButton(LevelButtonStyle style, BasicLevelMenu menu, int i) {
        super();
        this.style = style;
        levelMenu = menu;
        index = i;

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
                if (selected) {
                    levelMenu.setSelectedLevelIndex(index);
                } else {
                    levelMenu.setSelectedLevelIndex(-1);
                }
            }
        });
    }

    public void setSelected(boolean selected) {
        if (selected) {
            setBackground(style.selectedLevelButtonImage);
        } else {
            setBackground(style.levelButtonImage);
        }

        this.selected = selected;
    }

    public boolean isSelected() {
        return selected;
    }

    public int getIndex() {
        return index;
    }

    public static class LevelButtonStyle {
        public Drawable levelImage;
        public Drawable levelButtonImage;
        public Drawable selectedLevelButtonImage;
        public Label.LabelStyle labelStyle;
        public float levelImagePadTop;

        public LevelButtonStyle() {
        }

        public LevelButtonStyle(Drawable levelImage, Drawable levelButtonImage, Drawable selectedLevelButtonImage,
                                Label.LabelStyle labelStyle, float levelImagePadTop) {
            this.levelImage = levelImage;
            this.levelButtonImage = levelButtonImage;
            this.selectedLevelButtonImage = selectedLevelButtonImage;
            this.labelStyle = labelStyle;
            this.levelImagePadTop = levelImagePadTop;
        }
    }
}
