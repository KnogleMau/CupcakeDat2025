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





public class CupcakeController {


    public static void routes(Javalin app, ConnectionPool connectionPool) {


        ToppingMapper toppingMapper = new ToppingMapper();
        BottomMapper bottomMapper = new BottomMapper();

        app.get("/", ctx -> {
            List<Topping> toppings = toppingMapper.getAllToppings();
            List<Bottom> bottoms = bottomMapper.getAllBottoms(); // ✅

            ctx.attribute("toppings", toppings);
            ctx.attribute("bottoms", bottoms); // ✅
            ctx.render("index.html");
        });

       //
        // app.post("login", ctx -> login(ctx, connectionPool));
       // app.get("/", ctx -> ctx.render("index.html"));
        app.post("/", ctx -> makeCupcakes(ctx, connectionPool));



    }



        public static List<Cupcake> makeCupcakes(Context ctx, ConnectionPool connectionPool){

            List<Cupcake> cupcakes = ctx.sessionAttribute("basket");
            if (cupcakes == null) {
                cupcakes = new ArrayList<>();
            }

             int toppingId = Integer.parseInt(ctx.formParam("topping_id"));
             int bottomId = Integer.parseInt(ctx.formParam("bottom_id"));
             int quantity = Integer.parseInt(ctx.formParam("quantity"));


             cupcakes.add(new Cupcake(toppingId,bottomId,quantity));


            System.out.println("Topping ID: " + ctx.formParam("topping_id"));
            System.out.println("Bottom ID: " + ctx.formParam("bottom_id"));
            System.out.println("Quantity: " + ctx.formParam("quantity"));


            ctx.sessionAttribute("basket", cupcakes);


            ctx.redirect("/");

            return cupcakes;
        }

        public static BigDecimal calculatePrice(BigDecimal toppingPrice, BigDecimal bottomPrice, int quantity){

            BigDecimal totalToppingPrice = toppingPrice.multiply(BigDecimal.valueOf(quantity));
            BigDecimal totalBottomPrice = bottomPrice.multiply(BigDecimal.valueOf(quantity));

            return totalToppingPrice.add(totalBottomPrice);
        }

    }




