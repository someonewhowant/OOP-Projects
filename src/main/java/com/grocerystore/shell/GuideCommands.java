package com.grocerystore.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class GuideCommands {

    @ShellMethod(key = {"guide", "tutorial", "howto"}, value = "Show a quick guide on how to use the Grocery Store CLI")
    public String showGuide() {
        StringBuilder sb = new StringBuilder();
        sb.append("🏪 === GROCERY STORE CLI GUIDE === 🏪\n\n");
        
        sb.append("🛒 1. MANAGING CATALOG & INVENTORY\n");
        sb.append("   - Add a new item to the system:\n");
        sb.append("     > item add <barcode> <name> <category> <price>\n");
        sb.append("     Example: item add 111 Apple Fruits 1.50\n\n");
        
        sb.append("   - Receive stock from supplier:\n");
        sb.append("     > stock add <barcode> <quantity>\n");
        sb.append("     Example: stock add 111 100\n\n");

        sb.append("   - Check remaining stock:\n");
        sb.append("     > stock view <barcode>\n");
        sb.append("     Example: stock view 111\n\n");

        sb.append("💳 2. CASHIER DESK (INTERACTIVE CHECKOUT)\n");
        sb.append("   - Step A: Open a new receipt for a customer\n");
        sb.append("     > start\n\n");

        sb.append("   - Step B: Scan items (can be repeated multiple times)\n");
        sb.append("     > scan <barcode> [quantity]\n");
        sb.append("     Example: scan 111 5\n\n");

        sb.append("   - Step C: Complete payment and print receipt\n");
        sb.append("     > pay <amount_given_by_customer>\n");
        sb.append("     Example: pay 20.00\n\n");

        sb.append("⚙️ 3. GENERAL COMMANDS\n");
        sb.append("   - Type 'help' to see all available system commands.\n");
        sb.append("   - Type 'clear' to clear the screen.\n");
        sb.append("   - Type 'exit' or 'quit' to close the application.\n");
        
        return sb.toString();
    }
}
