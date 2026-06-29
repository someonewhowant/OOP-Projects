package com.grocerystore.shell;

import com.grocerystore.discount.*;
import com.grocerystore.order.Checkout;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.math.BigDecimal;

@ShellComponent
public class CampaignCommands {
    private final Checkout checkout;

    public CampaignCommands(Checkout checkout) {
        this.checkout = checkout;
    }

    @ShellMethod(key = {"discount add-category"}, value = "Add a percentage discount for a specific category")
    public String addCategoryDiscount(String category, double percent) {
        String desc = String.format("%s%% off on %s category", percent, category);
        DiscountCampaign campaign = new DiscountCampaign(
                desc,
                new CategoryBasedCriteria(category),
                new PercentageDiscountStrategy(new BigDecimal(String.valueOf(percent)))
        );
        checkout.addCampaign(campaign);
        return "✅ Added discount campaign: " + desc;
    }

    @ShellMethod(key = {"discount add-item"}, value = "Add a fixed amount discount for a specific item")
    public String addItemDiscount(
            @ShellOption(valueProvider = BarcodeValueProvider.class) String barcode, 
            double amount) {
        String desc = String.format("$%s off on item %s", amount, barcode);
        DiscountCampaign campaign = new DiscountCampaign(
                desc,
                new ItemBasedCriteria(barcode),
                new FixedAmountDiscountStrategy(new BigDecimal(String.valueOf(amount)))
        );
        checkout.addCampaign(campaign);
        return "✅ Added discount campaign: " + desc;
    }

    @ShellMethod(key = {"discount list", "view-discounts"}, value = "List all active discount campaigns")
    public String listDiscounts() {
        if (checkout.getActiveCampaigns().isEmpty()) {
            return "No active campaigns right now.";
        }
        StringBuilder sb = new StringBuilder("🎟️ Active Discount Campaigns:\n");
        for (DiscountCampaign campaign : checkout.getActiveCampaigns()) {
            sb.append(" - ").append(campaign.getDescription()).append("\n");
        }
        return sb.toString();
    }
}
