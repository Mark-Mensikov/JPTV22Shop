/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managers;

import entity.Customer;
import java.util.List;
import java.util.Scanner;
import facades.CustomerFacade;
/**
 *
 * @author pupil
 */
public class CustomerOperator {
    private Scanner scanner;
    private final CustomerFacade customerFacade;
    
    public CustomerOperator(Scanner scanner){
        this.scanner = scanner;
        this.customerFacade = new CustomerFacade();
    }
    public void addCustomer(){
        Customer customer = new Customer();

        System.out.print("Enter first name: ");
        customer.setFname(scanner.nextLine());

        System.out.print("Enter last name: ");
        customer.setLname(scanner.nextLine());

        System.out.print("Enter phone: ");
        customer.setPhone(scanner.nextLine());

        System.out.print("Enter balance: ");
        // Assuming balance is an integer, you might want to add validation
        customer.setBalance(0);

        System.out.println("Added customer: ");
        System.out.println(customer.toString());

        // Use the CustomerFacade to persist the customer
        customerFacade.create(customer);

        System.out.println("Customer created successfully!");
    }
    public void printCustomers() {
    List<Customer> customers = customerFacade.findAll();
    System.out.println("------- Customers List --------");
    for (int i = 0; i < customers.size(); i++) {
        System.out.printf("%d.%-10s %-10s %-10s, Phone: %-8s, Balance: %-4s%n \n",
                i + 1,
                customers.get(i).getId(),
                customers.get(i).getFname(),
                customers.get(i).getLname(),
                customers.get(i).getPhone(),
                customers.get(i).getBalance());
    }
    }
    public void changeBalance() {
    List<Customer> customers = customerFacade.findAll();
    
    System.out.println("------- Customers List --------");
    
    for (int i = 0; i < customers.size(); i++) {
        System.out.printf("%d. %s %s%n",
                i + 1,
                customers.get(i).getFname(),
                customers.get(i).getLname()
        );
    }
    
    System.out.println("Select the number of the customer (1-" + customers.size() + "):");
    int selectedCustomer = scanner.nextInt();
    scanner.nextLine();
    
    if (selectedCustomer <= customers.size() && selectedCustomer > 0) {
        System.out.println("Insert amount you wish to add:");
        int amountToAdd = scanner.nextInt();
        scanner.nextLine();
        
        customers.get(selectedCustomer - 1).setBalance(customers.get(selectedCustomer - 1).getBalance() + amountToAdd);
        System.out.println("Balance updated successfully!");
        System.out.println("New Balance of " + customers.get(selectedCustomer - 1).getFname() + " is " + customers.get(selectedCustomer - 1).getBalance());
        
        // Update the database with the new balance
        customerFacade.edit(customers.get(selectedCustomer - 1));
        
    } else {
        System.out.println("Customer not found in the list.");
    }
}

    public void editCustomer() {
    List<Customer> customers = customerFacade.findAll();
    
    System.out.println("------- Customers List --------");
    
    for (int i = 0; i < customers.size(); i++) {
        System.out.printf("%d. %s %s%n",
                i + 1,
                customers.get(i).getFname(),
                customers.get(i).getLname()
        );
    }
    
    System.out.println("Select the number of the customer (1-" + customers.size() + "):");
    int selectedCustomer = scanner.nextInt();
    scanner.nextLine();
    
    if (selectedCustomer <= customers.size() && selectedCustomer > 0) {
        
        boolean repeat = true;
        
        do {
            System.out.println("Exit program press: 0");
            System.out.println("Change FirstName press: 1");
            System.out.println("Change LastName press: 2");
            System.out.println("Change Phone press: 3");
            
            int task = scanner.nextInt();
            scanner.nextLine();
            
            switch (task) {
                case 0:
                    repeat = false;
                    break;
                case 1:
                    System.out.println("Enter new First Name:");
                    customers.get(selectedCustomer - 1).setFname(scanner.nextLine());
                    break;
                case 2:
                    System.out.println("Enter new Last Name:");
                    customers.get(selectedCustomer - 1).setLname(scanner.nextLine());
                    break;
                case 3:
                    System.out.println("Enter new Phone:");
                    customers.get(selectedCustomer - 1).setPhone(scanner.nextLine());
                    break;
                default:
                    System.out.println("Select a number from the list!");
            }
        } while (repeat);

        // Update the database with the changes
        customerFacade.edit(customers.get(selectedCustomer - 1));

    } else {
        System.out.println("Customer not found in the list.");
    }
}
    public List<Customer> customers(){
        return customerFacade.findAll();
    }
    
    public Customer findById(int id){
        return customerFacade.find((long)id);
    }
    

}
