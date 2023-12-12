/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managers;

import entity.Customer;
import entity.Product;
import facades.ProductFacade;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author pupil
 */
public class ProductOperator {
    private Scanner  scanner;
    private final ProductFacade productFacade;
    
    public ProductOperator(Scanner scanner){
        this.scanner = scanner;
        this.productFacade = new ProductFacade();
    }
    public Product addProduct() {
    Product product = new Product();
    
    System.out.print("Enter name of product: ");
    product.setType(scanner.nextLine());
    
    System.out.print("Enter price of product: ");
    int price = scanner.nextInt();
    scanner.nextLine();
    product.setPrice(price);
    
    System.out.print("Enter quantity of product: ");
    int quantity = scanner.nextInt();
    scanner.nextLine();
    product.setQuantity(quantity);
    
  
    
    System.out.println("Added product: ");
    System.out.println(product.toString());

    
    productFacade.create(product);

    System.out.println("Product created successfully!");
    
    return product;
}
    
    public void printProducts() {
    List<Product> products = productFacade.findAll();
    System.out.println("------- Products List --------");
    for (int i = 0; i < products.size(); i++) {
        System.out.printf("%d.  %-10s Price: %-3s Quantity: %-3s \n",
                i + 1,
                products.get(i).getType(),
                products.get(i).getPrice(),
                products.get(i).getQuantity());
    }
    }

    public void editProduct() {
    List<Product> products = productFacade.findAll();

    System.out.println("------- Products List --------");

    for (int i = 0; i < products.size(); i++) {
        System.out.printf("%d. %-10s  %-10s  %-3s %-3s \n",
                i + 1,
                products.get(i).getId(),
                products.get(i).getType(),
                products.get(i).getPrice(),
                products.get(i).getQuantity());
    }

    System.out.println("Select the number of the product (1-" + products.size() + "):");
    int selectedProduct = scanner.nextInt();
    scanner.nextLine();

    if (selectedProduct <= products.size() && selectedProduct > 0) {

        boolean repeat = true;

        do {
            System.out.println("0. Exit program press");
            System.out.println("1. Change Product Name");
            System.out.println("2. Change Price");
            System.out.println("3. Change Quantity");
            

            int task = scanner.nextInt();
            scanner.nextLine();

            switch (task) {
                case 0:
                    repeat = false;
                    break;
                case 1:
                    System.out.println("Enter new Product Name:");
                    products.get(selectedProduct - 1).setType(scanner.nextLine());
                    break;
                case 2:
                    System.out.println("Enter new Price:");
                    products.get(selectedProduct - 1).setPrice(scanner.nextInt());
                    scanner.nextLine();
                    break;
                case 3:
                    System.out.println("Enter new Quantity:");
                    products.get(selectedProduct - 1).setQuantity(scanner.nextInt());
                    scanner.nextLine();
                    break;
                
                default:
                    System.out.println("Select a number from the list!");
            }
        } while (repeat);

        // Update the database with the changes
        productFacade.edit(products.get(selectedProduct - 1));

    } else {
        System.out.println("Product not found in the list.");
    }
    }
    public List<Product> products(){
        return productFacade.findAll();
    }
    
    public Product findById(int id){
        return productFacade.find((long)id);
    }
    
    
    private static boolean exitRequested = false;
    public  void expireTimer(){
        
        LocalDateTime currentDateTime = LocalDateTime.now();

        
        int currentYear = currentDateTime.getYear();

        
        LocalDateTime newYearDateTime = LocalDateTime.of(currentYear + 1, 1, 1, 0, 0, 0);

        
        Duration duration ;
        duration = Duration.between(currentDateTime, newYearDateTime);
        
        long years = duration.toDays() / 365;
        long months = (duration.toDays() % 365) / 30;
        long days = duration.toDays() % 30;
        long hours = duration.toHours() % 24;
        long minutes = duration.toMinutes() % 60;
        long seconds = duration.getSeconds() % 60;

        System.out.println("Time until New Year: " +
                        years + " years, " +
                        months + " months, " +
                        days + " days, " +
                        hours + " hours, " +
                        minutes + " minutes, " +
                        seconds + " seconds");
        
    }
            
}

