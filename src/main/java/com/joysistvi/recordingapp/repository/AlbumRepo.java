package com.joysistvi.recordingapp.repository;

import com.joysistvi.recordingapp.model.Album;

import java.util.List;

public interface AlbumRepo {

    List<Album> getAllAlbumsWithArtist();

    List<Album> searchAlbums(String keyword);

    boolean createAlbum (Album album);

    boolean updateAlbum (Album album);

    boolean deleteAlbum(int id);
 }
