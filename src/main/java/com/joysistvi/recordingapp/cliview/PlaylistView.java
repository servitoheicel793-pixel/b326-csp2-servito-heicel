package com.joysistvi.recordingapp.cliview;

import com.joysistvi.recordingapp.controller.PlaylistController;
import java.util.List;
import java.util.Scanner;

public class PlaylistView {

    private final PlaylistController playlistController;
    private final Scanner scanner;
    private final int userId; // Logged-in user's ID

    // Constructor injection
    public PlaylistView(PlaylistController playlistController,
                        Scanner scanner,
                        int userId) {

        this.playlistController = playlistController;
        this.scanner = scanner;
        this.userId = userId;
    }

    // =========================
    // MAIN MENU LOOP
    // =========================
    public void run() {

        int choice;

        do {
            printMenu();
            choice = promptChoice();

            switch (choice) {

                case 1 -> viewMyPlaylists();

                case 2 -> createPlaylist();

                case 3 -> deletePlaylist();

                case 4 -> viewSongsInPlaylist();

                case 5 -> addSongToPlaylist();

                case 6 -> removeSongFromPlaylist();

                case 0 ->
                        System.out.println("Returning to dashboard...");

                default ->
                        System.out.println("Invalid choice. Try again.");
            }

            if (choice != 0) {
                System.out.print("\nPress Enter to continue...");
                scanner.nextLine();
            }

        } while (choice != 0);
    }


    // =========================
    // PRINT MENU
    // =========================
    private void printMenu() {

        printHeader("MY PLAYLISTS");

        System.out.println("1. View My Playlists");
        System.out.println("2. Create Playlist");
        System.out.println("3. Delete Playlist");
        System.out.println("4. View Songs in Playlist");
        System.out.println("5. Add Song to Playlist");
        System.out.println("6. Remove Song from Playlist");
        System.out.println("0. Back");
    }


    // =========================
    // PROMPT CHOICE
    // =========================
    private int promptChoice() {

        System.out.print("Choice: ");

        try {
            return Integer.parseInt(scanner.nextLine());

        } catch (NumberFormatException e) {

            System.out.println("Please enter a valid number.");
            return -1;
        }
    }


    // =========================
    // VIEW MY PLAYLISTS
    // =========================
    private void viewMyPlaylists() {

        printHeader("VIEW MY PLAYLISTS");

        try {

            List<?> playlists =
                    playlistController.handleViewPlaylistsByUser(userId);

            if (playlists == null || playlists.isEmpty()) {

                System.out.println("You do not have any playlists.");

            } else {

                System.out.println("Your Playlists:");
                System.out.println("--------------------------------");

                for (Object playlist : playlists) {
                    System.out.println(playlist);
                }

                System.out.println("--------------------------------");
            }

        } catch (Exception e) {

            System.out.println("Error loading playlists: "
                    + e.getMessage());
        }
    }


    // =========================
    // CREATE PLAYLIST
    // =========================
    private void createPlaylist() {

        printHeader("CREATE PLAYLIST");

        System.out.print("Enter playlist name: ");
        String playlistName = scanner.nextLine();

        if (playlistName.trim().isEmpty()) {

            System.out.println("Playlist name cannot be empty.");
            return;
        }

        try {

            boolean success =
                    playlistController.handleCreatePlayList(
                            userId,
                            playlistName
                    );

            if (success) {

                System.out.println(
                        "Playlist created successfully."
                );

            } else {

                System.out.println(
                        "Failed to create playlist."
                );
            }

        } catch (Exception e) {

            System.out.println(
                    "Error creating playlist: "
                            + e.getMessage()
            );
        }
    }


    // =========================
    // DELETE PLAYLIST
    // =========================
    private void deletePlaylist() {

        printHeader("DELETE PLAYLIST");

        System.out.print("Enter playlist ID: ");

        int playlistId = readInt();

        if (playlistId == -1) {
            return;
        }

        try {

            boolean success =
                    playlistController.handleDeletePlaylist(
                            playlistId,
                            userId
                    );

            if (success) {

                System.out.println(
                        "Playlist deleted successfully."
                );

            } else {

                System.out.println(
                        "Failed to delete playlist."
                );
            }

        } catch (Exception e) {

            System.out.println(
                    "Error deleting playlist: "
                            + e.getMessage()
            );
        }
    }


    // =========================
    // VIEW SONGS IN PLAYLIST
    // =========================
    private void viewSongsInPlaylist() {

        printHeader("VIEW SONGS IN PLAYLIST");

        System.out.print("Enter playlist ID: ");

        int playlistId = readInt();

        if (playlistId == -1) {
            return;
        }

        try {

            List<?> songs =
                    playlistController.handleViewSongsInPlaylist(
                            playlistId,
                            userId
                    );

            if (songs == null || songs.isEmpty()) {

                System.out.println(
                        "This playlist has no songs."
                );

            } else {

                System.out.println("Songs:");
                System.out.println("--------------------------------");

                for (Object song : songs) {
                    System.out.println(song);
                }

                System.out.println("--------------------------------");
            }

        } catch (Exception e) {

            System.out.println(
                    "Error loading songs: "
                            + e.getMessage()
            );
        }
    }


    // =========================
    // ADD SONG TO PLAYLIST
    // =========================
    private void addSongToPlaylist() {

        printHeader("ADD SONG TO PLAYLIST");

        System.out.print("Enter playlist ID: ");

        int playlistId = readInt();

        if (playlistId == -1) {
            return;
        }

        System.out.print("Enter song ID: ");

        int songId = readInt();

        if (songId == -1) {
            return;
        }

        try {

            boolean success =
                    playlistController.handleAddSongToPlaylist(
                            playlistId,
                            songId,
                            userId
                    );

            if (success) {

                System.out.println(
                        "Song added to playlist successfully."
                );

            } else {

                System.out.println(
                        "Failed to add song to playlist."
                );
            }

        } catch (Exception e) {

            System.out.println(
                    "Error adding song: "
                            + e.getMessage()
            );
        }
    }


    // =========================
    // REMOVE SONG FROM PLAYLIST
    // =========================
    private void removeSongFromPlaylist() {

        printHeader("REMOVE SONG FROM PLAYLIST");

        System.out.print("Enter playlist ID: ");

        int playlistId = readInt();

        if (playlistId == -1) {
            return;
        }

        System.out.print("Enter song ID: ");

        int songId = readInt();

        if (songId == -1) {
            return;
        }

        try {

            boolean success =
                    playlistController.handleRemoveSongFromPlaylist(
                            playlistId,
                            songId,
                            userId
                    );

            if (success) {

                System.out.println(
                        "Song removed from playlist successfully."
                );

            } else {

                System.out.println(
                        "Failed to remove song from playlist."
                );
            }

        } catch (Exception e) {

            System.out.println(
                    "Error removing song: "
                            + e.getMessage()
            );
        }
    }


    // =========================
    // READ INTEGER
    // =========================
    private int readInt() {

        try {

            return Integer.parseInt(scanner.nextLine());

        } catch (NumberFormatException e) {

            System.out.println("Invalid number.");
            return -1;
        }
    }


    // =========================
    // HEADER
    // =========================
    private void printHeader(String title) {

        System.out.println();
        System.out.println("========================================");
        System.out.println("             " + title);
        System.out.println("========================================");
    }
}