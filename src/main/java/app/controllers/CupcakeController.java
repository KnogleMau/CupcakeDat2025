package app.controllers;

import app.entities.Cupcake;
import app.entities.User;
import app.exceptions.DatabaseException;
import app.persistence.*;
import io.javalin.Javalin;
import io.javalin.http.Context;
import app.entities.Topping;
import app.entities.Bottom;

import java.math.BigDecimal;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

import static app.controllers.UserController.*;

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

        app.get("/admin", ctx -> {

            ctx.render("admin.html");

        });

       /* app.post("/displayOrder", ctx ->{
            ctx.render("adminDisplayOrders.html");
        });  */

        app.get("/displayOrder", ctx -> {
            int startPoint = ctx.sessionAttribute("startPoint") != null ? ctx.sessionAttribute("startPoint") : 0;
            if (ctx.sessionAttribute("choice") == null) {
                ctx.sessionAttribute("choice", 0);}
            displayOrders(startPoint, ctx, connectionPool);
            ctx.render("adminDisplayOrders.html");});

        app.post("/displayOrderDetails", ctx -> {
            String requestedOrderID = ctx.formParam("orderID");
            Integer startPoint = ctx.sessionAttribute("startPoint");
            if (startPoint == null) {
                startPoint = 0;
            }
            String userChoise = ctx.formParam("choice");
            int userChoises = userChoise != null ? Integer.parseInt(userChoise) : 0;
            startPoint += userChoises;
            ctx.sessionAttribute("startPoint", startPoint);

            displayOrders(startPoint, ctx, connectionPool);
            int requestedOrderIdNumber = Integer.parseInt(requestedOrderID);
            displayOrderDetails(requestedOrderIdNumber, ctx, connectionPool);

            ctx.render("adminDisplayOrders.html");});

        app.post("/displayOrders", ctx -> {
            String requestedOrderID = ctx.formParam("orderID");
            Integer startPoint = ctx.sessionAttribute("startPoint");
            if (startPoint == null) {
                startPoint = 0;
            }
            String userChoise = ctx.formParam("choice");
            int userChoises = userChoise != null ? Integer.parseInt(userChoise) : 0;
            startPoint += userChoises;
            ctx.sessionAttribute("startPoint", startPoint);

            displayOrders(startPoint, ctx, connectionPool);
            ctx.render("adminDisplayOrders.html");});

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

            double totalPrice = 0;
            for (Cupcake cupcake : cupcakes) {
                totalPrice += cupcake.getTotalPrice(); // Husk at have en getTotalPrice() i din Cupcake-klasse
            }

            ctx.attribute("basket", cupcakes);
            ctx.attribute("totalPrice", totalPrice);
            ctx.render("basket.html"); // Sørg for at have en basket.html-side
        });

        app.post("/remove-cupcake", ctx -> {
            int indexToRemove = Integer.parseInt(ctx.formParam("basketIndex"));
            List<Cupcake> basket = ctx.sessionAttribute("basket");

            if (basket != null && indexToRemove >= 0 && indexToRemove < basket.size()) {
                basket.remove(indexToRemove);
                ctx.sessionAttribute("basket", basket);
            }

            ctx.redirect("/basket");
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
            double toppingPrice = toppingMapper.getToppingPriceById(toppingId);
            double bottomPrice = bottomMapper.getBottomPriceById(bottomId);

            double totalPrice = calculatePrice(toppingPrice,bottomPrice,quantity);

             cupcakes.add(new Cupcake(toppingId,bottomId,quantity,bottomName,toppingName,totalPrice));

            ctx.sessionAttribute("basket", cupcakes);

            System.out.println(bottomName);
            System.out.println(toppingName);
            System.out.println(totalPrice);

            ctx.redirect("/index");

            return cupcakes;
        }

        public static double calculatePrice(double toppingPrice,double bottomPrice, int quantity){

          double totalPrice = (toppingPrice + bottomPrice) * quantity;

        return totalPrice;
        }

    }




