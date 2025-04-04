package app.controllers;

import app.entities.Cupcake;
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

import java.math.BigDecimal;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

import static app.controllers.UserController.createUser;
import static app.controllers.UserController.login;


public class CupcakeController {


    public static void routes(Javalin app, ConnectionPool connectionPool) {


        ToppingMapper toppingMapper = new ToppingMapper();
        BottomMapper bottomMapper = new BottomMapper();

        app.get("/", ctx -> {
            ctx.render("frontpage.html");
        });

        app.get("/login", ctx ->{

            ctx.render("login.html");

        });

        app.get("/createUser", ctx ->{

            ctx.render("createUser.html");

        });


        app.post("/createUser", ctx -> createUser(ctx, connectionPool));

         app.post("/login", ctx -> login(ctx, connectionPool));





        app.get("/index", ctx -> {
            List<Topping> toppings = toppingMapper.getAllToppings();
            List<Bottom> bottoms = bottomMapper.getAllBottoms(); // ✅

            User user = ctx.sessionAttribute("currentUser");
            ctx.attribute("user", user); // makes ${user.email}, ${user.balance} etc available


            List<Cupcake> cupcakes = ctx.sessionAttribute("basket");
            if (cupcakes == null) {
                cupcakes = new ArrayList<>();
            }

            ctx.attribute("basket", cupcakes);  // Makes the basket available in the template
            ctx.attribute("toppings", toppings);
            ctx.attribute("bottoms", bottoms); // ✅
            ctx.render("index.html");




        });


        app.post("/index", ctx -> makeCupcakes(ctx, connectionPool));



        app.get("/basket", ctx -> {

            User user = ctx.sessionAttribute("currentUser");
            ctx.attribute("user", user);

            List<Cupcake> cupcakes = ctx.sessionAttribute("basket");
            if (cupcakes == null) {
                cupcakes = new ArrayList<>();
            }
            ctx.attribute("basket", cupcakes);
            ctx.render("basket.html"); // Sørg for at have en basket.html-side
        });

    }



        public static List<Cupcake> makeCupcakes(Context ctx, ConnectionPool connectionPool){

            List<Cupcake> cupcakes = ctx.sessionAttribute("basket");
            if (cupcakes == null) {
                cupcakes = new ArrayList<>();
            }

             int toppingId = Integer.parseInt(ctx.formParam("topping_id"));
             int bottomId = Integer.parseInt(ctx.formParam("bottom_id"));
             int quantity = Integer.parseInt(ctx.formParam("quantity"));


            BottomMapper bottomMapper = new BottomMapper();
            ToppingMapper toppingMapper = new ToppingMapper();
             String toppingName = toppingMapper.getToppingById(toppingId);
            String bottomName = bottomMapper.getBottomById(bottomId);

             cupcakes.add(new Cupcake(toppingId,bottomId,quantity,bottomName,toppingName));

            ctx.sessionAttribute("basket", cupcakes);

            System.out.println(bottomName);
            System.out.println(toppingName);

            ctx.redirect("/index");

            return cupcakes;
        }

        public static BigDecimal calculatePrice(BigDecimal toppingPrice, BigDecimal bottomPrice, int quantity){

            BigDecimal totalToppingPrice = toppingPrice.multiply(BigDecimal.valueOf(quantity));
            BigDecimal totalBottomPrice = bottomPrice.multiply(BigDecimal.valueOf(quantity));

            return totalToppingPrice.add(totalBottomPrice);
        }

    }




