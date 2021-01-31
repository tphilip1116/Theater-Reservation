/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Project_4;

import static Project_4.Main.num2char;
import java.util.ArrayList;

/**
 *
 * @author tphil
 */
public class Customer {
     private final String password; //used to scure customer data
        private ArrayList<Order> tickets = new ArrayList<>(); //where order details are stored
        
        //default constructor
        public Customer(){
            password = "";
        }
        //Overloaded constructors
        public Customer(String password){
            this.password = password;
        }
        
        //Accessor to check for correct password
        public boolean isPassword(String s){
            return s.compareTo(password) == 0;
        }
        //Acessor for the tickets list
        public ArrayList<Order> getTickets(){
            return tickets;
        }
        //Accessor to print all the orders
        public void printOrders(){
            if(tickets.size() > 0){//checks if there orders to print
                for(int i = 0; i < tickets.size(); i++){
                    System.out.println("Order: " + (i + 1) + "\n"+tickets.get(i) + "\n");
                }
            }else{System.out.println("\nNo orders\n");}
        }
        //Accessors to print all the receipts
        public void printPrices(){
            double totalPrice = 0.0;
            if(tickets.size() > 0){ //checks 
                for(int i =0; i < tickets.size(); i++){
                    System.out.println("Order: " + (i+1) + "\n" + tickets.get(i));
                    System.out.printf("Order Total: $%.2f\n" + "\n",tickets.get(i).getPrice());
                    totalPrice += tickets.get(i).getPrice();
                }
            }
            System.out.printf("Customer Total: $%.2f\n" + "\n",totalPrice);
        }
        
        //Mutator to add a single order
        public void addOrder(int aud, int row,int sea,int adu,int chi,int sen){
            tickets.add(new Order(aud,row,num2char(sea),adu,chi,sen));//adding the object to the list
        }
        //Mutator method to remove single order
        public ArrayList<Seat> removeOrder(int x){
            ArrayList<Seat> deletedSeats = tickets.get(x-1).getSeats();
            tickets.remove(x - 1);
            return deletedSeats; //returning the seat to be unreserved in the auditorium
        }
        //Mutator to deleta a seat from the order
        public void deleteSeat(int index, int r,char sea,char type){
            tickets.get(index).deleteSeats(r, sea, type); //deletes the seat from chosen orders
            if(tickets.get(index).getSeats().isEmpty()){ //checks if theres any seats left
                tickets.remove(index);//delete the order if there no seats left
            }
        }
}
