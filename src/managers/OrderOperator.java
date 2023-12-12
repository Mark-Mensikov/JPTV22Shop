/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managers;

import entity.Customer;
import entity.Orders;
import entity.Product;
import facades.CustomerFacade;
import facades.ProductFacade;
import facades.ZakazFacade;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 *
 * @author pupil
 */
public class OrderOperator {
    private Scanner scanner;
    private CustomerOperator customerOperator;
    private ProductOperator productOperator;
    private ZakazFacade zakazFacade;
    private  CustomerFacade customerFacade;
    private ProductFacade productFacade;
    
    private Map<Product, Integer> mapProductsDay;
    private Map<Product, Integer> mapProductsMonth;
    private Map<Product, Integer> mapProductsYear;
    private Map<Customer, Integer> mapCustomersDay;
    private Map<Customer, Integer> mapCustomersMonth;
    private Map<Customer, Integer> mapCustomersYear;
    
    
    public OrderOperator(Scanner scanner, CustomerOperator customerOperator,ProductOperator productOperator){
        this.scanner = scanner;
        this.customerOperator = customerOperator;
        this.productOperator = productOperator;
        this.zakazFacade = new ZakazFacade();
        this.productFacade = new ProductFacade();
        this.customerFacade = new CustomerFacade();
        this.mapProductsDay = new HashMap<>();
        this.mapProductsMonth = new HashMap<>();
        this.mapProductsYear = new HashMap<>();
        this.mapCustomersDay = new HashMap<>();
        this.mapCustomersMonth = new HashMap<>();
        this.mapCustomersYear = new HashMap<>();
        
    }
    public Orders order(){
        Orders order = new Orders();
        List<Customer> customers = customerOperator.customers();
        List<Product> products = productOperator.products();
        this.customerOperator.printCustomers();
        System.out.print("Enter id of customer: ");
        int customerNumber = scanner.nextInt(); scanner.nextLine();
        this.productOperator.printProducts();
        System.out.print("Enter id of product: ");
        int productNumber = scanner.nextInt(); scanner.nextLine();
        System.out.print("Enter quantity of product: ");
        int productQuantity = scanner.nextInt(); scanner.nextLine();
        
     
        if (customers.get(customerNumber-1).getBalance()>= (products.get(productNumber-1).getPrice()*productQuantity)){
            order.setCustomer(customers.get(customerNumber-1));
            order.setProduct(products.get(productNumber-1));
            order.setSold(productQuantity);
            order.setDate(new GregorianCalendar().getTime());
            zakazFacade.edit(order);
            customers.get(customerNumber-1).setBalance((int) (customers.get(customerNumber-1).getBalance()-products.get(productNumber-1).getPrice()*productQuantity));
            customerFacade.edit(customers.get(customerNumber-1));
            products.get(productNumber-1).setQuantity(products.get(productNumber-1).getQuantity()-productQuantity);
            productFacade.edit(products.get(productNumber-1));
            System.out.println(order.toString());
            return order;
        }else{
            System.out.println("Not enought money");
            return null;
        }
    }
    public void printProducts() {
        List<Orders> orders = zakazFacade.findAll();
        System.out.println("------- Products List --------");
        for (int i = 0; i < orders.size(); i++) {
            System.out.printf("%d. %-10s  %-4stk.  %-10s %s%n",
                    i+1,
                    orders.get(i).getProduct().getType(),
                    orders.get(i).getSold(),
                    orders.get(i).getCustomer().getFname(),
                    orders.get(i).getDate()
                    );
                    
                   
           
        }
    }
    
    public void productListByCustomer (){
        List<Orders> orders = zakazFacade.findAll();
        List<Customer> customers = customerOperator.customers();
        List<Product> products = productOperator.products();
        this.customerOperator.printCustomers();
        System.out.print("Enter number of customer: ");
        int customerNumber = scanner.nextInt(); scanner.nextLine();
        for (int i = 0; i < orders.size(); i++) {
            String inputName =customers.get(customerNumber-1).getFname();
            String customerName = orders.get(i).getCustomer().getFname();
            if (customerName.equals(inputName)){
                System.out.printf("%d. %-10s  %stk.  %-10s %-3s%n",
                    i+1,
                    orders.get(i).getProduct().getType(),
                    orders.get(i).getSold(),
                    orders.get(i).getCustomer().getFname(),
                    orders.get(i).getDate()
                    );
            }
        }
    }
    public void shopIncome (){
        List<Orders> orders = zakazFacade.findAll();
        int sum = 0;
        for (int i = 0; i < orders.size(); i++) {
            sum += orders.get(i).getProduct().getPrice()*orders.get(i).getSold();   
        }
        System.out.println("Total income: "+ sum);
    }
/*
    public void customersRating() {
        List<Zakazi> orders = zakazFacade.findAll();
        Map<Customer,Integer> mapCustomers = new HashMap<>();
        for (int i = 0; i < orders.size(); i++) {
            if(!mapCustomers.containsKey(orders.get(i).getCustomer())){
                mapCustomers.put(orders.get(i).getCustomer(), 1);
            }else{
                mapCustomers.put(orders.get(i).getCustomer(), mapCustomers.get(orders.get(i).getCustomer())+1);
            }
        }
        
        Map<Customer, Integer> sortedMapCustomer = mapCustomers.entrySet()
            .stream()
            .sorted(Map.Entry.<Customer, Integer>comparingByValue().reversed())
            .collect(Collectors.toMap(
                Map.Entry::getKey, 
                Map.Entry::getValue, 
                (oldValue, newValue) -> oldValue, 
                LinkedHashMap::new)
            );
        System.out.println("--------------Customers Rating------------------");
        int n = 1;
        for (Map.Entry<Customer, Integer> entry : sortedMapCustomer.entrySet()) {
            System.out.printf("%d. %-10s %s: %d%n",
                    n,
                    entry.getKey().getFname(),
                    entry.getKey().getLname(),
                    entry.getValue()
            );
            n++;
        }
        
    }
 */  
    public void customersRating() {
        List<Orders> orders = zakazFacade.findAll();

        
        for (Orders order : orders) {
            updateCustomerRatingMap(mapCustomersDay, order, 1); // 1 day period
            updateCustomerRatingMap(mapCustomersMonth, order, 30); // 30 days period
            updateCustomerRatingMap(mapCustomersYear, order, 365); // 365 days period
        }

        
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("Select a period:");
            System.out.println("1. One day");
            System.out.println("2. One month");
            System.out.println("3. One year");
            System.out.println("0. Exit");
            System.out.print("Your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline

            switch (choice) {
                case 1:
                    printCustomerRating(mapCustomersDay, "One Day");
                    break;
                case 2:
                    printCustomerRating(mapCustomersMonth, "One Month");
                    break;
                case 3:
                    printCustomerRating(mapCustomersYear, "One Year");
                    break;
                case 0:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }

        } while (choice != 0);
    }

