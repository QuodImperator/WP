package mk.ukim.finki.wp.lab.web.controller;

import mk.ukim.finki.wp.lab.model.Album;
import mk.ukim.finki.wp.lab.model.Song;
import mk.ukim.finki.wp.lab.service.AlbumService;
import mk.ukim.finki.wp.lab.service.SongService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("")
public class SongController {
    private final SongService songService;
    private final AlbumService albumService;

    public SongController(SongService songService, AlbumService albumService) {
        this.songService = songService;
        this.albumService = albumService;
    }

    @GetMapping({"/", "/songs"})
    public String getSongsPage(@RequestParam(required = false) String error, Model model) {
        List<Song> songs = songService.listSongs();
        model.addAttribute("songs", songs);
        if (error != null) {
            model.addAttribute("error", error);
        }
        return "listSongs";
    }

    @PostMapping("/listSongs")
    public String selectSong(@RequestParam String trackId) {
        if (trackId != null && !trackId.isEmpty()) {
            return "redirect:/artists/add-to-song?trackId=" + trackId;
        }
        return "redirect:/songs";
    }

    @GetMapping("/songDetails")
    public String getSongDetails(@RequestParam String trackId, Model model) {
        Song song = songService.findByTrackId(trackId);
        model.addAttribute("song", song);
        return "songDetails";
    }

    @GetMapping("/songs/add-form")
    public String getAddSongPage(Model model) {
        List<Album> albums = albumService.findAll();
        model.addAttribute("albums", albums);
        return "add-song";
    }

    @GetMapping("/songs/edit-form/{id}")
    public String getEditSongForm(@PathVariable Long id, Model model) {
        try {
            Song song = songService.findById(id);
            List<Album> albums = albumService.findAll();
            model.addAttribute("song", song);
            model.addAttribute("albums", albums);
            return "add-song";
        } catch (RuntimeException ex) {
            return "redirect:/songs?error=SongNotFound";
        }
    }

    @PostMapping("/songs/edit/{id}")
    public String editSong(
            @PathVariable Long id,
            @RequestParam String title,
            @RequestParam String trackId,
            @RequestParam String genre,
            @RequestParam int releaseYear,
            @RequestParam Long albumId) {
        songService.editSong(id, title, trackId, genre, releaseYear, albumId);
        return "redirect:/songs";
    }

    @PostMapping("/songs/add")
    public String saveSong(
            @RequestParam String title,
            @RequestParam String trackId,
            @RequestParam String genre,
            @RequestParam int releaseYear,
            @RequestParam Long albumId) {
        songService.saveSong(title, trackId, genre, releaseYear, albumId);
        return "redirect:/songs";
    }

    @GetMapping("/songs/delete/{id}")
    public String deleteSong(@PathVariable Long id) {
        songService.deleteSong(id);
        return "redirect:/songs";
    }
}