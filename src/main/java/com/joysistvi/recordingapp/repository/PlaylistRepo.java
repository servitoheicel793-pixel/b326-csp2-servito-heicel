package com.joysistvi.recordingapp.repository;

import com.joysistvi.recordingapp.model.Song;

import java.util.List;

public interface PlaylistRepo {

        // This is the one you already have in your Impl
        List<Song> getSongsInPlaylist(int playlistId);

        // Add these so you can use them in your View
        boolean addSongToPlaylist(int playlistId, int songId);

        boolean removeSongFromPlaylist(int playlistId, int songId);

        // Optional but needed for a full playlist feature
        boolean createPlaylist(String playlistName, int userId);

        boolean deletePlaylist(int playlistId);
}
