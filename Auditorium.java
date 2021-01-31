/*
 * Student Name: Timothy Philip
 * NetID: tkp180001
 * CS2336.003 - Jason Smith
 * Date = 11/10/20
 */
package Project_4;

import static Project_4.Main.char2num;
import static Project_4.Main.num2char;
import java.io.*;
import java.util.ArrayList;
/**
 *
 * @author tphil
 * @param <G>
 */
public class Auditorium<G extends Comparable<G>> {
//--------------------------------PRIVATE-MEMBERS------------------------------------
    Node<G> first = new Node<>();//pointer to the first seat in the auditorium
    private int rows,cols;//private data about the size of the auditorium    
//----------------------------------CONSTUCTORS----------------------------------------
    //Default 
    public Auditorium(){first = null;}
    //Contructor that create the connected matrix
    public Auditorium(Node<G>[][] array){
        if(array[0][0] != null){
            rows = array.length;
            cols = array[0].length;
            first = array[0][0];//setting the first member to the initial node in the array
            //looping the link all the nodes together
            for(int i = 0; i < array.length; i++){
                for(int j = 0; j < array[0].length; j++ ){
                    //Pointing up if possible (after first row)
                    if(i > 0){//When not the first row points up
                        array[i][j].setUp(array[i-1][j]);
                    }else{array[i][j].setUp(null);}//points to null if not possible

                    //Pointing down if possible (before last row)
                    if(i < array.length - 1){//When not the last row point down
                        array[i][j].setDown(array[i+1][j]);
                    }else{array[i][j].setDown(null);}//points to null if not possible

                    //Pointing left is possible(after first column)
                    if(j > 0){//When not the first column point left
                        array[i][j].setLeft(array[i][j-1]);
                    }else{array[i][j].setLeft(null);}//points to null if not possible

                    //pointing right if possible (before last column)
                    if(j < array[0].length -1){//When not the last column 
                        array[i][j].setRight(array[i][j+1]);
                    }else{array[i][j].setRight(null);}//points to null if not possible
                }
            }
        }else{first = null;}
    }    
//---------------------------------ACCESSORS----------------------------------------
    public int getRows(){ //to get number of rows
        return rows;
    }
    public int getCols(){ //to get number of columns
        return cols;
    }
    public char getSeat(int keyrow,int keycol){//to get a certain seat in the auditorium
        Node<G> pointer = first;
        for(int i=1; i<keyrow;i++){
            pointer = pointer.getDown();//iterating downwards
        }for(int i=1; i < keycol; i++){
            pointer = pointer.getRight();//iterating rightwards
        }return ((Seat)pointer.getPayload()).getSeat();
    }
    //Accessor to display the auditorium opens seats
    public void display(){
        Node<G> hpointer, vpointer = first;//initializing vertical and horizontal pointers to iterate
        int rowNumber=0;
        System.out.println("  ABCDEFGHIJKLMNOPQRSTIVWXYZ".substring(0, cols+2));//displating the availiable seats

        while(vpointer != null){
            System.out.print((++rowNumber)+" ");
            hpointer = vpointer;
            while(hpointer != null){
                switch( ((Seat)hpointer.getPayload()).getTicket() ){
                    case '.':
                        System.out.print(".");
                        break;
                    default:
                        System.out.print("#");
                        break;
                }
                hpointer = hpointer.getRight();
            }
            System.out.println();
            vpointer = vpointer.getDown();
        }
    }
    //Acessor to display ticket types
    public void displayTickets(){
        Node<G> hpointer, vpointer = first;//initializing vertical and horizontal pointers to iterate
        
        int rowNumber=0;
        System.out.println("  ABCDEFGHIJKLMNOPQRSTIVWXYZ".substring(0, cols+2));
        
        while(vpointer != null){
            System.out.print((++rowNumber)+" "); //prints row number before every row
            hpointer = vpointer;
            while(hpointer != null){
                switch( ((Seat)hpointer.getPayload()).getTicket() ){
                    case 'A':
                        System.out.print("A");
                        break;
                    case 'C':
                        System.out.print("C");
                        break;
                    case 'S':
                        System.out.print("S");
                        break;
                    default:
                        System.out.print(".");
                        break;
                }
                hpointer = hpointer.getRight();
            }
            System.out.println();
            vpointer = vpointer.getDown();
        }
    }
    //Accessor to write the auditorium to output file
    public void writeFile(File OutputFile)throws IOException{
        Node<G> hpointer, vpointer;//initializing vertical and horizontal pointers to iterate
        vpointer = first; //setting main iterator to head pointer
        
        //writing content of auditorium into a file
        try (FileWriter Output = new FileWriter(OutputFile)){//using try to check if the file exists
            while(vpointer != null){
                hpointer = vpointer; //iterating downward
                while(hpointer != null){
                    switch(((Seat)hpointer.getPayload()).getTicket()){//using the switch to populate correct character into file array
                        case 'A':
                            Output.write("A");
                            break;
                        case 'C':
                            Output.write("C");
                            break;
                        case 'S':
                            Output.write("S");
                            break;
                        default:
                            Output.write(".");
                            break;
                    }hpointer = hpointer.getRight();//iterating rightward
                }vpointer = vpointer.getDown();//iterating downward
                Output.write("\n");
            } 
        }
    }
    //Acessor to print info back to the user
    public void report(){
        int adult = 0,child = 0,senior = 0;//Initialising variable to Zero
        Node<G> vpointer = first,hpointer;//pointers to iterate through the auditorium
//Going through the auditorium to count each type of ticket
        while(vpointer != null){
                hpointer = vpointer; //iterating downward
                while(hpointer != null){
                    switch( ((Seat)hpointer.getPayload()).getTicket() ){// using this to switch between counters for the whole audtiorium
                    case 'A':
                        adult++;
                        break;
                    case 'C':
                        child++;
                        break;
                    case 'S':
                        senior++;
                        break;
                    default:
                        break;
                    }
                    hpointer = hpointer.getRight();
                }
                vpointer = vpointer.getDown();//iterating rightward
            } 
//Displaying auditorium info to the console
        System.out.println("\nTotal Seats:\t" + (rows*cols));
        System.out.println("Total Tickets:\t" + (adult+child+senior));
        System.out.println("Adult Tickets:\t" + adult);
        System.out.println("Child Tickets:\t" + child);
        System.out.println("Senior Tickets:\t" + senior);
        System.out.printf("Total Sales:\t" + "$%.2f\n",(adult*10.0 + child*5.0 + senior*7.5));// to output in decimal formant 0.00
    }
    //Accessor to retrieve data to the main program
    public int[] lineReport(){
        int data[] =  new int[5];//0=OPEN,1=RSVD,2=ADULT,3=CHILD,4=SENIOR
        Node<G> vpointer=first,hpointer;
//looping through the entire auditorium to collect data
        while(vpointer != null){
            hpointer = vpointer;
            while(hpointer != null){
                switch(((Seat)hpointer.getPayload()).getTicket()){//using switch to count the seats
                    case 'A':
                        data[1]++;//incrementing RSVD count
                        data[2]++;//incrementing ADULT count
                        break;
                    case 'C':
                        data[1]++;//incrementing RSVD count
                        data[3]++;//incrementing CHILD count
                        break;
                    case 'S':
                        data[1]++;//incrementing RSVD count
                        data[4]++;//incrementing SENIOR count
                        break;
                    default:
                        data[0]++;//incrementing OPEN count
                        break;
                }
                hpointer = hpointer.getRight();//iterating rightwards
            }
            vpointer = vpointer.getDown();//iterating leftwards
        }
        return data;
    }
    //Acessor to check if the range of seats are open
    public boolean areSeatsAvailable(int keyRow, int keyCol, int total){
        Node<G> start = first;

    //Finding the available seats of the row
        int available = 0;
        for(int i=1; i < keyRow;i++){
            start = start.getDown(); //going to the specified row
        }while(start != null){
            if(((Seat)start.getPayload()).getTicket() == '.')
                available++;//couting all the available seats in the row
            start = start.getRight();
        }
        
        //Sets the starting seat to chosen location
        start = first; //reset pointer
        for(int i=1; i < keyRow;i++){
            start = start.getDown();//iterating downards to desired row
        }for(int i=1; i < keyCol;i++){
            start = start.getRight();//iterarting leftwards to desired columns
        }
       
        //Checks location is available
        if(total < available){ //error bound checking for seats
            for(int i = 0; i < total; i++){
                if(start != null ){
                    if(((Seat)start.getPayload()).getTicket()!= '.'){
                        return false;//one of the seats are not open
                    }
                    start = start.getRight();
                }else{return false;}//starting seat could not be found
            }return true;//seats are all open
        }else{return false;}//not enough open seats in the row
    }
//Accessor to find the best seats in the entire auditorium
 public int[] bestAvailable(int totalSeats){
        //Finding the size of the auditorium
        Node<G> counter = first,hcounter,rchecker;
        
        //Finding the best seat in the row
        double  smallestDistance = cols,smalledRowDistance = rows;
        char seatStart = 'A', seatEnd = 'Z';
        int bestRow =0,bestSeat =0;
        boolean allSeatsAvailable = true;
        double midCol = (cols+1)/2.0,midRow = (rows+1)/2.0,rowDistance,colDistance,Distance;//target in
        //Loooping through the entire auditoriium to find the best seats
        for(int i = 1; i <= rows; i++){
            hcounter = counter;
            for(int j = 1; j <= (cols - totalSeats + 1); j++){
                rchecker = hcounter;
                for(int k = j; k < j+totalSeats; k++){
                    if(((Seat)rchecker.getPayload()).getTicket() != '.'){
                        allSeatsAvailable = false;
                        break; 
                    }
                    rchecker = rchecker.getRight();
                }
                if(allSeatsAvailable){
                    //j=starting seat; j+totalSeats-1= ending seat; mid= middle of entire row
                    colDistance =  Math.abs(((j+j+totalSeats-1)/2.0) - midCol); //finding the distance between the middle of row and the middle of selection
                    rowDistance = Math.abs(i-midRow);//finding distance between target row and current row 
                    Distance = Math.sqrt(Math.pow(colDistance, 2.0) + Math.pow(rowDistance, 2.0));
                    if(Distance < smallestDistance){ //searching for smallest distance
                        smallestDistance = Distance;
                        smalledRowDistance = rowDistance;
                        bestSeat = j;
                        bestRow = i;
                        seatStart = num2char(j);
                        seatEnd = num2char(j+totalSeats-1);
                    }else if(Distance == smallestDistance){
                        if(rowDistance < smalledRowDistance){
                            smallestDistance = Distance;
                            smalledRowDistance = rowDistance;
                            bestSeat = j;
                            bestRow = i;
                            seatStart = num2char(j);
                            seatEnd = num2char(j+totalSeats-1);
                        }
                    }
                }
                allSeatsAvailable = true;
                hcounter = hcounter.getRight();//iterating rightward
            }
            counter = counter.getDown();//iterating downward
        }
        //Formatted ouput for the user
        StringBuilder str = new StringBuilder(); //Best way to concatenate string in Java
        System.out.println(str.append(bestRow).append(seatStart).append(" - ").append(bestRow).append(seatEnd));
        //Error checking if best seat was found
        int[] best = {bestRow,bestSeat};
        if(totalSeats > cols || bestRow == 0)
            best[1] = -1;
        
        return best;
    }
//-----------------------------------------MUTATORS---------------------------------------
    //Mutator to change seats the approprieate reserved type
    public void reserveSeats(int keyRow,int keyCol, int adult, int child, int senior){
        Node<G> start = first;
    
        //Counting the available seats in the desired row
        int availableSeats =0;
        for(int i=1; i < keyRow;i++){
           start = start.getDown();//going downwards toward the row
        }
        while(start != null){
            if(((Seat)start.getPayload()).getTicket() == '.'){
                availableSeats++;//counting the open seats in the row
            }
            start = start.getRight();
        }
        
        //Sets the starting seat to chosen location
        start = first; //reset pointer
        for(int i=1; i < keyRow;i++){
            start = start.getDown();//going to the chosen row
        }for(int i=1; i < keyCol;i++){
            start = start.getRight();//going to the chosen seat
        }
        
        //Changing the seats to reserved within the row
        if(availableSeats >= (adult + child + senior)){ //checking if row has space for seats
            if(start != null){
                for(int i = 0; i < adult; i++){
                    if(keyCol+i-1 < cols){//chekcing if goes out of bounds
                        ((Seat)start.getPayload()).setTicket('A') ;
                        start = start.getRight();
                    }
                }
                for(int i = 0; i < child; i++){
                    if(keyCol+adult+i-1 < cols ){//bound checking
                        ((Seat)start.getPayload()).setTicket('C');//reserve seats after the adult seat
                        start = start.getRight();
                    }
                }
                for(int i = 0; i < senior; i++){
                    if(keyCol+adult+child+i-1 < cols){//boud checking
                        ((Seat)start.getPayload()).setTicket('S');//reserve seats after the child seats
                        start = start.getRight();
                    }
                }
            }else{System.out.println("Seat was not found");}
        }else{
            System.out.println("The row doesn't have enough seats");
        }
    }
    //Mutator to change seats back to open
    public void unreserveSeats(ArrayList<Seat> seats){
        Node<G> seat_pointer;
        for(int i=0; i < seats.size(); i++){
            seat_pointer = first; //reseting the pointer for every seat to be deleted
            for(int j=1; j < seats.get(i).getRow();j++){
                seat_pointer = seat_pointer.getDown();//pointing downwards
            }for(int j=1; j < char2num(seats.get(i).getSeat());j++){
                seat_pointer = seat_pointer.getRight();//pointing rightwards
            }
            ((Seat)seat_pointer.getPayload()).setTicket('.');//setting the seat to open
        }
    }
    //Mutator to changed a single seat in the auditorium to whatever status
    public void setSeat(int keyrow,int keycol, char type){
        Node<G> pointer = first;
        for(int i=1; i<keyrow;i++){
            pointer = pointer.getDown();//iteratinf downwards
        }for(int i=1; i < keycol; i++){
            pointer = pointer.getRight();//iteratinf rightwards
        } ((Seat)pointer.getPayload()).setTicket(type);//Setting the seat to the specified type
    }
}

