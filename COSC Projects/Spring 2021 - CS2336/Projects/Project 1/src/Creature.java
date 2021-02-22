abstract public class Creature
{
    private
    
    char Character = ' ';
    int rowLoco;
    int colLoco;
    int breedCounter=0;
    boolean hasMoved;
    boolean pastCheck=false;

    void resetBreedCounter(){breedCounter= 0;}
    
    public
    
    //setters
    abstract char movement(Character[][] array, Character ant, Character Beetle);
    abstract boolean breedChecker();
    void setCharacter(char x){Character=x;}
    void setRowLoco(int x){rowLoco=x;}
    void setColLoco(int x){colLoco=x;}
    void setHasMoved(){hasMoved=true;}
    void resetHasMoved(){hasMoved=false;}
    void setPastCheck(){pastCheck=true;}
    
    //getters
    void resetPastCheck(){pastCheck=false;}
    boolean getHasMoved(){return hasMoved;}
    Character getCharacter(){return Character;}
    int getRowLoco(){return rowLoco;}
    int getColLoco(){return colLoco;}
}