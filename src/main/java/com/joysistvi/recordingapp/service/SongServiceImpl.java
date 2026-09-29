package com.joysistvi.recordingapp.service;

import com.joysistvi.recordingapp.model.Song;
import com.joysistvi.recordingapp.repository.SongRepo;
import com.joysistvi.recordingapp.repository.SongRepoImpl;
import java.util.List;

public class SongServiceImpl implements SongService {

    private SongRepo songRepo;

    public SongServiceImpl() {
        this.songRepo = new SongRepoImpl(); // connected to your RepoImpl
    }

    // Constructor for testing / injection
    public SongServiceImpl(SongRepo songRepo) {
        this.songRepo = songRepo;
    }

    @Override
    public List<Song> getAllSongs() {
        return songRepo.getAllSongs();
    }

    @Override
    public Song getSongById(int id) {
        if (id <= 0) return null;
        return songRepo.getSongById(id);
    }

    @Override
    public List<Song> searchSong(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return getAllSongs();
        }
        return songRepo.searchSongs(keyword);
    }

    @Override
    public List<Song> getArchivedSongs() {
        return songRepo.getArchivedSongs();
    }

    @Override
    public boolean addSong(Song song) {
        if (song == null) return false;
        if (song.getTitle() == null || song.getTitle().trim().isEmpty()) {
            System.out.println("Song title cannot be empty");
            return false;
        }
        if (song.getArtist() == null || song.getArtist().trim().isEmpty()) {
            System.out.println("Artist cannot be empty");
            return false;
        }
        return songRepo.createSong(song);
    }

    @Override
    public boolean updateSong(Song song) {
        if (song == null || song.getId() <= 0) {
            System.out.println("Invalid song ID for update");
            return false;
        }
        return songRepo.updateSong(song);
    }

    @Override
    public boolean deleteSong(int id) {
        if (id <= 0) {
            System.out.println("Invalid song ID for delete");
            return false;
        }
        return songRepo.deleteSong(id);
    }

    @Override
    public boolean archiveSong(int id) {
        if (id <= 0) return false;
        return songRepo.archiveSong(id);
    }

    @Override
    public boolean restoreSong(int id) {
        if (id <= 0) return false;
        return songRepo.restoreSong(id);
    }
}
