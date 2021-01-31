/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Project_4;

import static Project_4.Main.char2num;
import static Project_4.Main.num2char;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author tphil
 */
public class Order {
    final private int auditorium;//the auditorium of the number
        private int adults,children,seniors;//the count of each type of seat
        private double price; //calculated prices for each order
        private final ArrayList<Seat> seats = new ArrayList<>();//the list of seats in the orders
        //defalut constructor
        public Order(){
            auditorium=0;
            adults = 0;
            children = 0;
            seniors = 0;
            price = 0.0;
        }
        //constructor with creation of order with initial seats
        public Order(int aud, int r,char sea,int adu,int chi,int sen){
            auditorium = aud;
            adults = adu;
            children = chi;
            seniors = sen;//setting the new data to its memebers
            price = Math.round((adu *10.0 + chi * 5.0 + sen * 7.5) * 100) / 100.0;
            //Adding initial seats to the seat list
            int col = char2num(sea);
            for(int i = 0; i < (adu + chi + sen); i++){
                seats.add(new Seat(r,num2char(col + i)));
            }
        }
        //Accessor for price data
        public double getPrice(){
            return price;
        }
        //Accessor for auditorium data
        public int getAuditorium(){
            return auditorium;
        }
        //Accessor for the list of seats
        public ArrayList<Seat> getSeats(){
            return seats;
        }
        //Accessor to check is given seat is in the list
        public boolean isSeat(int r,char sea){
            return seats.contains(new Seat(r,sea)); 
        }
        //mutator to add seats to the order
        public void addSeats(int r, char sea,int adu,int chi, int sen){
            adults += adu;
            children += chi;
            seniors += sen;//updating the count of each type
            price += Math.round((adu*10.0 + chi*5.0 + sen*7.5)*100)/100.0;//updating the price of the order
            //adding all possible seat into the list
            for(int i=0; i < (adu + chi + sen); i++){
                seats.add(new Seat(r,num2char(char2num(sea)+i))); 
            }
            Collections.sort(seats); //sorts all the seats in alphabetical order after adding seats
        }
        //mutator to decrease counts and removes seat from list
        public void deleteSeats(int r,char sea, char type){
            switch(type){ 
                case 'A' -> {
                    adults--;price -= 10.0;
                }
                case 'C' -> {
                    children--;price -= 5.0;
                }
                case 'S' -> {
                    seniors--;  price -= 7.5;
                }
            }
            seats.remove(new Seat(r,sea));//remove given seat from list
        }
       
        @Override
        public String toString(){
            StringBuilder str = new StringBuilder();//best wasy to concatenate a string
            str.append("Auditorium ").append(auditorium).append(", ");//printig the auditorium number
            for(int i = 0; i<seats.size();i++){
                if(i < seats.size()-1) //printing all the seats in the order
                    str.append(seats.get(i)).append(",");
                else
                    str.append(seats.get(i)).append("\n");
            }
            return str.append(adults).append(" adult, ").append(children).append(" child, ").append(seniors).append(" senior").toString();
        }
}
