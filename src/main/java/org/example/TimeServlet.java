package org.example;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.FileTemplateResolver;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@WebServlet(value = "/time")
public class TimeServlet extends HttpServlet {
    private TemplateEngine engine;

    private String timezone;
    @Override
    public void init() {
        engine = new TemplateEngine();

        FileTemplateResolver resolver = new FileTemplateResolver();
        resolver.setPrefix("/Users/Tanya/Downloads/modul-9-dev/src/main/templates/");
        resolver.setSuffix(".html");
        resolver.setTemplateMode("HTML5");
        resolver.setOrder(engine.getTemplateResolvers().size());
        resolver.setCacheable(false);
        engine.addTemplateResolver(resolver);
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        
        resp.setContentType("text/html; charset=utf-8");

        String timezoneParam = req.getParameter("timezone");

        if( timezoneParam != null){
            timezone = timezoneParam;
            resp.addCookie(new Cookie("lastTimezone", timezone.replace(" ", "+")));
        } else {
            Cookie[] cookies = req.getCookies();
            if(cookies != null) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("lastTimezone")) {
                        timezone = cookie.getValue();
                    }
                }

            }

        }

        LocalDateTime localDateTime = LocalDateTime.now(ZoneId.of(timezone.replace(" ","+")));
        String currentTimeFormatted = localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));


        Context context = new Context(req.getLocale());
        context.setVariable("currentTime", currentTimeFormatted);

        engine.process("time", context, resp.getWriter());

        resp.getWriter().close();
    }
}