package app.controllers;

import app.entities.Order;
import app.entities.OrderDetails;
import app.entities.User;
import app.exceptions.DatabaseException;
import app.persistence.AdminMapper;
import app.persistence.ConnectionPool;
import app.persistence.UserMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;


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

        public static void adminRoutes(Javalin app, ConnectionPool connectionPool) {

            app.get("/", ctx -> {
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
    }

        public static void displayOrders(int startPoint, Context ctx, ConnectionPool connectionPool)
        {
            int currentUserID;
            int currentOrderID;
            BigDecimal currentTotalPrice;
            String currentDate;

            try {
                List<Order> cupcakeOrders = AdminMapper.getOrders();
                if(startPoint > (cupcakeOrders.size() - 5)){
                    startPoint = cupcakeOrders.size() - 5;
                }

                Order currentOrderOne;
                currentOrderOne = cupcakeOrders.get(0 + startPoint);
                HashMap<String, Object> taskOneValues = new HashMap<>();
                taskOneValues.put("orderID", currentOrderOne.getOrderID());
                taskOneValues.put("currentOrderID", currentOrderOne.getOrderID());
                taskOneValues.put("currentTotalPrice", currentOrderOne.getTotalPrice());
                taskOneValues.put("currentDate", currentOrderOne.getDate());
                ctx.attribute("taskOne", taskOneValues);

                Order currentOrderTwo;
                currentOrderTwo = cupcakeOrders.get(1 + startPoint);
                HashMap<String, Object> taskTwoValues = new HashMap<>();
                taskTwoValues.put("orderID", currentOrderTwo.getOrderID());
                taskTwoValues.put("currentOrderID", currentOrderTwo.getOrderID());
                taskTwoValues.put("currentTotalPrice", currentOrderTwo.getTotalPrice());
                taskTwoValues.put("currentDate", currentOrderTwo.getDate());
                ctx.attribute("taskTwo", taskTwoValues);

                Order currentOrderThree;
                currentOrderThree = cupcakeOrders.get(2 + startPoint);
                HashMap<String, Object> taskThreeValues = new HashMap<>();
                taskThreeValues.put("orderID", currentOrderThree.getOrderID());
                taskThreeValues.put("currentOrderID", currentOrderThree.getOrderID());
                taskThreeValues.put("currentTotalPrice", currentOrderThree.getTotalPrice());
                taskThreeValues.put("currentDate", currentOrderThree.getDate());
                ctx.attribute("taskThree", taskThreeValues);

                Order currentOrderFour;
                currentOrderFour = cupcakeOrders.get(3 + startPoint);
                HashMap<String, Object> taskFourValues = new HashMap<>();
                taskFourValues.put("orderID", currentOrderFour.getOrderID());
                taskFourValues.put("currentOrderID", currentOrderFour.getOrderID());
                taskFourValues.put("currentTotalPrice", currentOrderFour.getTotalPrice());
                taskFourValues.put("currentDate", currentOrderFour.getDate());
                ctx.attribute("taskFour", taskFourValues);

                Order currentOrderFive;
                currentOrderFive = cupcakeOrders.get(4 + startPoint);
                HashMap<String, Object> taskFiveValues = new HashMap<>();
                taskFiveValues.put("orderID", currentOrderFive.getOrderID());
                taskFiveValues.put("currentOrderID", currentOrderFive.getOrderID());
                taskFiveValues.put("currentTotalPrice", currentOrderFive.getTotalPrice());
                taskFiveValues.put("currentDate", currentOrderFive.getDate());
                ctx.attribute("taskFive", taskFiveValues);
            }

            catch(SQLException e){
                ctx.attribute("Der er ingen ordre i databasen.");
                ctx.render("admin.html");  //Skal ændres
            }
        }

    public static void displayOrderDetails(int orderID, Context ctx, ConnectionPool connectionPool) {

        try { List<OrderDetails> orderDetails1 = AdminMapper.getOrderDetails(orderID);

             OrderDetails currentOrderDetails;
             currentOrderDetails = orderDetails1.get(0);
           String name=  currentOrderDetails.getBottomName();

            int number = currentOrderDetails.getQuantity();

            HashMap<String, Object> taskSixValues = new HashMap<>();
            taskSixValues.put("orderID", currentOrderDetails.getOrderId());
            taskSixValues.put("toppingName", currentOrderDetails.getToppingName());
            taskSixValues.put("bottomName", currentOrderDetails.getBottomName());

            taskSixValues.put("quantity", currentOrderDetails.getQuantity());

            ctx.attribute("taskSix", taskSixValues);
        }
        catch(SQLException e){
            ctx.attribute("Der er ingen ordre i databasen.");
            ctx.render("admin.html");  //Skal ændres
        }

    }
    }


