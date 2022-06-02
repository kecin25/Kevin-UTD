public class Node
{
    private
    Node next;
    String name;
    int hit, out, strikeOut, walk, HBP, sacrifice;
    
    public
    Node()
    {
        next = null;
        name = null;
        hit = 0;
        out = 0;
        strikeOut = 0;
        walk = 0;
        HBP = 0;
        sacrifice = 0;
    }
    //copies the given node to make a deep copy
    Node(Node copy)
    {
        if(copy != null)
        {
            name = copy.getName();
            hit = copy.getHits();
            out = copy.getOut();
            strikeOut = copy.getStrikeOut();
            walk = copy.getWalks();
            HBP = copy.getHBP();
            sacrifice = copy.getSacrifice();
            if (copy.getNext() != null)
            {
                next = new Node(copy.getNext());
            }
            else
            {
                next = null;
            }
        }
    }
    //all the getters to get the needed information
    Node getNext(){return next;}
    String getName(){return name;}
    int getHits(){return hit;}
    int getOut(){return out;}
    int getStrikeOut(){return strikeOut;}
    int getWalks(){return walk;}
    int getHBP(){return HBP;}
    int getSacrifice(){return sacrifice;}
    
    //calculates the batting average instead of holding it to prevent stale data
    double getBA()
    {
        if((strikeOut + out + hit) == 0)
            return 0;
        else
            return (double)hit / (strikeOut + out + hit);
    }
    //calculates the on-base-percentage instead of holding it to prevent stale data
    double getOBP()
    {
        if((hit+out + strikeOut + walk + HBP + sacrifice) == 0)
            return 0;
        else
            return (double)(hit+walk+HBP) / (hit + out + strikeOut + walk + HBP + sacrifice);
    }
    //calculates the at-bat of holding it to prevent stale data
    int getAtBat()
    {
        return hit + out + strikeOut;
    }
    
    //setters to set the stored data
    void setNext      (Node x)   {next = x;}
    void setName      (String x) {name = x;}
    void setHits      (int x)    {hit = x;}
    void setOut       (int x)    {out = x;}
    void setStrikeOut (int x)    {strikeOut = x;}
    void setWalks     (int x)    {walk = x;}
    void setHBP       (int x)    {HBP = x;}
    void setSacrifice (int x)    {sacrifice = x;}
    
    
    //print function used to print all the data stored as well as the batting average and on-base-percentage
    void print(int longest)
    {
        System.out.print(getName());
//        for(int i=head.getName().length();i<longest;i++)
//            System.out.print(" ");
        System.out.print("\t");

        System.out.print(getAtBat() + "\t");
        System.out.print(getHits() + "\t");
        System.out.print(getWalks() + "\t");
        System.out.print(getStrikeOut() + "\t");
        System.out.print(getHBP() + "\t");
        System.out.print(getSacrifice() + "\t");
        System.out.print(String.format("%.3f",getBA()) + "\t");
        System.out.printf("%.3f%n", getOBP());
    }
}
