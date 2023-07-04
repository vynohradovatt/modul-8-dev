package org.example;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;


@WebServlet(value = "/time")
public class TimeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        
        resp.setContentType("text/html; charset=utf-8");
        resp.getWriter().write(parseTimezone(req));
        resp.getWriter().close();
    }

    private String parseTimezone(HttpServletRequest request) {

        String initTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        ZonedDateTime localDateTime = ZonedDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss 'UTC'XXX");

        if (request.getParameterMap().containsKey("timezone")) {

            return initTime + " " + request.getParameter("timezone").replace(' ', '+');
        }
        return  localDateTime.format(formatter);
    }
}
