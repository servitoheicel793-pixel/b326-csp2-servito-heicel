package com.joysistvi.recordingapp.cliview;

import com.joysistvi.recordingapp.model.Song;
import com.joysistvi.recordingapp.repository.*;
import com.joysistvi.recordingapp.service.SongService;

import java.util.List;
import java.util.Scanner;

import static com.joysistvi.recordingapp.TempApp.songService;

public class SongView {

    private Scanner scanner = new Scanner(System.in);
    private SongRepo songRepository = new SongRepoImpl();
    private PlaylistRepo playlistRepository = new PlaylistRepoImpl();
    private SongService songService = new SongService(songRepository);

    public void displaySongs() {
        List<Song> songs = songService.getAllSongs();
        if (songs.isEmpty()) {
            System.out.println("No songs found.");
            return;
        }
        System.out.println("\n--- ALL SONGS ---");
        for (Song song : songs) {
            System.out.println(song.getId() + " | " + song.getTitle() + " - " + song.getArtist());
        }
    }

    public void displaySongsInPlaylist(int playlistId) {
        List<Song> songs = playlistRepository.getSongsInPlaylist(playlistId);

        System.out.println("\n--- SONGS IN PLAYLIST ID: " + playlistId + " ---");
        if (songs.isEmpty()) {
            System.out.println("No songs in this playlist.");
            return;
        }
        for (Song song : songs) {
            System.out.println(song.getId() + " | " + song.getTitle() + " - " + song.getArtist());
        }
    }

    public void addSongToPlaylistView() {
        displaySongs();
        System.out.print("\nEnter Playlist ID: ");
        int playlistId = scanner.nextInt();

        System.out.print("Enter Song ID to add: ");
        int songId = scanner.nextInt();

        boolean success = playlistRepository.addSongToPlaylist(playlistId, songId);
        if (success) {
            System.out.println("Song added to playlist successfully!");
            displaySongsInPlaylist(playlistId);
        } else {
            System.out.println("Failed to add song. Maybe already exists?");
        }
    }

    public void removeSongFromPlaylistView() {
        System.out.print("Enter Playlist ID: ");
        int playlistId = scanner.nextInt();
        displaySongsInPlaylist(playlistId);

        System.out.print("\nEnter Song ID to remove: ");
        int songId = scanner.nextInt();

        boolean success = playlistRepository.removeSongFromPlaylist(playlistId, songId);
        if (success) {
            System.out.println("Song removed from playlist.");
        } else {
            System.out.println("Failed to remove song.");
        }
    }

    // Main menu for this view
    public void showMenu(int currentPlaylistId) {
        while (true) {
            System.out.println("\n--- SONG / PLAYLIST MENU ---");
            System.out.println("1. View All Songs");
            System.out.println("2. View Songs in Current Playlist [" + currentPlaylistId + "]");
            System.out.println("3. Add Song to Playlist");
            System.out.println("4. Remove Song from Playlist");
            System.out.println("5. Back");
            System.out.print("Choose: ");

            int choice = scanner.nextInt();
            switch (choice) {
                case 1 -> displaySongs();
                case 2 -> displaySongsInPlaylist(currentPlaylistId);
                case 3 -> addSongToPlaylistView();
                case 4 -> removeSongFromPlaylistView();
                case 5 -> { return; }
                default -> System.out.println("Invalid choice.");
            }
        }
    }
}
