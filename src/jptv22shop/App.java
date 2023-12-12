/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jptv22shop;


import entity.Orders;

import entity.Product;
import java.util.List;
import java.util.Scanner;
import managers.CustomerOperator;

import managers.SaleManager;
import managers.OrderOperator;
import managers.ProductOperator;


/**
 *
 * @author pupil
 */
public class App {
    //private List<Customer> customers;
    private List<Product> products;
    private List<Orders> orders;
    
    private final SaleManager saleManager;
    private final CustomerOperator customerOperator;
    private final ProductOperator productOperator;
    private  OrderOperator orderOperator;
    private final Scanner scanner;
    
    
    public App () {
        
        this.scanner = new Scanner(System.in);
        
        //this.customers = writerReader.readCustomers();
        
        this.customerOperator = new CustomerOperator(scanner);
        this.productOperator = new ProductOperator(scanner);
        this.orderOperator = new OrderOperator(scanner,customerOperator,productOperator);
        this.saleManager = new SaleManager(scanner);
        
    }
    void run() {
        
        Scanner scanner = new Scanner(System.in);
        boolean repeat = true;
        do{
            System.out.println("Welcome to the shop! Select task: ");
            System.out.println("0. Exit");
            System.out.println("1. Add product");
            System.out.println("2. Add customer");
            System.out.println("3. Add money ");
            System.out.println("4. Buy product");
            System.out.println("5. Print list of products");
            System.out.println("6. Print list of customers");
            System.out.println("7. Print total");
            System.out.println("8. Product list bought by customer");
            System.out.println("9. List of all sold products");
            System.out.println("10. Customers rating");
            System.out.println("11. Products rating");
            System.out.println("12. Edit Product");
            System.out.println("13. Edit Customer");
            System.out.println("14. Timer to sales");
            System.out.println("15. Add sale");
            System.out.print("Set task: ");
            int task = scanner.nextInt();scanner.nextLine();
            switch (task) {
                case 0:
                    repeat = false;
                    break;
                case 1:
                    //this.products.add(productOperator.addProduct());
                    //writerReader.writeProducts(products);
                    if (confirmAction()) {
                    productOperator.addProduct();
                    }else{System.out.println("Product addition canceled.");
                    }
                    break;
                case 2:
                    //this.customers.add(customerOperator.addCustomer());
                    //writerReader.writeCustomers(customers);
                     if (confirmAction()) {
                    customerOperator.addCustomer();
                    }else{System.out.println("Product addition canceled.");
                    }
                    break;
                case 3:
                     if (confirmAction()) {
                    customerOperator.changeBalance();
                    }else{System.out.println("Product addition canceled.");
                    }
                    break;
                case 4:
                    if (confirmAction()) {
                    orderOperator.order();
                    }else{System.out.println("Product addition canceled.");
                    }
                    break;
                case 5:
                    productOperator.printProducts();
                    break;
                case 6:
                    customerOperator.printCustomers();
                    break;
                case 7:
                    orderOperator.shopIncome();
                    break;
                case 8:
                    orderOperator.productListByCustomer();
                    break;
                case 9:
                    orderOperator.printProducts();
                    break;
                case 10:
                    orderOperator.customersRating();
                    break; 
                case 11:
                    orderOperator.productsRating();
                    break;
                case 12:
                    this.productOperator.editProduct();
                    
                    break;
                case 13:
                    customerOperator.editCustomer();
                    
                    break;
                case 14:
                    saleManager.timerUntilSaleStart();
                    
                    break;
                case 15:
                    saleManager.addSale();
                    
                    break;
                
                default:
                    System.out.println("Select number from list!");
            }
            System.out.println("\n-----------------------------------------------------");
        }while(repeat);
    }
    private boolean confirmAction() {
    System.out.println("Are you sure? (y/n)");
    String confirmation = scanner.nextLine().toLowerCase();
    return confirmation.equals("y");
}
}
