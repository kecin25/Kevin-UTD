/*
    Name: Chapter 5 Question 3
    Description: Given input {4371, 1323, 6173, 4199, 4344, 9679, 1989} and a hash function
    h(x) = x mod 10, show the resulting:
    a. Separate chaining hash table.
    b. Hash table using linear probing.
    c. Hash table using quadratic probing.
    d. Hash table with second hash function h2(x) = 7 − (x mod 7)
    Made by: Kevin Boudreaux
    Made on: 11/22/2021
    ID: KCB180002
 */
public class Main {

    public static void main(String[] args) {
        //building the 4 hashmaps
        hashMap ChainingMap = new hashMap();
        hashMap LinearMap = new hashMap();
        hashMap QuadraticMap = new hashMap();
        hashMap DoubleMap = new hashMap();
        
        //inputting the first number into all four hashmaps
        ChainingMap.insertChaining(4371);
        LinearMap.insertLinearProbing(4371);
        QuadraticMap.insertQuadraticProbing(4371);
        DoubleMap.insertDoubleHash(4371);

        //inputting the second number into all four hashmaps
        ChainingMap.insertChaining(1323);
        LinearMap.insertLinearProbing(1323);
        QuadraticMap.insertQuadraticProbing(1323);
        DoubleMap.insertDoubleHash(1323);
       
        //inputting the third number into all four hashmaps
        ChainingMap.insertChaining(6173);
        LinearMap.insertLinearProbing(6173);
        QuadraticMap.insertQuadraticProbing(6173);
        DoubleMap.insertDoubleHash(6173);

        //inputting the fourth number into all four hashmaps
        ChainingMap.insertChaining(4199);
        LinearMap.insertLinearProbing(4199);
        QuadraticMap.insertQuadraticProbing(4199);
        DoubleMap.insertDoubleHash(4199);

        //inputting the fifth number into all four hashmaps
        ChainingMap.insertChaining(4344);
        LinearMap.insertLinearProbing(4344);
        QuadraticMap.insertQuadraticProbing(4344);
        DoubleMap.insertDoubleHash(4344);

        //inputting the sixth number into all four hashmaps
        ChainingMap.insertChaining(9679);
        LinearMap.insertLinearProbing(9679);
        QuadraticMap.insertQuadraticProbing(9679);
        DoubleMap.insertDoubleHash(9679);

        //inputting the seventh number into all four hashmaps
        ChainingMap.insertChaining(1989);
        LinearMap.insertLinearProbing(1989);
        QuadraticMap.insertQuadraticProbing(1989);
        DoubleMap.insertDoubleHash(1989);
        
        
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
class hashMap
{
    private int size = 10;
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
        int hashCode = input % 10;
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
        int hashNum = key % 10;
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
        int hashNum = key % 10;
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
        int hashNum = key % 10;
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
        int hashNum = key % 10;
        int hashDouble = 7-(key % 7);
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