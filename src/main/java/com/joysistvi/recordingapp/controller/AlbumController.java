package com.joysistvi.recordingapp.controller;

import com.joysistvi.recordingapp.model.Album;
import com.joysistvi.recordingapp.service.AlbumService;
import com.joysistvi.recordingapp.service.AlbumServiceImpl;

import java.util.List;

public class AlbumController {

    private final AlbumService albumService;

    // Default constructor - connects to Service -> RepoImpl
    public AlbumController() {
        this.albumService = new AlbumServiceImpl();
    }

    // Constructor injection - this is what your AlbumView needs
    public AlbumController(AlbumService albumService) {
        this.albumService = albumService;
    }

    public List<Album> getAllAlbums() {
        return albumService.getAllAlbumsWithArtist();
    }

    public Album getAlbumById(int id) {
        return albumService.getAllAlbumsWithArtist(id);
    }

    public List<Album> searchAlbums(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return getAllAlbums();
        }
        return albumService.searchAlbums(keyword);
    }

    public boolean addAlbum(String title, int artistId) {
        if (title == null || title.trim().isEmpty()) return false;
        if (artistId <= 0) return false;

        Album album = new Album();
        album.setTitle(title);
        album.setArtistId(artistId);
        return albumService.addAlbum(album);
    }

    public boolean addAlbum(Album album) {
        return albumService.equals(album);
    }

    public boolean updateAlbum(int id, String newTitle, int newArtistId) {
        Album album = albumService.getAllAlbumsWithArtist(id);
        if (album == null) return false;

        album.setTitle(newTitle);
        album.setArtistId(newArtistId);
        return albumService.updateAlbum(album);
    }

    public boolean updateAlbum(Album album) {
        return albumService.updateAlbum(album);
    }

    public boolean deleteAlbum(int id) {
        return albumService.deleteAlbum(id);
    }
}
