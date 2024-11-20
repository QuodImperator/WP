package mk.ukim.finki.wp.lab.service;

import mk.ukim.finki.wp.lab.model.Artist;
import mk.ukim.finki.wp.lab.model.Song;

import java.util.List;

public interface SongService {
    List<Song> listSongs();
    Song findById(Long id);
    Song findByTrackId(String trackId);
    Song saveSong(String title, String trackId, String genre, int releaseYear, Long albumId);
    Song editSong(Long id, String title, String trackId, String genre, int releaseYear, Long albumId);
    void deleteSong(Long id);
    Artist addArtistToSong(Artist artist, Song song);
}