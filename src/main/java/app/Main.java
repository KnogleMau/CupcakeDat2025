package app;

import app.config.SessionConfig;
import app.config.ThymeleafConfig;
import app.controllers.CupcakeController;
import app.controllers.UserController;
import app.entities.Bottom;
import app.entities.Cupcake;
import app.entities.Topping;
import app.persistence.BottomMapper;
import app.persistence.ConnectionPool;
import app.persistence.CupcakeMapper;
import app.persistence.ToppingMapper;
import io.javalin.Javalin;
import io.javalin.rendering.template.JavalinThymeleaf;

import java.util.List;
import java.util.logging.Logger;

public class Main {
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres";
    private static final String URL = "jdbc:postgresql://localhost:5432/%s?currentSchema=public";
    private static final String DB = "cupcakes";

    public static final ConnectionPool connectionPool = ConnectionPool.getInstance(USER, PASSWORD, URL, DB);

    public static void main(String[] args) {
/*
        try {
            ToppingMapper mapper = new ToppingMapper();
            List<Topping> toppings = mapper.getAllToppings();

            BottomMapper mapper1 = new BottomMapper();
            List<Bottom> bottoms = mapper1.getAllBottoms();

            for (Topping t : toppings) {
                System.out.println("ID: " + t.getId() +
                        ", Name: " + t.getName() +
                        ", Price: " + t.getPrice());
            }

            for (Bottom t : bottoms) {
                System.out.println("ID: " + t.getId() +
                        ", Name: " + t.getName() +
                        ", Price: " + t.getPrice());
            }
        } catch (Exception e) {
            e.printStackTrace();
         }
*/




        // Initializing Javalin and Jetty webserver

        Javalin app = Javalin.create(config -> {
            config.staticFiles.add("/public");
            config.jetty.modifyServletContextHandler(handler -> handler.setSessionHandler(SessionConfig.sessionConfig()));
            config.fileRenderer(new JavalinThymeleaf(ThymeleafConfig.templateEngine()));
        }).start(7070);

        //routings

        // Frontpage

       // app.get("/", ctx -> ctx.render("index.html"));

        // Loginpage
       // UserController.cupcakeRoutes(app, connectionPool);
        CupcakeController.routes(app, connectionPool);

    }
}