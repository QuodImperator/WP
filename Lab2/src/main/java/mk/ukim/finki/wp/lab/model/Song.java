package mk.ukim.finki.wp.lab.model;

import lombok.Data;
import java.util.List;

@Data
public class Song {
    private Long id;
    private String trackId;
    private String title;
    private String genre;
    private int releaseYear;
    private List<Artist> performers;
    private Album album;

    private static Long counter = 1L;

    public Song(String trackId, String title, String genre, int releaseYear, List<Artist> performers) {
        this.id = counter++;
        this.trackId = trackId;
        this.title = title;
        this.genre = genre;
        this.releaseYear = releaseYear;
        this.performers = performers;
    }
}