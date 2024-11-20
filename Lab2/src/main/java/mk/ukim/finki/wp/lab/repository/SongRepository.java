package mk.ukim.finki.wp.lab.repository;

import mk.ukim.finki.wp.lab.model.Album;
import mk.ukim.finki.wp.lab.model.Artist;
import mk.ukim.finki.wp.lab.model.Song;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class SongRepository {
    private List<Song> songs;
    private final AlbumRepository albumRepository;

    public SongRepository(AlbumRepository albumRepository) {
        this.albumRepository = albumRepository;
        this.songs = new ArrayList<>();

        // Initialize songs with albums
        Song song1 = new Song("S1", "Bohemian Rhapsody", "Rock", 1975, new ArrayList<>());
        song1.setAlbum(albumRepository.findById(1L).orElse(null));

        Song song2 = new Song("S2", "Thriller", "Pop", 1982, new ArrayList<>());
        song2.setAlbum(albumRepository.findById(2L).orElse(null));

        Song song3 = new Song("S3", "No Woman No Cry", "Reggae", 1974, new ArrayList<>());
        song3.setAlbum(albumRepository.findById(3L).orElse(null));

        Song song4 = new Song("S4", "Imagine", "Rock", 1971, new ArrayList<>());
        song4.setAlbum(albumRepository.findById(4L).orElse(null));

        Song song5 = new Song("S5", "Jailhouse Rock", "Rock & Roll", 1957, new ArrayList<>());
        song5.setAlbum(albumRepository.findById(5L).orElse(null));

        songs.add(song1);
        songs.add(song2);
        songs.add(song3);
        songs.add(song4);
        songs.add(song5);
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

    public Optional<Song> findById(Long id) {
        return songs.stream()
                .filter(song -> song.getId().equals(id))
                .findFirst();
    }

    public Song save(Song song) {
        songs.add(song);
        return song;
    }

    public Song edit(Long id, String title, String trackId, String genre, int releaseYear, Album album) {
        Song song = findById(id).orElseThrow(() -> new RuntimeException("Song not found"));
        song.setTitle(title);
        song.setTrackId(trackId);
        song.setGenre(genre);
        song.setReleaseYear(releaseYear);
        song.setAlbum(album);
        return song;
    }

    public void deleteById(Long id) {
        songs.removeIf(song -> song.getId().equals(id));
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