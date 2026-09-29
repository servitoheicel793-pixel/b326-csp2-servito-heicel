package com.joysistvi.recordingapp.repository;

import com.joysistvi.recordingapp.config.DbConnection;
import com.joysistvi.recordingapp.model.Song;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SongRepoImpl implements SongRepo {

    @Override
    public List<Song> getAllSongs() {
        List<Song> songs = new ArrayList<>();
        String query = "SELECT * FROM songs WHERE is_archived = 0 OR is_archived IS NULL";

        try (Connection conn = DbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query);
             ResultSet rs = prep.executeQuery()) {

            while (rs.next()) {
                songs.add(mapResultSetToSong(rs));
            }
        } catch (SQLException e) {
            System.out.println("Get All Songs: " + e.getMessage());
        }
        return songs;
    }

    @Override
    public Song getSongById(int id) {
        String query = "SELECT * FROM songs WHERE id = ?";
        try (Connection conn = DbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query)) {

            prep.setInt(1, id);
            ResultSet rs = prep.executeQuery();
            if (rs.next()) {
                return mapResultSetToSong(rs);
            }
        } catch (SQLException e) {
            System.out.println("Get Song By ID: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Song> searchSongs(String keyword) {
        List<Song> songs = new ArrayList<>();
        String query = "SELECT * FROM songs WHERE (title LIKE ? OR artist LIKE ?) AND (is_archived = 0 OR is_archived IS NULL)";

        try (Connection conn = DbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query)) {

            prep.setString(1, "%" + keyword + "%");
            prep.setString(2, "%" + keyword + "%");
            ResultSet rs = prep.executeQuery();

            while (rs.next()) {
                songs.add(mapResultSetToSong(rs));
            }
        } catch (SQLException e) {
            System.out.println("Search Song: " + e.getMessage());
        }
        return songs;
    }

    @Override
    public List<Song> getArchivedSongs() {
        List<Song> songs = new ArrayList<>();
        String query = "SELECT * FROM songs WHERE is_archived = 1";

        try (Connection conn = DbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query);
             ResultSet rs = prep.executeQuery()) {

            while (rs.next()) {
                songs.add(mapResultSetToSong(rs));
            }
        } catch (SQLException e) {
            System.out.println("Get Archived Songs: " + e.getMessage());
        }
        return songs;
    }

    @Override
    public boolean createSong(Song song) {
        String query = "INSERT INTO songs (title, artist, album_id, file_path) VALUES (?, ?, ?, ?)";
        try (Connection conn = DbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query)) {

            prep.setString(1, song.getTitle());
            prep.setString(2, song.getArtist());
            prep.setInt(3, song.getAlbumId());
            prep.setString(4, song.getFilePath());

            return prep.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Add Song: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean updateSong(Song song) {
        String query = "UPDATE songs SET title = ?, artist = ?, album_id = ? WHERE id = ?";
        try (Connection conn = DbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query)) {

            prep.setString(1, song.getTitle());
            prep.setString(2, song.getArtist());
            prep.setInt(3, song.getAlbumId());
            prep.setInt(4, song.getId());

            return prep.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Update Song: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean deleteSong(int id) {
        String query = "DELETE FROM songs WHERE id = ?";
        try (Connection conn = DbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query)) {
            prep.setInt(1, id);
            return prep.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Delete Song: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean archiveSong(int id) {
        String query = "UPDATE songs SET is_archived = 1 WHERE id = ?";
        try (Connection conn = DbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query)) {
            prep.setInt(1, id);
            return prep.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Archive Song: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean restoreSong(int id) {
        String query = "UPDATE songs SET is_archived = 0 WHERE id = ?";
        try (Connection conn = DbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query)) {
            prep.setInt(1, id);
            return prep.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Restore Song: " + e.getMessage());
            return false;
        }
    }

    // helper to avoid repeating code
    private Song mapResultSetToSong(ResultSet rs) throws SQLException {
        Song song = new Song();
        song.setId(rs.getInt("id"));
        song.setTitle(rs.getString("title"));
        song.setArtist(rs.getString("artist"));
        song.setAlbumId(rs.getInt("album_id"));
        try { song.setFilePath(rs.getString("file_path")); } catch (Exception e) {}
        return song;
    }
}
