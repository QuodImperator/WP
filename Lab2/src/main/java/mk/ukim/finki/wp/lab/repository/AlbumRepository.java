package mk.ukim.finki.wp.lab.repository;

import mk.ukim.finki.wp.lab.model.Album;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class AlbumRepository {
    private List<Album> albums;

    public AlbumRepository() {
        albums = new ArrayList<>();
        albums.add(new Album(1L, "A Night at the Opera", "Rock", "1975"));
        albums.add(new Album(2L, "Thriller", "Pop", "1982"));
        albums.add(new Album(3L, "Catch a Fire", "Reggae", "1973"));
        albums.add(new Album(4L, "Imagine", "Rock", "1971"));
        albums.add(new Album(5L, "Elvis", "Rock & Roll", "1956"));
    }

    public List<Album> findAll() {
        return albums;
    }

    public Optional<Album> findById(Long id) {
        return albums.stream()
                .filter(album -> album.getId().equals(id))
                .findFirst();
    }
}