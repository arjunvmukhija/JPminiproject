import java.util.*;
import java.io.*;

class Mobile {
    int id;
    String brand;
    String model;
    double price;
    int quantity;

    Mobile(int id, String brand, String model, double price, int quantity) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.price = price;
        this.quantity = quantity;
    }

    public String toString() {
        return id + " | " + brand + " | " + model + " | ₹" + price + " | Qty: " + quantity;
    }
}

class Inventory {
    ArrayList<Mobile> mobiles = new ArrayList<>();

    void addMobile(Mobile m) {
        mobiles.add(m);
    }

    void viewMobiles() {
        if (mobiles.isEmpty()) {
            System.out.println("No mobiles in inventory.");
            return;
        }
        for (Mobile m : mobiles) {
            System.out.println(m);
        }
    }

    void searchMobile(int id) {
        for (Mobile m : mobiles) {
            if (m.id == id) {
                System.out.println("Found: " + m);
                return;
            }
        }
        System.out.println("Mobile not found.");
    }

    void updateMobile(int id, int qty) {
        for (Mobile m : mobiles) {
            if (m.id == id) {
                m.quantity = qty;
                return;
            }
        }
        System.out.println("Mobile not found.");
    }

    void deleteMobile(int id) {
        mobiles.removeIf(m -> m.id == id);
    }

    // Load CSV automatically
    void loadFromCSV(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine(); // skip header

            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");

                int id = Integer.parseInt(data[0].trim());
                String brand = data[1].trim();
                String model = data[2].trim();
                double price = Double.parseDouble(data[3].trim());
                int qty = Integer.parseInt(data[4].trim());

                mobiles.add(new Mobile(id, brand, model, price, qty));
            }

            System.out.println("CSV auto-loaded successfully.");
        } catch (Exception e) {
            System.out.println("No CSV found or error reading file.");
        }
    }

    // Save back to CSV (permanent update)
    void saveToCSV(String filePath) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(filePath))) {
            pw.println("id,brand,model,price,quantity");

            for (Mobile m : mobiles) {
                pw.println(m.id + "," + m.brand + "," + m.model + "," + m.price + "," + m.quantity);
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

        String file = "mobiles.csv"; // same folder
        inv.loadFromCSV(file); // auto-load at start

        while (true) {
            System.out.println("\n--- Mobile Inventory System ---");
            System.out.println("1. Add Mobile");
            System.out.println("2. View Mobiles");
            System.out.println("3. Search Mobile");
            System.out.println("4. Update Quantity");
            System.out.println("5. Delete Mobile");
            System.out.println("6. Exit (Save)");
            System.out.print("Enter choice: ");

            int ch = sc.nextInt();

            switch (ch) {
                case 1:
                    System.out.print("Enter ID: ");
                    int id = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Enter Brand: ");
                    String brand = sc.nextLine();
                    System.out.print("Enter Model: ");
                    String model = sc.nextLine();
                    System.out.print("Enter Price: ");
                    double price = sc.nextDouble();
                    System.out.print("Enter Quantity: ");
                    int qty = sc.nextInt();

                    inv.addMobile(new Mobile(id, brand, model, price, qty));
                    break;

                case 2:
                    inv.viewMobiles();
                    break;

                case 3:
                    System.out.print("Enter ID to search: ");
                    inv.searchMobile(sc.nextInt());
                    break;

                case 4:
                    System.out.print("Enter ID to update: ");
                    int uid = sc.nextInt();
                    System.out.print("Enter new quantity: ");
                    int newQty = sc.nextInt();
                    inv.updateMobile(uid, newQty);
                    break;

                case 5:
                    System.out.print("Enter ID to delete: ");
                    inv.deleteMobile(sc.nextInt());
                    break;

                case 6:
                    inv.saveToCSV(file); // save permanently
                    System.out.println("Exiting...");
                    return;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}   