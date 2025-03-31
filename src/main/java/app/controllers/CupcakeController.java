package app.controllers;

import app.entities.User;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import app.persistence.UserMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;
import app.persistence.ToppingMapper;
import app.persistence.BottomMapper;
import app.entities.Topping;
import app.entities.Bottom;

import java.util.List;


public class CupcakeController {


    public static void routes(Javalin app, ConnectionPool connectionPool) {
        ToppingMapper toppingMapper = new ToppingMapper();
        BottomMapper bottomMapper = new BottomMapper();

        // app.get("/index", ctx ->   (ctx));
        //app.post("/index", ctx -> asd (ctx, connectionPool));
        //app.get("/ask", ctx -> ctx.render("answer.html"));

        //}

        app.get("/", ctx -> {
            List<Topping> toppings = toppingMapper.getAllToppings();
            List<Bottom> bottoms = bottomMapper.getAllBottoms(); // ✅

            ctx.attribute("toppings", toppings);
            ctx.attribute("bottoms", bottoms); // ✅
            ctx.render("index.html");
        });

       //
        // app.post("login", ctx -> login(ctx, connectionPool));
    }

    public static void login(Context ctx, ConnectionPool connectionPool) {

        // hent form parametre
        String email = ctx.formParam("email");
        String password = ctx.formParam("password");

        // check om bruger findes i db med email og password
        try {
            User user = UserMapper.login(email, password, connectionPool);
            ctx.sessionAttribute("currentUser", user); //gemmer user i den tid web applikation er tændt

            //hvis ja send videre
            ctx.render(""); //Mangler siden den skal hen på

        } catch (DatabaseException e) {

            //hvis nej, send tilbage til login, med fejlbesked e
            ctx.attribute("message", e.getMessage());
            ctx.render("login.html");
        }
    }

}
