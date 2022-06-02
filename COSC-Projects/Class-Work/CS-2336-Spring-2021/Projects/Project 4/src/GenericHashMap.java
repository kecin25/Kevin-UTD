public class GenericHashMap<K,S>
{
    private static class Entry<K,S>
    {
        
        private K key;
        private S storage;
        private Entry<K,S> next;
                
        //setters and getters for the private values
        public K getKey(){return key;}
        public void setKey(K x){key = x;}
        public S getStorage(){return storage;}
        public void setStorage(S x){storage = x;}
        public void combine(S x){storage.equals(x);}

        //used for insert function
        public Entry(K x, S y)
        {
            key = x;
            storage = y;
        }
        
        public void setNext(Entry<K,S> x){next = x;}
        public Entry<K,S> getNext(){return next;}
    }
    private final Entry<K, S>[] table;
    //default constructor 
    public GenericHashMap(){ table =  new Entry[size];}
    //sets the default size to 11 but users can change it to what ever size they want
    private int size =11;
    
    //setters and getters used for the private table
    public int getSize(){return size;}
    public void setSize(int x){size = x;}
    
    //insert is used to insert into the hash table. If a spot os already filled then adds the item that needs to be inserted to the end of the list
    public void insert(K key, S storage)
    {
        //hashNum is the key value that is used to find the location needed fot insert the object
        int hashNum = Math.abs(key.hashCode()) % size;
        //temp used to get access to the list at the given location
        Entry<K,S> temp = table[hashNum];
        
        //if temp is empty then insert the current object where null is
        if(temp == null)
            table[hashNum] = new Entry<>(key, storage);
        else
        {
            //if not empty then goes to the end of the list and inserts the object
            while (temp.next != null)
            {
                String tempKey = (String) temp.getKey();
                String curKey = (String) key;
                
                //compares the need to be inserted item with the item that is already inserted
                if(tempKey.compareTo(curKey) == 0)
                {
                    //if they are the same the combine them
                    temp.combine(storage);
                    return;
                }
                temp = temp.getNext();
            }
            //if there is only one spot filled and now other spots after tempt
            String tempKey = (String) temp.getKey();
            String curKey = (String) key;
            //makes sure that the keys are different and if they are the same then combines the two objects
            if(tempKey.compareTo(curKey) == 0)
            {
                temp.combine(storage);
                return;
            }
            //inserts the object behind temp if the keys dont match
            temp.setNext(new Entry<>(key, storage));
        }
    }
    
    //gets what is stored at the given key value
    public S getStorage(K key)
    {
        //hashNum is used to find the location of the object
        int hashNum = Math.abs(key.hashCode()) % size;
        //temp gets access to that location
        Entry<K,S> temp = table[hashNum];
        //checks the list and if it finds the correct object then returns it
        while(temp != null)
        {
            String tempKey = (String) temp.getKey();
            String curKey = (String) key;
            if(tempKey.compareTo(curKey) == 0)
                return temp.getStorage();
            temp = temp.getNext();
        }
        //if not then returns null
        return null;
    }
    
    //deletes a object with the given key
    public Entry<K,S> delete (K key)
    {
        //hashNum is used to find the location of the object
        int hashNum = Math.abs(key.hashCode() % size);
        //temp is used to get access to the list
        Entry<K,S> temp = table[hashNum];
        //checks to see if the keys match with the first item in the list then sets the next item to be the first item
        if(temp.getKey() == key)
        {
            table[hashNum] = temp.getNext();
            temp.setNext(null);
        }
        //if not the first item then keeps track of the current item and the item before it
        Entry<K,S> past = temp;
        temp = temp.getNext();
        //searches the list until it finds the correct item to remove or if not found returns null
        while(temp != null)
        {
            //if it finds the correct item then the item's behind current next is set to current's next
            //and current's next is set to null
            if(temp.getKey() == key)
            {
                past.setNext(temp.getNext());
                temp.setNext(null);
                return temp;
            }
            temp = temp.getNext();
        }
        return null;
    }

    
}