package com.bsds.servlet;

import com.bsds.models.Skiers;
import com.google.gson.Gson;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "Lab2Servlet", value = "/Lab2Servlet")
public class Lab2Servlet extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
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
    // TODO: validate the request url path according to the API spec
    // urlPath  = "/1/seasons/2019/day/1/skier/123"
    // urlParts = [, 1, seasons, 2019, day, 1, skier, 123]
    return true;
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
      processRequest(request, response);
//    response.setContentType("text/plain");
//    BufferedReader urlPath = request.getReader();
  }

  protected void processRequest(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    response.setContentType("application/json");
    Gson gson = new Gson();

    try {
      StringBuilder sb = new StringBuilder();
      String s;
      while ((s = request.getReader().readLine()) != null) {
        sb.append(s);
      }

      Skiers skier = gson.fromJson(sb.toString(), Skiers.class);

//      Status status = new Status();
//      if (student.getName().equalsIgnoreCase("edw")) {
//        status.setSuccess(true);
//        status.setDescription("success");
//      } else {
//        status.setSuccess(false);
//        status.setDescription("not edw");
//      }
//      response.getOutputStream().print(gson.toJson(status));
//      response.getOutputStream().flush();
    } catch (Exception ex) {
      ex.printStackTrace();
//      Status status = new Status();
//      status.setSuccess(false);
//      status.setDescription(ex.getMessage());
//      response.getOutputStream().print(gson.toJson(status));
//      response.getOutputStream().flush();
    }
  }
}
