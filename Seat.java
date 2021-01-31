/*
 * Student Name: Timothy Philip
 * NetID: tkp180001
 * CS2336.003 - Jason Smith
 * Date = 11/10/20
 */
package Project_4;


/**
 *
 * @author tphil
 */
public class Seat implements Comparable<Seat>{
    private int row;
    private char seat,ticket;
    
    //Constructors
    public Seat(){
        row = 0;
        seat = ' ';
        ticket = ' ';
    }
    public Seat(int r, char s){
        row = r;
        seat = s;
        ticket = ' ';
    }
    public Seat(int r,char s,char t){
        row = r;
        seat = s;
        ticket = t;
    }
    
    //Accessors
    public int getRow(){
        return row;
    }
    public char getSeat(){
        return seat;
    }
    public char getTicket(){
        return ticket;
    }
    
    //Mutators
    public void setRow(int row){
        this.row = row;
    }
    public void setSeat(char seat){
        this.seat = seat;
    }
    public void setTicket(char ticket){
        this.ticket = ticket;
    }
    
    //Using the compareTo function so a list can be sorted properly
    @Override
    public int compareTo(Seat o){
        if(o.getRow()>row){
            return -1;
        }else if(row == o.getRow() ){
            if(o.getSeat()>seat){
                return -1;
            }else if(seat == o.getSeat() ){
                return 0;
            }else{
                return 1;
            }
        }else{
            return 1;
        }
    }
    //using equals so a list can cheack if it contains a seat correctly
    @Override
    public boolean equals(Object o){
        if(o instanceof Seat seat1)
            return seat1.getRow() == row && seat1.getSeat() == seat;
        return false;
    }
    //Generating the the missing hashcode for the object
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + this.row;
        hash = 47 * hash + this.seat;
        return hash;
    }
    //Helpful when printing the seats in a order
    @Override
    public String toString(){
        StringBuilder str = new StringBuilder();
        return str.append(row).append(seat).toString();
    }
    
}


