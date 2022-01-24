/*
    Name: Chapter 5 Question 3
    Description:Write a program to compute the number of collisions required in a long random
    sequence of insertions using linear probing, quadratic probing, and double hashing.
    Made by: Kevin Boudreaux
    Made on: 10/22/2021
    ID: KCB180002
 */
import java.util.Random;
import java.util.Scanner;
public class Main {

    public static void main(String[] args) 
    {
        hashMap ChainingMap = new hashMap();
        hashMap LinearMap = new hashMap();
        hashMap QuadraticMap = new hashMap();
        hashMap DoubleMap = new hashMap();
        
        Random rand = new Random();
        for(int i=0; i < ChainingMap.getSize(); i++)
        {
            int temp = Math.abs(rand.nextInt());
            ChainingMap.insertChaining(temp);
            LinearMap.insertLinearProbing(temp);
            QuadraticMap.insertQuadraticProbing(temp);
            DoubleMap.insertDoubleHash(temp);
        }
        
        System.out.println("Number of Collisions for Chaining, with a size of " +ChainingMap.getSize() + ", is: " + ChainingMap.getCollisionCount());
        System.out.println("Number of Collisions for Linear Probing, with a size of " +LinearMap.getSize() + ", is: " + LinearMap.getCollisionCount());
        System.out.println("Number of Collisions for Quadratic Probing, with a size of " +QuadraticMap.getSize() + ", is: " + QuadraticMap.getCollisionCount());
        System.out.println("Number of Collisions for Double Hashing with, a size of " +DoubleMap.getSize() + ", is: " + DoubleMap.getCollisionCount());
        System.out.println("Do you want to see the arrays? Y/N");
        Scanner input = new Scanner(System.in);
        String PrintArrays = input.nextLine();
        if(PrintArrays.equals("Y") || PrintArrays.equals("y"))
        {
            System.out.println("HashMap for Chaining:");
            for(int i = 0; i< ChainingMap.getSize(); i++)
            {
                System.out.print("[" + i + "] = ");
                Entry temp = ChainingMap.getTable()[i];
                if(temp == null)
                    System.out.print(0);
                while(temp != null)
                {
                    System.out.print(temp.getStorage() +" ");
                    temp = temp.getNext();
                }
                System.out.println();
            }
            System.out.println();


            System.out.println("HashMap for Linear Probing:");
            for(int i = 0; i< LinearMap.getSize(); i++)
            {
                System.out.print("[" + i + "] = ");
                if (LinearMap.getTable()[i] != null)
                    System.out.print(LinearMap.getTable()[i].getStorage() + " ");
                else
                    System.out.print(0);
                System.out.println();
            }
            System.out.println();


            System.out.println("HashMap for Quadratic Probing:");
            for(int i = 0; i< QuadraticMap.getSize(); i++)
            {
                System.out.print("[" + i + "] = ");
                if (QuadraticMap.getTable()[i] != null)
                    System.out.print(QuadraticMap.getTable()[i].getStorage() + " ");
                else
                    System.out.print(0);
                System.out.println();
            }
            System.out.println();


            System.out.println("HashMap for Double Hash Probing:");
            for(int i = 0; i< DoubleMap.getSize(); i++)
            {
                System.out.print("[" + i + "] = ");
                if (DoubleMap.getTable()[i] != null)
                    System.out.print(DoubleMap.getTable()[i].getStorage() + " ");
                else
                    System.out.print(0);
                System.out.println();
            }
        }
    }
}
class hashMap
{
    private int size = 7919;  //7919 is a prime number
    private int collisionCount = 0;
    //getters and setters for size and collisionCount
    public void setSize(int size) {this.size = size;}
    public int getSize() { return size; }
    public void setCollisionCount(int collisionCount) { this.collisionCount = collisionCount; }
    public int getCollisionCount() { return collisionCount; }

    //actual table for keys
    private final Entry[] table;

    //getter for table (would not normally use but for this demonstration used to show table
    public Entry[] getTable() { return table; }

    //default constructor
    public hashMap(){table = new Entry[size];}

    public void insertChaining(int input)
    {
        int hashNum = input % size;

        Entry tempEntry = table[hashNum];
        if(tempEntry == null)
            table[hashNum] = new Entry(input);
        else
        {
            collisionCount++;
            while(tempEntry.getNext() != null)
            {
                tempEntry = tempEntry.getNext();
                collisionCount++;
            }
            tempEntry.setNext(new Entry(input));
        }
    }
    public void insertLinearProbing(int input)
    {
        for(int i=0; i<size; i++)
        {
            Entry tempEntry = table[(input % size+ i) % size]; //need to resize 
            if(tempEntry == null)
            {
                table[(input % size+i) % size] = new Entry(input);
                break;
            }
            collisionCount++;
        }
    }
    public void insertQuadraticProbing(int input)
    {
        for(int i=0; i<size; i++)
        {
            Entry tempEntry = table[(input % size +(int)(Math.pow(i,2)))% size];
            if(tempEntry == null)
            {
                table[(input % size +(int)(Math.pow(i,2))) % size] = new Entry(input);
                break;
            }
            collisionCount++;
        }
    }
    public void insertDoubleHash(int input)
    {
        int hashCode = input % size;
        int hashDouble = 7 - (input % 7);
        for(int i=0; i<size; i++)
        {
            int tempHash = ((hashCode + (i ^ hashDouble)) % size);
            Entry tempEntry = table[tempHash];

            if(tempEntry == null)
            {
                table[tempHash] = new Entry(input);
                break;
            }
            collisionCount++;
        }
    }
    public int getStorageChaining(int key)
    {
        int hashNum = key % size;
        Entry temp = table[hashNum];
        while(temp !=  null)
        {
            int tempKey = temp.getKey();
            if(tempKey == key)
                return temp.getStorage();
            temp = temp.getNext();
        }
        //-1 is used as an error code if key could not be found
        return -1;
    }
    public int getStorageLinear(int key)
    {
        int hashNum = key % size;
        for(int i=0; i<size; i++)
        {
            Entry temp = table[hashNum+i];
            int tempKey = temp.getKey();
            if(tempKey == key)
                return temp.getStorage();
        }
        //-1 is used as an error code if key could not be found
        return -1;
    }
    public int getStorageQuadratic(int key)
    {
        int hashNum = key % size;
        for(int i=0; i<size; i++)
        {
            Entry temp = table[hashNum+(int)(Math.pow(i,2))];
            int tempKey = temp.getKey();
            if(tempKey == key)
                return temp.getStorage();
        }
        //-1 is used as an error code if key could not be found
        return -1;
    }
    public int getStorageDoubleHash(int key)
    {
        int hashNum = key % size;
        int hashDouble = 601-(key % 601); //601 is a prime number
        for(int i=0; i<size; i++)
        {
            Entry temp = table[hashNum + (i * hashDouble)];
            int tempKey = temp.getKey();
            if(tempKey == key)
                return temp.getStorage();
        }
        //-1 is used as an error code if key could not be found
        return -1;
    }


}
class Entry
{
    private int key;
    private int storage;
    private Entry next;

    //setters and getters for private values
    public Entry getNext() { return next; }
    public int getKey() { return key; }
    public int getStorage() { return storage; }
    public void setStorage(int storage) { this.storage = storage; }
    public void setKey(int key) { this.key = key; }
    public void setNext(Entry next) { this.next = next; }

    //use for insert function
    public Entry(int x) { key = storage = x; }
}