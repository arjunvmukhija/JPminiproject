import java.util.*;
import java.io.*;

class Kidswear {
    int id;
    String brand;
    String name;
    double price;
    int quantity;

    Kidswear(int id, String brand, String name, double price, int quantity) {
        this.id = id;
        this.brand = brand;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public String toString() {
        return id + " | " + brand + " | " + name + " | Rs." + price + " | Qty: " + quantity;
    }
}

class Inventory {
    ArrayList<Kidswear> kidswear = new ArrayList<>();

    void addItem(Kidswear k) {
        kidswear.add(k);
    }

    void viewItems() {
        if (kidswear.isEmpty()) {
            System.out.println("No items in inventory.");
            return;
        }
        for (Kidswear k : kidswear) {
            System.out.println(k.toString());
        }
    }

    void searchItem(int id) {
        for (Kidswear k : kidswear) {
            if (k.id == id) {
                System.out.println("Found: " + k.toString());
                return;
            }
        }
        System.out.println("Item not found.");
    }

    void updateItem(int id, int qty) {
        for (Kidswear k : kidswear) {
            if (k.id == id) {
                k.quantity = qty;
                return;
            }
        }
        System.out.println("Item not found.");
    }

    void deleteItem(int id) {
        kidswear.removeIf(k -> k.id == id);
    }

    // Load CSV
    void loadFromCSV(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine(); // skip header

            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");

                int id = Integer.parseInt(data[0].trim());
                String brand = data[1].trim();
                String name = data[2].trim();
                double price = Double.parseDouble(data[3].trim());
                int qty = Integer.parseInt(data[4].trim());

                kidswear.add(new Kidswear(id, brand, name, price, qty));
            }

            System.out.println("CSV auto-loaded successfully.");
        } catch (Exception e) {
            System.out.println("No CSV found or error reading file.");
        }
    }

    
    void saveToCSV(String filePath) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(filePath))) {
            pw.println("id,brand,name,price,quantity");

            for (Kidswear k : kidswear) {
                pw.println(k.id + "," + k.brand + "," + k.name + "," + k.price + "," + k.quantity);
            }

            System.out.println("CSV updated successfully.");
        } catch (Exception e) {
            System.out.println("Error writing CSV.");
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Inventory inv = new Inventory();

        String file = "kidswear.csv";
        inv.loadFromCSV(file);

        while (true) {
            System.out.println("\n--- Kidswear Inventory System ---");
            System.out.println("1. Add Item");
            System.out.println("2. View Items");
            System.out.println("3. Search Item");
            System.out.println("4. Update Quantity");
            System.out.println("5. Delete Item");
            System.out.println("6. Exit (Save)");
            System.out.print("Enter choice: ");

            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter ID: ");
                    int id = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Enter Brand: ");
                    String brand = sc.nextLine();

                    System.out.print("Enter Item Name: ");
                    String name = sc.nextLine();

                    System.out.print("Enter Price: ");
                    double price = sc.nextDouble();

                    System.out.print("Enter Quantity: ");
                    int qty = sc.nextInt();

                    inv.addItem(new Kidswear(id, brand, name, price, qty));
                    break;

                case 2:
                    inv.viewItems();
                    break;

                case 3:
                    System.out.print("Enter ID to search: ");
                    inv.searchItem(sc.nextInt());
                    break;

                case 4:
                    System.out.print("Enter ID to update: ");
                    int uid = sc.nextInt();

                    System.out.print("Enter new quantity: ");
                    int newQty = sc.nextInt();

                    inv.updateItem(uid, newQty);
                    break;

                case 5:
                    System.out.print("Enter ID to delete: ");
                    inv.deleteItem(sc.nextInt());
                    break;

                case 6:
                    inv.saveToCSV(file);
                    System.out.println("Exiting...");
                    return;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}