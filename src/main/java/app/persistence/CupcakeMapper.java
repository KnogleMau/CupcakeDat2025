package app.persistence;

import app.entities.Cupcake;

import java.math.BigDecimal;
import java.util.List;

public class CupcakeMapper {

    public List<Cupcake> MakeCupcakes(int toppingId, int bottomId){


        return null;
    }

    public BigDecimal calculatePrice(BigDecimal toppingPrice, BigDecimal bottomPrice, int quantity){

        BigDecimal totalToppingPrice = toppingPrice.multiply(BigDecimal.valueOf(quantity));
        BigDecimal totalBottomPrice = bottomPrice.multiply(BigDecimal.valueOf(quantity));

        return totalToppingPrice.add(totalBottomPrice);
    }

}
