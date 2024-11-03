package mk.ukim.finki.wp.lab.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mk.ukim.finki.wp.lab.model.Song;
import mk.ukim.finki.wp.lab.service.ArtistService;
import mk.ukim.finki.wp.lab.service.SongService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

import java.io.IOException;

@WebServlet(name = "artist-servlet", urlPatterns = "/artist")
public class ArtistServlet extends HttpServlet implements ApplicationContextAware {
    private ArtistService artistService;
    private SongService songService;
    private SpringTemplateEngine springTemplateEngine;
    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
        this.artistService = applicationContext.getBean(ArtistService.class);
        this.songService = applicationContext.getBean(SongService.class);
        this.springTemplateEngine = applicationContext.getBean(SpringTemplateEngine.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String trackId = req.getParameter("trackId");
        Song song = songService.findByTrackId(trackId);

        WebContext context = new WebContext(
                JakartaServletWebApplication.buildApplication(getServletContext())
                        .buildExchange(req, resp),
                req.getLocale());

        context.setVariable("artists", artistService.listArtists());
        context.setVariable("song", song);

        springTemplateEngine.process(
                "artistsList.html",
                context,
                resp.getWriter()
        );
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String artistId = req.getParameter("artistId");
        String trackId = req.getParameter("trackId");

        if (artistId != null && !artistId.isEmpty() && trackId != null && !trackId.isEmpty()) {
            Song song = songService.findByTrackId(trackId);
            songService.addArtistToSong(
                    artistService.findById(Long.parseLong(artistId)),
                    song
            );
            resp.sendRedirect("/songDetails?trackId=" + trackId);
        } else {
            resp.sendRedirect("/listSongs");
        }
    }
}