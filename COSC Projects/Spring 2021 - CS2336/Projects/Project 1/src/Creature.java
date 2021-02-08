import java.security.PrivateKey;

abstract public class Creature
{
    private
    
    char Character;
    int rowLoco;
    int colLoco;
    int breedCounter;

    void setBreedCounter(int x){breedCounter=x;}
    
    public
    
    abstract char movement(char array[][]);
    abstract boolean breedChecker(char array[][]);
    void setCharacter(char x){Character=x;}
    void setRowLoco(int x){rowLoco=x;}
    void setColLoco(int x){colLoco=x;}
    char getCharacter(){return Character;}
    int getRowLoco(){return rowLoco;}
    int getColLoco(){return colLoco;}
    int getBreedCounter(){return breedCounter;}
    
}
