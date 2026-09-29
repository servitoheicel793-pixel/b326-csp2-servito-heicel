package com.joysistvi.recordingapp.repository;

import com.joysistvi.recordingapp.config.DbConnection;
import com.joysistvi.recordingapp.model.Album;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AlbumRepoImpl implements AlbumRepo {

    @Override
    public List<Album> getAllAlbumsWithArtist() {
        List<Album> albums = new ArrayList<>();
        String query = "SELECT a.*, ar.name as artist_name FROM albums a " +
                "LEFT JOIN artists ar ON a.artist_id = ar.id";

        try (Connection conn = DbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query);
             ResultSet rs = prep.executeQuery()) {

            while (rs.next()) {
                Album album = new Album();
                album.setId(rs.getInt("id"));
                album.setTitle(rs.getString("title"));
                album.setArtistId(rs.getInt("artist_id"));
                album.setArtistName(rs.getString("artist_name")); // if you have this field
                // album.setReleaseDate(rs.getDate("release_date"));
                albums.add(album);
            }

        } catch (SQLException e) {
            System.out.println("Get All Albums: " + e.getMessage());
        }
        return albums;
    }

    @Override
    public List<Album> searchAlbums(String keyword) {
        List<Album> albums = new ArrayList<>();
        String query = "SELECT a.*, ar.name as artist_name FROM albums a " +
                "LEFT JOIN artists ar ON a.artist_id = ar.id " +
                "WHERE a.title LIKE ? OR ar.name LIKE ?";

        try (Connection conn = DbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query)) {

            prep.setString(1, "%" + keyword + "%");
            prep.setString(2, "%" + keyword + "%");
            ResultSet rs = prep.executeQuery();

            while (rs.next()) {
                Album album = new Album();
                album.setId(rs.getInt("id"));
                album.setTitle(rs.getString("title"));
                album.setArtistId(rs.getInt("artist_id"));
                album.setArtistName(rs.getString("artist_name"));
                albums.add(album);
            }

        } catch (SQLException e) {
            System.out.println("Search Albums: " + e.getMessage());
        }
        return albums;
    }

    @Override
    public boolean createAlbum(Album album) {
        String query = "INSERT INTO albums (title, artist_id) VALUES (?, ?)";

        try (Connection conn = DbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query)) {

            prep.setString(1, album.getTitle());
            prep.setInt(2, album.getArtistId());

            return prep.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Create Album: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean updateAlbum(Album album) {
        String query = "UPDATE albums SET title = ?, artist_id = ? WHERE id = ?";

        try (Connection conn = DbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query)) {

            prep.setString(1, album.getTitle());
            prep.setInt(2, album.getArtistId());
            prep.setInt(3, album.getId());

            return prep.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Update Album: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean deleteAlbum(int id) {
        String query = "DELETE FROM albums WHERE id = ?";

        try (Connection conn = DbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query)) {

            prep.setInt(1, id);
            return prep.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Delete Album: " + e.getMessage());
            return false;
        }
    }
}
