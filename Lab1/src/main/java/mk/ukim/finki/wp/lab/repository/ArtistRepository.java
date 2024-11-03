package mk.ukim.finki.wp.lab.repository;

import mk.ukim.finki.wp.lab.model.Artist;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class ArtistRepository {
    private List<Artist> artists;

    public ArtistRepository() {
        artists = new ArrayList<>();
        artists.add(new Artist(1L, "Freddie", "Mercury", "Lead singer of Queen"));
        artists.add(new Artist(2L, "Michael", "Jackson", "King of Pop"));
        artists.add(new Artist(3L, "Bob", "Marley", "Reggae legend"));
        artists.add(new Artist(4L, "Elvis", "Presley", "King of Rock and Roll"));
        artists.add(new Artist(5L, "John", "Lennon", "The Beatles founder"));
    }

    public List<Artist> findAll() {
        return artists;
    }

    public Optional<Artist> findById(Long id) {
        return artists.stream()
                .filter(artist -> artist.getId().equals(id))
                .findFirst();
    }
}