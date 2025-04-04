package app.controllers;

import app.entities.Order;
import app.entities.User;
import app.exceptions.DatabaseException;
import app.persistence.*;
import io.javalin.Javalin;
import app.entities.Topping;
import app.entities.Bottom;

import java.util.List;


public class CupcakeController {


    public static void routes(Javalin app, ConnectionPool connectionPool) {


        ToppingMapper toppingMapper = new ToppingMapper();
        BottomMapper bottomMapper = new BottomMapper();
      //  AdminMapper adminMapper = new AdminMapper();


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

      /*    app.get("/", ctx -> {
              List<Order> orders = AdminMapper.getOrders();
              ctx.attribute("orders", orders);
              ctx.render("adminDisplayOrders.html");
          });  */

       //
        // app.post("login", ctx -> login(ctx, connectionPool));
    }

    
}
