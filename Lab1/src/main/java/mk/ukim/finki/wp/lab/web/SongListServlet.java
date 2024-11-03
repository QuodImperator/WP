package mk.ukim.finki.wp.lab.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mk.ukim.finki.wp.lab.service.SongService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

import java.io.IOException;

@WebServlet(name = "song-list-servlet", urlPatterns = "/listSongs")
public class SongListServlet extends HttpServlet implements ApplicationContextAware {
    private SongService songService;
    private SpringTemplateEngine springTemplateEngine;
    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
        this.songService = applicationContext.getBean(SongService.class);
        this.springTemplateEngine = applicationContext.getBean(SpringTemplateEngine.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        WebContext context = new WebContext(
                JakartaServletWebApplication.buildApplication(getServletContext())
                        .buildExchange(req, resp),
                req.getLocale());
        context.setVariable("songs", songService.listSongs());

        springTemplateEngine.process(
                "listSongs.html",
                context,
                resp.getWriter()
        );
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String selectedSongId = req.getParameter("trackId");
        if (selectedSongId != null && !selectedSongId.isEmpty()) {
            resp.sendRedirect("/artist?trackId=" + selectedSongId);
        } else {
            resp.sendRedirect("/listSongs");
        }
    }
}