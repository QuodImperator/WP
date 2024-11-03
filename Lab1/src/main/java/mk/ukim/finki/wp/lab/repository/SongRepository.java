package mk.ukim.finki.wp.lab.repository;

import mk.ukim.finki.wp.lab.model.Artist;
import mk.ukim.finki.wp.lab.model.Song;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class SongRepository {
    private List<Song> songs;

    public SongRepository() {
        songs = new ArrayList<>();
        songs.add(new Song("S1", "Bohemian Rhapsody", "Rock", 1975, new ArrayList<>()));
        songs.add(new Song("S2", "Thriller", "Pop", 1982, new ArrayList<>()));
        songs.add(new Song("S3", "No Woman No Cry", "Reggae", 1974, new ArrayList<>()));
        songs.add(new Song("S4", "Imagine", "Rock", 1971, new ArrayList<>()));
        songs.add(new Song("S5", "Jailhouse Rock", "Rock & Roll", 1957, new ArrayList<>()));
    }

    public List<Song> findAll() {
        return songs;
    }

    public Song findByTrackId(String trackId) {
        return songs.stream()
                .filter(song -> song.getTrackId().equals(trackId))
                .findFirst()
                .orElse(null);
    }

    public Artist addArtistToSong(Artist artist, Song song) {
        Song foundSong = findByTrackId(song.getTrackId());
        if (foundSong != null) {
            if (foundSong.getPerformers() == null) {
                foundSong.setPerformers(new ArrayList<>());
            }
            foundSong.getPerformers().add(artist);
            return artist;
        }
        return null;
    }
}