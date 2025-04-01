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
       // app.get("/", ctx -> ctx.render("index.html"));
        app.post("/", ctx -> makeCupcakes(ctx, connectionPool));



    }



        public static List<Cupcake> makeCupcakes(Context ctx, ConnectionPool connectionPool){

            List<Cupcake> cupcakes = ctx.sessionAttribute("basket");
            if (cupcakes == null) {
                cupcakes = new ArrayList<>();
            }

            // int toppingId = Integer.parseInt(ctx.formParam("topping_id"));
            // int bottomId = Integer.parseInt(ctx.formParam("bottom_id"));
            // int quantity = Integer.parseInt(ctx.formParam("quantity"));

            String toppingId = ctx.formParam("topping_name");
            String bottomId = ctx.formParam("bottom_name");
            String quantity = ctx.formParam("quantity");

            System.out.println(toppingId);
            System.out.println(bottomId);
            System.out.println(quantity);

             // cupcakes.add(new Cupcake(toppingId,bottomId,quantity));
/*
              String toppingPrice = ctx.formParam("topping_price");
              String bottomPrice = ctx.formParam("bottom_price");

              BigDecimal topPrice = BigDecimal.valueOf(Long.parseLong(toppingPrice));
            BigDecimal botPrice = BigDecimal.valueOf(Long.parseLong(bottomPrice));

              BigDecimal totalCupcakePrice = calculatePrice(topPrice,botPrice, quantity);

            System.out.println(totalCupcakePrice);
*/

            for(int i=0; i <= cupcakes.size(); i++){
                System.out.println(cupcakes.get(i));
            }
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




