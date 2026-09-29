package com.joysistvi.recordingapp;

import com.joysistvi.recordingapp.model.Album;
import com.joysistvi.recordingapp.model.Song;
import com.joysistvi.recordingapp.model.User;
import com.joysistvi.recordingapp.repository.PlaylistRepo;
import com.joysistvi.recordingapp.repository.PlaylistRepoImpl;
import com.joysistvi.recordingapp.repository.SongRepo;
import com.joysistvi.recordingapp.repository.SongRepoImpl;
import com.joysistvi.recordingapp.service.*;

import java.util.List;
import java.util.Scanner;

public class TempApp {

    private static Scanner scanner = new Scanner(System.in);
    private static UserService userService = new UserServiceImpl();
    private static SongService songService = new SongServiceImpl();
    private static AlbumService albumService = new AlbumServiceImpl();
    private static PlaylistRepo playlistRepo = new PlaylistRepoImpl();
    private static SongRepo songRepo = new SongRepoImpl();

    private static User loggedInUser = null;

    public static void main(String[] args) {
        System.out.println("=== Recording App ===");

        while (loggedInUser == null) {
            showLoginMenu();
        }

        showMainMenu();
    }

    private static void showLoginMenu() {
        System.out.println("\n--- LOGIN ---");
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.println("3. Exit");
        System.out.print("Choose: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                System.out.print("Username: ");
                String username = scanner.nextLine();
                System.out.print("Password: ");
                String password = scanner.nextLine();

                loggedInUser = userService.login(username, password);
                if (loggedInUser != null) {
                    System.out.println("Welcome " + loggedInUser.getUsername() + "!");
                } else {
                    System.out.println("Invalid credentials!");
                }
                break;
            case 2:
                System.out.print("New Username: ");
                String newUser = scanner.nextLine();
                System.out.print("Password: ");
                String newPass = scanner.nextLine();
                System.out.print("Email: ");
                String email = scanner.nextLine();

                boolean success = userService.register(new User(newUser, newPass, email));
                if (success) System.out.println("Registered! Please login.");
                else System.out.println("Registration failed.");
                break;
            case 3:
                System.exit(0);
                break;
        }
    }

    private static void showMainMenu() {
        while (true) {
            System.out.println("\n--- MAIN MENU ---");
            System.out.println("1. View All Songs");
            System.out.println("2. View All Albums");
            System.out.println("3. View My Playlists");
            System.out.println("4. Add Song");
            System.out.println("5. Create Playlist");
            System.out.println("6. Add Song to Playlist");
            System.out.println("7. View Songs in Playlist");
            System.out.println("8. Logout / Exit");
            System.out.print("Choose: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    List<Song> songs = songService.getAllSongs();
                    System.out.println("\n--- SONGS ---");
                    for (Song s : songs) System.out.println(s);
                    break;
                case 2:
                    List<Album> albums = albumService.getAllAlbumsWithArtist();
                    System.out.println("\n--- ALBUMS ---");
                    for (Album a : albums) System.out.println(a.getId() + " | " + a.getTitle() + " - " + a.getArtistName());
                    break;
                case 3:
                    // You can add getPlaylistsByUser in your PlaylistRepo
                    System.out.println("Feature: getPlaylistsByUser(" + loggedInUser.getId() + ")");
                    break;
                case 4:
                    System.out.print("Title: "); String title = scanner.nextLine();
                    System.out.print("Artist: "); String artist = scanner.nextLine();
                    System.out.print("Album ID: "); int albumId = scanner.nextInt(); scanner.nextLine();
                    Song newSong = new Song(title, artist, albumId, null);
                    if (songService.addSong(newSong)) System.out.println("Song added!");
                    break;
                case 5:
                    System.out.print("Playlist Name: "); String pName = scanner.nextLine();
                    if (playlistRepo.createPlaylist(pName, loggedInUser.getId())) System.out.println("Playlist created!");
                    break;
                case 6:
                    System.out.print("Playlist ID: "); int pid = scanner.nextInt();
                    System.out.print("Song ID: "); int sid = scanner.nextInt(); scanner.nextLine();
                    if (playlistRepo.addSongToPlaylist(pid, sid)) System.out.println("Added to playlist!");
                    break;
                case 7:
                    System.out.print("Playlist ID: "); int viewPid = scanner.nextInt(); scanner.nextLine();
                    List<Song> pSongs = playlistRepo.getSongsInPlaylist(viewPid);
                    System.out.println("\n--- Songs in Playlist " + viewPid + " ---");
                    for (Song s : pSongs) System.out.println(s);
                    break;
                case 8:
                    System.out.println("Bye!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}
