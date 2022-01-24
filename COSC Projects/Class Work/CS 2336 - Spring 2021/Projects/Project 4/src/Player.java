public class Player
{
    private String name;
    private int hit;
    private int out;
    private int strikeout;
    private int walk;
    private int HBP;
    private int sacrifice;
    private int error;

    //default constructor
    public Player()
    {
        name = null;
        hit = 0;
        out = 0;
        strikeout = 0;
        walk = 0;
        HBP = 0;
        sacrifice = 0;
        error = 0;
    }
    //all the setters to set the needed information
    void setName      (String x) {name = x;}
    void setHits      (int x)    {hit = x;}
    void setOut       (int x)    {out = x;}
    void setStrikeout (int x)    {strikeout = x;}
    void setWalks     (int x)    {walk = x;}
    void setHBP       (int x)    {HBP = x;}
    void setSacrifice (int x)    {sacrifice = x;}
    void setError     (int x)    {error = x;}
    
    //all the getters to get the needed information
    String getName(){return name;}
    int getHits(){return hit;}
    int getOut(){return out;}
    int getStrikeOut(){return strikeout;}
    int getWalks(){return walk;}
    int getHBP(){return HBP;}
    int getSacrifice(){return sacrifice;}
    int getError(){return error;}

    //calculates the batting average instead of holding it to prevent stale data
    double getBA()
    {
        if((strikeout + out + hit) == 0)
            return 0;
        else
            return (double)hit / getAtBat();
    }
    //calculates the on-base-percentage instead of holding it to prevent stale data
    double getOBP()
    {
        if((hit+out + strikeout + walk + HBP + sacrifice) == 0)
            return 0;
        else
            return (double)(hit+walk+HBP) / (hit + out + strikeout + walk + HBP + sacrifice);
    }
    //calculates the at-bat of holding it to prevent stale data
    int getAtBat()
    { return hit + out + strikeout + error; }

    //toString is used to get the out put of the player class as a string instead of going strait to the console
    @Override
    public String toString()
    {
        String value = "";
        value += getName();
        value += "\t";
        value += getAtBat() + "\t";
        value += getHits() + "\t";
        value += getWalks() + "\t";
        value += getStrikeOut() + "\t";
        value += getHBP() + "\t";
        value += getSacrifice() + "\t";
        value += String.format("%.3f",getBA()) + "\t";
        value += String.format("%.3f%n", getOBP());
        
        return value;
    }
    //combine is used to combine one stats of a given player with the current player
    public void combine(Player x)
    {
        hit += x.getHits();
        out += x.getOut();
        strikeout += x.getStrikeOut();
        walk += x.getWalks();
        HBP += x.getHBP();
        sacrifice += x.getSacrifice();
        error += x.getError();
    }
}
