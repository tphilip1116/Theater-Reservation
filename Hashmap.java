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
import java.util.HashMap;
import java.util.Iterator;

/**
 *
 * @author tphil
 * @param <String>
 * @param <Customer>
 */
public class Hashmap<String,Customer> extends HashMap<String,Customer>{
    
    public static int[] put(int[] hash,int data){
        if(data != 0){ //only hashes when data is not default
            int key = data % hash.length;//mod to find initial key
            int i =0;
            while( hash[(key+i*i)%hash.length] != 0){
                i++;//quadratic probing to find open spot
            }
            hash[(key+i*i)%hash.length] = data;//entering data into hashmap
        }
        return hash;
    }
    
    public static int[] rehash(int[] hash){
       int[] old = hash;
       hash = new int[nextPrime(hash.length*2)]; //setting new size to the next prime after double the length
       for(int i = 0; i < old.length;i++){
           put(hash,old[i]);//hashing the old elements into the new hashmap
        }
       return hash;
    }
    
    private static int nextPrime(int num) {
      num++; //starts wiht the next number
      for (int i = 2; i*i <= num; i++) {
         if(num%i == 0){
            num++;
            i=2;
         }
      }
      return num;
    }
    
    public static boolean checkLoad(int[] hash, double factor){
        //Using load factor and total bucket to find load capacity
        int capacity = (int)(factor * hash.length),filled=0;
        for(int i = 0; i < hash.length; i++){
            if(hash[i] != 0)
               filled++; 
        }
        return filled < capacity;
    }
    
    public static void printHash(int[] hash){
        System.out.println();
        for(int i = 0; i < hash.length; i++){
            switch(hash[i]){
                case 0: System.out.print("_ "); break;
                default: System.out.print(hash[i]+" "); break;
            }
        }
        System.out.println();
    }
    
    @Override
    public java.lang.String toString(){
        Iterator<Entry<String,Customer>> i = entrySet().iterator();
        if (! i.hasNext())
            return  "{empty}";

        StringBuilder sb = new StringBuilder();
        sb.append("{");
        for (;;) {
            Entry<String,Customer> e = i.next();
            String key = e.getKey();
            sb.append(key);
            if (! i.hasNext())
                return sb.append("}").toString();
            sb.append("; ");
        }
    }
    
}



