import java.util.ArrayList;

public class GenericList <T extends Comparable<T>>
{
    private ArrayList<T> List = new ArrayList<>();
    
    //constructors
    public GenericList()
    {
        List = null;
    }
    public GenericList(ArrayList<T> x)
    {
        List.addAll(x);
    }
    
    //accessors and mutators
    
    public void setList(T x){List.add(x);}
    
    public ArrayList<T> getList(){return List;}
    
    public int compare(T x, T y){ return x.compareTo(y);}
    
    public void InsertionSort()
    {
        int loco;
        for(int i=0; i < List.size(); i++)
        {
            T temp = List.get(i);
            loco = i;
            while(loco > 0 && compare(List.get(loco-1),temp) > 0)
            {
                List.set(loco,List.get(loco-1));
                loco--;
            }
            List.set(loco,temp);
        }
    }
    
    public boolean BinarySearch(T temp)
    {
        int left = 0;
        int right = List.size()-1;
        
        //looks to see if the middle matches what we are looking for and if not, it sees if its smaller or larger then
        //what we are looking for, if larger then the right moves to the left of where middle was
        // and if its smaller the left moves to the right of where middle was
        
        while(left <= right)
        {
            int middle = (left+right) / 2;
            
            if(compare(List.get(middle),temp) == 0)
                return true;
            else if (compare(List.get(middle),temp) > 0)
                right = middle - 1;
            else
                left = middle + 1;
        }
        return false;
    }
    
    public String toString()
    {
        String temp = "";
        for (T t : List)
            temp = temp + t + " ";
        return temp;
    }
    
}
