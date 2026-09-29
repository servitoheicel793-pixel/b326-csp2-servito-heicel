package com.joysistvi.recordingapp.controller;

import com.joysistvi.recordingapp.model.Playlist;
import com.joysistvi.recordingapp.model.Song;
import com.joysistvi.recordingapp.service.PlaylistService;

import java.util.List;

public class PlaylistController {

    private final PlaylistService playlistService;

    // Constructor injection
    public PlaylistController(PlaylistService playlistService) {
        this.playlistService = playlistService;
    }

    // ==========================================
    // VIEW PLAYLISTS
    // ==========================================

    public List<Playlist> handleViewPlaylistsByUser(int userId) {

        return playlistService.getPlaylistsByUser(userId);
    }


    // ==========================================
    // CREATE PLAYLIST
    // ==========================================

    public boolean handleCreatePlayList(
            int userId,
            String playlistName) {

        if (playlistName == null ||
                playlistName.trim().isEmpty()) {

            return false;
        }

        return playlistService.createPlaylist(
                userId,
                playlistName
        );
    }


    // ==========================================
    // DELETE PLAYLIST
    // ==========================================

    public boolean handleDeletePlaylist(
            int playlistId,
            int userId) {

        return playlistService.deletePlaylist(
                playlistId,
                userId
        );
    }


    // ==========================================
    // VIEW SONGS IN PLAYLIST
    // ==========================================

    public List<Song> handleViewSongsInPlaylist(
            int playlistId,
            int userId) {

        return playlistService.getSongsInPlaylist(
                playlistId,
                userId
        );
    }


    // ==========================================
    // ADD SONG TO PLAYLIST
    // ==========================================

    public boolean handleAddSongToPlaylist(
            int playlistId,
            int songId,
            int userId) {

        return playlistService.addSongToPlaylist(
                playlistId,
                songId,
                userId
        );
    }


    // ==========================================
    // REMOVE SONG FROM PLAYLIST
    // ==========================================

    public boolean handleRemoveSongFromPlaylist(
            int playlistId,
            int songId,
            int userId) {

        return playlistService.removeSongFromPlaylist(
                playlistId,
                songId,
                userId
        );
    }
}
