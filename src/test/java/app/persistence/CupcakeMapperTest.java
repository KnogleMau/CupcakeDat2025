package app.persistence;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class CupcakeMapperTest {
CupcakeMapper instance = new CupcakeMapper();


    @BeforeEach
    void setUp(){

    }

    @AfterEach
    void tearDown(){

    }

    @Test
    @DisplayName("Calculate Cupcakes from given price")
    void test1(){
        BigDecimal expected = BigDecimal.valueOf(108);
        //BigDecimal actual = instance.calculatePrice(BigDecimal.valueOf(5), BigDecimal.valueOf(7), 9);
        assertEquals(expected, actual);

    }

}