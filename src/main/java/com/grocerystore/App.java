package com.grocerystore;

import com.grocerystore.discount.CategoryBasedCriteria;
import com.grocerystore.discount.DiscountCampaign;
import com.grocerystore.discount.FixedAmountDiscountStrategy;
import com.grocerystore.discount.ItemBasedCriteria;
import com.grocerystore.discount.PercentageDiscountStrategy;
import com.grocerystore.model.Item;
import com.grocerystore.order.Order;
import com.grocerystore.order.Receipt;

import java.math.BigDecimal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootApplication
public class App {
    private static final Logger logger = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {
        logger.info("Initializing Database...");
        DatabaseManager.initializeDatabase();

        logger.info("Starting Grocery Store Spring Shell Application...");
        SpringApplication.run(App.class, args);
    }

}
