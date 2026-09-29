package com.joysistvi.recordingapp.service;

import com.joysistvi.recordingapp.model.Album;
import com.joysistvi.recordingapp.repository.AlbumRepo;
import com.joysistvi.recordingapp.repository.AlbumRepoImpl;

import java.util.List;

public class AlbumServiceImpl implements AlbumService {

    private AlbumRepo albumRepo;

    public AlbumServiceImpl() {
        this.albumRepo = new AlbumRepoImpl(); // connection to your RepoImpl
    }

    @Override
    public List<Album> getAllAlbumsWithArtist() {
        return albumRepo.getAllAlbumsWithArtist();
    }

    @Override
    public List<Album> searchAlbums(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return albumRepo.getAllAlbumsWithArtist();
        }
        return albumRepo.searchAlbums(keyword);
    }

    @Override
    public boolean createAlbum(Album album) {
        // simple validation
        if (album.getTitle() == null || album.getTitle().trim().isEmpty()) {
            System.out.println("Album title cannot be empty");
            return false;
        }
        if (album.getArtistId() <= 0) {
            System.out.println("Invalid Artist ID");
            return false;
        }
        return albumRepo.createAlbum(album);
    }

    @Override
    public boolean updateAlbum(Album album) {
        if (album.getId() <= 0) {
            System.out.println("Invalid Album ID");
            return false;
        }
        return albumRepo.updateAlbum(album);
    }

    @Override
    public boolean deleteAlbum(int id) {
        if (id <= 0) {
            System.out.println("Invalid Album ID");
            return false;
        }
        return albumRepo.deleteAlbum(id);
    }
}
