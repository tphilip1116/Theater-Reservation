/*
 * Student Name: Timothy Philip
 * NetID: tkp180001
 * CS2336.003 - Jason Smith
 * Date = 11/10/20
 */
package Project_4;

import java.util.*;
import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException{
// Preliminary declarations of i/o 
        Scanner scnr = new Scanner(System.in); // scanner to read keyboard input
        File OutputFile = new File("A1Final.txt"),output2 = new File("A2Final.txt"),output3 = new File("A3Final.txt"); //creating files for the output
//Reading the input files and creating the Auditoriums
        Auditorium[] auditoriums = {readAuditoriumFile(1),readAuditoriumFile(2), readAuditoriumFile(3)};
//Gets user info from database
        Hashmap<String,Customer> users = database();
//Username & Password prompt
        boolean isInputValid,isUserAdmin = false,isLoggedIn=false;//conditions for error check
        Customer current;
        do{
            System.out.println(users.toString()); //diplays all possible users
            System.out.println("Username:");
            String username = scnr.next(),password;
            current = users.get(username);
            if(current == null){ //error check for a valid username
                System.out.println("invalid username");
                continue;
            }else{
                System.out.println(username+" is valid");
            }
            
            //Prompt the password up to 3 times
            for(int i = 0; i < 3;i++){
                System.out.println("Password:");
                password = scnr.next();
                isLoggedIn = current.isPassword(password);
                //When password is correct, check if user is admin and exit password prompt
                if(isLoggedIn){
                   System.out.println("Logging in");
                   isUserAdmin = username.compareTo("admin") == 0;
                   break;
                }else{
                    System.out.println("Invalid password");
                }
            }
            
            if(isLoggedIn){ //Once logged in display appropiate main menu
                int menuChoice;//number inputs,default values
                String input; //reading everything as string

                if(isUserAdmin){                    
//Admin main menu
                    do{
                        System.out.println("1. Print Report");
                        System.out.println("2. Logout");
                        System.out.println("3. Exit");
                        System.out.println("Please enter menu choice: ");
                        input = scnr.next();
                        isInputValid = isNumber(input);
                        if(isInputValid){
                            menuChoice = Integer.parseInt(input);
                            System.out.println("Menu Choice: "+ menuChoice);

                            //Using a switch case to interpret menu choice
                            switch(menuChoice){
                                case 1:
                                    //Printing the Ticket Report at the end of the program run
                                    reportTheater(auditoriums);
                                    isInputValid  = false;//repeats main menu prompt
                                    break;
                                case 2:
                                    System.out.println("Logging Out");
                                    isInputValid = true;//exits the menu prompt
                                    isLoggedIn = false;//back to username prompt
                                    break;
                                case 3:
                                    System.out.println("Exiting");
                                    System.exit(0);//terminating the program
                                    break;
                                default:
                                    isInputValid = false;//repeats main menu prompt
                                    System.out.println("No valid menu choice selected, try again");
                                    break;
                            }
                        }else{
                            System.out.println(input+ " is not a valid option");
                        }
                    }while(!isInputValid);
                }else{
//Normal User main menu
                    do{
                        System.out.println("1. Reserve seats");
                        System.out.println("2. View Orders");
                        System.out.println("3. Update Orders");
                        System.out.println("4. Display Receipt");
                        System.out.println("5. Log Out");
                        System.out.println("Please enter menu choice: ");
                        input = scnr.next();
                        //Validating input
                        isInputValid = isNumber(input);
                        if(isInputValid){
                            menuChoice = Integer.parseInt(input);
                            System.out.println("Menu Choice: " + menuChoice);

                            //Using a switch case to interpret menu choice
                            switch(menuChoice){
                                case 1:
                                    reservation(auditoriums, current, scnr,0); //sagin the the chanegd auditorium after a reservation
                                    auditoriums[0].writeFile(OutputFile);//uptdating the output files
                                    auditoriums[1].writeFile(output2);
                                    auditoriums[2].writeFile(output3);
                                    isInputValid = false; //to prompt main menu again 
                                    break;
                                case 2:
                                    current.printOrders();//Printing orders of the customer
                                    isInputValid  = false; //go back to main menu
                                    break;
                                case 3:
                                    if(current.getTickets().size() > 0){
                                        update(current, scnr, auditoriums);
                                    }else{
                                        System.out.println("\nNo orders\n");
                                    }
                                    auditoriums[0].writeFile(OutputFile);//uptdating the output files
                                    auditoriums[1].writeFile(output2);
                                    auditoriums[2].writeFile(output3);
                                    isInputValid = false;//back to menu prompt
                                    break;
                                case 4:
                                    current.printPrices();//Printing the orders with affiliated price
                                    isInputValid = false;
                                    break;
                                case 5:
                                    isInputValid  = true; //exits menu prompt
                                    isLoggedIn = false; //back to the username prompt
                                    System.out.println("Logging Out"); 
                                    break;
                                default:
                                    isInputValid = false;
                                    System.out.println("No valid menu choice selected, try again");
                                    break;
                            }
                        }else{
                            System.out.println(input+ " is not a valid option");
                        }
                    }while(!isInputValid);
                } 
            }
        }while(!isLoggedIn);
    }
    
    private static Auditorium<Seat> readAuditoriumFile(int inputFileChoice) throws IOException{
        //Choosing the correct file from the choice given
        String[] names = {"A1.txt","A2.txt","A3.txt"};//Array of file choices
        File f = new File(names[inputFileChoice-1]);//using the file chosen
        
        //Checking if the chosen file exists
        if(!f.exists()){
            System.out.println("Input File was not found");//telling the console the file error
            return null;
        }
        //Finding the size of the file or auditorium
        int rows = 0, cols;
        try (BufferedReader reader = new BufferedReader(new FileReader(f))) {//Using try to check if file exists
            while(reader.readLine() != null){rows++;}//Using buffered reader to count the rows in the file
        }try(Scanner x = new Scanner(f)){
            cols = x.nextLine().length();//Using scanner to find the length of the row for columns
        }
        //Filling the array with 
        Node[][] nodes = new Node[rows][cols];//creating the array with file size
        try(Scanner scnr = new Scanner(f)){
            String input = " ";
            //<editor-fold defaultstate="collapsed" desc="Loop through the file">
            while(scnr.hasNext()){
                for(int i = 0; i < rows; i++){ //looping through the lines of the file
                    if(scnr.hasNext()){ // only reading nextline if available
                        input = scnr.nextLine();// reads in line of string for every row
                    }for(int j = 0; j <  cols; j++){ //looping across the each line in the file
                        Seat index = new Seat(i + 1,num2char(j + 1),input.charAt(j));//creating payload w/ file character
                        Node<Seat> n = new Node<>(index);//loading payload into node
                        nodes[i][j] = n;//loading the node into the nodes array
                    }
                }
            }
            //</editor-fold>
        }
        Auditorium<Seat> theater = new Auditorium<>(nodes);//loading the nodes array into auditorium
        return theater;
    }
    
    public static void update(Customer cust,Scanner scnr,Auditorium[] theater) throws IOException{
        boolean isInputValid;
        String input;
        Auditorium chosen = new Auditorium();
        int choice = 1,orderAuditorium = 1;        
//Orders prompts
        cust.printOrders(); //displaying the orders once
        do{
            System.out.println("Which order would you like to edit: ");
            input = scnr.next();
            isInputValid  = isNumber(input);
            if(isInputValid){
                choice = Integer.parseInt(input);
                isInputValid = choice <= cust.getTickets().size();
                 if(isInputValid){
                    orderAuditorium = cust.getTickets().get(choice - 1).getAuditorium();
                    chosen = theater[orderAuditorium - 1];
                    System.out.println("Choice " + input + " is valid");
                }else{
                     System.out.println("Choice " + input + " is not valid");
                 }
            }else{
                System.out.println(input + " is not a number");
            }
        }while(!isInputValid);
//Update options prompt
        do{
            System.out.println("1. Add tickets to order");
            System.out.println("2. Delete tickets from order");
            System.out.println("3. Cancel Order");
            System.out.println("Please enter menu choice: ");
            input = scnr.next();
            
            isInputValid = isNumber(input);
            if(isInputValid){
                System.out.println("Menu choice " + input + " is valid");
                int menuChoice = Integer.parseInt(input);
                switch(menuChoice){
                    case 1:
                        Auditorium[] pack = {chosen}; //sending the pack to avoid auditorium prompt
                        reservation(pack,cust,scnr,choice); //reserving without best suggestion
                        theater[orderAuditorium-1] = pack[0];//saving changed auditorium into theater
                        break;
                    case 2:
                        System.out.println("Please enter row:");//prompting the row number
                        String row = scnr.next();
                        int rowNumber;
                        
                        //Error checking the row from the user
                        isInputValid = isNumber(row);
                        if(isInputValid){//validating the row number
                            rowNumber = Integer.parseInt(row);//only parses the string if its a number
                        }else{
                            System.out.println("Invalid row: " + row + " is not a number");
                            break;
                        }
                        
                        System.out.println("Please enter the seat");//prompting the seat letter
                        String seatInput = scnr.next();
                        char seat;
                        
                        //Validating the user input of seat 
                        isInputValid = seatInput.length() == 1;
                        if(isInputValid){
                            seat = seatInput.charAt(0);
                        }else{
                            System.out.println("Seat invalid");
                            break;
                        }
                        
                        //Checking if seat exists in the order
                        isInputValid = cust.getTickets().get(choice-1).isSeat(rowNumber, seat);
                        if(isInputValid){
                            System.out.println("The seat " + row + seat + " is valid");
                            cust.deleteSeat(choice-1, rowNumber, seat, chosen.getSeat(rowNumber, char2num(seat)));
                            chosen.setSeat(rowNumber, char2num(seat), '.');
                            System.out.println("Seat has been deleted from order");
                        }else{
                            System.out.println("The seat " + row + seat + " is not in the order");
                            isInputValid = false;
                        }
                        theater[orderAuditorium-1] = chosen;//saving changed auditorium into theater
                        break;
                    case 3:
                        chosen.unreserveSeats(cust.removeOrder(choice));
                        theater[orderAuditorium-1] = chosen;
                        break;
                    default:
                        System.out.println("Menu choice " + input + " is invalid");
                        isInputValid = false;
                        break;
                }
            }
        }while(!isInputValid);
    }
    
    public static void reservation(Auditorium[] theater,Customer cust,Scanner scnr,int orderNumber)throws IOException{
        boolean isInputValid,isOrderPossible = true; //boolean for recurring prompts
        Auditorium auditorium = new Auditorium(); //the auditorium to be reserved at
        String input; //the user input to be validated
        int choice = 0,rows = 0,cols = 0,userRow = 0,colInt = 0,adultTickets = 0,childTickets = 0,seniorTickets = 0;//high scope data
//AUDITORIUM prompt
        if(theater.length > 1){//check to see if used to edit orders don't prompt auditorium choice
            do{
                System.out.println("1. Auditorium 1");
                System.out.println("2. Auditorium 2");
                System.out.println("3. Auditorium 3");
                System.out.println("Please choose auditorium");
                input = scnr.next();
                isInputValid = isNumber(input);
                if(isInputValid){
                    choice = Integer.parseInt(input);
                    isInputValid = choice <= 3 && choice > 0;
                }
                if(isInputValid){
                    System.out.println("Auditorium " + input + " is valid"); 
                    auditorium = theater[choice-1];
                    rows = auditorium.getRows();
                    cols = auditorium.getCols();
                }else{
                    System.out.println("Auditorium " + input + " is invalid");
                }
            }while(!isInputValid); //exits when menu input is valid
        }else{
            auditorium = theater[0];
            rows = auditorium.getRows();
            cols = auditorium.getCols();
            choice = 1;
        }
        auditorium.display();//displaying the chosen auditorium
//ROW prompt
        do{
            System.out.println("Which row would you like to reserve at: ");
            input  = scnr.next();// try/catch to handle exceptions  
            isInputValid = isNumber(input);
            if(isInputValid){
                userRow = Integer.parseInt(input);
                isInputValid = userRow <= rows && userRow > 0;
                if(isInputValid){//Checking if user input is valid
                    System.out.println("Row " + input + " is valid");
                }else{
                    System.out.println("Row " + input + " is out of range");
                }
            }else{
                System.out.println(input + " is not a number");
            }
        }while(!isInputValid);
//SEAT prompt
        do{
            System.out.println("Which seat would you like to start at?: ");
            input = scnr.next();
            isInputValid = input.length() == 1 && Character.isLetter(input.charAt(0));
            if(isInputValid){
                colInt = char2num(input.charAt(0)); // converting string to int value using index
                isInputValid = colInt <= cols;
                if(isInputValid){
                    System.out.println("Seat " + input + ":(" + colInt +") is valid");
                }else{
                    System.out.println("Seat " + input + " is out of range");
                }
            }else{
                System.out.println(input+" is not a proper chracter");
            }
        }while(!isInputValid);    
//ADULT prompt
        do{
            System.out.println("Enter number of adult tickets: ");
            input = scnr.next();
            if(isNumber(input)){
                adultTickets = Integer.parseInt(input);
                isInputValid = adultTickets >= 0;
                if(isInputValid){
                    System.out.println("Adult Number " + input + " is valid");
                }else{
                    System.out.println("Adult Number " + input + " is invalid");
                }
            }else{
                System.out.println(input + " is not a number");
            }
            
        }while(!isInputValid);
//CHILD prompt
        do{
            System.out.println("Enter number of child tickets: ");
            input = scnr.next();
            isInputValid = isNumber(input);
            if(isInputValid){
                childTickets = Integer.parseInt(input);
                isInputValid = childTickets >= 0;
                if(isInputValid){
                    System.out.println("Child Number " + input + " is valid");
                }else{
                    System.out.println("Child Number " + input + " is valid");
                }
            }else{
                System.out.println(input + " is not a number");
            }
        }while(!isInputValid); 
//SENIOR prompt 
        do{
            System.out.println("Enter number of senior tickets: ");
            input = scnr.next();
            isInputValid = isNumber(input);//checking if input was a number
            if(isInputValid){
                seniorTickets = Integer.parseInt(input);
                isInputValid = seniorTickets >= 0;
                if(isInputValid){
                    System.out.println("Senior Number " + input + " is valid");
                }else{
                    System.out.println("Senior Number " + input + " is invalid");
                }
            }else{
                System.out.println(input + " is not a number");
            }
        }while(!isInputValid);
//Checking if seats are available
        if(auditorium.areSeatsAvailable(userRow,colInt,(adultTickets + childTickets + seniorTickets))){
            System.out.println("Seats are reserved");
            auditorium.reserveSeats(userRow,colInt,adultTickets,childTickets,seniorTickets); //
        }else{
            if(orderNumber == 0){
//Suggesting and reserving best seat available
                System.out.print("seats not available, best availble seats: ");
                int[] best = auditorium.bestAvailable((adultTickets+childTickets+seniorTickets));//prints suggests seat and send them
                userRow = best[0];colInt = best[1]; //updating info to best data
                if(best[0] == -1){ //error checking if best seats could be found
                    isOrderPossible = false;//order cant be possible if seats are not found
                    System.out.println("Best seats could not be found");
                }else{
                    System.out.println("Would you like to reserve?(Y/N)");
                    String reserveBest = scnr.next();
                    if(reserveBest.charAt(0) == 'Y' || reserveBest.charAt(0) == 'y'){
                        auditorium.reserveSeats(userRow,colInt,adultTickets,childTickets,seniorTickets);//reservinf the best seat
                        System.out.println("Best seats are reserved");
                    }else{
                        System.out.println("Best seats are not reserved");
                        isOrderPossible = false;
                    }//order isn't possible when not wanted
                }
            }else{System.out.print("seats not available");}
        }
        if(orderNumber == 0){
//Adding order to customer object
            if(isOrderPossible && (adultTickets+childTickets+seniorTickets) !=0){
                System.out.println("Adding order to account");
                cust.addOrder(choice, userRow, colInt, adultTickets, childTickets, seniorTickets);
            }
        }else{
            System.out.println("Adding seat to order");
            cust.getTickets().get(orderNumber - 1).addSeats(userRow, num2char(colInt), adultTickets, childTickets, seniorTickets);
        }
//Updating auditorium in the theater
        theater[choice-1] = auditorium;
    }
    
    public static void reportTheater(Auditorium[] theater){
        int[][] data = new int[3][5];
        System.out.println("\t\tOpen\tRsrvd\tAdult\tChild\tSenior\tAmount");//label coulumns to understand presented data
        for(int i = 0; i < 3; i++){//looping through the 3 auditoriums
            data[i]  = theater[i].lineReport();//finding the data for the particular auditorium
            System.out.printf("Auditorium " + (i + 1) + "\t" + 
                    data[i][0] + "\t" +//dipaying the current auditorium's open seats
                    data[i][1] + "\t" +//dipaying the current auditorium's reserved seats
                    data[i][2] + "\t" +//dipaying the current auditorium's adult seats
                    data[i][3] + "\t" +//dipaying the current auditorium's child seats
                    data[i][4] + "\t" + //dipaying the current auditorium's senior seats
                    "$%.2f\n",(data[i][2] * 10.0 +//dipaying the current auditorium's total price
                               data[i][3] * 5.0  +
                               data[i][4] * 7.5));
        }
        System.out.printf("Total\t" +
                        "\t" + (data[0][0] + data[1][0] + data[2][0]) +//dipaying the total open seats
                        "\t" + (data[0][1] + data[1][1] + data[2][1]) +//dipaying the total reserved seats
                        "\t" + (data[0][2] + data[1][2] + data[2][2]) +//dipaying the total adult seats
                        "\t" + (data[0][3] + data[1][3] + data[2][3]) +//dipaying the total child seats
                        "\t" + (data[0][4] + data[1][4] + data[2][4]) +//dipaying the total senior seats
                        "\t" + "$%.2f\n",((data[0][2] + data[1][2] + data[2][2]) * 10.0 + //dipaying the total price for all 3 auditoriums
                                         (data[0][3]  + data[1][3] + data[2][3]) * 5.0  +
                                         (data[0][4]  + data[1][4] + data[2][4]) * 7.5));
    }
    public static char num2char(int num){//BAD PRACTICE for using function on string literal
        return "ABCDEFGHIJKLMNOPQRSTUVWXYZ".charAt(num-1); //minus 1 to change the seat number to proper index
    }
    public static int char2num(char c){
        return ("ABCDEFGHIJKLMNOPQRSTUVWXYZ".indexOf(Character.toUpperCase(c)))+1;//adds 1 to change index to proper seat number
    }
    public static boolean isNumber(String s){
        boolean isNumber = false;
        for(int i = 0; i < s.length();i++){
            if(Character.isDigit(s.charAt(i))){
                isNumber = true;
            }else{
                isNumber = false;
                break;//if there is any non digit character 
            }
        }
        return isNumber;
    }
    
    public static Hashmap<String,Customer> database() throws FileNotFoundException{
        Hashmap<String,Customer> hash = new Hashmap<>();
        File f = new File("userdb.dat");
        try(Scanner scnr = new Scanner(f)){
            while(scnr.hasNext()){
                String line = scnr.nextLine();//store line of the file 
                if(line.compareTo("") != 0){
                    String[] spaces = line.split(" "); //splits the line by spaces
                    Customer curr = new Customer(spaces[1]);//Creating customer with the password
                    hash.put(spaces[0], curr); //putting the customer in the database by username
                }
            }
        }
        return hash;
    }
}
