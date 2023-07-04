package org.example;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.DateTimeException;
import java.time.ZoneId;

@WebFilter(value = "/time")
public class TimezoneValidateFilter extends HttpFilter {
    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {

        String timezone = req.getParameter("timezone");
        if(timezone != null) {
            try {
                ZoneId.of(timezone.replace(" ", "+"));
                chain.doFilter(req, res);
            } catch (DateTimeException e) {

                res.setStatus(400);
                res.setContentType("application/json");
                res.getWriter().write("Invalid timezone");
                res.getWriter().close();
            }
        } else {
            new TimeServlet().doGet(req, res);
        }
    }
}