    private void updateCustomerRatingMap(Map<Customer, Integer> customerRatingMap, Orders order, int daysPeriod) {
        Date currentDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.add(Calendar.DAY_OF_YEAR, -daysPeriod);

        if (order.getDate().after(calendar.getTime())) {
            customerRatingMap.merge(order.getCustomer(), 1, Integer::sum);
        }
    }

    private void printCustomerRating(Map<Customer, Integer> customerRatingMap, String period) {
        Map<Customer, Integer> sortedMapCustomer = customerRatingMap.entrySet()
                .stream()
                .sorted(Map.Entry.<Customer, Integer>comparingByValue().reversed())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue,
                        LinkedHashMap::new)
                );

        System.out.println("----Customers Rating for " + period + "----");
        int n = 1;
        for (Map.Entry<Customer, Integer> entry : sortedMapCustomer.entrySet()) {
            System.out.printf("%d. %-10s %s: %d%n",
                    n,
                    entry.getKey().getFname(),
                    entry.getKey().getLname(),
                    entry.getValue()
            );
            n++;
        }
    }
/*
    public void productsRating() {
        List<Zakazi> orders = zakazFacade.findAll();
        Map<Product,Integer> mapProducts = new HashMap<>();
        for (int i = 0; i < orders.size(); i++) {
            if(!mapProducts.containsKey(orders.get(i).getProduct())){
                mapProducts.put(orders.get(i).getProduct(), orders.get(i).getSold());
            }else{
                mapProducts.put(orders.get(i).getProduct(), mapProducts.get(orders.get(i).getProduct())+orders.get(i).getSold());
            }
        }
        
        Map<Product, Integer> sortedMapProduct = mapProducts.entrySet()
            .stream()
            .sorted(Map.Entry.<Product, Integer>comparingByValue().reversed())
            .collect(Collectors.toMap(
                Map.Entry::getKey, 
                Map.Entry::getValue, 
                (oldValue, newValue) -> oldValue, 
                LinkedHashMap::new)
            );
        System.out.println("--------------Product Rating------------------");
        int n = 1;
        for (Map.Entry<Product, Integer> entry : sortedMapProduct.entrySet()) {
            System.out.printf("%d. %-10s: %d%n",
                    n,
                    entry.getKey().getType(),
                    entry.getValue()
            );
            n++;
        }
    }
    
    */
    public void productsRating() {
        List<Orders> orders = zakazFacade.findAll();

        // Calculate product ratings for different periods
        for (Orders order : orders) {
            updateProductRatingMap(mapProductsDay, order, 1); // 1 day period
            updateProductRatingMap(mapProductsMonth, order, 30); // 30 days period
            updateProductRatingMap(mapProductsYear, order, 365); // 365 days period
        }

        // Implement console user interface
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("Select a period:");
            System.out.println("1. One day");
            System.out.println("2. One month");
            System.out.println("3. One year");
            System.out.println("0. Exit");
            System.out.print("Your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline

            switch (choice) {
                case 1:
                    printProductRating(mapProductsDay, "One Day");
                    break;
                case 2:
                    printProductRating(mapProductsMonth, "One Month");
                    break;
                case 3:
                    printProductRating(mapProductsYear, "One Year");
                    break;
                case 0:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }

        } while (choice != 0);
    }

    private void updateProductRatingMap(Map<Product, Integer> productRatingMap, Orders order, int daysPeriod) {
        Date currentDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.add(Calendar.DAY_OF_YEAR, -daysPeriod);

        if (order.getDate().after(calendar.getTime())) {
            productRatingMap.merge(order.getProduct(), order.getSold(), Integer::sum);
        }
    }

    private void printProductRating(Map<Product, Integer> productRatingMap, String period) {
        Map<Product, Integer> sortedMapProduct = productRatingMap.entrySet()
                .stream()
                .sorted(Map.Entry.<Product, Integer>comparingByValue().reversed())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue,
                        LinkedHashMap::new)
                );

        System.out.println("----Product Rating for " + period + "----");
        int n = 1;
        for (Map.Entry<Product, Integer> entry : sortedMapProduct.entrySet()) {
            System.out.printf("%d. %-10s: %d%n",
                    n,
                    entry.getKey().getType(),
                    entry.getValue()
            );
            n++;
        }
    }
    
}

