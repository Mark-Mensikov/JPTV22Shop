/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managers;
import entity.Sale;
import java.util.Scanner;
import facades.SaleFacade;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
/**
 *
 * @author pupil
 */
public class SaleManager {
    private Scanner  scanner;
    private final SaleFacade saleFacade;
    
    
    public SaleManager(Scanner scanner){
        this.scanner = scanner;
        this.saleFacade = new SaleFacade();
    }
    
    //---------------Create Sale------------------
    public Sale addSale() {
        Sale sale = new Sale();

        System.out.println("Enter sale details:");

        
        System.out.print("Name: ");
        String name = scanner.nextLine();
        sale.setName(name);

        
        Date dateStart = enterDate("Enter start date (yyyy-MM-dd): ");
        sale.setDateStart(dateStart);

        
        Date dateEnd = enterDate("Enter end date (yyyy-MM-dd): ");
        sale.setDateEnd(dateEnd);
        saleFacade.create(sale);
        
        return sale;
    }

    private Date enterDate(String prompt) {
        Date date = null;
        boolean validInput = false;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        while (!validInput) {
            try {
                System.out.print(prompt);
                String input = scanner.nextLine();
                date = dateFormat.parse(input);
                validInput = true;
            } catch (ParseException e) {
                System.out.println("Invalid date format. Please use yyyy-MM-dd.");
            }
        }

        return date;
    }
    
    
    //-------------------Timer----------------------------
    
    public void timerUntilSaleStart(){
        List<Sale> sales = saleFacade.findAll();

    // Print available sales
    System.out.println("Available Sales:");
    for (int i = 0; i < sales.size(); i++) {
        System.out.println((i + 1) + ". " + sales.get(i).getName());
    }

    // Select a sale
    System.out.print("Select Sale: ");
    int saleNumber = scanner.nextInt();
    scanner.nextLine();

    if (saleNumber <= 0 || saleNumber > sales.size()) {
        System.out.println("Invalid sale selection.");
        return;
    }

    Sale selectedSale = sales.get(saleNumber - 1);
    Date currentDate = new Date();

    
    if (currentDate.after(selectedSale.getDateStart()) && currentDate.before(selectedSale.getDateEnd())) {
        
        long timeUntilEnd = selectedSale.getDateEnd().getTime() - currentDate.getTime();
        long seconds = timeUntilEnd / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;
        long days = hours / 24;

        
        System.out.println("Time until sale ends:");
        System.out.println(days + " days, " + (hours % 24) + " hours, " + (minutes % 60) + " minutes, " + (seconds % 60) + " seconds.");
    } else {
        
        long timeUntilStart = selectedSale.getDateStart().getTime() - currentDate.getTime();
        long seconds = timeUntilStart / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;
        long days = hours / 24;

        
        System.out.println("Time until sale starts:");
        System.out.println(days + " days, " + (hours % 24) + " hours, " + (minutes % 60) + " minutes, " + (seconds % 60) + " seconds.");
    }
    }
    }
    
    

