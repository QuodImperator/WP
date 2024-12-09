package mk.ukim.finki.wp.lab.web.controller;

import mk.ukim.finki.wp.lab.model.Song;
import mk.ukim.finki.wp.lab.service.ArtistService;
import mk.ukim.finki.wp.lab.service.SongService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

@Controller
@RequestMapping("/artists")
public class ArtistController {
    private final ArtistService artistService;
    private final SongService songService;

    public ArtistController(ArtistService artistService, SongService songService) {
        this.artistService = artistService;
        this.songService = songService;
    }

    @GetMapping("/add-form")
    public String getAddArtistForm() {
        return "add-artist";
    }

    @PostMapping("/add")
    public String saveArtist(@RequestParam String firstName,
                             @RequestParam String lastName,
                             @RequestParam String bio) {
        artistService.saveArtist(firstName, lastName, bio);
        return "redirect:/songs";
    }

    @GetMapping("/add-to-song")
    public String getArtistsForSong(@RequestParam String trackId, Model model) {
        Song song = songService.findByTrackId(trackId);
        model.addAttribute("artists", artistService.listArtists());
        model.addAttribute("song", song);
        return "artistsList";
    }

    @PostMapping("/add-to-song")
    public String addArtistToSong(@RequestParam Long artistId,
                                  @RequestParam String trackId) {
        songService.addArtistToSong(
                artistService.findById(artistId),
                songService.findByTrackId(trackId)
        );
        return "redirect:/songDetails?trackId=" + trackId;
    }
}