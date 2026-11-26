import java.util.HashSet;
import java.util.TreeSet;
import java.util.LinkedHashSet;
import java.util.HashMap;
import java.util.Scanner;

public class Main {
    static Scanner sc = new Scanner(System.in);

    static HashMap<Integer, Integer> inventory = new HashMap<>();
    static HashMap<Integer, String> productNames = new HashMap<>();
    static HashMap<Integer, String> productCategories = new HashMap<>();
    static HashMap<Integer, Double> productPrices = new HashMap<>();
    static HashSet<String> categories = new HashSet<>();
    static LinkedHashSet<String> recentlyAdded = new LinkedHashSet<>();

    public static void main(String[] args) {
        int choice;
        do {
            showMenu();
            System.out.print("Enter Your Choice: ");
            choice = readInt();

            switch (choice) {
                case 1: addProduct(); break;
                case 2: displayProducts(); break;
                case 3: searchProduct(); break;
                case 4: updateQuantity(); break;
                case 5: sellProduct(); break;
                case 6: removeProduct(); break;
                case 7: addCategory(); break;
                case 8: displayCategories(); break;
                case 9: displaySortedCategories(); break;
                case 10: displayRecentlyAdded(); break;
                case 11: inventoryReport(); break;
                case 12: System.out.println("Thank you! Exiting Inventory System."); break;
                default: System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 12);
    }

    static void showMenu() {
        System.out.println("\n========================================");
        System.out.println("       PRODUCT INVENTORY SYSTEM");
        System.out.println("========================================");
        System.out.println("1. Add Product");
        System.out.println("2. Display All Products");
        System.out.println("3. Search Product");
        System.out.println("4. Update Product Quantity");
        System.out.println("5. Sell Product");
        System.out.println("6. Remove Product");
        System.out.println("7. Add Product Category");
        System.out.println("8. Display Unique Categories");
        System.out.println("9. Display Sorted Categories");
        System.out.println("10. Display Recently Added Products");
        System.out.println("11. Generate Inventory Report");
        System.out.println("12. Exit");
    }

    static int readInt() {
        while (!sc.hasNextInt()) {
            System.out.print("Please enter a number: ");
            sc.next();
        }
        int value = sc.nextInt();
        sc.nextLine();
        return value;
    }

    static void addProduct() {
        System.out.print("Product ID: ");
        int id = readInt();
        if (inventory.containsKey(id)) {
            System.out.println("Product ID already exists!");
            return;
        }
        System.out.print("Product Name: ");
        String name = sc.nextLine();
        System.out.print("Category: ");
        String category = sc.nextLine();
        System.out.print("Price: ");
        double price = readDouble();
        System.out.print("Quantity: ");
        int quantity = readInt();

        if (price < 0 || quantity < 0) {
            System.out.println("Price and quantity cannot be negative.");
            return;
        }
        inventory.put(id, quantity);
        productNames.put(id, name);
        productCategories.put(id, category);
        productPrices.put(id, price);
        categories.add(category);
        recentlyAdded.add(name);
        System.out.println("\nProduct Added Successfully!");
    }

    static double readDouble() {
        while (!sc.hasNextDouble()) {
            System.out.print("Please enter a valid price: ");
            sc.next();
        }
        double value = sc.nextDouble();
        sc.nextLine();
        return value;
    }

    static void displayProducts() {
        if (inventory.isEmpty()) {
            System.out.println("No products available.");
            return;
        }
        System.out.println("\n---------- PRODUCT INVENTORY ----------");
        System.out.printf("%-10s %-18s %-18s %-12s %-10s%n", "ID", "Product", "Category", "Price", "Quantity");
        for (int id : inventory.keySet()) {
            displayOneProduct(id);
        }
        System.out.println("Total Products: " + inventory.size());
    }

    static void displayOneProduct(int id) {
        System.out.printf("%-10d %-18s %-18s %-12.2f %-10d%n",
                id, productNames.get(id), productCategories.get(id), productPrices.get(id), inventory.get(id));
    }

    static void searchProduct() {
        System.out.print("Enter Product ID: ");
        int id = readInt();
        if (!inventory.containsKey(id)) {
            System.out.println("Product not found.");
            return;
        }
        System.out.println("\nProduct Details:");
        displayOneProduct(id);
    }

    static void updateQuantity() {
        System.out.print("Enter Product ID: ");
        int id = readInt();
        if (!inventory.containsKey(id)) {
            System.out.println("Product not found.");
            return;
        }
        System.out.print("Enter quantity to add: ");
        int amount = readInt();
        if (amount <= 0) {
            System.out.println("Quantity must be greater than zero.");
            return;
        }
        inventory.put(id, inventory.get(id) + amount);
        System.out.println("Stock Updated Successfully! Available Quantity: " + inventory.get(id));
    }

    static void sellProduct() {
        System.out.print("Enter Product ID: ");
        int id = readInt();
        if (!inventory.containsKey(id)) {
            System.out.println("Product not found.");
            return;
        }
        System.out.print("Enter Quantity Sold: ");
        int amount = readInt();
        if (amount <= 0) {
            System.out.println("Quantity must be greater than zero.");
        } else if (amount > inventory.get(id)) {
            System.out.println("Not enough stock available!");
        } else {
            inventory.put(id, inventory.get(id) - amount);
            System.out.println("\nSale Completed Successfully!");
            System.out.println("Product: " + productNames.get(id));
            System.out.println("Remaining Quantity: " + inventory.get(id));
        }
    }

    static void removeProduct() {
        System.out.print("Enter Product ID to remove: ");
        int id = readInt();
        if (!inventory.containsKey(id)) {
            System.out.println("Product not found.");
            return;
        }
        String name = productNames.remove(id);
        String category = productCategories.remove(id);
        inventory.remove(id);
        productPrices.remove(id);
        if (!productNames.containsValue(name)) recentlyAdded.remove(name);
        if (!productCategories.containsValue(category)) categories.remove(category);
        System.out.println("Product Removed Successfully!");
    }

    static void addCategory() {
        System.out.print("Enter Category Name: ");
        String category = sc.nextLine();
        if (categories.add(category)) System.out.println("Category Added Successfully!");
        else System.out.println("Category already exists.");
    }

    static void displayCategories() {
        System.out.println("\n--------- PRODUCT CATEGORIES ---------");
        if (categories.isEmpty()) System.out.println("No categories available.");
        else for (String category : categories) System.out.println(category);
        System.out.println("Total Unique Categories: " + categories.size());
    }

    static void displaySortedCategories() {
        TreeSet<String> sorted = new TreeSet<>(categories);
        System.out.println("\n--------- SORTED CATEGORIES ----------");
        if (sorted.isEmpty()) System.out.println("No categories available.");
        else {
            for (String category : sorted) System.out.println(category);
            System.out.println("First Category: " + sorted.first());
            System.out.println("Last Category: " + sorted.last());
            System.out.println("Descending Order: " + sorted.descendingSet());
        }
    }

    static void displayRecentlyAdded() {
        System.out.println("\n--------- RECENTLY ADDED PRODUCTS ---------");
        if (recentlyAdded.isEmpty()) System.out.println("No products available.");
        else for (String name : recentlyAdded) System.out.println(name);
        System.out.print("Search product name: ");
        String search = sc.nextLine();
        if (recentlyAdded.contains(search)) System.out.println("Product found: " + search);
        else System.out.println("Product not found.");
    }

    static void inventoryReport() {
        System.out.println("\n--------- INVENTORY REPORT ---------");
        displayProducts();
        System.out.println("\nProducts with quantity below 5:");
        boolean found = false;
        for (int id : inventory.keySet()) {
            if (inventory.get(id) < 5) {
                displayOneProduct(id);
                found = true;
            }
        }
        if (!found) System.out.println("No low-stock products.");
        int totalUnits = 0;
        int highestId = -1;
        int highestQuantity = -1;
        for (int id : inventory.keySet()) {
            totalUnits += inventory.get(id);
            if (inventory.get(id) > highestQuantity) {
                highestQuantity = inventory.get(id);
                highestId = id;
            }
        }
        System.out.println("Total Units Available: " + totalUnits);
        if (highestId != -1) System.out.println("Highest Stock Product: " + productNames.get(highestId) + " (" + highestQuantity + ")");
        System.out.println("Unique Categories: " + categories);
        System.out.println("Total Unique Categories: " + categories.size());
    }
}
