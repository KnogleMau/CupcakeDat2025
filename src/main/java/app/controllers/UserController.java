package app.controllers;

import app.entities.User;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import app.persistence.UserMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;

public class UserController {

    public static void cupcakeRoutes(Javalin app, ConnectionPool connectionPool) {
    app.get("/", ctx -> ctx.render("createUser.html"));
    app.post("/", ctx -> createUser(ctx, connectionPool));
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

    private static void createUser(Context ctx, ConnectionPool connectionPool)
    {
        // Hent form parametre
        String email = ctx.formParam("email");
        String password1 = ctx.formParam("password1");
        String password2 = ctx.formParam("password2");
        String firstName = ctx.formParam("firstName");
        String lastName = ctx.formParam("lastName");
        String phoneNumber = ctx.formParam("phoneNumber");


        if (password1.equals(password2))
        {
            try
            {
                UserMapper.createUser(email, password1,firstName, lastName, phoneNumber, connectionPool);
                ctx.attribute("message", "Du er hermed oprettet med brugernavn: " + firstName +
                        ". Nu skal du logge på.");
                ctx.render("login.html");
            }

            catch (DatabaseException e)
            {
                ctx.attribute("message", "Dit brugernavn findes allerede. Prøv igen, eller log ind");
                ctx.render("createUser.html");
            }
        } else
        {
            ctx.attribute("message", "Dine to passwords matcher ikke! Prøv igen");
            ctx.render("createUser.html");
        }

    }


}
