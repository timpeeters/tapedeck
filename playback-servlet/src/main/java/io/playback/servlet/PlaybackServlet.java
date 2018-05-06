package io.playback.servlet;

import io.playback.Playback;
import io.playback.client.DefaultHttpClient;
import io.playback.matcher.RequestMatchers;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet
public class PlaybackServlet extends HttpServlet {
    private final Playback playback = new Playback(RequestMatchers.DEFAULT, new DefaultHttpClient());

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        playback.record(RequestMapper.map(req), null);

        resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
    }
}
