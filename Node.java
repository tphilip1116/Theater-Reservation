/*
 * Student Name: Timothy Philip
 * NetID: tkp180001
 * CS2336.003 - Jason Smith
 * Date = 11/10/20
 */
package Project_4;  

import java.util.Objects;

public class Node<G extends Comparable<G>> implements Comparable<Node<G>>{
    private G payload;
    private Node<G> up,down,left,right; //pointers for surrounding nodes
    
//Constructors
    public Node(){
        //default constructors set pointers to null
        up = null;
        down = null;
        left = null;
        right = null;
    }
    public Node(G obj){
        //constructor that takes in the payload object
        payload  = obj;
        up = null;
        down = null;
        left = null;
        right = null;
    }
    public Node(G obj,int row,char seat, char ticket){
        //Constructor that takes in the object members
        if(obj instanceof Seat seat1){
            seat1.setRow(row);
            seat1.setSeat(seat);
            seat1.setTicket(ticket);
        }
        payload = obj;
    }
//Mutators
    public void setUp(Node<G> up){
        this.up = up;
    }
    public void setDown(Node<G> down){
        this.down = down;
    }
    public void setLeft(Node<G> left){
        this.left = left;
    }
    public void setRight(Node<G> right){
        this.right = right;
    }
//Acessors
    public Node<G> getUp(){
        return up;
    }
    public Node<G> getDown(){
        return down;
    }
    public Node<G> getLeft(){
        return left;
    }
    public Node<G> getRight(){
        return right;
    }
    public G getPayload(){
        return payload;
    }
    
    @Override
    public int compareTo(Node<G> o){
        return o.getPayload().compareTo(payload);
    }
    @Override
    public boolean equals(Object o){
        if(o instanceof Node node)
            return node.getPayload().equals(payload);
        return false;
    }
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.payload);
        return hash;
    }
    @Override
    public String toString(){
        return payload.toString();
    }
    
}

