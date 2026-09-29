package com.joysistvi.recordingapp.controller;

import com.joysistvi.recordingapp.model.Song;
import com.joysistvi.recordingapp.repository.PlaylistRepo;
import com.joysistvi.recordingapp.repository.PlaylistRepoImpl;
import com.joysistvi.recordingapp.repository.SongRepo;
import com.joysistvi.recordingapp.repository.SongRepoImpl;
import com.joysistvi.recordingapp.service.SongService;
import com.joysistvi.recordingapp.service.SongServiceImpl;

import java.util.List;

public class SongController {

    private final SongService songService;
    private final PlaylistRepo playlistRepo;
    private final SongRepo songRepo;

    // Default constructor - this is what you need to fix your error
    public SongController() {
        this.songRepo = new SongRepoImpl();
        this.songService = new SongServiceImpl(songRepo);
        this.playlistRepo = new PlaylistRepoImpl();
    }

    // Constructor injection for your AlbumView style
    public SongController(SongService songService, PlaylistRepo playlistRepo, SongRepo songRepo) {
        this.songService = songService;
        this.playlistRepo = playlistRepo;
        this.songRepo = songRepo;
    }

    public List<Song> getAllSongs() {
        return songService.getAllSongs();
    }

    public List<Song> getSongsInPlaylist(int playlistId) {
        return playlistRepo.getSongsInPlaylist(playlistId);
    }

    public List<Song> getSongsInAlbum(int albumId) {
        return songRepo.getSongsByAlbumId(albumId);
    }

    public List<Song> searchSongs(String keyword) {
        return songService.searchSong(keyword);
    }

    public Song getSongById(int id) {
        return songService.getSongById(id);
    }

    public boolean addSong(String title, String artist, int albumId, String filePath) {
        if (title == null || title.trim().isEmpty()) return false;
        Song song = new Song();
        song.setTitle(title);
        song.setArtist(artist);
        song.setAlbumId(albumId);
        song.setFilePath(filePath);
        return songService.addSong(song);
    }

    public boolean addSong(Song song) {
        return songService.addSong(song);
    }

    public boolean updateSong(Song song) {
        return songService.updateSong(song);
    }

    public boolean deleteSong(int id) {
        return songService.deleteSong(id);
    }

    public boolean addSongToPlaylist(int playlistId, int songId) {
        return playlistRepo.addSongToPlaylist(playlistId, songId);
    }

    public boolean removeSongFromPlaylist(int playlistId, int songId) {
        return playlistRepo.removeSongFromPlaylist(playlistId, songId);
    }
}
