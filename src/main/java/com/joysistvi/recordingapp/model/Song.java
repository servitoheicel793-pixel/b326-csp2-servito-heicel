package com.joysistvi.recordingapp.model;

public class Song {
    private int id;
    private String title;
    private String artist;
    private int albumId;
    private String filePath;
    private boolean isArchived;

    // For display with JOIN - optional
    private String albumTitle;
    private String artistName;

    public Song() {
    }

    public Song(String title, String artist, int albumId, String filePath) {
        this.title = title;
        this.artist = artist;
        this.albumId = albumId;
        this.filePath = filePath;
    }

    public Song(int id, String title, String artist, int albumId, String filePath) {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.albumId = albumId;
        this.filePath = filePath;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public int getAlbumId() {
        return albumId;
    }

    public void setAlbumId(int albumId) {
        this.albumId = albumId;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public boolean isArchived() {
        return isArchived;
    }

    public void setArchived(boolean archived) {
        isArchived = archived;
    }

    public String getAlbumTitle() {
        return albumTitle;
    }

    public void setAlbumTitle(String albumTitle) {
        this.albumTitle = albumTitle;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    @Override
    public String toString() {
        return id + " | " + title + " - " + artist;
    }
}
