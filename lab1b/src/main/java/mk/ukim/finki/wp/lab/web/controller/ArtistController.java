package mk.ukim.finki.wp.lab.web.controller;

import mk.ukim.finki.wp.lab.model.Artist;
import mk.ukim.finki.wp.lab.model.Song;
import mk.ukim.finki.wp.lab.service.ArtistService;
import mk.ukim.finki.wp.lab.service.SongService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ArtistController {

    private final ArtistService artistService;
    private final SongService songService;

    public ArtistController(ArtistService artistService, SongService songService) {
        this.artistService = artistService;
        this.songService = songService;
    }

    @GetMapping("/artist")
    public String getArtistPage(@RequestParam(required = false) String error,
                                @RequestParam(required = false) Long trackId,
                                Model model){
        if (error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }
        model.addAttribute("trackId", trackId);
        model.addAttribute("artists", this.artistService.listArtists());
        return "artistList";
    }



    @PostMapping("/songDetails")
    public String getSongDetails(@RequestParam(required = false) String error,
                                 @RequestParam(required = false) Long trackId,
                                 @RequestParam(required = false) Long artistId,
                                 Model model){
        if (error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }
        Song song = songService.findByTrackId(trackId);
        Artist artist = artistService.findById(artistId).orElse(null);
        song.getPerformers().add(artist);
        //List<Artist> performers = song.getPerformers();
        model.addAttribute("song", song);
        model.addAttribute("trackId", trackId);
        //model.addAttribute("performers", performers);
        model.addAttribute("artist", artist);
        return "songDetails";
    }
}
