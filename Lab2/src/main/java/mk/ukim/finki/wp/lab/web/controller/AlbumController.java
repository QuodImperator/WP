package mk.ukim.finki.wp.lab.web.controller;

import mk.ukim.finki.wp.lab.service.AlbumService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/albums")
public class AlbumController {
    private final AlbumService albumService;

    public AlbumController(AlbumService albumService) {
        this.albumService = albumService;
    }

    @GetMapping("/add-form")
    public String getAddAlbumForm() {
        return "add-album";
    }

    @PostMapping("/add")
    public String saveAlbum(@RequestParam String name,
                            @RequestParam String genre,
                            @RequestParam String releaseYear) {
        albumService.saveAlbum(name, genre, releaseYear);
        return "redirect:/songs/add-form";
    }
}