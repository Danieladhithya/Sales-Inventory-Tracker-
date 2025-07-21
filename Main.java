import dao.ProductDAO;
import models.Product;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n1. Add Product\n2. View Products\n3. Exit");
            int choice = scanner.nextInt();
            scanner.nextLine();

            try {
                switch (choice) {
                    case 1:
                        System.out.print("Enter product name: ");
                        String name = scanner.nextLine();
                        System.out.print("Enter quantity: ");
                        int qty = scanner.nextInt();
                        System.out.print("Enter price: ");
                        double price = scanner.nextDouble();
                        Product p = new Product(0, name, qty, price);
                        ProductDAO.addProduct(p);
                        System.out.println("Product added!");
                        break;
                    case 2:
                        List<Product> products = ProductDAO.getAllProducts();
                        System.out.println("\nProduct List:");
                        for (Product prod : products) {
                            System.out.printf("%d | %s | Qty: %d | Price: %.2f\n",
                                    prod.getId(), prod.getName(), prod.getQuantity(), prod.getPrice());
                        }
                        break;
                    case 3:
                        System.exit(0);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}