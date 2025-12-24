package com.example.demo.servlet;

import jakarta.servlet.http.*;
import java.io.IOException;

public class HelloServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        resp.setContentType("text/plain");
        resp.getWriter()
            .write("Hello from Dynamic Event Ticket Pricing Servlet");
    }
}
