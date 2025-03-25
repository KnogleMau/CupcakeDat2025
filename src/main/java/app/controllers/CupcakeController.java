package app.controllers;

import app.persistence.ConnectionPool;
import io.javalin.Javalin;

public class CupcakeController {
    public static void routes(Javalin app, ConnectionPool connectionPool) {
        app.get("/philosophers", ctx -> ?  (ctx));
        app.post("/philosophers", ctx -> ? (ctx, connectionPool));
        // app.get("/ask", ctx -> ctx.render("answer.html"));

    }



}
