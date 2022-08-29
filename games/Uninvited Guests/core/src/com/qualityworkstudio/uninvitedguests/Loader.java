package com.qualityworkstudio.uninvitedguests;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class Loader {

    public final static int TEXTURE_FILE = 0;
    public final static int BITMAP_FONT_FILE = 1;
    public final static int SOUND_FILE = 2;
    public final static int MUSIC_FILE = 3;

    private List<String> loadedTextureFiles;
    private List<String> loadedBitmapFontFiles;
    private List<String> loadedSoundFiles;
    private List<String> loadedMusicFiles;

    private List<String> textureFiles;
    private List<String> bitmapFontFiles;
    private List<String> soundFiles;
    private List<String> musicFiles;

    private AssetManager assetManager;

    public Loader(AssetManager assetManager) {
        this.assetManager = assetManager;

        loadedTextureFiles = new ArrayList<>();
        loadedBitmapFontFiles = new ArrayList<>();
        loadedSoundFiles = new ArrayList<>();
        loadedMusicFiles = new ArrayList<>();

        textureFiles = new ArrayList<>();
        bitmapFontFiles = new ArrayList<>();
        soundFiles = new ArrayList<>();
        musicFiles = new ArrayList<>();
    }

    public synchronized void add(String fileName, int fileType) {
        switch (fileType) {
            case TEXTURE_FILE:
                if (!textureFiles.contains(fileName)) {
                    textureFiles.add(fileName);
                }
                break;
            case BITMAP_FONT_FILE:
                if (!bitmapFontFiles.contains(fileName)) {
                    bitmapFontFiles.add(fileName);
                }
                break;
            case SOUND_FILE:
                if (!soundFiles.contains(fileName)) {
                    soundFiles.add(fileName);
                }
                break;
            case MUSIC_FILE:
                if (!musicFiles.contains(fileName)) {
                    musicFiles.add(fileName);
                }
                break;
        }
    }

    public synchronized void add(Collection<String> fileNames, int fileType) {
        for (String name : fileNames) {
            add(name, fileType);
        }
    }

    public synchronized void add(String fileName) {
        FileHandle file = Gdx.files.internal(fileName);
        if (file.exists()) {
            JsonReader jsonReader = new JsonReader();
            JsonValue value = jsonReader.parse(file);
            add(Arrays.asList(value.get("textureFiles").asStringArray()), TEXTURE_FILE);
            add(Arrays.asList(value.get("bitmapFontFiles").asStringArray()), BITMAP_FONT_FILE);
            add(Arrays.asList(value.get("soundFiles").asStringArray()), SOUND_FILE);
            add(Arrays.asList(value.get("musicFiles").asStringArray()), MUSIC_FILE);
        } else {
            throw new RuntimeException("File not found.");
        }
    }

    public synchronized void load() {
        for (String fileName : textureFiles) {
            if (!assetManager.isLoaded(fileName)) {
                assetManager.load(fileName, Texture.class);
                loadedTextureFiles.add(fileName);
            }
        }
        textureFiles.clear();

        for (String fileName : bitmapFontFiles) {
            if (!assetManager.isLoaded(fileName)) {
                assetManager.load(fileName, BitmapFont.class);
            }
        }
        bitmapFontFiles.clear();

        for (String fileName : soundFiles) {
            if (!assetManager.isLoaded(fileName)) {
                assetManager.load(fileName, Sound.class);
            }
        }
        soundFiles.clear();

        for (String fileName : musicFiles) {
            if (!assetManager.isLoaded(fileName)) {
                assetManager.load(fileName, Music.class);
            }
        }
        musicFiles.clear();
    }

    public synchronized void unload() {
        List<String> files = new ArrayList<>(loadedTextureFiles);
        for (String fileName : files) {
            if (!textureFiles.contains(fileName)) {
                assetManager.unload(fileName);
                loadedTextureFiles.remove(fileName);
            } else {
                textureFiles.remove(fileName);
            }
        }

        files = new ArrayList<>(loadedBitmapFontFiles);
        for (String fileName : files) {
            if (!bitmapFontFiles.contains(fileName)) {
                assetManager.unload(fileName);
                loadedBitmapFontFiles.remove(fileName);
            } else {
                bitmapFontFiles.remove(fileName);
            }
        }

        files = new ArrayList<>(loadedSoundFiles);
        for (String fileName : files) {
            if (!soundFiles.contains(fileName)) {
                assetManager.unload(fileName);
                loadedSoundFiles.remove(fileName);
            } else {
                soundFiles.remove(fileName);
            }
        }

        files = new ArrayList<>(loadedMusicFiles);
        for (String fileName : files) {
            if (!musicFiles.contains(fileName)) {
                assetManager.unload(fileName);
                loadedMusicFiles.remove(fileName);
            } else {
                musicFiles.remove(fileName);
            }
        }
    }

    public boolean isFinished() {
        return assetManager.update();
    }

    public List<String> getLoadedTextureFiles() {
        return loadedTextureFiles;
    }

    public List<String> getLoadedBitmapFontFiles() {
        return loadedBitmapFontFiles;
    }

    public List<String> getLoadedSoundFiles() {
        return loadedSoundFiles;
    }

    public List<String> getLoadedMusicFiles() {
        return loadedMusicFiles;
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

    public AssetManager getAssetManager() {
        return assetManager;
    }
}
