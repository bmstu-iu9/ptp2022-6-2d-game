package com.qualityworkstudio.uninvitedguests;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LoadingData {
    private List<String> textureFiles;
    private List<String> bitmapFontFiles;
    private List<String> soundFiles;
    private List<String> musicFiles;
    private List<String> unloadFiles;

    public LoadingData() {
        textureFiles = new ArrayList<>();
        bitmapFontFiles = new ArrayList<>();
        soundFiles = new ArrayList<>();
        musicFiles = new ArrayList<>();
        unloadFiles = new ArrayList<>();
    }

    public LoadingData(String fileName) {
        FileHandle file = Gdx.files.internal(fileName);
        if (file.exists()) {
            JsonReader jsonReader = new JsonReader();
            JsonValue value = jsonReader.parse(file);
            textureFiles = Arrays.asList(value.get("textureFiles").asStringArray());
            bitmapFontFiles = Arrays.asList(value.get("bitmapFontFiles").asStringArray());
            soundFiles = Arrays.asList(value.get("soundFiles").asStringArray());
            musicFiles = Arrays.asList(value.get("musicFiles").asStringArray());
            unloadFiles = Arrays.asList(value.get("unloadFiles").asStringArray());
        } else {
            throw new RuntimeException("File not found.");
        }
    }

    public LoadingData(List<String> textureFiles, List<String> bitmapFontFiles, List<String> soundFiles,
                       List<String> musicFiles, List<String> unloadFiles) {
        this.textureFiles = textureFiles;
        this.bitmapFontFiles = bitmapFontFiles;
        this.soundFiles = soundFiles;
        this.musicFiles = musicFiles;
        this.unloadFiles = unloadFiles;
    }

    public void setTextureFiles(List<String> textureFiles) {
        this.textureFiles = textureFiles;
    }

    public void setBitmapFontFiles(List<String> bitmapFontFiles) {
        this.bitmapFontFiles = bitmapFontFiles;
    }

    public void setSoundFiles(List<String> soundFiles) {
        this.soundFiles = soundFiles;
    }

    public void setMusicFiles(List<String> musicFiles) {
        this.musicFiles = musicFiles;
    }

    public void setUnloadFiles(List<String> unloadFiles) {
        this.unloadFiles = unloadFiles;
    }

    public List<String> getTextureFiles() {
        return textureFiles;
    }

    public List<String> getBitmapFontFiles() {
        return bitmapFontFiles;
    }

    public List<String> getSoundFiles() {
        return soundFiles;
    }

    public List<String> getMusicFiles() {
        return musicFiles;
    }

    public List<String> getUnloadFiles() {
        return unloadFiles;
    }
}
