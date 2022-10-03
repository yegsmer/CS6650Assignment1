package com.bsds.servlet;

import com.bsds.models.Message;
import com.bsds.models.Skiers;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import com.google.gson.Gson;

@WebServlet(name = "SkierServlet", value = "/SkierServlet")
public class SkierServlet extends HttpServlet {

    private Gson gson = new Gson();
    private final int MIN_DAY = 1;
    private final int MAX_DAY = 366;
    private final String SEASONS_PARAMETER = "seasons";
    private final String DAYS_PARAMETER = "days";
    private final String SKIERS_PARAMETER = "skiers";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain");
        String urlPath = request.getPathInfo();

        // check we have a URL!
        if (urlPath == null || urlPath.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.getWriter().write("missing paramterers");
            return;
        }

        String[] urlParts = urlPath.split("/");
        // and now validate url path and return the response status code
        // (and maybe also some value if input is valid)

        if (!isUrlValid(urlParts)) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } else {
            response.setStatus(HttpServletResponse.SC_OK);
            // do any sophisticated processing with urlParts which contains all the url params
            // TODO: process url params in `urlParts`
            response.getWriter().write("It works!");
        }
    }

    private boolean isUrlValid(String[] urlPath) {

        // urlPath  = "/1/seasons/2019/day/1/skier/123"
        // urlParts = [, 1, seasons, 2019, day, 1, skier, 123]

        if(urlPath.length != 8) return false;

        try {
            // validate the range of values
            return urlPath[3].length() == 4
                && Integer.valueOf(urlPath[5]) >= MIN_DAY
                && Integer.valueOf(urlPath[5]) <= MAX_DAY
                && urlPath[2].equals(SEASONS_PARAMETER)
                && urlPath[4].equals(DAYS_PARAMETER)
                && urlPath[6].equals(SKIERS_PARAMETER);
        } catch (Exception e) {
            return false;
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        processRequest(request, response);

    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        String urlPath = request.getPathInfo();

        // check we have a URL!
        if (urlPath == null || urlPath.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.getWriter().write("missing paramterers");
            return;
        }

        String[] urlParts = urlPath.split("/");

        if (!isUrlValid(urlParts)) {
            Message message = new Message("string");
            response.getWriter().write(gson.toJson(message));
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }

        try {
            StringBuilder sb = new StringBuilder();
            String s;
            while ((s = request.getReader().readLine()) != null) {
                sb.append(s);
            }

            Skiers skier = gson.fromJson(sb.toString(), Skiers.class);
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
